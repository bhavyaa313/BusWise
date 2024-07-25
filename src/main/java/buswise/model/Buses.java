package buswise.model;

import org.apache.poi.ss.formula.eval.UnaryMinusEval;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "buses",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bus_number"})
)
public class Buses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int busId;
    @Column(name = "bus_number")
    private String busNumber;
    @Column(name = "bus_type")
    private String busType;
    @Column(name = "seating_capacity")
    private int seatingCapacity;
    @ManyToOne
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

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
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

    public Buses(int busId, String busNumber, String busType, int seatingCapacity, User createdBy, User modifiedBy, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean isDeleted) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busType = busType;
        this.seatingCapacity = seatingCapacity;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
    }

    public Buses() {
    }
}
