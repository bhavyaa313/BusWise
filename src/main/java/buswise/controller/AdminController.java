package buswise.controller;

import buswise.dto.*;
import buswise.model.*;
import buswise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmailService emailService;

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




    @RequestMapping("/dashboard/{userId}")
    public String dashboard(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("userId", userId);
        String activeString = "active  text-dark";
        model.addAttribute("active1", activeString);
        return "/admin/dashboard";
    }

    @RequestMapping("/buses/{userId}")
    public String busesView(Model model, HttpServletRequest request, @ModelAttribute("BusDto") BusDto BusDto,@PathVariable("userId") int userId) {
        model.addAttribute("BusDto", new BusDto());
        String activeString = "active  text-dark";
        model.addAttribute("active3", activeString);
        model.addAttribute("userId", userId);
        return "/admin/buses";
    }

    @RequestMapping("/routes/{userId}")
    public String routesView(Model model, HttpServletRequest request, @PathVariable("userId") int userId) {
        String activeString = "active  text-dark";
        model.addAttribute("active4", activeString);
        model.addAttribute("userId", userId);
        return "/admin/routes";
    }

    @RequestMapping("/schedule/{userId}")
    public String scheduleView(Model model, HttpServletRequest request,@PathVariable("userId") int userId) {

        String activeString = "active  text-dark";
        model.addAttribute("active5", activeString);
        model.addAttribute("userId", userId);
        return "/admin/schedule";
    }

    @PostMapping("/buses/addBus/{userId}")
    @ResponseBody
    public void addBus(@Valid @ModelAttribute("BusDto") BusDto BusDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        else {
            projectService.addBus(BusDto);
        }



    }

    @PostMapping("/schedule/addSchedule/{userId}")
    @ResponseBody
    public void addSchedule(@Valid @ModelAttribute("ScheduleDto") ScheduleDto ScheduleDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        else {
            projectService.addSchedule(ScheduleDto);
        }



    }


    @GetMapping("/buses/checkBusNumber/{userId}")
    public ResponseEntity<Map<String, Boolean>> checkBusNumber(@RequestParam String busNumber) {
        List<String> busNumbers = projectService.getBusNumbers();
        boolean exists = busNumbers.contains(busNumber);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/buses/editBus/{userId}")
    @ResponseBody
    public void editBus(@Valid @ModelAttribute("BusDto") BusDto BusDto, @RequestParam("id") int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
        }
        else{
            projectService.editBus(BusDto, id);
        }


    }

    @PostMapping("/buses/deleteBus/{busId}/{userId}")
    @ResponseBody
    public void deleteBus(@PathVariable("busId") int busId) {
        projectService.deleteBus(busId);

    }

    @GetMapping("/buses/checkBusUsage/{busId}/{userId}")
    public ResponseEntity<?> checkBusUsage( @PathVariable int busId) {
        boolean isBusUsed = projectService.isBusUsedInSchedule(busId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUsed", isBusUsed);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/buses/ajaxforSearchBus/{userId}")
    @ResponseBody
    public List<BusDto> ajaxforSearchBus(Model model, HttpServletRequest request, @RequestParam("busNumber") String busNumber, @RequestParam("curPage") int curPage) {
        List<BusDto> busDtos = projectService.viewBuses(busNumber, curPage);
        return busDtos;
    }



    @PostMapping("/routes/ajaxforSearchRoute/{userId}")
    @ResponseBody
    public List<RoutesDto> ajaxforSearchRoute(Model model, HttpServletRequest request, @RequestParam("region") String region, @RequestParam("curPage") int curPage) {
        List<RoutesDto> routesDtos = projectService.viewRoutes(region, curPage);
        return routesDtos;
    }

    @PostMapping("/schedule/ajaxforSchedule/{userId}")
    @ResponseBody
    public List<Schedule1Dto> ajaxforDisplaySchedule(@RequestParam("region") String region, @RequestParam("curPage") int curPage, @RequestParam("sort") String sort)
    {
        List<Schedule1Dto> schedule1Dtos = projectService.viewSchedule(region, curPage, sort);
        return schedule1Dtos;
    }


    @PostMapping("/routes/addRoute/{userId}")
    @ResponseBody
    public void addRoute(@Valid @ModelAttribute("RoutesDto") RoutesDto RoutesDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());


        } else {
            projectService.saveRoute(RoutesDto);
        }
    }

    @PostMapping("/routes/editRoute/{userId}")
    @ResponseBody
    public void editRoute(@Valid @ModelAttribute("RoutesDto") RoutesDto RoutesDto, BindingResult bindingResult,  @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());


        } else {
            projectService.editRoute(RoutesDto, id);
        }
    }


    @PostMapping("/routes/deleteRoute/{routeId}/{userId}")
    @ResponseBody
    public void deleteRoute(@PathVariable("routeId") int routeId) {

        projectService.deleteRoute(routeId);

    }

    @GetMapping("/routes/checkRouteUsage/{routeId}/{userId}")
    public ResponseEntity<?> checkRouteUsage( @PathVariable int routeId) {
        boolean isRouteUsed = projectService.isRouteUsedInSchedule(routeId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUsed", isRouteUsed);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/schedule/checkScheduleUsage/{scheduleId}/{userId}")
    public ResponseEntity<?> checkScheduleUsage( @PathVariable int scheduleId) {
        boolean isScheduleUsed = projectService.isScheduleInBooking(scheduleId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUsed", isScheduleUsed);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/schedule/getRoutes/{userId}")
    @ResponseBody
    public List<Routes1Dto> ajaxcallForRoutes() {
         List<Routes1Dto> routes =  projectService.getRoutes();
        return routes;

    }

    @PostMapping("/schedule/getBuses/{userId}")
    @ResponseBody
    public List<Bus1Dto> ajaxcallForBuses() {
        List<Bus1Dto> buses =  projectService.getBuses();
        return buses;

    }

    @PostMapping("/schedule/checkBusAvailability/{userId}")
    @ResponseBody
    public boolean checkBusAvailability(@RequestParam("bus") int busID, @RequestParam("Date") String date, @RequestParam("Time") String Time) {
        boolean available = projectService.checkBusSchedule(busID, date, Time);
        return available;

    }


    @PostMapping("/schedule/editSchedule/{userId}")
    @ResponseBody
    public void editSchedule(@Valid @ModelAttribute("ScheduleDto") ScheduleDto ScheduleDto, BindingResult bindingResult,  @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());


        } else {

            projectService.editSchedule(ScheduleDto, id);

        }
    }

    @PostMapping("/schedule/deleteSchedule/{scheduleId}/{userId}")
    @ResponseBody
    public void deleteSchedule(@PathVariable("scheduleId") int scheduleId) {
        projectService.deleteSchedule(scheduleId);

    }

    @RequestMapping("/myProfile/{userId}")
    public String userProfile(Model model,@PathVariable("userId") int userId)
    {
        String activeString = "active  text-dark";
        model.addAttribute("userId", userId);
        model.addAttribute("active2", activeString);
        List<UserProfile> userProfiles = projectService.userProfileList(userId);
        model.addAttribute("userProfiles", userProfiles);
        return "/admin/myProfile";
    }


    @PostMapping("/myProfile/editProfile/{userId}")
    @ResponseBody
    public void userProfileEdit(@Valid @ModelAttribute("UserProfileDto") UserProfileDto UserProfileDto, BindingResult bindingResult,  @PathVariable("userId") int id) {
        if (bindingResult.hasErrors()) {

            System.out.println(bindingResult.getAllErrors());


        } else {

            projectService.myProfileEdit(UserProfileDto, id);
        }
    }


    @GetMapping ("/dashboard/getSources/{userId}")
    @ResponseBody
    public List<String> ajaxcallForSource(@PathVariable("userId") int userId) {
        List<String> source =  projectService.viewSourceList();
        return source;

    }


    @GetMapping ("/dashboard/getDestination/{userId}")
    @ResponseBody
    public List<String> ajaxcallForDestination(@PathVariable("userId") int userId) {
        List<String> destination =  projectService.viewDestinationList();

        return destination;

    }


    @PostMapping("/search/{userId}")
    public String SearchTicket(@RequestParam("source") String source, @RequestParam("destination") String destination ,@RequestParam("date") String date, @PathVariable("userId") int userId)
    {
        return "redirect:/admin/details/"+source+"/"+destination+"/"+date +"/" +userId;
    }


    @RequestMapping("/details/{source}/{destination}/{date}/{userId}")
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
        return "/admin/ticketDetails";
    }



    @RequestMapping("/success/{source}/{destinaton}/{id}/{userId}")
    public String bookingDetails(@PathVariable("id") int id, @PathVariable("userId") int userId , Model model)
    {
        model.addAttribute("id", id);
        model.addAttribute("userId", userId);
        TicketDto ticketDto = projectService.ticketDetails(id);
        model.addAttribute("t", ticketDto);
        return "/admin/succesBooking";
    }


    @RequestMapping("/myBookingsView/{userId}")
    public String myBookingsView(Model model, HttpServletRequest request, @PathVariable("userId") int userId) {
        String activeString = "active  text-dark";
        model.addAttribute("active6", activeString);
        model.addAttribute("userId", userId);
        return "/admin/myTrips"; // This should be the view name of your JSP page
    }

    @PostMapping("/myBookings/{userId}")
    @ResponseBody
    public List<MyBookingsDto> myBookings(@PathVariable("userId") int userId, @RequestParam("curPage") int curPage, @RequestParam("filterOption") int filterOption) {
        return projectService.myBookings(userId, curPage, filterOption);
    }

    @RequestMapping(value = "/cancelSeat/{bookingDetailId}/{bookingId}/{userId}", method = RequestMethod.POST)
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
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Dear Customer,\n\n")
                    .append("We regret to inform you that your ticket for the following route has been cancelled:\n\n")
                    .append("Route: ").append(route).append("\n")
                    .append("Date of Journey: ").append(date).append("\n")
                    .append("Departure Time: ").append(departureTime).append("\n\n")
                    .append("Seat Number: ").append(seatNumber).append("\n\n")
                    .append("This cancellation was processed on: ").append(cancellationDateTime).append("\n\n")
                    .append("If you have any questions or need further assistance, please do not hesitate to contact our support team.\n\n")
                    .append("We apologize for any inconvenience this may cause.\n\n")
                    .append("Best regards,\n")
                    .append("BusWise\n")
                    .append("Customer Support Team");

            String message = messageBuilder.toString();


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


    @RequestMapping(value = "/cancelBooking/{bookingId}/{userId}", method = RequestMethod.POST)
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
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Dear Customer,\n\n")
                    .append("We regret to inform you that your ticket for the following route has been cancelled:\n\n")
                    .append("Route: ").append(route).append("\n")
                    .append("Date of Journey: ").append(date).append("\n")
                    .append("Departure Time: ").append(departureTime).append("\n\n")
                    .append("This cancellation was processed on: ").append(cancellationDateTime).append("\n\n")
                    .append("If you have any questions or need further assistance, please do not hesitate to contact our support team.\n\n")
                    .append("We apologize for any inconvenience this may cause.\n\n")
                    .append("Best regards,\n")
                    .append("BusWise\n")
                    .append("Customer Support Team");

            String message = messageBuilder.toString();
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

    @GetMapping("/salesReports/{userId}")
    public String salesReports(Model model, @PathVariable("userId") int userId)
    {
        String activeString = "active  text-dark";
        model.addAttribute("active8", activeString);
        model.addAttribute("userId", userId);
        return "/admin/salesReports";
    }


    @GetMapping("/occupancyReports/{userId}")
    public String occupancyReports(Model model, @PathVariable("userId") int userId)
    {
        String activeString = "active  text-dark";
        model.addAttribute("active8", activeString);
        model.addAttribute("userId", userId);
        return "/admin/occupancyReports";
    }



    @GetMapping("/bus-location/{userId}")
    public String busLocation(@PathVariable("userId") int userId, Model model)
    {
        model.addAttribute("userId", userId);
        String activeString = "active  text-dark";
        model.addAttribute("active10", activeString);
        return "/admin/busLocation";
    }

    @PostMapping("/dailySales/{userId}")
    @ResponseBody
    public SalesData fetchDailySales(@RequestParam String date, @PathVariable("userId") int userId) {
        LocalDate parsedDate = LocalDate.parse(date);
        return projectService.getDailySales(parsedDate);
    }

    @PostMapping("/monthlySales/{userId}")
    @ResponseBody
    public SalesData fetchMonthlySales(@RequestParam String date) {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return projectService.getMonthlySalesData(year, month);
    }

    @PostMapping("/yearly-sales/{userId}")
    @ResponseBody
    public SalesData fetchYearlySales(@RequestParam int year) {

        return projectService.getYearlySalesData(year);
    }

    @RequestMapping("/dailySalesDownlaod/{date}/{userId}")
    @ResponseBody
    public String dailySalesDownlod(HttpServletRequest request, @PathVariable("date") String date) {
        return dailySalesPdfService.createDailySalesPdf(request, date);

    }

    @RequestMapping("/monthly-sales-download/{date}/{userId}")
    @ResponseBody
    public String MonthlySalesDownload(HttpServletRequest request, @PathVariable("date") String date) {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        LocalDate date1 = LocalDate.of(year, month, 1);
        return monthlySalesPdfService.createMonthlySalesPdf(request, date1);

    }

    @PostMapping("/year-monthly-sales/{userId}")
    @ResponseBody
    public List<MonthlySalesData> getMonthlySales(@RequestParam("year") int year) {
        return projectService.getMonthlySalesData(year);
    }


    @RequestMapping("/yearly-sales-download/{year}/{userId}")
    @ResponseBody
    public String yearlySalesDownload(HttpServletRequest request, @PathVariable("year") int year) {
        return yearlySalesPdfService.createYearlySalesPdf(request, year);

    }

    @RequestMapping("/daily-occupancy-download/{date}/{userId}")
    @ResponseBody
    public String dailyOccupancyDownload(HttpServletRequest request, @PathVariable("date") String date) {
        return dailyOccupancyReportPdf.createDailyOccupancyPdf(request, date);

    }


    @PostMapping("/daily-occupancy/{userId}")
    @ResponseBody
    public List<OccupancyReportDto> getDailyOccupancy(@RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return projectService.getOccupancyDailyData(localDate);
    }


    @RequestMapping("/monthly-occupancy-download/{date}/{userId}")
    @ResponseBody
    public String monthlyOccupancyDownload(HttpServletRequest request, @PathVariable("date") String date) {

        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return monthlyOccupancyReportPdf.createMonthlyOccupancyPdf(request, year, month);

    }

    @RequestMapping("/yearly-occupancy-download/{year}/{userId}")
    @ResponseBody
    public String yearlyOccupancyDownload(HttpServletRequest request, @PathVariable("year") int year) {

        return yearlyOccupancyReportPdf.createYearlyOccupancyPdf(request, year);

    }


    @PostMapping("/monthly-occupancy/{userId}")
    @ResponseBody
    public List<OccupancyReportDto> fetchMonthlyOccupancy(@RequestParam String date) {
        return projectService.getRoutesWiseMonthlyOccupancy(date);
    }

    @PostMapping("/yearly-occupancy/{userId}")
    @ResponseBody
    public List<OccupancyReportDto> fetchMonthlyOccupancy(@RequestParam int year) {
        return projectService.getRoutesWiseYearlyOccupancy(year);
    }

}
