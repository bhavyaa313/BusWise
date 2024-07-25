package buswise.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="schedules")
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "schedule_id")
    private Integer scheduleId;
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Buses busId;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Routes routeId;
    @Column(name = "depature_time")
    private LocalTime depatureTime;
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    @OneToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name="trip_date")
    private LocalDate tripDate;
    @Column(name = "available_seats")
    private int availableSeats;

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Buses getBusId() {
        return busId;
    }

    public void setBusId(Buses busId) {
        this.busId = busId;
    }

    public Routes getRouteId() {
        return routeId;
    }

    public void setRouteId(Routes routeId) {
        this.routeId = routeId;
    }

    public LocalTime getDepatureTime() {
        return depatureTime;
    }

    public void setDepatureTime(LocalTime depatureTime) {
        this.depatureTime = depatureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Schedules(Integer scheduleId, Buses busId, Routes routeId, LocalTime depatureTime, LocalTime arrivalTime, User createdBy, User modifiedBy, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean isDeleted, LocalDate tripDate, int availableSeats) {
        this.scheduleId = scheduleId;
        this.busId = busId;
        this.routeId = routeId;
        this.depatureTime = depatureTime;
        this.arrivalTime = arrivalTime;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
        this.tripDate = tripDate;
        this.availableSeats = availableSeats;
    }

    public Schedules() {
    }
}
