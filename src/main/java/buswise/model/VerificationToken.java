package buswise.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;
    private String email;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    private boolean validation;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValidation() {
        return validation;
    }
    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public VerificationToken(int id, String token, String email, LocalDateTime createdDate, boolean validation) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.createdDate = createdDate;
        this.validation = validation;
    }

    public VerificationToken() {
        super();
        // TODO Auto-generated constructor stub
    }





}
