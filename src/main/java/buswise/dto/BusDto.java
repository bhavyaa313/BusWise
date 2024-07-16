package buswise.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class BusDto {

    private int busId;
    @NotBlank(message = "Please Enter busNumber")
    @Size(min =5, max = 12, message = "Your bus number's length should be between 5 and 12")
    private String busNumber;
    @NotBlank(message = "Please select bus type")
    private String busType;

    @Min(value = 20, message = "Seating capacity must be at least 1")
    private int seatingCapacity;
    private String modifiedDate;
    private long count;



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

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public BusDto(int busId, String busNumber, String busType, int seatingCapacity, String modifiedDate, long count) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busType = busType;
        this.seatingCapacity = seatingCapacity;
        this.modifiedDate = modifiedDate;
        this.count = count;
    }

    public BusDto() {
    }
}
