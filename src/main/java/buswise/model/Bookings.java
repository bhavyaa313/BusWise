package buswise.model;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_id")
    private int bookingId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User userId;
    @OneToOne
    @JoinColumn(name = "schedule_id")
    private  Schedules scheduleId;
    private LocalDateTime bookingDate;
    private double totalAmount;
    private boolean isCancelled;
    @ManyToOne
    @JoinColumn(name = "cancelled_by")
    private User cancelledBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    private String phone;
    private String email;
    @Column(name = "selected_source")
    private String selectedSource;
    @Column(name = "selected_destination")
    private String selectedDestination;
    @Column(name = "depature_time")
    private String depatureTime;
    @Column(name = "is_booked")
    private boolean isBooked;
    private LocalTime timeStamp;

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Schedules getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedules scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public User getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(User cancelledBy) {
        this.cancelledBy = cancelledBy;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(String selectedSource) {
        this.selectedSource = selectedSource;
    }

    public String getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(String selectedDestination) {
        this.selectedDestination = selectedDestination;
    }

    public String getDepatureTime() {
        return depatureTime;
    }

    public void setDepatureTime(String depatureTime) {
        this.depatureTime = depatureTime;
    }

    public Bookings(int bookingId, User userId, Schedules scheduleId, LocalDateTime bookingDate, double totalAmount, boolean isCancelled, User cancelledBy, LocalDateTime createdDate, LocalDateTime modifiedDate, String phone, String email, String selectedSource, String selectedDestination, String depatureTime, boolean isBooked, LocalTime timeStamp) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.isCancelled = isCancelled;
        this.cancelledBy = cancelledBy;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.phone = phone;
        this.email = email;
        this.selectedSource = selectedSource;
        this.selectedDestination = selectedDestination;
        this.depatureTime = depatureTime;
        this.isBooked = isBooked;
        this.timeStamp = timeStamp;
    }

    public Bookings() {
    }
}
