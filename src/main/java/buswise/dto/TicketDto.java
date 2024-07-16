package buswise.dto;

import java.util.List;
public class TicketDto {

    private String name;
    private String age;
    private String  seats;
    private String gender;
    private String phone;
    private String email;
    private String dateTime;
    private String busNumber;
    private String route;
    private String source;
    private String destination;
    private int amount;

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

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TicketDto(String name, String age, String seats, String gender, String phone, String email, String dateTime, String busNumber, String route, String source, String destination, int amount) {
        this.name = name;
        this.age = age;
        this.seats = seats;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.dateTime = dateTime;
        this.busNumber = busNumber;
        this.route = route;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public TicketDto() {
    }
}
