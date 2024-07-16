package buswise.dto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.*;
import java.util.List;

public class BookTicketDto {
    @NotBlank(message = "Please Enter Your Email")
    @Email(message = "Please enter a valid email address")
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "Please Enter Your Phone Number")
    @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number")
    @Size(min = 10, max = 10, message = "Your phone number must be consist of 10 numbers ")
    private String phone;
    private String wpCheck;
    @NotNull(message = "Amount is empty")
    private long amount;
    private String scheduleId;
    private List<String> name;
    private List<String> seat;
    private List<String> gender;
    private List<Integer> age;
    private int bookingId;
    private String depatureTime;


    public @NotBlank(message = "Please Enter Your Email") @Email(message = "Please enter a valid email address") @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Please Enter Your Email") @Email(message = "Please enter a valid email address") @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Please Enter Your Phone Number") @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number") @Size(min = 10, max = 10, message = "Your phone number must be consist of 10 numbers ") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Please Enter Your Phone Number") @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number") @Size(min = 10, max = 10, message = "Your phone number must be consist of 10 numbers ") String phone) {
        this.phone = phone;
    }

    public String getWpCheck() {
        return wpCheck;
    }

    public void setWpCheck(String wpCheck) {
        this.wpCheck = wpCheck;
    }

    @NotNull(message = "Amount is empty")
    public long getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Amount is empty") long amount) {
        this.amount = amount;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getSeat() {
        return seat;
    }

    public void setSeat(List<String> seat) {
        this.seat = seat;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<Integer> getAge() {
        return age;
    }

    public void setAge(List<Integer> age) {
        this.age = age;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getDepatureTime() {
        return depatureTime;
    }

    public void setDepatureTime(String depatureTime) {
        this.depatureTime = depatureTime;
    }

    public BookTicketDto(String depatureTime, int bookingId, List<Integer> age, List<String> gender, List<String> seat, List<String> name, String scheduleId, long amount, String wpCheck, String phone, String email) {
        this.depatureTime = depatureTime;
        this.bookingId = bookingId;
        this.age = age;
        this.gender = gender;
        this.seat = seat;
        this.name = name;
        this.scheduleId = scheduleId;
        this.amount = amount;
        this.wpCheck = wpCheck;
        this.phone = phone;
        this.email = email;
    }

    public BookTicketDto() {
    }
}
