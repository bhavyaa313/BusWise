package buswise.controller;

import buswise.dao.BookingDao;
import buswise.dto.*;
import buswise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.List;

@Controller
public class CommonController {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private TicketPDFService ticketPDFService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BookingDao bookingDao;

    @GetMapping("/locations")
    @ResponseBody
    public ResponseEntity<List<BusLocationDto>> getBusLocations() {
        List<BusLocationDto> busLocations = projectService.getDetailsForBusLocation();
        return ResponseEntity.ok().body(busLocations);
    }


    @PostMapping("/bookTicket/{userId}/{source}/{destination}")
    @ResponseBody
    public int bookTicket(@Valid @ModelAttribute("BookTicketDto") BookTicketDto BookTicketDto
            , BindingResult bindingResult, @PathVariable("source") String source, @PathVariable("destination") String destination, @PathVariable("userId") int userID) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return 0;


        } else {
            int bookingId = projectService.saveBookingDetails(BookTicketDto, source, destination, userID);
            return bookingId;
        }

    }

    @RequestMapping("/ticketDownlaod/{id}")
    @ResponseBody
    public String downlaodTicket(HttpServletRequest request1, @PathVariable("id") int id) {
        String ticket = ticketPDFService.createPdf(request1, id);
        System.out.println(ticket);
        return ticket;
    }


    @GetMapping("/sendEmail/{email}/{bookingId}/{userId}")
    public String sendEmailWithAttachment(@PathVariable("email") String toMail, @PathVariable("bookingId") int id, @PathVariable("userId") int userId, HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();
            String filePath = httpSession.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources"
                    + File.separator + "tickets" + File.separator + "Ticket_" + id + ".pdf";
            String subject = "Your BusWise Ticket Booking Confirmation";
            String message = "Dear User,\n\n" +
                    "Thank you for booking with BusWise! We are pleased to confirm that your ticket booking was successful. " +
                    "Please find your e-ticket attached for your upcoming journey.\n\n" +
                    "Should you have any questions or require further assistance, do not hesitate to contact us.\n\n" +
                    "Safe travels!\n\n" +
                    "Best regards,\n" +
                    "The BusWise Team";

            System.out.println("Creating PDF...");
            ticketPDFService.createPdf(request, id);

            System.out.println("Sending email with attachment...");
          emailService.sendWithAttachment(toMail, subject, message, filePath);

            System.out.println("Saving email logs...");
            projectService.saveEmailLogsWhileBooking(toMail, userId);

            return "Email sent successfully with attachment!";

     }
        catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email with attachment.";
        }
      catch (Exception e) {
            e.printStackTrace();
            return "An error occurred.";
        }
    }


}


