package buswise.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String toMail, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("bhavya.shah3133@outlook.com");
        email.setTo(toMail);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
        System.out.println("emailService ");
    }

    public void sendWithAttachment(String toMail, String subject, String message, String attachmentPath) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("bhavya.shah3133@outlook.com");
        helper.setTo(toMail);
        helper.setSubject(subject);
        helper.setText(message);
        FileSystemResource file = new FileSystemResource(new File(attachmentPath));
        helper.addAttachment(file.getFilename(), file);
        mailSender.send(mimeMessage);
    }
}
