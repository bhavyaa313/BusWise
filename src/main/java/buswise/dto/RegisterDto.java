package buswise.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class RegisterDto {

    private int userId;

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
    @NotBlank(message = "Please Enter Password")
    @Size(min = 8, max = 25, message = "Your password's length should be between 8 and 25")
    private String password;
    @NotBlank(message = "Please confirm your Password")
    @Size(min = 8, max = 25, message = "Your password's length should be between 8 and 25")
    private String confirmPassword;
    private String role;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public @NotBlank(message = "Please confirm your Password") @Size(min = 8, max = 25, message = "Your password's length should be between 8 and 25") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "Please confirm your Password") @Size(min = 8, max = 25, message = "Your password's length should be between 8 and 25") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public RegisterDto(int userId, String firstName, String lastName, String phone, String email, String password, String confirmPassword, String role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }

    public RegisterDto() {
    }
}
