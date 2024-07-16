package buswise.dto;

import java.util.List;

public class MyBookingsDto {

    private int noOfBookings;
    private String route;
    private String depature;
    private String arrival;
    private String busNumber;
    private int bookingId;
    private List<PassengerDto> passengers;
    private String email;
    private String phone;
    private String seats;
    private long count;
    private String date;


    public int getNoOfBookings() {
        return noOfBookings;
    }

    public void setNoOfBookings(int noOfBookings) {
        this.noOfBookings = noOfBookings;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDepature() {
        return depature;
    }

    public void setDepature(String depature) {
        this.depature = depature;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public List<PassengerDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDto> passengers) {
        this.passengers = passengers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MyBookingsDto(int noOfBookings, String route, String depature, String arrival, String busNumber, int bookingId, List<PassengerDto> passengers, String email, String phone, String seats, long count, String date) {
        this.noOfBookings = noOfBookings;
        this.route = route;
        this.depature = depature;
        this.arrival = arrival;
        this.busNumber = busNumber;
        this.bookingId = bookingId;
        this.passengers = passengers;
        this.email = email;
        this.phone = phone;
        this.seats = seats;
        this.count = count;
        this.date = date;
    }

    public MyBookingsDto() {
    }




    public static class PassengerDto {

        private String name;
        private String age;
        private String gender;
        private String seatNumber;
        private int bookingDetailId;
        private int bookingId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
        }

        public int getBookingDetailId() {
            return bookingDetailId;
        }

        public void setBookingDetailId(int bookingDetailId) {
            this.bookingDetailId = bookingDetailId;
        }

        public int getBookingId() {
            return bookingId;
        }

        public void setBookingId(int bookingId) {
            this.bookingId = bookingId;
        }

        public PassengerDto(String name, String age, String gender, String seatNumber, int bookingDetailId, int bookingId) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.seatNumber = seatNumber;
            this.bookingDetailId = bookingDetailId;
            this.bookingId = bookingId;
        }

        public PassengerDto() {
        }
    }

}


