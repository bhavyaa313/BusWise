package buswise.dto;

import javax.validation.constraints.*;


public class UserProfileDto {
    @NotNull(message = "Please enter UserProfile Id")
    private int userProfileId;
    @NotBlank(message = "Please enter your First Name")
    private String firstName;
    @NotBlank(message = "Please enter yout Last Name")
    private String lastName;
    @NotBlank(message = "Please Enter Your Phone Number")
    @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number")
    @Size(min = 10, max = 10, message = "Your phone number must be consist of 10 numbers ")
    private String phone;
    @NotBlank(message = "Please Enter Your Email")
    @Email(message = "Please enter a valid email address")
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address")
    private String email;
    private Integer age;
    @Pattern(regexp = "^[A-Za-z]*$", message = "City must contain only alphabetic characters")
    private String city="";

    @Pattern(regexp = "^[A-Za-z]*$", message = "State must contain only alphabetic characters")
    private String state="";
    private int userId;


    @NotNull(message = "Please enter UserProfile Id")
    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(@NotNull(message = "Please enter UserProfile Id") int userProfileId) {
        this.userProfileId = userProfileId;
    }

    public @NotBlank(message = "Please enter your First Name") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "Please enter your First Name") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Please enter yout Last Name") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Please enter yout Last Name") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Please Enter Your Phone Number") @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number") @Size(min = 10, max = 10, message = "Your phone number must be consist of 10 numbers ") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Please Enter Your Phone Number") @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid phone number") @Size(min = 10, max = 10, message = "Your phone number must be consist of 10 numbers ") String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "Please Enter Your Email") @Email(message = "Please enter a valid email address") @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Please Enter Your Email") @Email(message = "Please enter a valid email address") @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address") String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public UserProfileDto(int userProfileId, String firstName, String lastName, String phone, String email, Integer age, String city, String state, int userId) {
        this.userProfileId = userProfileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.city = city;
        this.state = state;
        this.userId = userId;
    }

    public UserProfileDto() {
    }
}
