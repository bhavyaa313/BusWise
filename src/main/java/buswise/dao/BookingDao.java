package buswise.dao;

import buswise.dto.OccupancyReportDto;
import buswise.dto.SalesData;
import buswise.dto.TicketDto;
import buswise.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;


import javax.crypto.spec.PSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookingDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;

    @Autowired
    private RoutesDao routesDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void bookingSave(Bookings bookings) {
        template.save(bookings);
    }

    @Transactional
    public void bookingUpdate(Bookings bookings) {
        template.update(bookings);
    }

    @Transactional
    public void bookingDetailsSave(BookingDetails bookingDetails) {
        template.save(bookingDetails);
    }

    @Transactional
    public void bookingDetailsUpdate(BookingDetails bookingDetails) {
        template.update(bookingDetails);
    }

    public List<Bookings> getBooking() {

        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Bookings where isCancelled=false and isBooked=true ";
        Query query = session.createQuery(hqlString);
        List<Bookings> list = query.list();
        return list;
    }

    public List<Bookings> getBookingById(int bookingId) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Bookings where bookingId=:bookingId and isCancelled=false and isBooked = true  ";
        Query query = session.createQuery(hqlString);
        query.setParameter("bookingId", bookingId);
        List<Bookings> list = query.list();
        return list;

    }

    public List<Bookings> getBookingByUserId(int userId, int startIndex, int endIndex, int valueOfSelectedFilter) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Bookings> cr = cb.createQuery(Bookings.class);
        Root<Bookings> root = cr.from(Bookings.class);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now().withSecond(0).withNano(0); // Get current time with hours and minutes only
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("userId"), userId));
        if (valueOfSelectedFilter == 2) {
            Predicate futureDatePredicate = cb.greaterThanOrEqualTo(root.get("scheduleId").get("tripDate"), today);
            Predicate futureTimePredicate = cb.greaterThanOrEqualTo(root.get("scheduleId").get("arrivalTime"), now);

            Predicate futureBookingPredicate = cb.or(
                    cb.greaterThan(root.get("scheduleId").get("tripDate"), today),
                    cb.and(
                            cb.equal(root.get("scheduleId").get("tripDate"), today),
                            futureTimePredicate
                    )
            );
            predicates.add(futureBookingPredicate);
            cr.orderBy(cb.desc(root.get("scheduleId").get("tripDate")));
            predicates.add(cb.equal(root.get("isCancelled"), false));
            predicates.add(cb.equal(root.get("isBooked"), true));

        } else if (valueOfSelectedFilter == 3) {
            Predicate pastDatePredicate = cb.lessThan(root.get("scheduleId").get("tripDate"), today);
            Predicate pastTimePredicate = cb.lessThan(root.get("scheduleId").get("arrivalTime"), now);

            Predicate pastBookingPredicate = cb.or(
                    pastDatePredicate,
                    cb.and(
                            cb.equal(root.get("scheduleId").get("tripDate"), today),
                            pastTimePredicate
                    )
            );

            predicates.add(pastBookingPredicate);
            cr.orderBy(cb.desc(root.get("scheduleId").get("tripDate")));
            predicates.add(cb.equal(root.get("isCancelled"), false));
            predicates.add(cb.equal(root.get("isBooked"), true));
        } else {
            predicates.add(cb.equal(root.get("isCancelled"), false));
            predicates.add(cb.equal(root.get("isBooked"), true));
            cr.orderBy(cb.desc(root.get("scheduleId").get("tripDate")));
        }
        cr.select(root).where(predicates.toArray(new Predicate[0]));
        Query<Bookings> query = session.createQuery(cr);
        query.setFirstResult(startIndex);
        query.setMaxResults(endIndex);
        List<Bookings> list = query.getResultList();

        session.close();
        return list;
    }


    public int getBookingCountByUserId(int userId, int valueOfSelectedFilter) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<Bookings> root = cr.from(Bookings.class);
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("userId"), userId));
        if (valueOfSelectedFilter == 2) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("scheduleId").get("tripDate"), today));
            predicates.add(cb.greaterThanOrEqualTo(root.get("scheduleId").get("arrivalTime"), time));
            predicates.add(cb.equal(root.get("isCancelled"), false));
            predicates.add(cb.equal(root.get("isBooked"), true));
        } else if (valueOfSelectedFilter == 3) {
            predicates.add(cb.lessThan(root.get("scheduleId").get("tripDate"), today));  //orequalto
            predicates.add(cb.equal(root.get("isCancelled"), false));
            predicates.add(cb.equal(root.get("isBooked"), true));
//            predicates.add(cb.lessThan(root.get("scheduleId").get("arrivalTime"), time));
        } else {
            predicates.add(cb.equal(root.get("isCancelled"), false));
            predicates.add(cb.equal(root.get("isBooked"), true));

        }
        cr.select(cb.count(root)).where(predicates.toArray(new Predicate[0]));
        Query<Long> query = session.createQuery(cr);
        Long count = query.getSingleResult();
        session.close();
        return count.intValue();
    }




    public List<String> getNamesByBookingId(int bookingId) {
        Session session = sessionFactory.openSession();
        String hqlString = "SELECT bd.name FROM BookingDetails bd WHERE bd.booking_id.bookingId = :bookingId and isCancelled=false and bd.booking_id.isBooked = true";
        Query<String> query = session.createQuery(hqlString, String.class);
        query.setParameter("bookingId", bookingId);
        List<String> names = query.list();
        session.close();
        return names;
    }

    public List<String> getGenderByBookingId(int bookingId) {
        Session session = sessionFactory.openSession();
        String hqlString = "SELECT bd.gender FROM BookingDetails bd WHERE bd.booking_id.bookingId = :bookingId and isCancelled=false and bd.booking_id.isBooked = true";
        Query<String> query = session.createQuery(hqlString, String.class);
        query.setParameter("bookingId", bookingId);
        List<String> gender = query.list();
        session.close();
        return gender;
    }


    public List<String> getSeatsByBookingId(int bookingId) {
        Session session = sessionFactory.openSession();
        String hqlString = "SELECT bd.seatNumber FROM BookingDetails bd WHERE bd.booking_id.bookingId = :bookingId and isCancelled=false and bd.booking_id.isBooked = true";
        Query<String> query = session.createQuery(hqlString, String.class);
        query.setParameter("bookingId", bookingId);
        List<String> seats = query.list();
        session.close();
        return seats;
    }

    public List<String> getAgeByBookingId(int bookingId) {
        Session session = sessionFactory.openSession();
        String hqlString = "SELECT bd.age FROM BookingDetails bd WHERE bd.booking_id.bookingId = :bookingId and isCancelled=false and bd.booking_id.isBooked = true";
        Query<String> query = session.createQuery(hqlString, String.class);
        query.setParameter("bookingId", bookingId);
        List<String> age = query.list();
        session.close();
        return age;
    }


    public List<BookingDetails> getBookingDetailsById(int bookingId) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.BookingDetails where booking_id.bookingId=:bookingId and isCancelled=false";
        Query query = session.createQuery(hqlString);
        query.setParameter("bookingId", bookingId);
        List<BookingDetails> list = query.list();
        return list;

    }

    public List<BookingDetails> getBookingDetailsByBookingDetailId(int bookingDetailId) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.BookingDetails where id=:bookingDetailId and isCancelled=false ";
        Query query = session.createQuery(hqlString);
        query.setParameter("bookingDetailId", bookingDetailId);
        List<BookingDetails> list = query.list();
        return list;

    }

    public SalesData getDailySales(LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bookings> cq = cb.createQuery(Bookings.class);
        Root<Bookings> root = cq.from(Bookings.class);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        Predicate datePredicate = cb.between(root.get("bookingDate"), startOfDay, endOfDay);
        Predicate isCancelledPredicate = cb.equal(root.get("isCancelled"), false);
        Predicate isBookedPredicate = cb.equal(root.get("isBooked"), true);
        cq.where(cb.and(datePredicate, isCancelledPredicate, isBookedPredicate));
        List<Bookings> bookings = entityManager.createQuery(cq).getResultList();
        SalesData salesData = processSalesData(bookings);
        return salesData;
    }

    public List<Bookings> getDailySalesWhole(LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bookings> cq = cb.createQuery(Bookings.class);
        Root<Bookings> root = cq.from(Bookings.class);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        Predicate datePredicate = cb.between(root.get("bookingDate"), startOfDay, endOfDay);
        Predicate isCancelledPredicate = cb.equal(root.get("isCancelled"), false);
        Predicate isBookedPredicate = cb.equal(root.get("isBooked"), true);
        cq.where(cb.and(datePredicate, isCancelledPredicate, isBookedPredicate));
        List<Bookings> bookings = entityManager.createQuery(cq).getResultList();
        return bookings;
    }


    private SalesData processSalesData(List<Bookings> bookings) {
        double totalSales = 0.0;
        for (Bookings booking : bookings) {
            totalSales += booking.getTotalAmount();
        }
        SalesData salesData = new SalesData();
        salesData.setTotalSales(totalSales);
        salesData.setNumberOfBookings(bookings.size());
        return salesData;
    }


    public List<Bookings> getMonthlyBookings(LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bookings> cq = cb.createQuery(Bookings.class);
        Root<Bookings> root = cq.from(Bookings.class);
        LocalDateTime startOfMonth = date.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = date.withDayOfMonth(date.lengthOfMonth()).atTime(23, 59, 59);
        Predicate datePredicate = cb.between(root.get("bookingDate"), startOfMonth, endOfMonth);
        Predicate isCancelledPredicate = cb.equal(root.get("isCancelled"), false);
        Predicate isBookedPredicate = cb.equal(root.get("isBooked"), true);
        cq.where(cb.and(datePredicate, isCancelledPredicate, isBookedPredicate));
        List<Bookings> bookings = entityManager.createQuery(cq).getResultList();
        return bookings;
    }

    public List<Bookings> getYearlyBookings(int year) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bookings> cq = cb.createQuery(Bookings.class);
        Root<Bookings> root = cq.from(Bookings.class);
        LocalDateTime startOfYear = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endOfYear = LocalDate.of(year, 12, 31).atTime(23, 59, 59);
        Predicate datePredicate = cb.between(root.get("bookingDate"), startOfYear, endOfYear);
        Predicate isCancelledPredicate = cb.equal(root.get("isCancelled"), false);
        Predicate isBookedPredicate = cb.equal(root.get("isBooked"), true);
        cq.where(cb.and(datePredicate, isCancelledPredicate, isBookedPredicate));
        List<Bookings> bookings = entityManager.createQuery(cq).getResultList();
        return bookings;
    }


    public List<OccupancyReportDto> getDailyOccupancyData(LocalDate date) {
        EntityManager em = entityManager;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bookings> cq = cb.createQuery(Bookings.class);
        Root<Bookings> bookingRoot = cq.from(Bookings.class);
        bookingRoot.fetch("scheduleId", JoinType.LEFT);
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = date.atTime(23, 59, 59).toLocalDate();
        Predicate datePredicate = cb.between(bookingRoot.join("scheduleId").get("tripDate"), startOfDay, endOfDay);
        Predicate isCancelledPredicate = cb.equal(bookingRoot.get("isCancelled"), false);
        Predicate isBookedPredicate = cb.equal(bookingRoot.get("isBooked"), true);
        cq.where(cb.and(datePredicate, isCancelledPredicate, isBookedPredicate));

        List<Bookings> bookings = em.createQuery(cq).getResultList();
        Map<Integer, OccupancyReportDto> reportDataMap = new HashMap<>();
        for (Bookings booking : bookings) {
            Schedules schedule = booking.getScheduleId();
            if (schedule != null) {
                Integer scheduleId = schedule.getScheduleId();

                OccupancyReportDto reportData = reportDataMap.getOrDefault(scheduleId, new OccupancyReportDto());
                reportData.setScheduleId(scheduleId);
                reportData.setSelectedSource(booking != null ? booking.getSelectedSource() : null);
                reportData.setSelectedDestination(booking != null ? booking.getSelectedDestination() : null);
                reportData.setDepartureTime(booking != null ? booking.getDepatureTime() : null);
                reportData.setTotalSeats(schedule.getBusId().getSeatingCapacity());
                reportData.setRouteId(schedule.getRouteId().getRouteId());
                reportData.setRoute(schedule.getRouteId().getSource() + " to " + schedule.getRouteId().getDestination());
                reportData.setSeatsOccupied(schedule.getBusId().getSeatingCapacity());
                List<BookingDetails> bookingDetails = getBookingDetailsById(booking != null ? booking.getBookingId() : null);

                if (bookingDetails != null) {
                    List<BookingDetails> canceledBookings = bookingDetails.stream()
                            .filter(bookingDetail -> bookingDetail.isCancelled())
                            .collect(Collectors.toList());
                    int canceledSeats = canceledBookings.size();
                    reportData.setSeatsCancelled(canceledSeats);
                }

                reportData.setSeatsOccupied(schedule.getBusId().getSeatingCapacity()-schedule.getAvailableSeats()+reportData.getSeatsCancelled());
                reportData.setOccupancyPercentage((double) reportData.getSeatsOccupied() / schedule.getBusId().getSeatingCapacity() * 100);
                reportDataMap.put(scheduleId, reportData);
            }
        }

        return new ArrayList<>(reportDataMap.values());
    }

    public List<OccupancyReportDto> getMonthlyOccupancyData(int year, int month) {
        EntityManager em = entityManager;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bookings> cq = cb.createQuery(Bookings.class);
        Root<Bookings> bookingRoot = cq.from(Bookings.class);
        bookingRoot.fetch("scheduleId", JoinType.LEFT);
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        Predicate datePredicate = cb.between(bookingRoot.join("scheduleId").get("tripDate"), startDate, endDate);
        Predicate isDeletedPredicate = cb.equal(bookingRoot.get("isCancelled"), 0);
        Predicate isBookedPredicate = cb.equal(bookingRoot.get("isBooked"), true);
        cq.where(cb.and(datePredicate, isDeletedPredicate, isBookedPredicate));
        List<Bookings> bookings = em.createQuery(cq).getResultList();
        Map<Integer, OccupancyReportDto> reportDataMap = new HashMap<>();
        for (Bookings booking : bookings) {
            Schedules schedule = booking.getScheduleId();
            if (schedule != null) {
                Integer scheduleId = schedule.getScheduleId();
                OccupancyReportDto reportData = reportDataMap.getOrDefault(scheduleId, new OccupancyReportDto());
                reportData.setScheduleId(scheduleId);
                reportData.setSelectedSource(booking != null ? booking.getSelectedSource() : null);
                reportData.setSelectedDestination(booking != null ? booking.getSelectedDestination() : null);
                reportData.setDepartureTime(booking != null ? booking.getDepatureTime() : null);
                reportData.setTotalSeats(schedule.getBusId().getSeatingCapacity());
                reportData.setRouteId(schedule.getRouteId().getRouteId());
                reportData.setRouteId(schedule.getRouteId().getRouteId());
                reportData.setRoute(schedule.getRouteId().getSource() + " to " + schedule.getRouteId().getDestination());
                reportData.setSeatsOccupied(schedule.getBusId().getSeatingCapacity());
                List<BookingDetails> bookingDetails = getBookingDetailsById(booking != null ? booking.getBookingId() : null);
                if (bookingDetails != null) {
                    List<BookingDetails> canceledBookings = bookingDetails.stream()
                            .filter(bookingDetail -> bookingDetail.isCancelled())
                            .collect(Collectors.toList());
                    int canceledSeats = canceledBookings.size();
                    reportData.setSeatsCancelled(canceledSeats);
                }
                reportData.setSeatsOccupied(schedule.getBusId().getSeatingCapacity()-schedule.getAvailableSeats() + reportData.getSeatsCancelled());
                reportData.setOccupancyPercentage((double) reportData.getSeatsOccupied() / schedule.getBusId().getSeatingCapacity() * 100);
                reportDataMap.put(scheduleId, reportData);
            }
        }

        return new ArrayList<>(reportDataMap.values());
    }


    public List<OccupancyReportDto> getYearlyOccupancyData(int year) {
        EntityManager em = entityManager;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bookings> cq = cb.createQuery(Bookings.class);
        Root<Bookings> bookingRoot = cq.from(Bookings.class);
        bookingRoot.fetch("scheduleId", JoinType.LEFT);
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1).minusDays(1);
        Predicate datePredicate = cb.between(bookingRoot.join("scheduleId").get("tripDate"), startDate, endDate);
        Predicate isDeletedPredicate = cb.equal(bookingRoot.get("isCancelled"), 0);
        Predicate isBookedPredicate = cb.equal(bookingRoot.get("isBooked"), true);
        cq.where(cb.and(datePredicate, isDeletedPredicate, isBookedPredicate));
        List<Bookings> bookings = em.createQuery(cq).getResultList();
        Map<Integer, OccupancyReportDto> reportDataMap = new HashMap<>();
        for (Bookings booking : bookings) {
            Schedules schedule = booking.getScheduleId();
            if (schedule != null) {
                Integer scheduleId = schedule.getScheduleId();
                OccupancyReportDto reportData = reportDataMap.getOrDefault(scheduleId, new OccupancyReportDto());
                reportData.setScheduleId(scheduleId);
                reportData.setSelectedSource(booking.getSelectedSource());
                reportData.setSelectedDestination(booking.getSelectedDestination());
                reportData.setDepartureTime(booking.getDepatureTime());
                reportData.setRouteId(schedule.getRouteId().getRouteId());
                reportData.setTotalSeats(schedule.getBusId().getSeatingCapacity());
                reportData.setRoute(schedule.getRouteId().getSource() + " to " + schedule.getRouteId().getDestination());
                reportData.setSeatsOccupied(schedule.getBusId().getSeatingCapacity());
                List<BookingDetails> bookingDetails = getBookingDetailsById(booking.getBookingId());
                if (bookingDetails != null) {
                    List<BookingDetails> canceledBookings = bookingDetails.stream()
                            .filter(BookingDetails::isCancelled)
                            .collect(Collectors.toList());
                    int canceledSeats = canceledBookings.size();
                    reportData.setSeatsCancelled(canceledSeats);
                }
                reportData.setSeatsOccupied(schedule.getBusId().getSeatingCapacity() - schedule.getAvailableSeats() + reportData.getSeatsCancelled());
                reportData.setOccupancyPercentage((double) reportData.getSeatsOccupied() / schedule.getBusId().getSeatingCapacity() * 100);
                reportDataMap.put(scheduleId, reportData);
            }
        }
        return new ArrayList<>(reportDataMap.values());
    }





    public List<Bookings> getBookingsVyScheduleId(int id){
        Session session = sessionFactory.openSession();
        String hql = "from buswise.model.Bookings where scheduleId.scheduleId=:id and isCancelled=false and scheduleId.tripDate>=CURRENT_DATE and isBooked=true ";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        List<Bookings> bookings = query.list();
        session.close();
        return bookings;
    }




}

