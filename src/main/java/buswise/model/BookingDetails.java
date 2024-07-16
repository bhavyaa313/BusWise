package buswise.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_details")
public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Bookings booking_id;
    @Column(name = "seat_number")
    private String seatNumber;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    private String name;
    private String age;
    private String gender;
    @Column(name = "is_cancelled")
    private boolean isCancelled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bookings getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Bookings booking_id) {
        this.booking_id = booking_id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public BookingDetails(int id, Bookings booking_id, String seatNumber, LocalDateTime createdDate, LocalDateTime modifiedDate, String name, String age, String gender, boolean isCancelled) {
        this.id = id;
        this.booking_id = booking_id;
        this.seatNumber = seatNumber;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.isCancelled = isCancelled;
    }

    public BookingDetails() {
    }
}
