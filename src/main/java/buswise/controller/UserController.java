package buswise.controller;

import buswise.dao.BookingDao;
import buswise.dao.UserDao;
import buswise.dto.*;
import buswise.exceptions.ResourceNotFoundException;
import buswise.model.BookingDetails;
import buswise.model.Bookings;
import buswise.model.UserProfile;
import buswise.service.EmailService;
import buswise.service.ProjectService;
import buswise.service.TicketPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TicketPDFService ticketPDFService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BookingDao bookingDao;

    @GetMapping("/home-page")
    private String homepage()
    {
        return "/user/homepage";
    }

    @GetMapping ("/getSources")
    @ResponseBody
    public List<String> ajaxcallForSource() {
        List<String> source =  projectService.viewSourceList();
        return source;
    }


    @GetMapping ("/getDestination")
    @ResponseBody
    public List<String> ajaxcallForDestination() {
        List<String> destination =  projectService.viewDestinationList();

        return destination;

    }

    @PostMapping("/search")
    public String SearchTicket(@RequestParam("source") String source, @RequestParam("destination") String destination , @RequestParam("date") String date)
    {
        return "redirect:/details/"+source+"/"+destination+"/"+date;
    }


    @RequestMapping("/details/{source}/{destination}/{date}")
    public String displayTickets(@PathVariable String source,
                                 @PathVariable String destination,
                                 @PathVariable String date ,

                                 Model model)
    {

        String activeString = "active  text-dark";
        model.addAttribute("active1", activeString);
        List<Schedule1Dto> ticketList = projectService.ticketDteails(source, destination, date);
        model.addAttribute("ticketDetails", ticketList);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        return "/user/ticketDetails";
    }


    @RequestMapping("/user/user-dashboard/{userId}")
    public String dashboard(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("userId", userId);
        String activeString = "active  text-dark";
        model.addAttribute("active1", activeString);
        return "/user/dashboard";
    }

    @GetMapping ("/{userId}/getSources")
    @ResponseBody
    public List<String> ajaxcallForSource(@PathVariable("userId") int userId) {
        List<String> source =  projectService.viewSourceList();
        return source;

    }


    @GetMapping ("/{userId}/getDestination")
    @ResponseBody
    public List<String> ajaxcallForDestination(@PathVariable("userId") int userId) {
        List<String> destination =  projectService.viewDestinationList();
        return destination;

    }

    @PostMapping("/search/{userId}")
    public String SearchTicket(@RequestParam("source") String source, @RequestParam("destination") String destination ,@RequestParam("date") String date, @PathVariable("userId") int userId)
    {
        return "redirect:/details/"+userId+"/"+source+"/"+destination+"/"+date;
    }


    @RequestMapping("/details/{userId}/{source}/{destination}/{date}")
    public String displayTickets(@PathVariable String source,
                                 @PathVariable String destination,
                                 @PathVariable String date ,
                                 @PathVariable int userId,
                                 Model model)
    {

        String activeString = "active  text-dark";
        model.addAttribute("active1", activeString);
        List<Schedule1Dto> ticketList = projectService.ticketDteails(source, destination, date);
        model.addAttribute("ticketDetails", ticketList);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("userId", userId);

        return "/user/ticketDetails";
    }

    @PostMapping("/user/bookTicket/{source}/{destination}/{userId}")
    @ResponseBody
    public int bookTicket(@Valid @ModelAttribute("BookTicketDto") BookTicketDto BookTicketDto
            , BindingResult bindingResult, @PathVariable("source") String source, @PathVariable("destination") String destination, @PathVariable("userId") int userID) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return 0;


        } else {
            int bookingId =    projectService.saveBookingDetails(BookTicketDto, source, destination, userID);
            return  bookingId;

        }

    }

    @RequestMapping("/user/success/{source}/{destinaton}/{id}/{userId}")
    public String bookingDetails(@PathVariable("id") int id, @PathVariable("userId") int userId , Model model)
    {
        model.addAttribute("id", id);
        model.addAttribute("userId", userId);
        TicketDto ticketDto = projectService.ticketDetails(id);
        model.addAttribute("t", ticketDto);
        return "/user/successBooking";
    }


    @GetMapping("/user/sendEmail/{email}/{bookingId}/{userid}")
    public String sendEmailWithAttachment(@PathVariable("email") String toMail,@PathVariable("bookingId") int id, @PathVariable("userId") int userId
            , HttpServletRequest request) {
        try {
            HttpSession httpSession = request.getSession();
            String filePath = httpSession.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources"
                    + File.separator + "tickets" + File.separator + "Ticket_"+id+".pdf";
            String subject = "Your BusWise Ticket Booking Confirmation";
            String message = "Dear User,\n\n" +
                    "Thank you for booking with BusWise! We are pleased to confirm that your ticket booking was successful. " +
                    "Please find your e-ticket attached for your upcoming journey.\n\n" +
                    "Should you have any questions or require further assistance, do not hesitate to contact us.\n\n" +
                    "Safe travels!\n\n" +
                    "Best regards,\n" +
                    "The BusWise Team";

            ticketPDFService.createPdf(request,id);
            emailService.sendWithAttachment(toMail, subject, message, filePath);
            projectService.saveEmailLogsWhileBooking(toMail, userId);

            return "Email sent successfully with attachment!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email with attachment.";
        }
    }


    @RequestMapping("/user/myProfile/{userId}")
    public String userProfile(Model model,@PathVariable("userId") int userId)
    {
        String activeString = "active  text-dark";
        model.addAttribute("userId", userId);
        model.addAttribute("active2", activeString);
        List<UserProfile> userProfiles = projectService.userProfileList(userId);
        model.addAttribute("userProfiles", userProfiles);
        return "/user/myProfile";
    }

    @PostMapping("/user/myProfile/editProfile/{userId}")
    @ResponseBody
    public void userProfileEdit(@Valid @ModelAttribute("UserProfileDto") UserProfileDto UserProfileDto, BindingResult bindingResult, @PathVariable("userId") int id) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        } else {
            projectService.myProfileEdit(UserProfileDto, id);
        }
    }

    @RequestMapping("/user/myBookingsView/{userId}")
    public String myBookingsView(Model model, HttpServletRequest request, @PathVariable("userId") int userId) {
        String activeString = "active  text-dark";
        model.addAttribute("active6", activeString);
        model.addAttribute("userId", userId);

        return "/user/myTrips";
    }

    @PostMapping("/user/myBookings/{userId}")
    @ResponseBody
    public List<MyBookingsDto> myBookings(@PathVariable("userId") int userId, @RequestParam("curPage") int curPage, @RequestParam("filterOption") int filterOption, Model model) {
        model.addAttribute("userId", userId);
        return projectService.myBookings(userId, curPage, filterOption);

    }

    @RequestMapping(value = "/user/cancelSeat/{bookingDetailId}/{bookingId}/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> cancelSeat(
            @PathVariable("bookingDetailId") int bookingDetailId,
            @PathVariable("userId") int userId,
            @PathVariable("bookingId") int bookingId) {

        Map<String, String> response = new HashMap<>();
        List<Bookings> bookings = projectService.bookingsList(bookingId);
        List<BookingDetails> bookingDetails =projectService.bookingDetails(bookingDetailId);

        if (bookings.isEmpty()) {
            response.put("message", "Booking not found.");
        } else if (bookingDetails.isEmpty()) {
            response.put("message", "Booking details not found.");
        } else {
            BookingDetails bookingDetail = bookingDetails.get(0);
            Bookings booking = bookings.get(0);
            String email = booking.getEmail();
            String route = booking.getSelectedSource() + " to " + booking.getSelectedDestination();
            String date = booking.getScheduleId().getTripDate().toString();
            String departureTime = booking.getDepatureTime();
            String cancellationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            String seatNumber = bookingDetail.getSeatNumber();

            String subject = "Cancellation of Ticket";
            String message = "Dear Customer,\n\n" +
                    "We regret to inform you that your ticket for the following route has been cancelled:\n\n" +
                    "Route: " + route + "\n" +
                    "Date of Journey: " + date + "\n" +
                    "Departure Time: " + departureTime + "\n\n" +
                    "Seat Number: " + seatNumber + "\n\n" +
                    "This cancellation was processed on: " + cancellationDateTime + "\n\n" +
                    "If you have any questions or need further assistance, please do not hesitate to contact our support team.\n\n" +
                    "We apologize for any inconvenience this may cause.\n\n" +
                    "Best regards,\n" +
                    "BusWise\n" +
                    "Customer Support Team";

            projectService.cancelSeat(bookingDetailId, userId, email);

            try {
                emailService.send(email, subject, message);

                response.put("message", "Booking cancelled successfully.");
            } catch (Exception e) {
                response.put("message", "Booking cancelled, but the email could not be sent.");
            }

        }

        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "/user/cancelBooking/{bookingId}/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> cancelBooking(
            @PathVariable("bookingId") int bookingId,
            @PathVariable("userId") int userId) {

        Map<String, String> response = new HashMap<>();
        List<Bookings> bookings = projectService.bookingsList(bookingId);


        if (bookings != null && !bookings.isEmpty()) {
            Bookings booking = bookings.get(0);
            String email = booking.getEmail();
            String route = booking.getSelectedSource() + " to " + booking.getSelectedDestination();
            String date = booking.getScheduleId().getTripDate().toString();
            String departureTime = booking.getDepatureTime();
            String cancellationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            String subject = "Cancellation of Ticket";
            String message = "Dear Customer,\n\n" +
                    "We regret to inform you that your ticket for the following route has been cancelled:\n\n" +
                    "Route: " + route + "\n" +
                    "Date of Journey: " + date + "\n" +
                    "Departure Time: " + departureTime + "\n\n" +
                    "This cancellation was processed on: " + cancellationDateTime + "\n\n" +
                    "If you have any questions or need further assistance, please do not hesitate to contact our support team.\n\n" +
                    "We apologize for any inconvenience this may cause.\n\n" +
                    "Best regards,\n" +
                    "BusWise\n" +
                    "Customer Support Team";

            projectService.cancelBooking(bookingId, userId, email);

            try {
                emailService.send(email, subject, message);

                response.put("message", "Booking cancelled successfully.");
            } catch (Exception e) {
                response.put("message", "Booking cancelled, but the email could not be sent.");
            }

        } else {
            response.put("message", "Booking not found.");
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/bus-location/{userId}")
    public String busLocation(@PathVariable("userId") int userId, Model model)
    {
        model.addAttribute("userId", userId);
        String activeString = "active  text-dark";
        model.addAttribute("active10", activeString);
        return "/user/busLocation";
    }

    @GetMapping("/locations/{userId}")
    @ResponseBody
    public ResponseEntity<List<BusLocationDto>> getBusLocations(@PathVariable("userId") int userId) {
        List<BusLocationDto> busLocations = projectService.getDetailsForBusLocationForUser(userId);
        return ResponseEntity.ok().body(busLocations);
    }

    @RequestMapping("/user/mywallet/{userId}")
    public String mywallet(@PathVariable("userId") int userid, Model model){
        model.addAttribute("userId", userid);
        return "/user/myWallet";
    }

    @PostMapping("/addAmount/{userId}")
    @ResponseBody
    public void addAmount(@RequestParam("amount") double amount, @PathVariable("userId") int userId){
        projectService.addFunds(userId, amount);

    }

    @PostMapping("/withdrawAmount/{userId}")
    @ResponseBody
    public void withdrawAmount(@RequestParam("amount") double amount, @PathVariable("userId") int userId){
        projectService.deductFunds(userId,amount);

    }

    @PostMapping("/getBalance/{userId}")
    @ResponseBody
    public double getBalance(@PathVariable("userId") int userid){
        return projectService.getBalance(userid);
    }

    @PostMapping("/getTransactions/{userId}")
    @ResponseBody
    public List<TransactionHistoryDto> getTransactions(@RequestParam("curPage") int curPage, @PathVariable("userId") int userId){
        return projectService.getTransactions(userId, curPage);
    }


    @PostMapping("/confirmBooking/{bookingId}/{userId}")
    public ResponseEntity<Void> confirmBooking(@PathVariable int bookingId, @PathVariable int userId, Model model) {
        try {
            model.addAttribute("userId", userId);
            projectService.confirmBooking(bookingId, userId);

            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/resetReservation/{bookingId}")
    @ResponseBody
    public void resetReservationTimestamp(@PathVariable int bookingId,  Model model) {

        projectService.resetReservationTimestamp(bookingId);
    }
}