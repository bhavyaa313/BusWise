package buswise.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDto {
    @NotBlank(message = "Please Enter Your Email")
    @Email(message = "Please enter a valid email address")
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "Please Enter Password")
    @Size(min = 8, max = 25, message = "Your password's length should be between 8 and 25")
    private String password1;

    public @NotBlank(message = "Please Enter Your Email") @Email(message = "Please enter a valid email address") @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Please Enter Your Email") @Email(message = "Please enter a valid email address") @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Please enter a valid email address") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Please Enter Password") @Size(min = 8, max = 25, message = "Your password's length should be between 8 and 25") String getPassword1() {
        return password1;
    }

    public void setPassword1(@NotBlank(message = "Please Enter Password") @Size(min = 8, max = 25, message = "Your password's length should be between 8 and 25") String password1) {
        this.password1 = password1;
    }


    public LoginDto(String email, String password1) {
        this.email = email;
        this.password1 = password1;
    }

    public LoginDto() {
    }
}
