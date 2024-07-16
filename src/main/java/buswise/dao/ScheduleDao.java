package buswise.dao;

import buswise.model.Routes;
import buswise.model.Schedules;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;

    @Transactional
    public void scheduleSave(Schedules schedules) {
        template.save(schedules);
    }

    @Transactional
    public void scheduleUpdate(Schedules schedules) {
        template.update(schedules);
    }

    public boolean checkBus(int busId, LocalDate date, LocalTime time)
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.scheduleId from buswise.model.Schedules b where b.busId.busId=:busId and isDeleted=false and b.tripDate=:date and b.arrivalTime=:time";
        Query query = session.createQuery(hqlString);
        query.setParameter("busId", busId);
        query.setParameter("date", date);
        query.setParameter("time", time);
        List<Integer> list = query.list();
        if(list.size()>0){
            boolean check = false;
            return check;
        }
        else {
            boolean check = true;
            return check;
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
                "and b.routeId.source=:source " +
                "and b.routeId.destination=:destination " +
                "and DATE_FORMAT(b.tripDate, '%Y-%m-%d')=:tripDateString or (b.tripDate = CURRENT_DATE and b.arrivalTime > CURRENT_TIME)";
        Query query = session.createQuery(hqlString);
        query.setParameter("source", source);
        query.setParameter("destination", destination);
        query.setParameter("tripDateString", tripDateString);
        List<Schedules> results = query.list();
        session.close();
        return results;
    }


    public List<Schedules> getSchedules1(String source, String destination, String tripDateString, int startIndex, int endIndex) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Schedules> criteriaQuery = builder.createQuery(Schedules.class);
            Root<Schedules> root = criteriaQuery.from(Schedules.class);
            criteriaQuery.where(
                    builder.and(
                            builder.equal(root.get("isDeleted"), false),
                            builder.equal(root.get("routeId").get("source"), source),
                            builder.equal(root.get("routeId").get("destination"), destination),
                            builder.equal(builder.function("DATE_FORMAT", String.class, root.get("tripDate"), builder.literal("%Y-%m-%d")), tripDateString)
                    )
            );

            Query<Schedules> query = session.createQuery(criteriaQuery);
            query.setFirstResult(startIndex);
            query.setMaxResults(endIndex); // Max results is exclusive, so adjust accordingly
            List<Schedules> results = query.getResultList();
            return results;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

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
                    "AND src.stop = :source " +
                    "AND dest.stop = :destination " +
                    "AND s.tripDate = :tripDateString " +
                    "AND src.sequence < dest.sequence or (s.tripDate = CURRENT_DATE and s.arrivalTime > CURRENT_TIME)";

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
                    "AND src.stop = :source " +
                    "AND r.destination = :destination " +
                    "AND s.tripDate = :tripDateString or (s.tripDate = CURRENT_DATE and s.arrivalTime > CURRENT_TIME)";

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
                    "AND r.source = :source " +
                    "AND dest.stop = :destination " +
                    "AND s.tripDate = :tripDateString or (s.tripDate = CURRENT_DATE and s.arrivalTime > CURRENT_TIME)" ;

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


    public List<String> seatsBooked(int scheduleId)
    {
        Session session = sessionFactory.openSession();
        String hql =  "SELECT bd.seatNumber " +
                "FROM BookingDetails bd " +
                "JOIN bd.booking_id b " +
                "JOIN b.scheduleId s " +
                "WHERE s.scheduleId = :scheduleId AND b.isCancelled = false AND bd.isCancelled = false ";

        Query query = session.createQuery(hql);
        query.setParameter("scheduleId", scheduleId);
        List<String> list = query.list();
        return list;

    }

    public List<Schedules> getScheduleAll()
    {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Schedules b where b.isDeleted=false ";
        Query query = session.createQuery(hqlString);
        List<Schedules> list = query.list();
        return list;

    }

    public List<Schedules> getScheduleByUserID(int userID) {
        Session session = sessionFactory.openSession();
        String hqlString = "select b.scheduleId from buswise.model.Bookings b where b.isCancelled=false and b.userId.id=:userID";
        Query<Schedules> query = session.createQuery(hqlString, Schedules.class);
        query.setParameter("userID", userID);
        List<Schedules> list = query.list();
        session.close();
        return list;
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

}



