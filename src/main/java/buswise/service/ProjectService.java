package buswise.service;

import buswise.dao.*;
import buswise.dto.*;
import buswise.exceptions.ResourceNotFoundException;
import buswise.helper.PasswordHash;
import buswise.helper.TokenGenerator;
import buswise.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProjectService {

    @Autowired
    private PasswordHash passwordHash;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BusesDao busesDao;

    @Autowired
    private RoutesDao routesDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationDao verificationDao;

    @Autowired
    private WalletDao walletDao;


    public boolean login(LoginDto loginDto) throws NoSuchAlgorithmException {

        String email = loginDto.getEmail();
        String password = loginDto.getPassword1();
        List<User> userList = userDao.checkEmail(email);
        if (userList.size() == 0) {
            return false;
        } else {
            User user = userList.get(0);
            String passwordHashed = user.getPassword();
            byte[] salt = user.getSalt();
            String hash = passwordHash.getSecurePassword(password, salt);
            if (passwordHashed.equals(hash)) {
                return true;
            } else {
                return false;
            }


        }

    }


    public boolean checkEmail(String email) {
        List<User> userList = userDao.checkEmail(email);
        if (userList.size() == 0) {
            return false;
        } else {
            return true;
        }

    }

    public List<BusDto> viewBuses(String busNumber, int curPage) {
        List<BusDto> busDtos = new ArrayList<>();
        try {
            int startIndex = ((curPage - 1) * 5);
            int endIndex = 5;

            List<Buses> buses = busesDao.getBusesSearch(busNumber, startIndex, endIndex);
            long count = busesDao.getBusesSearchCount(busNumber);


            for (Buses bus : buses) {
                BusDto busDto = new BusDto();
                busDto.setBusId(bus.getBusId());
                busDto.setBusNumber(bus.getBusNumber());
                busDto.setCount(count);
                busDto.setModifiedDate(bus.getModifiedDate().toLocalDate().toString());
                busDto.setSeatingCapacity(bus.getSeatingCapacity());
                busDto.setBusType(bus.getBusType());
                busDtos.add(busDto);
            }
        } catch (Exception e) {
            System.err.println("Error occurred while fetching buses: " + e.getMessage());
            e.printStackTrace();

        }
        return busDtos;
    }


    public List<RoutesDto> viewRoutes(String region, int curPage) {
        List<RoutesDto> routesDtos = new ArrayList<>();
        try {
            int startIndex = ((curPage - 1) * 6);
            int endIndex = 6;

            List<Routes> routes = routesDao.getRoutesSearch(region, startIndex, endIndex);
            long count = routesDao.getRoutesSearchCount(region);


            for (Routes route : routes) {
                RoutesDto routesDto = new RoutesDto();
                routesDto.setRouteId(route.getRouteId());
                routesDto.setSource(route.getSource());
                routesDto.setDestination(route.getDestination());
                routesDto.setDistance(route.getDistance());
                routesDto.setCount(count);

                List<String> subroutes = routesDao.getsubRoutes(route);
                List<Integer> subrouteDistance = routesDao.distanceSubRoute(route);
                List<Integer> subrouteSequecne = routesDao.sequeceSubRoute(route);
                List<Integer> idSubroute = routesDao.idSubRoute(route);


                routesDto.setSequence(subrouteSequecne);
                routesDto.setName(subroutes);
                routesDto.setDistancesub(subrouteDistance);
                routesDto.setSubrouteId(idSubroute);
                routesDto.setSubroutesCount(route.getNoOfSubroutes());

                String subRoutesString = subroutes.toString();
                String subRoutesString1 = subRoutesString.substring(1, subRoutesString.length() - 1);
                routesDto.setSubRoutes(subRoutesString1);

                routesDtos.add(routesDto);
            }
        } catch (Exception e) {
            System.err.println("Error occurred while fetching routes: " + e.getMessage());
            e.printStackTrace();

        }
        return routesDtos;
    }


    public List<Schedule1Dto> viewSchedule(String region, int curPage, String sort) {
        List<Schedule1Dto> schedule1Dtos = new ArrayList<>();
        try {
            int startIndex = ((curPage - 1) * 6);
            int endIndex = 6;


            List<Schedules> schedules = scheduleDao.getRoutesSearch(region, startIndex, endIndex, sort);
            long count = scheduleDao.getRoutesSearchCount(region);


            for (Schedules schedule : schedules) {
                Schedule1Dto schedule1Dto = new Schedule1Dto();
                schedule1Dto.setScheduleId(schedule.getScheduleId());
                schedule1Dto.setSource(schedule.getRouteId().getSource());
                schedule1Dto.setDestination(schedule.getRouteId().getDestination());
                schedule1Dto.setCreatedDate(schedule.getCreatedDate().toLocalDate().toString());

                String date = schedule.getTripDate().toString();
                String time = schedule.getArrivalTime().toString();

                List<String> subroutes = routesDao.getsubRoutes(schedule.getRouteId());
                String subRoutesString = subroutes.toString();
                String subRoutesString1 = subRoutesString.substring(1, subRoutesString.length() - 1);

                int distance = schedule.getRouteId().getDistance();
                long speed = 50L;
                long time1 = distance / speed;
                String duration = time1 + " hours " + (time1 % 24) + " minutes";
                long fare = distance * 2L;
                String busDetails = schedule.getBusId().getBusNumber() + " (" + schedule.getBusId().getBusType() + ") ";

                schedule1Dto.setDate(date);
                schedule1Dto.setDuration(duration);
                schedule1Dto.setFare(fare);
                schedule1Dto.setBusDetails(busDetails);
                schedule1Dto.setSubroutes("( via " + subRoutesString1 + ")");
                schedule1Dto.setTime(time);
                schedule1Dto.setCount(count);
                schedule1Dto.setBusId(schedule.getBusId().getBusId());
                schedule1Dto.setRouteId(schedule.getRouteId().getRouteId());

                schedule1Dtos.add(schedule1Dto);
            }
        } catch (Exception e) {
            System.err.println("Error occurred while fetching schedules: " + e.getMessage());
            e.printStackTrace();
        }
        return schedule1Dtos;
    }


    public void addBus(BusDto busDto) {
        Buses buses = new Buses();
        buses.setBusNumber(busDto.getBusNumber());
        buses.setDeleted(false);
        buses.setModifiedDate(LocalDateTime.now());
        buses.setSeatingCapacity(busDto.getSeatingCapacity());
        buses.setBusType(busDto.getBusType());
        buses.setCreatedDate(LocalDateTime.now());
        busesDao.busSave(buses);
    }


    public void editBus(BusDto busDto, int id) {
        List<Buses> busesList = busesDao.getBusbyId(id);

        if (busesList.isEmpty()) {
            System.err.println("No bus found with ID: " + id);
            return;
        }

        Buses buses = busesList.get(0);
        buses.setBusNumber(busDto.getBusNumber());
        buses.setModifiedDate(LocalDateTime.now());
        buses.setBusType(busDto.getBusType());
        buses.setSeatingCapacity(busDto.getSeatingCapacity());
        busesDao.busUpdate(buses);
    }


    public void deleteBus(int busId) {
        List<Buses> busesList = busesDao.getBusbyId(busId);

        if (busesList.isEmpty()) {
            System.err.println("No bus found with ID: " + busId);
            return;
        }

        Buses buses = busesList.get(0);
        buses.setDeleted(true);
        buses.setModifiedDate(LocalDateTime.now());
        busesDao.busUpdate(buses);
    }


    public void registerUser(RegisterDto registerDto) throws NoSuchAlgorithmException {
        User user = new User();
        user.setCreatedDate(LocalDateTime.now());
        user.setDeleted(false);
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());
        List<Role> roles = userDao.getRolebyRoleId(2);
        Role role = roles.get(0);
        user.setRoleId(role);

        String password = registerDto.getPassword();
        byte[] salt = passwordHash.getSalt();
        String passwordHashed = passwordHash.getSecurePassword(password, salt);

        user.setPassword(passwordHashed);
        user.setSalt(salt);
        userDao.userSave(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user);
        userProfile.setDeleted(false);
        userProfile.setFirstName(registerDto.getFirstName());
        userProfile.setLastName(registerDto.getLastName());
        userProfile.setCreatedDate(LocalDateTime.now());
        userProfile.setCity(registerDto.getCity());
        userProfile.setAge(registerDto.getAge());
        userProfile.setState(registerDto.getState());
        userDao.userProfileSave(userProfile);

        Wallet newWallet = new Wallet();
        newWallet.setUser(user);
        newWallet.setBalance(5000.0);
        walletDao.walletSave(newWallet);
        Transaction transaction = new Transaction();
        transaction.setAmount(5000);
        transaction.setDate(LocalDateTime.now());
        transaction.setType("credit");
        transaction.setWallet(newWallet);
        transaction.setDescription("New User Bonus");
        walletDao.transactionSave(transaction);

    }

    public boolean checkEmailRegister(String email){
        List<User> userList =    userDao.checkEmail(email);
        if(userList.isEmpty()){
            return true;
        }
        return false;

    }


//    public void saveRoute(RoutesDto routesDto) {
//        Routes routes = new Routes();
//        routes.setSource(routesDto.getSource());
//        routes.setDistance(routesDto.getDistance());
//        routes.setDestination(routesDto.getDestination());
//        routes.setNoOfSubroutes(routesDto.getSubroutesCount());
//        routes.setCreatedDate(LocalDateTime.now());
//        routesDao.routeSave(routes);
//        List<String> name = routesDto.getName();
//        List<Integer> distancesub = routesDto.getDistancesub();
//        List<Integer> sequence = routesDto.getSequence();
//
//
//        for (int i = 0; i < routesDto.getSubroutesCount(); i++) {
//
//            SubRoute subRoute = new SubRoute();
//            subRoute.setRouteId(routes);
//            subRoute.setDistance(distancesub.get(i));
//            subRoute.setStop(name.get(i));
//            subRoute.setSequence(sequence.get(i));
//            routesDao.subrouteSave(subRoute);
//
//
//        }
//
//
//    }


    public void saveRoute(RoutesDto routesDto) {
        Routes routes = new Routes();
        routes.setSource(routesDto.getSource());
        routes.setDistance(routesDto.getDistance());
        routes.setDestination(routesDto.getDestination());
        routes.setNoOfSubroutes(routesDto.getSubroutesCount() != null ? routesDto.getSubroutesCount() : 0);
        routes.setCreatedDate(LocalDateTime.now());
        routesDao.routeSave(routes);

        List<String> name = routesDto.getName();
        List<Integer> distancesub = routesDto.getDistancesub();
        List<Integer> sequence = routesDto.getSequence();


        if (routesDto.getSubroutesCount() != null && routesDto.getSubroutesCount() > 0 &&
                name != null && !name.isEmpty() &&
                distancesub != null && !distancesub.isEmpty() &&
                sequence != null && !sequence.isEmpty()) {

            for (int i = 0; i < routesDto.getSubroutesCount(); i++) {
                SubRoute subRoute = new SubRoute();
                subRoute.setRouteId(routes);
                subRoute.setDistance(distancesub.get(i));
                subRoute.setStop(name.get(i));
                subRoute.setSequence(sequence.get(i));
                routesDao.subrouteSave(subRoute);
            }
        } else {

            System.out.println("No subroutes to save.");
        }
    }




    public void editRoute(RoutesDto routesDto, int routeId) {
        List<Routes> routesList = routesDao.getRoutesById(routeId);

        if (routesList.isEmpty()) {
            System.err.println("No route found with ID: " + routeId);
            return;
        }

        Routes routes = routesList.get(0);
        routes.setSource(routesDto.getSource());
        routes.setDistance(routesDto.getDistance());
        routes.setDestination(routesDto.getDestination());
        routes.setNoOfSubroutes(routesDto.getSubroutesCount());
        routes.setModifiedDate(LocalDateTime.now());
        routesDao.routeUpdate(routes);

        List<String> name = routesDto.getName();
        List<Integer> distancesub = routesDto.getDistancesub();
        List<Integer> sequence = routesDto.getSequence();
        List<SubRoute> subRoutes = routesDao.getSubRoutesByRouteID(routes);

        for (SubRoute subRoute : subRoutes) {
            subRoute.setDeleted(true);
            routesDao.subrouteUpdate(subRoute);
        }

        for (int i = 0; i < routesDto.getSubroutesCount(); i++) {
            SubRoute subRoute = new SubRoute();
            subRoute.setRouteId(routes);
            subRoute.setDistance(distancesub.get(i));
            subRoute.setStop(name.get(i));
            subRoute.setSequence(sequence.get(i));
            routesDao.subrouteSave(subRoute);
        }
    }


    public void deleteRoute(int routeId) {
        List<Routes> routesList = routesDao.getRoutesById(routeId);

        if (routesList.isEmpty()) {
            System.err.println("No route found with ID: " + routeId);
            return;
        }

        Routes routes = routesList.get(0);
        routes.setDeleted(true);
        routes.setModifiedDate(LocalDateTime.now());
        routesDao.routeUpdate(routes);

        List<SubRoute> subRoutes = routesDao.getSubRoutesByRouteID(routes);

        for (SubRoute subRoute : subRoutes) {
            subRoute.setDeleted(true);
            routesDao.subrouteUpdate(subRoute);
        }
    }


    public boolean isRouteUsedInSchedule(int routeId) {
        List<Schedules> schedules = scheduleDao.findByRouteId(routeId);
        return !schedules.isEmpty();
    }

    public boolean isScheduleInBooking(int scheduleId) {
        List<Bookings> bookings = bookingDao.getBookingsVyScheduleId(scheduleId);
        return !bookings.isEmpty();
    }

    public boolean isBusUsedInSchedule(int busId) {
        List<Schedules> schedules = scheduleDao.findByBusId(busId);
        return !schedules.isEmpty();
    }


    public List<Routes1Dto> getRoutes() {
        List<Routes> routes = routesDao.getRoutesAll();
        List<Routes1Dto> routes1Dto = new ArrayList<Routes1Dto>();
        for (int i = 0; i < routes.size(); i++) {
            Routes routes1 = routes.get(i);
            Routes1Dto routes1Dto1 = new Routes1Dto();
            routes1Dto1.setRouteId(routes1.getRouteId());
            List<String> subroutes = routesDao.getsubRoutes(routes1);
            String allSubroutes = subroutes.toString();
            String source = routes1.getSource();
            String destination = routes1.getDestination();
            int distance = routes1.getDistance();
            long speed = (long) 50;
            long time = distance / speed;
            String formattedTime = time + " hours " + (time % 24) + " minutes";
            routes1Dto1.setDuration(formattedTime);
            long fare = distance * 2;
            routes1Dto1.setFare(fare);
            String routeDetails = source + " to " + destination + "\nvia " + allSubroutes;
            routes1Dto1.setRouteDetails(routeDetails);

            routes1Dto.add(routes1Dto1);

        }
        return routes1Dto;

    }


    public List<Bus1Dto> getBuses() {
        List<Buses> buses = busesDao.getBusAll();
        List<Bus1Dto> bus1Dtos = new ArrayList<Bus1Dto>();
        for (int i = 0; i < buses.size(); i++) {
            Buses buses1 = buses.get(i);
            Bus1Dto bus1Dto = new Bus1Dto();
            bus1Dto.setBusId(buses1.getBusId());
            bus1Dto.setBusNumber(buses1.getBusNumber());
            String type = buses1.getBusType();
            bus1Dto.setBusDetails(buses1.getBusNumber() + " " + "(" + type + ")");
            bus1Dtos.add(bus1Dto);
        }
        return bus1Dtos;

    }


    public void addSchedule(ScheduleDto scheduleDto) {
        Schedules schedules = new Schedules();
        List<Buses> buses = busesDao.getBusbyId(scheduleDto.getBus());
        Buses buses1 = buses.get(0);
        schedules.setBusId(buses1);
        List<Routes> routes = routesDao.getRoutesById(scheduleDto.getRoute());
        Routes routes1 = routes.get(0);
        schedules.setRouteId(routes1);
        schedules.setCreatedDate(LocalDateTime.now());
        schedules.setAvailableSeats(buses1.getSeatingCapacity());
        String receivedTimeString = scheduleDto.getTime();
        String timeString = receivedTimeString;
        DateTimeFormatter parserFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(timeString, parserFormatter);
        schedules.setArrivalTime(localTime);
        String dateString = scheduleDto.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        schedules.setTripDate(localDate);
        scheduleDao.scheduleSave(schedules);
    }


    public void editSchedule(ScheduleDto scheduleDto, int id) {
        List<Schedules> schedulesList = scheduleDao.getScheduleByID(id);

        if (schedulesList.isEmpty()) {
            System.err.println("No schedule found with ID: " + id);
            return;
        }

        Schedules schedules = schedulesList.get(0);
        List<Buses> busesList = busesDao.getBusbyId(scheduleDto.getBus());

        if (busesList.isEmpty()) {
            System.err.println("No bus found with ID: " + scheduleDto.getBus());
            return;
        }

        Buses buses = busesList.get(0);
        schedules.setBusId(buses);
        schedules.setModifiedDate(LocalDateTime.now());

        String receivedTimeString = scheduleDto.getTime();
        DateTimeFormatter parserFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(receivedTimeString, parserFormatter);
        schedules.setArrivalTime(localTime);

        scheduleDao.scheduleUpdate(schedules);
    }


    public boolean checkBusSchedule(int busId, String date, String time)
    {
        String receivedTimeString = time;
        String timeString = receivedTimeString;
        DateTimeFormatter parserFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(timeString, parserFormatter);
        String dateString = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        boolean check = scheduleDao.checkBus(busId, localDate, localTime);
        return check;
    }

    public void deleteSchedule(int scheduleId) {
        List<Schedules> schedulesList = scheduleDao.getScheduleByID(scheduleId);

        if (schedulesList.isEmpty()) {
            System.err.println("No schedule found with ID: " + scheduleId);
            return;
        }

        Schedules schedules = schedulesList.get(0);
        schedules.setDeleted(true);
        schedules.setModifiedDate(LocalDateTime.now());

        scheduleDao.scheduleUpdate(schedules);
    }

    public void myProfileEdit(UserProfileDto userProfileDto, int id) {
        List<UserProfile> userProfiles = userDao.userProfiles(id);

        if (userProfiles.isEmpty()) {
            System.err.println("No user profile found with ID: " + id);
            return;
        }

        UserProfile userProfile = userProfiles.get(0);
        userProfile.setModifiedDate(LocalDateTime.now());
        userProfile.setCity(userProfileDto.getCity());
        userProfile.setState(userProfileDto.getState());
        userProfile.setFirstName(userProfileDto.getFirstName());
        userProfile.setLastName(userProfileDto.getLastName());
        userProfile.getUserId().setPhone(userProfileDto.getPhone());
        userProfile.getUserId().setModifiedDate(LocalDateTime.now());
        userProfile.setAge(userProfileDto.getAge());

        userDao.userProfileUpdate(userProfile);
    }


    public List<ViewSourceDto> viewSource()
    {
        List<Routes> routes = routesDao.getRoutesAll();
        List<ViewSourceDto> viewSourceDtos = new ArrayList<ViewSourceDto>();
        for(int i=0; i<routes.size(); i++)
        {
            Routes routes1 = routes.get(i);
            ViewSourceDto viewSourceDto = new ViewSourceDto();
            viewSourceDto.setId(routes1.getRouteId());
            viewSourceDto.setSource(routes1.getSource());
            viewSourceDtos.add(viewSourceDto);
        }
        List<SubRoute> subRoutes = routesDao.getSubRoutesAll();

        for(int i = 0 ;  i< subRoutes.size() ; i++ )
        {
            SubRoute subRoute = subRoutes.get(i);
            ViewSourceDto viewSourceDto1 = new ViewSourceDto();
            viewSourceDto1.setId(subRoute.getId());
            viewSourceDto1.setSource(subRoute.getStop());
            viewSourceDtos.add(viewSourceDto1);
        }
        return  viewSourceDtos;

    }

    public List<String> viewSourceList()
    {

        List<Routes> routes = routesDao.getRoutesAll();
        List<String> list = new ArrayList<>();
        for(int i=0; i<routes.size(); i++)
        {
            Routes routes1 = routes.get(i);
            String z =   routes1.getSource().toUpperCase();
            list.add(z);
        }
        List<SubRoute> subRoutes = routesDao.getSubRoutesAll();

        for(int i = 0 ;  i< subRoutes.size() ; i++ )
        {
            SubRoute subRoute = subRoutes.get(i);
            String y=  subRoute.getStop().toUpperCase();
            list.add(y);

        }
        Set<String> set = new HashSet<>(list);
        List<String> uniqueList = new ArrayList<>(set);
        return  uniqueList;
    }



    public List<String> viewDestinationList()
    {

        List<Routes> routes = routesDao.getRoutesAll();
        List<String> list = new ArrayList<>();
        for(int i=0; i<routes.size(); i++)
        {
            Routes routes1 = routes.get(i);
            String z =   routes1.getDestination().toUpperCase();
            list.add(z);
        }
        List<SubRoute> subRoutes = routesDao.getSubRoutesAll();

        for(int i = 0 ;  i< subRoutes.size() ; i++ )
        {
            SubRoute subRoute = subRoutes.get(i);
            String y=  subRoute.getStop().toUpperCase();
            list.add(y);

        }

        Set<String> set = new HashSet<>(list);
        List<String> uniqueList = new ArrayList<>(set);
        return uniqueList;

    }

    public List<Schedule1Dto> ticketDteails(String source1, String destination1, String date) {
        String source = source1.toUpperCase();
        String destination = destination1.toUpperCase();
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        List<Schedules> schedules = scheduleDao.getSchedules(source, destination, date);
        List<Schedules> schedulesBySubroute = scheduleDao.getSchedulesBySubroute(source,destination,date);
        List<Schedules> schedulebySubrouteandRoute = scheduleDao.getSchedulesBySubrouteRoute(source,destination,date);
        List<Schedules> getSourceToSubroute = scheduleDao.getSourceToSubroute(source, destination, date);
        List<Schedule1Dto> schedule1Dtos = new ArrayList<Schedule1Dto>();
        if(!schedules.isEmpty()  ) {

            for (Schedules schedules1 : schedules) {
                Schedule1Dto schedule1Dto = new Schedule1Dto();
                schedule1Dto.setDate(date);
                schedule1Dto.setScheduleId(schedules1.getScheduleId());
                schedule1Dto.setTime(schedules1.getArrivalTime().toString());
                schedule1Dto.setSource(schedules1.getRouteId().getSource());
                schedule1Dto.setDestination(schedules1.getRouteId().getDestination());
                schedule1Dto.setYourSource(source);
                schedule1Dto.setYourdestination(destination);

//              List<String> seatBooked = scheduleDao.seatsBooked(schedules1.getScheduleId(), source1, destination1);
                List<String> seatBooked = scheduleDao.getBookedSeats(schedules1.getScheduleId(), source1, destination1);
                schedule1Dto.setBookedSeats(seatBooked);

                List<String> subroutes = routesDao.getsubRoutes(schedules1.getRouteId());
                String subRoutesString = String.join(", ", subroutes);
                schedule1Dto.setSubroutes("( via " + subRoutesString + ")");

                double distance = schedules1.getRouteId().getDistance();
                long fare = (long) distance * 2L; // Example fare calculation
                double speed = 50.0; // Speed in km/h
                double timeInHours = distance / speed;
                int totalMinutes = (int) (timeInHours * 60);
                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;
                String duration = hours + " hours " + minutes + " minutes";

                schedule1Dto.setDuration(duration);
                schedule1Dto.setFare(fare);
                schedule1Dto.setBusDetails(schedules1.getBusId().getBusType());
                schedule1Dto.setBusId(schedules1.getBusId().getBusId());
                schedule1Dto.setRouteId(schedules1.getRouteId().getRouteId());
                schedule1Dto.setNoOfSeats(schedules1.getBusId().getSeatingCapacity());
                schedule1Dto.setNoOfSeatsAvailable(schedules1.getAvailableSeats());


                if (schedules1.getTripDate().isEqual(today) && schedules1.getArrivalTime().isAfter(currentTime)) {
                    schedule1Dtos.add(schedule1Dto);
                } else if (!schedules1.getTripDate().isEqual(today)) {

                    schedule1Dtos.add(schedule1Dto);
                }
            }

        }

        if (!schedulesBySubroute.isEmpty())
        {
            for (int i = 0; i < schedulesBySubroute.size(); i++) {
                Schedule1Dto schedule1Dto = new Schedule1Dto();
                Schedules schedules1 = schedulesBySubroute.get(i);
                schedule1Dto.setScheduleId(schedules1.getScheduleId());
                schedule1Dto.setDate(date);
//                List<String> seatBooked = scheduleDao.seatsBooked(schedules1.getScheduleId(), source1, destination1);
                List<String> seatBooked = scheduleDao.getBookedSeats(schedules1.getScheduleId(), source1, destination1);
                schedule1Dto.setBookedSeats(seatBooked);
                schedule1Dto.setSource(schedules1.getRouteId().getSource());
                schedule1Dto.setDestination(schedules1.getRouteId().getDestination());
                schedule1Dto.setYourSource(source);
                schedule1Dto.setYourdestination(destination);
                List<String> subroutes = routesDao.getsubRoutes(schedules1.getRouteId());
                String subroutes1 = subroutes.toString();
                String subRoutesString1 = subroutes.toString().substring(1, subroutes1.length() - 1);
                int sourceDistance = scheduleDao.getSubrouteDistance(schedules1.getRouteId().getRouteId(), source);
                int destinationDistance = scheduleDao.getSubrouteDistance(schedules1.getRouteId().getRouteId(), destination);
                double distance = destinationDistance-sourceDistance;
                double speed = (double) 50;
                double timeInHours = distance / speed;
                int totalMinutes = (int) (timeInHours * 60);
                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;
                String duration = hours + " hours " + minutes + " minutes";
                double hoursFromOriginToSource = sourceDistance/speed;
                int totalMinutesFromOriginToSource = (int) (hoursFromOriginToSource * 60);
                LocalTime TimeDepatureFromSource = schedules1.getArrivalTime();
                LocalTime newTime = TimeDepatureFromSource.plusMinutes(totalMinutesFromOriginToSource);
                schedule1Dto.setTime(newTime.toString());
                long fare =(long) distance * 2L;
                schedule1Dto.setDuration(duration);
                schedule1Dto.setFare(fare);
                schedule1Dto.setBusDetails(schedules1.getBusId().getBusType());
                schedule1Dto.setSubroutes("( via " + subRoutesString1 + ")");
                schedule1Dto.setBusId(schedules1.getBusId().getBusId());
                schedule1Dto.setRouteId(schedules1.getRouteId().getRouteId());
                schedule1Dto.setNoOfSeats(schedules1.getBusId().getSeatingCapacity());
                schedule1Dto.setNoOfSeatsAvailable(schedules1.getAvailableSeats());
                if (schedules1.getTripDate().isEqual(today) && schedules1.getArrivalTime().isAfter(currentTime)) {
                    schedule1Dtos.add(schedule1Dto);
                } else if (!schedules1.getTripDate().isEqual(today)) {

                    schedule1Dtos.add(schedule1Dto);
                }
            }


        } if (!schedulebySubrouteandRoute.isEmpty()) {
            for (int i = 0; i < schedulebySubrouteandRoute.size(); i++) {
                Schedule1Dto schedule1Dto = new Schedule1Dto();
                Schedules schedules1 = schedulebySubrouteandRoute.get(i);
                schedule1Dto.setScheduleId(schedules1.getScheduleId());
                schedule1Dto.setDate(date);
//                List<String> seatBooked = scheduleDao.seatsBooked(schedules1.getScheduleId(), source1, destination1);
                List<String> seatBooked = scheduleDao.getBookedSeats(schedules1.getScheduleId(), source1, destination1);
                schedule1Dto.setBookedSeats(seatBooked);
                schedule1Dto.setTime(schedules1.getArrivalTime().toString());
                schedule1Dto.setSource(schedules1.getRouteId().getSource());
                schedule1Dto.setDestination(schedules1.getRouteId().getDestination());
                schedule1Dto.setYourSource(source1);
                schedule1Dto.setYourdestination(destination1);
                List<String> subroutes = routesDao.getsubRoutes(schedules1.getRouteId());
                String subroutes1 = subroutes.toString();
                String subRoutesString1 = subroutes.toString().substring(1, subroutes1.length() - 1);
                int sourceDistance = scheduleDao.getSubrouteDistance(schedules1.getRouteId().getRouteId(), source);
                int destinationDistance = schedules1.getRouteId().getDistance();
                double distance = destinationDistance-sourceDistance;
                double speed = (double) 50;
                double timeInHours = distance / speed;
                int totalMinutes = (int) (timeInHours * 60);
                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;
                String duration = hours + " hours " + minutes + " minutes";
                double hoursFromOriginToSource = sourceDistance/speed;
                int totalMinutesFromOriginToSource = (int) (hoursFromOriginToSource * 60);
                LocalTime TimeDepatureFromSource = schedules1.getArrivalTime();
                LocalTime newTime = TimeDepatureFromSource.plusMinutes(totalMinutesFromOriginToSource);
                schedule1Dto.setTime(newTime.toString());
                long fare = (long) distance * 2L;
                schedule1Dto.setDuration(duration);
                schedule1Dto.setFare(fare);
                schedule1Dto.setBusDetails(schedules1.getBusId().getBusType());
                schedule1Dto.setSubroutes("( via " + subRoutesString1 + ")");
                schedule1Dto.setBusId(schedules1.getBusId().getBusId());
                schedule1Dto.setRouteId(schedules1.getRouteId().getRouteId());
                schedule1Dto.setNoOfSeats(schedules1.getBusId().getSeatingCapacity());
                schedule1Dto.setNoOfSeatsAvailable(schedules1.getAvailableSeats());
                if (schedules1.getTripDate().isEqual(today) && schedules1.getArrivalTime().isAfter(currentTime)) {
                    schedule1Dtos.add(schedule1Dto);
                } else if (!schedules1.getTripDate().isEqual(today)) {

                    schedule1Dtos.add(schedule1Dto);
                }
            }
        }


        if (!getSourceToSubroute.isEmpty()) {
            for (int i = 0; i < getSourceToSubroute.size(); i++) {
                Schedule1Dto schedule1Dto = new Schedule1Dto();
                Schedules schedules1 = getSourceToSubroute.get(i);
                schedule1Dto.setDate(date);
//                List<String> seatBooked = scheduleDao.seatsBooked(schedules1.getScheduleId(), source1, destination1);

                List<String> seatBooked = scheduleDao.getBookedSeats(schedules1.getScheduleId(), source1, destination1);

                schedule1Dto.setBookedSeats(seatBooked);
                schedule1Dto.setTime(schedules1.getArrivalTime().toString());
                schedule1Dto.setScheduleId(schedules1.getScheduleId());
                schedule1Dto.setSource(schedules1.getRouteId().getSource());
                schedule1Dto.setDestination(schedules1.getRouteId().getDestination());
                schedule1Dto.setYourSource(source);
                schedule1Dto.setYourdestination(destination);
                List<String> subroutes = routesDao.getsubRoutes(schedules1.getRouteId());
                String subroutes1 = subroutes.toString();
                String subRoutesString1 = subroutes.toString().substring(1, subroutes1.length() - 1);
                double distance = scheduleDao.getSubrouteDistance(schedules1.getRouteId().getRouteId(), destination);
                double speed = (double) 50;
                double timeInHours = distance / speed;
                int totalMinutes = (int) (timeInHours * 60);
                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;
                String duration = hours + " hours " + minutes + " minutes";
                long fare = (long) distance * 2L;
                schedule1Dto.setDuration(duration);
                schedule1Dto.setFare(fare);
                schedule1Dto.setBusDetails(schedules1.getBusId().getBusType());
                schedule1Dto.setSubroutes("( via " + subRoutesString1 + ")");
                schedule1Dto.setBusId(schedules1.getBusId().getBusId());
                schedule1Dto.setRouteId(schedules1.getRouteId().getRouteId());
                schedule1Dto.setNoOfSeats(schedules1.getBusId().getSeatingCapacity());
                schedule1Dto.setNoOfSeatsAvailable(schedules1.getAvailableSeats());
                if (schedules1.getTripDate().isEqual(today) && schedules1.getArrivalTime().isAfter(currentTime)) {
                    schedule1Dtos.add(schedule1Dto);
                } else if (!schedules1.getTripDate().isEqual(today)) {

                    schedule1Dtos.add(schedule1Dto);
                }
            }

        }

        return schedule1Dtos;
    }

    public int saveBookingDetails(BookTicketDto bookTicketDto, String source, String destination, int userID) {



        String x  =bookTicketDto.getScheduleId();
        int id =   Integer.parseInt(x);
        List<Schedules> schedules = scheduleDao.getScheduleByID(id);
        Schedules schedules1 = schedules.get(0);
        List<User> userList = userDao.getUserbyUserId(userID);
        User user = userList.get(0);
        Bookings bookings = new Bookings();
        bookings.setBookingDate(LocalDateTime.now());
        bookings.setEmail(bookTicketDto.getEmail());
        bookings.setPhone(bookTicketDto.getPhone());
        bookings.setCreatedDate(LocalDateTime.now());
        bookings.setScheduleId(schedules1);
        bookings.setTotalAmount(bookTicketDto.getAmount());
        bookings.setSelectedSource(source);
        bookings.setSelectedDestination(destination);
        bookings.setDepatureTime(bookTicketDto.getDepatureTime());
        bookings.setBooked(true);
        bookings.setUserId(user);
        List<String> name = bookTicketDto.getName();
        List<Integer> age = bookTicketDto.getAge();
        List<String> seats = bookTicketDto.getSeat();
        List<String> gender = bookTicketDto.getGender();
        int count = seats.size();
        int exCount = schedules1.getAvailableSeats();
        schedules1.setAvailableSeats(exCount-count);
        scheduleDao.scheduleUpdate(schedules1);
        bookingDao.bookingSave(bookings);
        List<Bookings> bookings1 = bookingDao.getBooking();
        Bookings bookings2 = bookings1.get(bookings1.size()-1);
        int bookingId = bookings2.getBookingId();


        for (int i = 0; i < seats.size(); i++) {

            BookingDetails bookingDetails = new BookingDetails();
            bookingDetails.setBooking_id(bookings);
            bookingDetails.setAge(age.get(i).toString());
            bookingDetails.setName(name.get(i));
            bookingDetails.setGender(gender.get(i));
            bookingDetails.setSeatNumber(seats.get(i));
            bookingDetails.setCreatedDate(LocalDateTime.now());

            bookingDao.bookingDetailsSave(bookingDetails);

        }
//
//        if (user.getRoleId().getRoleId()==2) {
//            Wallet wallets = walletDao.getWalletByUserId(user);
//            wallets.setBalance(wallets.getBalance() - bookTicketDto.getAmount());
//
//            Transaction transaction = new Transaction();
//            transaction.setDescription("Ticket Booking");
//            transaction.setDate(LocalDateTime.now());
//            transaction.setType("debit");
//            transaction.setAmount(bookTicketDto.getAmount());
//            transaction.setWallet(wallets);
//            walletDao.walletUpdate(wallets);
//            walletDao.transactionSave(transaction);
//        }

        return(bookingId);

    }

    public void confirmBooking(int bookingId, int userID) {
        try {

            List<Bookings> bookingsList = bookingDao.getBookingById(bookingId);
            if (bookingsList.isEmpty()) {
                throw new ResourceNotFoundException("Booking not found");
            }


            Bookings booking = bookingsList.get(0);
            booking.setBooked(true);
            bookingDao.bookingUpdate(booking);
            Double amount = booking.getTotalAmount();

            List<User> userList = userDao.getUserbyUserId(userID);

            if (userList.isEmpty()) {
                throw new ResourceNotFoundException("User not found");
            }

            User user = userList.get(0);

            if (user.getRoleId().getRoleId() == 2) {
                Wallet wallets = walletDao.getWalletByUserId(user);

                if (wallets == null) {
                    throw new ResourceNotFoundException("Wallet not found for the user");
                }

                wallets.setBalance(wallets.getBalance() - amount);

                Transaction transaction = new Transaction();
                transaction.setDescription("Ticket Booking");
                transaction.setDate(LocalDateTime.now());
                transaction.setType("debit");
                transaction.setAmount(amount);
                transaction.setWallet(wallets);

                walletDao.walletUpdate(wallets);
                walletDao.transactionSave(transaction);
            }
        } catch (ResourceNotFoundException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());

        }
    }


    public TicketDto ticketDetails(int bookingId)
    {
        TicketDto ticketDto = new TicketDto();
        List<Bookings> bookings = bookingDao.getBookingById(bookingId);
        Bookings bookings1 = bookings.get(0);
        List<String> names = bookingDao.getNamesByBookingId(bookingId);
        List<String> seats = bookingDao.getSeatsByBookingId(bookingId);
        List<String> gender = bookingDao.getGenderByBookingId(bookingId);
        List<String> age = bookingDao.getAgeByBookingId(bookingId);
        ticketDto.setAge(age.toString().substring(1,age.toString().length()-1));
        ticketDto.setAmount((int) bookings1.getTotalAmount());
        ticketDto.setDestination(bookings1.getSelectedDestination());
        ticketDto.setEmail(bookings1.getEmail());
        ticketDto.setGender(gender.toString().substring(1,gender.toString().length()-1));
        ticketDto.setName(names.toString().substring(1,names.toString().length()-1));
        ticketDto.setRoute(bookings1.getScheduleId().getRouteId().getSource()+ " to " +bookings1.getScheduleId().getRouteId().getDestination() );
        ticketDto.setSource(bookings1.getSelectedSource());
        ticketDto.setBusNumber(bookings1.getScheduleId().getBusId().getBusNumber());
        ticketDto.setPhone(bookings1.getPhone());
        ticketDto.setSeats(seats.toString().substring(1,seats.toString().length()-1));
        ticketDto.setDateTime(bookings1.getScheduleId().getTripDate().toString() + " "+ bookings1.getDepatureTime() );
        return ticketDto;

    }


    public List<MyBookingsDto> myBookings(int userId, int curPage, int valueOfSelectedFilter) {
        List<MyBookingsDto> myBookingsDtos = new ArrayList<>();

        try {
            int startIndex = ((curPage - 1) * 6);
            int endIndex = 6;

            List<Bookings> bookings = bookingDao.getBookingByUserId(userId, startIndex, endIndex, valueOfSelectedFilter);
            int count = bookingDao.getBookingCountByUserId(userId, valueOfSelectedFilter);

            if (bookings.isEmpty()) {
                System.out.println("No bookings found for user ID: " + userId);
                return myBookingsDtos;
            }

            for (Bookings booking : bookings) {
                MyBookingsDto myBookingsDto = new MyBookingsDto();
                myBookingsDto.setRoute(booking.getScheduleId().getRouteId().getSource() + " to " + booking.getScheduleId().getRouteId().getDestination());
                myBookingsDto.setDepature(booking.getSelectedSource() + " " + booking.getScheduleId().getTripDate().toString() + " " + booking.getDepatureTime());
                myBookingsDto.setArrival(booking.getSelectedDestination());
                myBookingsDto.setBusNumber(booking.getScheduleId().getBusId().getBusNumber());
                myBookingsDto.setBookingId(booking.getBookingId());
                myBookingsDto.setEmail(booking.getEmail());
                myBookingsDto.setPhone(booking.getPhone());
                myBookingsDto.setCount(count);
                myBookingsDto.setDate(booking.getScheduleId().getTripDate().toString());
                myBookingsDto.setTime(booking.getDepatureTime());

                List<BookingDetails> bookingDetailsList = bookingDao.getBookingDetailsById(booking.getBookingId());
                if (bookingDetailsList.isEmpty()) {
                    System.out.println("No booking details found for booking ID: " + booking.getBookingId());
                    myBookingsDto.setPassengers(new ArrayList<>());
                } else {
                    List<MyBookingsDto.PassengerDto> passengers = new ArrayList<>();
                    for (BookingDetails details : bookingDetailsList) {
                        MyBookingsDto.PassengerDto passengerDto = new MyBookingsDto.PassengerDto();
                        passengerDto.setName(details.getName());
                        passengerDto.setAge(details.getAge());
                        passengerDto.setGender(details.getGender());
                        passengerDto.setSeatNumber(details.getSeatNumber());
                        passengerDto.setBookingDetailId(details.getId());
                        passengerDto.setBookingId(details.getBooking_id().getBookingId());
                        passengers.add(passengerDto);
                    }
                    myBookingsDto.setPassengers(passengers);
                }

                myBookingsDtos.add(myBookingsDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while fetching bookings: " + e.getMessage());
        }

        return myBookingsDtos;
    }


    public void cancelSeat(int bookingDetailId, int userId, String email) {
        try {
            List<User> userList = userDao.getUserbyUserId(userId);
            if (userList.isEmpty()) {
                System.out.println("User not found for user ID: " + userId);
                return;
            }
            User user = userList.get(0);

            List<BookingDetails> bookingDetailsList = bookingDao.getBookingDetailsByBookingDetailId(bookingDetailId);
            if (bookingDetailsList.isEmpty()) {
                System.out.println("Booking details not found for booking detail ID: " + bookingDetailId);
                return;
            }
            BookingDetails bookingDetails1 = bookingDetailsList.get(0);
            bookingDetails1.setCancelled(true);
            bookingDao.bookingDetailsUpdate(bookingDetails1);

            List<Bookings> bookingsList = bookingDao.getBookingById(bookingDetails1.getBooking_id().getBookingId());
            if (bookingsList.isEmpty()) {
                System.out.println("Booking not found for booking ID: " + bookingDetails1.getBooking_id().getBookingId());
                return;
            }
            Bookings bookings1 = bookingsList.get(0);
            bookings1.setTotalAmount(bookings1.getTotalAmount() / 2);
            bookings1.setModifiedDate(LocalDateTime.now());
            bookingDao.bookingUpdate(bookings1);

            List<Schedules> schedulesList = scheduleDao.getScheduleByID(bookingDetails1.getBooking_id().getScheduleId().getScheduleId());
            if (schedulesList.isEmpty()) {
                System.out.println("Schedule not found for schedule ID: " + bookingDetails1.getBooking_id().getScheduleId().getScheduleId());
                return;
            }
            Schedules schedules1 = schedulesList.get(0);
            schedules1.setAvailableSeats(schedules1.getAvailableSeats() + 1);
            scheduleDao.scheduleUpdate(schedules1);

            Notifications notifications = new Notifications();
            EmailLogs emailLogs = new EmailLogs();
            notifications.setCreatedDate(LocalDateTime.now());
            notifications.setMessage("Cancel Seat Information");
            notifications.setType("Email");
            notifications.setUserId(user);
            emailLogs.setEmail(email);
            emailLogs.setNotificationId(notifications);
            emailLogs.setSentAt(LocalDateTime.now());

            notificationDao.NotificationSave(notifications);
            notificationDao.EmailLogsSave(emailLogs);

            if (user.getRoleId().getRoleId() == 2) {
                Wallet wallets = walletDao.getWalletByUserId(user);
                if (wallets == null) {
                    System.out.println("Wallet not found for user ID: " + userId);
                    return;
                }
                wallets.setBalance(wallets.getBalance() + bookings1.getTotalAmount());

                Transaction transaction = new Transaction();
                transaction.setDescription("Seat Cancellation");
                transaction.setDate(LocalDateTime.now());
                transaction.setType("credit");
                transaction.setAmount(bookings1.getTotalAmount());
                transaction.setWallet(wallets);

                walletDao.walletUpdate(wallets);
                walletDao.transactionSave(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred during seat cancellation: " + e.getMessage());
        }
    }


    public void cancelBooking(int bookingId, int userId, String email) {
        try {
            List<User> userList = userDao.getUserbyUserId(userId);
            if (userList.isEmpty()) {
                System.out.println("User not found for user ID: " + userId);
                return;
            }
            User user = userList.get(0);


            List<Bookings> bookingsList = bookingDao.getBookingById(bookingId);
            if (bookingsList.isEmpty()) {
                System.out.println("Booking not found for booking ID: " + bookingId);
                return; // or throw a custom exception
            }
            Bookings bookings1 = bookingsList.get(0);
            bookings1.setCancelled(true);


            List<BookingDetails> bookingDetailsList = bookingDao.getBookingDetailsById(bookingId);
            if (bookingDetailsList.isEmpty()) {
                System.out.println("Booking details not found for booking ID: " + bookingId);
                return;
            }

            bookingDao.bookingUpdate(bookings1);


            List<Schedules> schedulesList = scheduleDao.getScheduleByID(bookings1.getScheduleId().getScheduleId());
            if (schedulesList.isEmpty()) {
                System.out.println("Schedule not found for schedule ID: " + bookings1.getScheduleId().getScheduleId());
                return; // or throw a custom exception
            }
            Schedules schedules1 = schedulesList.get(0);


            for (BookingDetails bookingDetails1 : bookingDetailsList) {
                bookingDetails1.setCancelled(true);
                bookingDao.bookingDetailsUpdate(bookingDetails1);
            }
            schedules1.setAvailableSeats(schedules1.getAvailableSeats() + bookingDetailsList.size());
            scheduleDao.scheduleUpdate(schedules1);


            Notifications notifications = new Notifications();
            EmailLogs emailLogs = new EmailLogs();
            notifications.setCreatedDate(LocalDateTime.now());
            notifications.setMessage("Cancel Booking Information");
            notifications.setType("Email");
            notifications.setUserId(user);
            emailLogs.setEmail(email);
            emailLogs.setNotificationId(notifications);
            emailLogs.setSentAt(LocalDateTime.now());

            notificationDao.NotificationSave(notifications);
            notificationDao.EmailLogsSave(emailLogs);


            if (user.getRoleId().getRoleId() == 2) {
                Wallet wallets = walletDao.getWalletByUserId(user);
                if (wallets == null) {
                    System.out.println("Wallet not found for user ID: " + userId);
                    return;
                }
                wallets.setBalance(wallets.getBalance() + bookings1.getTotalAmount());

                Transaction transaction = new Transaction();
                transaction.setDescription("Ticket Cancellation");
                transaction.setDate(LocalDateTime.now());
                transaction.setType("credit");
                transaction.setWallet(wallets);
                transaction.setAmount(bookings1.getTotalAmount());

                walletDao.walletUpdate(wallets);
                walletDao.transactionSave(transaction);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred during booking cancellation: " + e.getMessage());
        }
    }


    public void saveEmailLogsWhileBooking(String email, int userId) {
        try {
            List<User> userList = userDao.getUserbyUserId(userId);
            if (userList.isEmpty()) {
                System.out.println("User not found for user ID: " + userId);
                return;
            }
            User user = userList.get(0);

            Notifications notifications = new Notifications();
            EmailLogs emailLogs = new EmailLogs();
            notifications.setCreatedDate(LocalDateTime.now());
            notifications.setMessage("Booking Information");
            notifications.setType("Email");
            notifications.setUserId(user);
            emailLogs.setEmail(email);
            emailLogs.setNotificationId(notifications);
            emailLogs.setSentAt(LocalDateTime.now());

            notificationDao.NotificationSave(notifications);
            notificationDao.EmailLogsSave(emailLogs);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while saving email logs: " + e.getMessage());
        }
    }


    public SalesData getMonthlySalesData(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        List<Bookings> bookings = bookingDao.getMonthlyBookings(date);
        return processSalesDataMonthly(bookings);
    }

    private SalesData processSalesDataMonthly(List<Bookings> bookings) {
        double totalSales = 0.0;
        for (Bookings booking : bookings) {
            totalSales += booking.getTotalAmount();
        }
        SalesData salesData = new SalesData();
        salesData.setTotalSales(totalSales);
        salesData.setNumberOfBookings(bookings.size());
        return salesData;
    }

    public SalesData getYearlySalesData(int year) {
        List<Bookings> bookings = bookingDao.getYearlyBookings(year);
        return processSalesDataYearly(bookings);
    }

    private SalesData processSalesDataYearly(List<Bookings> bookings) {
        double totalSales = 0.0;
        for (Bookings booking : bookings) {
            totalSales += booking.getTotalAmount();
        }
        SalesData salesData = new SalesData();
        salesData.setTotalSales(totalSales);
        salesData.setNumberOfBookings(bookings.size());
        return salesData;
    }

    public List<MonthlySalesData> getMonthlySalesData(int year) {
        List<Bookings> bookings = bookingDao.getYearlyBookings(year);
        return processMonthlySalesData(bookings);
    }

    private List<MonthlySalesData> processMonthlySalesData(List<Bookings> bookings) {
        Map<Integer, List<Bookings>> bookingsByMonth = bookings.stream()
                .collect(Collectors.groupingBy(booking -> booking.getBookingDate().getMonthValue()));

        List<MonthlySalesData> monthlySalesDataList = new ArrayList<>();
        for (int month = 1; month <= 12; month++)   {
            List<Bookings> monthlyBookings = bookingsByMonth.getOrDefault(month, new ArrayList<>());
            double totalSales = monthlyBookings.stream().mapToDouble(Bookings::getTotalAmount).sum();
            int numberOfBookings = monthlyBookings.size();
            MonthlySalesData monthlySalesData = new MonthlySalesData();
            monthlySalesData.setMonth(month);
            monthlySalesData.setTotalSales(totalSales);
            monthlySalesData.setNumberOfBookings(numberOfBookings);
            monthlySalesDataList.add(monthlySalesData);
        }

        return monthlySalesDataList;
    }




    public  List<OccupancyReportDto> getRoutesWiseMonthlyOccupancy(String date)
    {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return bookingDao.getMonthlyOccupancyData(year, month);
    }

    public  List<OccupancyReportDto> getRoutesWiseYearlyOccupancy(int year)
    {
        return bookingDao.getYearlyOccupancyData(year);
    }


    public  List<BusLocationDto> getDetailsForBusLocation()
    {
        List<Schedules> schedules = scheduleDao.getScheduleAll();
        List<BusLocationDto> busLocationDtos = new ArrayList<>();
        if (!schedules.isEmpty()) {
            for (int i = 0; i < schedules.size(); i++) {
                Schedules schedules1 = schedules.get(i);
                BusLocationDto busLocationDto = new BusLocationDto();
                busLocationDto.setBusId(schedules1.getBusId().getBusId());
                busLocationDto.setBusNumber(schedules1.getBusId().getBusNumber());
                busLocationDto.setDate(schedules1.getTripDate().toString());
                busLocationDto.setDestination(schedules1.getRouteId().getDestination());
                busLocationDto.setSource(schedules1.getRouteId().getSource());
                busLocationDto.setTime(schedules1.getArrivalTime().toString());
                busLocationDto.setRouteId(schedules1.getRouteId().getRouteId());
                List<String> subroutes = routesDao.getsubRoutes(schedules1.getRouteId());
                busLocationDto.setSubroutes(subroutes);
                busLocationDtos.add(busLocationDto);
            }
        }
        return busLocationDtos;
    }

    public  List<BusLocationDto> getDetailsForBusLocationForUser(int userID)
    {
        List<Schedules> schedules = scheduleDao.getScheduleByUserID(userID);
        List<BusLocationDto> busLocationDtos = new ArrayList<>();

        if (!schedules.isEmpty()) {
            for (int i = 0; i < schedules.size(); i++) {
                Schedules schedules1 = schedules.get(i);
                BusLocationDto busLocationDto = new BusLocationDto();
                busLocationDto.setBusId(schedules1.getBusId().getBusId());
                busLocationDto.setBusNumber(schedules1.getBusId().getBusNumber());
                busLocationDto.setDate(schedules1.getTripDate().toString());
                busLocationDto.setDestination(schedules1.getRouteId().getDestination());
                busLocationDto.setSource(schedules1.getRouteId().getSource());
                busLocationDto.setTime(schedules1.getArrivalTime().toString());
                busLocationDto.setRouteId(schedules1.getRouteId().getRouteId());
                List<String> subroutes = routesDao.getsubRoutes(schedules1.getRouteId());
                busLocationDto.setSubroutes(subroutes);
                busLocationDtos.add(busLocationDto);
            }
        }
        return busLocationDtos;
    }

    public void sendMailforReset(String mail) {
        try {
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setEmail(mail);
            verificationToken.setCreatedDate(LocalDateTime.now());
            String token = new TokenGenerator().GenerateToken();
            verificationToken.setToken(token);
            String mailUrl = "http://localhost:8080/BusWise/reset/" + token;
            String message = "Hello,\n\n" +
                    "We received a request to reset the password for your account associated with " + mail + ".\n" +
                    "You can reset your password by clicking the link below:\n\n" +
                    mailUrl + "\n\n" +
                    "If you did not request a password reset, please ignore this email or contact support if you have questions.\n\n" +
                    "Thank you,\n" +
                    "BusWise Team";

            // Send email
            emailService.send(mail, "Reset Password", message);

            List<VerificationToken> tokenList = verificationDao.checkMailStatusList(verificationToken.getEmail());
            List<User> userList = userDao.checkEmail(verificationToken.getEmail());
            User user = userList.get(0);

            if (tokenList.size() != 0) {
                VerificationToken lastVerificationToken = tokenList.get(tokenList.size() - 1);
                lastVerificationToken.setValidation(false);
                verificationToken.setValidation(true);
                verificationDao.verificationTokenSave(verificationToken);
                verificationDao.verificationTokenUpdate(lastVerificationToken);
            } else {
                verificationToken.setValidation(true);
                verificationDao.verificationTokenSave(verificationToken);
            }


            Notifications notifications = new Notifications();
            notifications.setUserId(user);
            notifications.setType("Reset Password");
            notifications.setCreatedDate(LocalDateTime.now());
            notifications.setMessage("email send for reset password");
            notificationDao.NotificationSave(notifications);

            EmailLogs emailLogs = new EmailLogs();
            emailLogs.setSentAt(LocalDateTime.now());
            emailLogs.setNotificationId(notifications);
            emailLogs.setEmail(verificationToken.getEmail());
            notificationDao.EmailLogsSave(emailLogs);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while processing the password reset email: " + e.getMessage());
        }
    }


    public void resetPassword(String password, String token) {
        try {
            List<VerificationToken> verificationTokens = verificationDao.checkToken(token);
            if (verificationTokens.isEmpty()) {
                System.err.println("Invalid or expired token.");
                return;
            }

            VerificationToken verificationToken = verificationTokens.get(0);
            String email = verificationToken.getEmail();


            List<User> userList = userDao.checkEmail(email);
            if (userList.isEmpty()) {
                System.err.println("User not found for the provided email.");
                return;
            }

            User user = userList.get(0);

            byte[] salt = passwordHash.getSalt();
            String passwordHashed = passwordHash.getSecurePassword(password, salt);


            user.setPassword(passwordHashed);
            user.setSalt(salt);
            user.setModifiedDate(LocalDateTime.now());
            userDao.userUpdate(user);

            System.out.println("Password reset successfully.");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.err.println("Error occurred while hashing the password: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    public boolean checkToken(String token)  {
        List<VerificationToken> verificationTokens = verificationDao.checkToken(token);
        if(verificationTokens.size()!=0){
            VerificationToken verificationToken = verificationTokens.get(0);
            return true;
        }
        else {
            return false;
        }
    }


    public Wallet getWalletByUserId(int userId) {
        try {
            List<User> userList = userDao.getUserbyUserId(userId);
            if (userList.isEmpty()) {
                System.err.println("User not found for userId: " + userId);
                return null;
            }

            User user = userList.get(0);

            Wallet wallet = walletDao.getWalletByUserId(user);
            if (wallet == null) {
                System.err.println("Wallet not found for userId: " + userId);
                return null;
            }

            return wallet;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while fetching the wallet: " + e.getMessage());
            return null;
        }
    }


    public void addFunds(int userId, double amount) {
        try {

            Wallet wallet = getWalletByUserId(userId);
            if (wallet == null) {
                throw new RuntimeException("Wallet not found for userId: " + userId);
            }

            wallet.setBalance(wallet.getBalance() + amount);
            walletDao.walletUpdate(wallet);

            Transaction transaction = new Transaction();
            transaction.setWallet(wallet);
            transaction.setType("credit");
            transaction.setAmount(amount);
            transaction.setDate(LocalDateTime.now());
            transaction.setDescription("Fund added by user");
            walletDao.transactionSave(transaction);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while adding funds: " + e.getMessage());

        }
    }


    public void deductFunds(int userId, double amount) {
        try {
            Wallet wallet = getWalletByUserId(userId);
            if (wallet == null) {
                throw new RuntimeException("Wallet not found for userId: " + userId);
            }


            if (wallet.getBalance() < amount) {
                throw new RuntimeException("Insufficient balance");
            }

            wallet.setBalance(wallet.getBalance() - amount);
            walletDao.walletUpdate(wallet);

            Transaction transaction = new Transaction();
            transaction.setWallet(wallet);
            transaction.setType("debit");
            transaction.setAmount(amount);
            transaction.setDate(LocalDateTime.now());
            transaction.setDescription("Fund withdrawn by user");
            walletDao.transactionSave(transaction);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while deducting funds: " + e.getMessage());

        }
    }



    public double getBalance(int userId) {
        try {
            double balance = walletDao.getBalanceByuserId(userId);
            if (balance < 0) {
                throw new RuntimeException("Error retrieving balance for userId: " + userId);
            }
            return balance;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while retrieving the balance: " + e.getMessage());
            return 0.0;
        }
    }


    public List<TransactionHistoryDto> getTransactions(int userId, int curPage) {
        List<TransactionHistoryDto> transactionHistoryDtos = new ArrayList<>();
        try {
            int startIndex = ((curPage - 1) * 6);
            int endIndex = 6;


            List<Transaction> transactions = walletDao.getTransactionsByUserId(userId, startIndex, endIndex);
            long count = walletDao.getTransactionCountByUserId(userId);

            double runningBalance = 0.0;
            if (!transactions.isEmpty()) {
                runningBalance = transactions.get(0).getWallet().getBalance();
            }


            for (Transaction transaction : transactions) {
                TransactionHistoryDto transactionHistoryDto = new TransactionHistoryDto();
                transactionHistoryDto.setAmount(transaction.getAmount());
                transactionHistoryDto.setCount(count);
                transactionHistoryDto.setDate(transaction.getDate().toString().substring(0, 10));
                transactionHistoryDto.setDebit(transaction.getType());
                transactionHistoryDto.setDescription(transaction.getDescription());

                if (transaction.getType().equalsIgnoreCase("debit")) {
                    runningBalance -= transaction.getAmount();
                } else if (transaction.getType().equalsIgnoreCase("credit")) {
                    runningBalance += transaction.getAmount();
                }

                transactionHistoryDto.setBalance(runningBalance);

                transactionHistoryDtos.add(transactionHistoryDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while fetching transaction history: " + e.getMessage());

        }

        return transactionHistoryDtos;
    }


    public void resetReservationTimestamp(int bookingId) {

        List<Bookings> bookings = bookingDao.getBookingById(bookingId);

       if(!bookings.isEmpty()){
           Bookings bookings1 = bookings.get(0);
           bookings1.setBooked(false);
           bookingDao.bookingUpdate(bookings1);
       }
    }



}





