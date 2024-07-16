package buswise.model;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="email_logs")
public class EmailLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notifications notificationId;
    private String email;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Notifications getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Notifications notificationId) {
        this.notificationId = notificationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public EmailLogs(int id, Notifications notificationId, String email, LocalDateTime sentAt) {
        this.id = id;
        this.notificationId = notificationId;
        this.email = email;
        this.sentAt = sentAt;
    }

    public EmailLogs() {
    }
}
