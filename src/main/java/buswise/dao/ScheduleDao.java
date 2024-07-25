package buswise.dao;

import buswise.model.Bookings;
import buswise.model.Routes;
import buswise.model.Schedules;
import buswise.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ScheduleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;


    @PersistenceContext

    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public void scheduleSave(Schedules schedules) {
        template.save(schedules);
    }

    @Transactional
    public void scheduleUpdate(Schedules schedules) {
        template.update(schedules);
    }

//    public boolean checkBus(int busId, LocalDate date, LocalTime time)
//    {
//        Session session = sessionFactory.openSession();
//        String hqlString = " select b.scheduleId from buswise.model.Schedules b where b.busId.busId=:busId and isDeleted=false and b.tripDate=:date and b.arrivalTime=:time";
//        Query query = session.createQuery(hqlString);
//        query.setParameter("busId", busId);
//        query.setParameter("date", date);
//        query.setParameter("time", time);
//        List<Integer> list = query.list();
//        if(list.size()>0){
//            boolean check = false;
//            return check;
//        }
//        else {
//            boolean check = true;
//            return check;
//        }
//
//    }

    public boolean checkBus(int busId, LocalDate date, LocalTime time) {
        try (Session session = sessionFactory.openSession()) {
            // Define the time window for 6 hours before and after the given time
            LocalTime sixHoursBefore = time.minus(6, ChronoUnit.HOURS);
            LocalTime sixHoursAfter = time.plus(6, ChronoUnit.HOURS);

            String hqlString = "SELECT b.scheduleId FROM buswise.model.Schedules b " +
                    "WHERE b.busId.busId = :busId AND b.isDeleted = false AND b.tripDate = :date " +
                    "AND (b.arrivalTime BETWEEN :sixHoursBefore AND :sixHoursAfter)";

            Query<Integer> query = session.createQuery(hqlString, Integer.class);
            query.setParameter("busId", busId);
            query.setParameter("date", date);
            query.setParameter("sixHoursBefore", sixHoursBefore);
            query.setParameter("sixHoursAfter", sixHoursAfter);

            List<Integer> list = query.list();
            return list.isEmpty(); // return true if no conflicting schedules are found
        }
    }




    public List<Schedules> getRoutesSearch(String region, int startIndex, int endIndex, String sort) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Schedules> cr = cb.createQuery(Schedules.class);
        Root<Schedules> root = cr.from(Schedules.class);
        if (region.equals("empty")) {
            cr.select(root)
                    .where(cb.equal(root.get("isDeleted"), 0));
        } else {
            Predicate isDeleted = cb.like(root.get("routeId").get("source"), "%" + region + "%");
            Predicate source = cb.equal(root.get("isDeleted"), 0);
            Predicate destination = cb.like(root.get("routeId").get("destination"), "%" + region + "%");
            cr.select(root)
                    .where(cb.and(source, cb.or(isDeleted, destination)));
        }

        if (sort != null) {
            switch (sort) {
                case "asc":
                    cr.orderBy(cb.asc(root.get("tripDate")));
                    break;
                case "desc":
                    cr.orderBy(cb.desc(root.get("tripDate")));
                    break;
                default:

                    break;
            }
        } else {
            cr.orderBy(cb.desc(root.get("tripDate")));
        }
        Query<Schedules> query = session.createQuery(cr);
        query.setFirstResult(startIndex);
        query.setMaxResults(endIndex);
        List<Schedules> list = query.getResultList();
        return list;
    }

    public long getRoutesSearchCount(String region) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<Schedules> root = cr.from(Schedules.class);
        if (region.equals("empty")) {
            cr.select(cb.count(root))
                    .where(cb.equal(root.get("isDeleted"), 0));
        } else {
            Predicate isDeleted = cb.like(root.get("routeId").get("source"), "%" + region + "%");
            Predicate source = cb.equal(root.get("isDeleted"), 0);
            Predicate destination = cb.like(root.get("routeId").get("destination"), "%" + region + "%");
            cr.select(cb.count(root))
                    .where(cb.and(source, cb.or(isDeleted, destination)));
        }

        Query<Long> query = session.createQuery(cr);
        Long count = query.getSingleResult();
        session.close();
        return count;
    }




    public List<String> getSchedule()
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.scheduleId from buswise.model.Schedules b where b.isDeleted=false ";
        Query query = session.createQuery(hqlString);
        List<String> list = query.list();
        return list;


    }

    public List<Schedules> getScheduleByID(int id)
    {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Schedules b where b.isDeleted=false and b.scheduleId=:id ";
        Query query = session.createQuery(hqlString);
        query.setParameter("id", id);
        List<Schedules> list = query.list();
        return list;


    }
    public List<Schedules> getSchedules(String source, String destination, String tripDateString) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Schedules b " +
                "where b.isDeleted=false " +
                "and upper(b.routeId.source) =:source " +
                "and upper(b.routeId.destination) =:destination " +
                "and DATE_FORMAT(b.tripDate, '%Y-%m-%d')=:tripDateString ";
        Query query = session.createQuery(hqlString);
        query.setParameter("source", source);
        query.setParameter("destination", destination);
        query.setParameter("tripDateString", tripDateString);
        List<Schedules> results = query.list();
        session.close();
        return results;
    }









//    public List<Schedules> getSchedules1(String source, String destination, String tripDateString, int startIndex, int endIndex) {
//        Session session = null;
//        try {
//            session = sessionFactory.openSession();
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Schedules> criteriaQuery = builder.createQuery(Schedules.class);
//            Root<Schedules> root = criteriaQuery.from(Schedules.class);
//            criteriaQuery.where(
//                    builder.and(
//                            builder.equal(root.get("isDeleted"), false),
//                            builder.equal(root.get("routeId").get("source"), source),
//                            builder.equal(root.get("routeId").get("destination"), destination),
//                            builder.equal(builder.function("DATE_FORMAT", String.class, root.get("tripDate"), builder.literal("%Y-%m-%d")), tripDateString)
//                    )
//            );
//
//            Query<Schedules> query = session.createQuery(criteriaQuery);
//            query.setFirstResult(startIndex);
//            query.setMaxResults(endIndex); // Max results is exclusive, so adjust accordingly
//            List<Schedules> results = query.getResultList();
//            return results;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }

    // subroute to subroute
    public List<Schedules> getSchedulesBySubroute(String source, String destination, String tripDateString) {
        Session session = null;
        List<Schedules> results = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT s FROM Schedules s " +
                    "JOIN Routes r ON s.routeId.routeId = r.routeId " +
                    "JOIN SubRoute src ON src.routeId.routeId = r.routeId " +
                    "JOIN SubRoute dest ON dest.routeId.routeId = r.routeId " +
                    "WHERE s.isDeleted = false " +
                    "AND r.isDeleted = false " +
                    "AND src.isDeleted = false " +
                    "AND dest.isDeleted = false " +
                    "AND upper(src.stop) = :source " +
                    "AND upper(dest.stop) = :destination " +
                    "AND s.tripDate = :tripDateString " +
                    "AND src.sequence < dest.sequence";

            Query<Schedules> query = session.createQuery(hql, Schedules.class);
            query.setParameter("source", source);
            query.setParameter("destination", destination);
            query.setParameter("tripDateString", LocalDate.parse(tripDateString));
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }


    //subroute to destination
    public List<Schedules> getSchedulesBySubrouteRoute(String source, String destination, String tripDateString) {
        Session session = null;
        List<Schedules> results = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT s FROM Schedules s " +
                    "JOIN Routes r ON s.routeId.routeId = r.routeId " +
                    "JOIN SubRoute src ON src.routeId.routeId = r.routeId " +
                    "WHERE s.isDeleted = false " +
                    "AND r.isDeleted = false " +
                    "AND src.isDeleted = false " +
                    "AND upper(src.stop) = :source " +
                    "AND upper(r.destination) = :destination " +
                    "AND s.tripDate = :tripDateString ";

            Query<Schedules> query = session.createQuery(hql, Schedules.class);
            query.setParameter("source", source);
            query.setParameter("destination", destination);
            query.setParameter("tripDateString", LocalDate.parse(tripDateString));
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }

    public List<Schedules> getSourceToSubroute(String source, String destination, String tripDateString) {
        Session session = null;
        List<Schedules> results = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT s FROM Schedules s " +
                    "JOIN Routes r ON s.routeId.routeId = r.routeId " +
                    "JOIN SubRoute dest ON dest.routeId.routeId = r.routeId " +
                    "WHERE s.isDeleted = false " +
                    "AND r.isDeleted = false " +
                    "AND dest.isDeleted = false " +
                    "AND upper(r.source) = :source " +
                    "AND upper(dest.stop) = :destination " +
                    "AND s.tripDate = :tripDateString" ;

            Query<Schedules> query = session.createQuery(hql, Schedules.class);
            query.setParameter("source", source);
            query.setParameter("destination", destination);
            query.setParameter("tripDateString", LocalDate.parse(tripDateString));
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }


    public Integer getSubrouteDistance(int routeId, String stop) {
        Session session = null;
        Integer distance = null;
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT sr.distance FROM SubRoute sr " +
                    "WHERE sr.routeId.routeId = :routeId " +
                    "AND sr.stop = :stop " +
                    "AND sr.isDeleted = false";

            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("routeId", routeId);
            query.setParameter("stop", stop);
            distance = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return distance;
    }










//    public List<Schedules> getScheduleAll()
////    {
////        Session session = sessionFactory.openSession();
////        String hqlString = "from buswise.model.Schedules b where b.isDeleted=false AND b.tripDate= CURRENT_DATE AND b.arrivalTime<=CURRENT_TIME";
////        Query query = session.createQuery(hqlString);
////        List<Schedules> list = query.list();
////        return list;
////
////    }

    public List<Schedules> getScheduleAll() {
        Session session = sessionFactory.openSession();


        double speedMetersPerSec = 50.0;

        // Get the list of all schedules
        String hqlString = "from buswise.model.Schedules b " +
                "where b.isDeleted = false " +
                "and b.tripDate = CURRENT_DATE " +
                "and b.arrivalTime <= CURRENT_TIME";

        Query<Schedules> query = session.createQuery(hqlString, Schedules.class);
        List<Schedules> schedules = query.list();


        List<Schedules> filteredSchedules = schedules.stream()
                .filter(schedule -> {
                    Integer distance = schedule.getRouteId().getDistance();
                    Integer distanceInMeters = distance*1000;
                    double durationInSeconds = distanceInMeters / speedMetersPerSec;

                    // Calculate the time difference in seconds between arrivalTime and CURRENT_TIME
                    LocalTime arrivalTime = schedule.getArrivalTime();
                    LocalTime currentTime = LocalTime.now();
                    long timeDifferenceInSeconds = Duration.between(arrivalTime, currentTime).getSeconds();

                    return timeDifferenceInSeconds < durationInSeconds;
                })
                .collect(Collectors.toList());

        session.close();

        return filteredSchedules;
    }


    public List<Schedules> getScheduleByUserID(int userID) {
        double speedMetersPerSec = 50.0;

        Session session = sessionFactory.openSession();
        String hqlString = "select distinct b.scheduleId from buswise.model.Bookings b where b.isCancelled=false and b.userId.id=:userID AND b.scheduleId.tripDate= CURRENT_DATE AND b.scheduleId.arrivalTime<=CURRENT_TIME";
        Query<Schedules> query = session.createQuery(hqlString, Schedules.class);
        query.setParameter("userID", userID);
        List<Schedules> schedules = query.list();
        List<Schedules> filteredSchedules = schedules.stream()
                .filter(schedule -> {
                    Integer distance = schedule.getRouteId().getDistance();
                    Integer distanceInMeters = distance*1000;
                    double durationInSeconds = distanceInMeters / speedMetersPerSec;

                    LocalTime arrivalTime = schedule.getArrivalTime();
                    LocalTime currentTime = LocalTime.now();
                    long timeDifferenceInSeconds = Duration.between(arrivalTime, currentTime).getSeconds();

                    return timeDifferenceInSeconds < durationInSeconds;
                })
                .collect(Collectors.toList());

        session.close();

        return filteredSchedules;
    }

    public List<Schedules> findByRouteId(int routeId){
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Schedules s where s.isDeleted=false and s.routeId.routeId=:routeId and s.tripDate>=CURRENT_DATE";
        Query<Schedules> query = session.createQuery(hqlString, Schedules.class);
        query.setParameter("routeId", routeId);
        List<Schedules> list = query.list();
        session.close();
        return list;
    }

    public List<Schedules> findByBusId(int busId){
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Schedules s where s.isDeleted=false and s.busId.busId=:busId AND s.tripDate>=CURRENT_DATE";
        Query<Schedules> query = session.createQuery(hqlString, Schedules.class);
        query.setParameter("busId", busId);
        List<Schedules> list = query.list();
        session.close();
        return list;
    }

    public List<User> getUsersByScheduleId(int scheduleId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<Bookings> root = cq.from(Bookings.class);
        cq.select(root.get("userId"))
                .where(cb.equal(root.get("scheduleId").get("scheduleId"), scheduleId));

        return entityManager.createQuery(cq).getResultList();
    }







    public Map<String, String> getSourceAndDestinationByRouteId(int routeId) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT r.source, r.destination " +
                "FROM Routes r " +
                "WHERE r.routeId = :routeId";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("routeId", routeId);
        Object[] result = query.uniqueResult();
        session.close();

        if (result != null) {
            String source = (String) result[0];
            String destination = (String) result[1];
            Map<String, String> sourceAndDestination = new HashMap<>();
            sourceAndDestination.put("source", source);
            sourceAndDestination.put("destination", destination);
            return sourceAndDestination;
        } else {
            return Collections.emptyMap();
        }
    }

    public List<String> getSubRoutesByRouteId(int routeId) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT sr.stop " +
                "FROM SubRoute sr " +
                "WHERE sr.routeId.routeId = :routeId " +
                "ORDER BY sr.sequence";

        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter("routeId", routeId);
        List<String> subRoutes = query.getResultList();
        session.close();
        return subRoutes;
    }

    public List<String> getRouteStops(int routeId) {
        Map<String, String> sourceAndDestination = getSourceAndDestinationByRouteId(routeId);
        String source = sourceAndDestination.get("source");
        String destination = sourceAndDestination.get("destination");

        List<String> subRoutes = getSubRoutesByRouteId(routeId);
        List<String> stops = new ArrayList<>();
        if (source != null) {
            stops.add(source);
        }
        stops.addAll(subRoutes);
        if (destination != null) {
            stops.add(destination);
        }
        stops = stops.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        return stops;
    }


    public List<String> getBookedSeats(Integer scheduleId, String selectedSource, String selectedDestination) {

        int routeId = getRouteIdByScheduleId(scheduleId);


        List<String> routeStops = getRouteStops(routeId);


        List<Object[]> bookedSeatsDetails = seatsBookedDetails(scheduleId);


        List<String> bookedSeats = new ArrayList<>();


        int sourceIndex = routeStops.indexOf(selectedSource);
        int destinationIndex = routeStops.indexOf(selectedDestination);

        for (Object[] detail : bookedSeatsDetails) {
            String seatNumber = (String) detail[0];
            String bookedSource = (String) detail[1];
            String bookedDestination = (String) detail[2];

            int bookedSourceIndex = routeStops.indexOf(bookedSource);
            int bookedDestinationIndex = routeStops.indexOf(bookedDestination);


            if (!(destinationIndex <= bookedSourceIndex || sourceIndex >= bookedDestinationIndex)) {
                bookedSeats.add(seatNumber);
            }
        }

        return bookedSeats;
    }




    private int getRouteIdByScheduleId(int scheduleId) {
        Session session = sessionFactory.openSession();
        int routeId = 0;
        try {
            String hqlString = "SELECT s.routeId.routeId FROM Schedules s WHERE s.scheduleId = :scheduleId";
            Query<Integer> query = session.createQuery(hqlString, Integer.class);
            query.setParameter("scheduleId", scheduleId);
            routeId = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return routeId;
    }









    public List<Object[]> seatsBookedDetails(Integer scheduleId)
    {


        Session session = sessionFactory.openSession();
//        String hql = "SELECT bd.seatNumber, b.selectedSource, b.selectedDestination " +
//            "FROM BookingDetails bd " +
//            "JOIN bd.booking_id b " +
//            "WHERE b.scheduleId = :scheduleId " +
//            "AND b.isCancelled = false " +
//            "AND bd.isCancelled = false "
//        +"AND b.isBooked = true ";

        String hql = "select b.seatNumber, b.booking_id.selectedSource, b.booking_id.selectedDestination  from BookingDetails b where b.booking_id.scheduleId.scheduleId = :scheduleId and b.booking_id.isBooked= true and b.isCancelled=false and b.booking_id.isCancelled= false";

        Query<Object[]> query = session.createQuery(hql);
        query.setParameter("scheduleId", scheduleId);
        List<Object[]> list = query.getResultList();
        return list;

    }







}







