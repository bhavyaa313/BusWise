package buswise.controller;

import buswise.dao.BookingDao;
import buswise.dao.BusesDao;
import buswise.dao.UserDao;
import buswise.dao.VerificationDao;
import buswise.dto.*;
import buswise.model.*;
import buswise.service.*;


import com.itextpdf.forms.xfdf.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")

public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BusesDao busesDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private EmailService emailService;




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
        List<String> busNumbers = busesDao.getBusNumber();
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
        boolean isBusUsed = projectService.isRouteUsedInSchedule(busId);
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
        List<UserProfile> userProfiles = userDao.userProfiles(userId);
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

    @RequestMapping("/cancelSeat/{bookingDetailId}/{bookingId}/{userId}")
    @ResponseBody
    public void cancelSeat( @PathVariable("bookingDetailId") int bookingDetailId, @PathVariable("userId") int userid, @PathVariable("bookingId") int bookingId)
    {
        List<Bookings> bookings = bookingDao.getBookingById(bookingId);
        List<BookingDetails> bookingDetails = bookingDao.getBookingDetailsByBookingDetailId(bookingDetailId);
        BookingDetails bookingDetails1 = bookingDetails.get(0);


        Bookings bookings1 = bookings.get(0);
        String email = bookings1.getEmail();
        String route = bookings1.getSelectedSource() + " to " + bookings1.getSelectedDestination();
        String date = bookings1.getScheduleId().getTripDate().toString();
        String departureTime = bookings1.getDepatureTime();
        String cancellationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String seatNumber = bookingDetails1.getSeatNumber();

        String subject = "Cancellation of Ticket";

        String message = "Dear Customer,\n\n" +
                "We regret to inform you that your ticket  for the following route has been cancelled:\n\n" +
                "Route: " + route + "\n" +
                "Date of Journey: " + date + "\n" +
                "Departure Time: " + departureTime + "\n\n" +
                "Seat Number:" + seatNumber + "\n\n"+
                "This cancellation was processed on: " + cancellationDateTime + "\n\n" +
                "If you have any questions or need further assistance, please do not hesitate to contact our support team.\n\n" +
                "We apologize for any inconvenience this may cause.\n\n" +
                "Best regards,\n" +
                "BusWise\n" +
                "Customer Support Team";

        emailService.send(email, subject, message);
        projectService.cancelSeat(bookingDetailId,userid, email);
    }

    @RequestMapping("/cancelBooking/{bookingId}/{userId}")
    @ResponseBody
    public void cancelBooking( @PathVariable("bookingId") int bookingId, @PathVariable("userId") int userid)
    {
        List<Bookings> bookings = bookingDao.getBookingById(bookingId);
        if (bookings != null && !bookings.isEmpty()) {
            Bookings bookings1 = bookings.get(0);
            String email = bookings1.getEmail();
            String route = bookings1.getSelectedSource() + " to " + bookings1.getSelectedDestination();
            String date = bookings1.getScheduleId().getTripDate().toString();
            String departureTime = bookings1.getDepatureTime();
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
            emailService.send(email, subject, message);
            projectService.cancelBooking(bookingId,userid, email);
        }


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

}
