package buswise.controller;

import buswise.dao.BookingDao;
import buswise.dto.*;
import buswise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class NewController {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private DailySalesPdfService dailySalesPdfService;

    @Autowired
    private MonthlySalesPdfService monthlySalesPdfService;

    @Autowired
    private YearlySalesPdfService yearlySalesPdfService;

    @Autowired
    private DailyOccupancyReportPdf dailyOccupancyReportPdf;

    @Autowired
    private MonthlyOccupancyReportPdf monthlyOccupancyReportPdf;

    @Autowired
    private YearlyOccupancyReportPdf yearlyOccupancyReportPdf;

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

//    @GetMapping("/ticketDownload/{id}")
//
//    public ResponseEntity<Resource> downloadTicket(HttpServletRequest request1, @PathVariable("id") int id) {
//        try {
//            // Generate the file and get its path
//            String filePath = ticketPDFService.createPdf(request1, id);
//            System.out.println(filePath);
//            System.out.println("-----------------");
//
//            // Convert the local file path to a URL that can be used to serve the file
//            File file = new File(filePath);
//
//            // Ensure the file is within the static directory
//            String fileName = file.getName();
//            Resource resource = new ClassPathResource("static/tickets/" + fileName);
//
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                        .contentType(MediaType.APPLICATION_PDF)
//                        .body(resource);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//        } catch (Exception e ) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

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
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email with attachment.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred.";
        }
    }


    @PostMapping("/dailySales")
    @ResponseBody
    public SalesData fetchDailySales(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return bookingDao.getDailySales(parsedDate);
    }

    @PostMapping("/monthlySales")
    @ResponseBody
    public SalesData fetchMonthlySales(@RequestParam String date) {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return projectService.getMonthlySalesData(year, month);
    }

    @PostMapping("/yearly-sales")
    @ResponseBody
    public SalesData fetchYearlySales(@RequestParam int year) {

        return projectService.getYearlySalesData(year);
    }

    @RequestMapping("/dailySalesDownlaod/{date}")
    @ResponseBody
    public String dailySalesDownlod(HttpServletRequest request, @PathVariable("date") String date) {
        return dailySalesPdfService.createDailySalesPdf(request, date);

    }

    @RequestMapping("/monthly-sales-download/{date}")
    @ResponseBody
    public String MonthlySalesDownload(HttpServletRequest request, @PathVariable("date") String date) {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        LocalDate date1 = LocalDate.of(year, month, 1);
        return monthlySalesPdfService.createMonthlySalesPdf(request, date1);

    }

    @PostMapping("/year-monthly-sales")
    @ResponseBody
    public List<MonthlySalesData> getMonthlySales(@RequestParam("year") int year) {
        return projectService.getMonthlySalesData(year);
    }


    @RequestMapping("/yearly-sales-download/{year}")
    @ResponseBody
    public String yearlySalesDownload(HttpServletRequest request, @PathVariable("year") int year) {
        return yearlySalesPdfService.createYearlySalesPdf(request, year);

    }

    @RequestMapping("/daily-occupancy-download/{date}")
    @ResponseBody
    public String dailyOccupancyDownload(HttpServletRequest request, @PathVariable("date") String date) {
        return dailyOccupancyReportPdf.createDailyOccupancyPdf(request, date);

    }


    @PostMapping("/daily-occupancy")
    @ResponseBody
    public List<OccupancyReportDto> getDailyOccupancy(@RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return bookingDao.getDailyOccupancyData(localDate);
    }


    @RequestMapping("/monthly-occupancy-download/{date}")
    @ResponseBody
    public String monthlyOccupancyDownload(HttpServletRequest request, @PathVariable("date") String date) {

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return monthlyOccupancyReportPdf.createMonthlyOccupancyPdf(request, year, month);

    }

    @RequestMapping("/yearly-occupancy-download/{year}")
    @ResponseBody
    public String yearlyOccupancyDownload(HttpServletRequest request, @PathVariable("year") int year) {

        return yearlyOccupancyReportPdf.createYearlyOccupancyPdf(request, year);

    }


    @PostMapping("/monthly-occupancy")
    @ResponseBody
    public List<OccupancyReportDto> fetchMonthlyOccupancy(@RequestParam String date) {
        return projectService.getRoutesWiseMonthlyOccupancy(date);
    }

    @PostMapping("/yearly-occupancy")
    @ResponseBody
    public List<OccupancyReportDto> fetchMonthlyOccupancy(@RequestParam int year) {
        return projectService.getRoutesWiseYearlyOccupancy(year);
    }
}


