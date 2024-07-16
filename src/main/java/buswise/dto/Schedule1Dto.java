package buswise.dto;

import java.util.List;

public class Schedule1Dto {
    private int scheduleId;
    private String source;
    private String destination;
    private String date;
    private String Duration;
    private long fare;
    private String busDetails;
    private String subroutes;
    private String time;
    private long count;
    private int busId;
    private String routeDetails;
    private int routeId;
    private int noOfSeats;
    private String yourSource;
    private String yourdestination;
    private int noOfSeatsAvailable;
    private List<String> bookedSeats;


    public String getYourSource() {
        return yourSource;
    }

    public void setYourSource(String yourSource) {
        this.yourSource = yourSource;
    }

    public String getYourdestination() {
        return yourdestination;
    }

    public void setYourdestination(String yourdestination) {
        this.yourdestination = yourdestination;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getRouteDetails() {
        return routeDetails;
    }

    public void setRouteDetails(String routeDetails) {
        this.routeDetails = routeDetails;
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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubroutes() {
        return subroutes;
    }

    public void setSubroutes(String subroutes) {
        this.subroutes = subroutes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public long getFare() {
        return fare;
    }

    public void setFare(long fare) {
        this.fare = fare;
    }

    public String getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(String busDetails) {
        this.busDetails = busDetails;
    }

    public int getNoOfSeatsAvailable() {
        return noOfSeatsAvailable;
    }

    public void setNoOfSeatsAvailable(int noOfSeatsAvailable) {
        this.noOfSeatsAvailable = noOfSeatsAvailable;
    }

    public List<String> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(List<String> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public Schedule1Dto(int scheduleId, String source, String destination, String date, String duration, long fare, String busDetails, String subroutes, String time, long count, int busId, String routeDetails, int routeId, int noOfSeats, String yourSource, String yourdestination, int noOfSeatsAvailable, List<String> bookedSeats) {
        this.scheduleId = scheduleId;
        this.source = source;
        this.destination = destination;
        this.date = date;
        Duration = duration;
        this.fare = fare;
        this.busDetails = busDetails;
        this.subroutes = subroutes;
        this.time = time;
        this.count = count;
        this.busId = busId;
        this.routeDetails = routeDetails;
        this.routeId = routeId;
        this.noOfSeats = noOfSeats;
        this.yourSource = yourSource;
        this.yourdestination = yourdestination;
        this.noOfSeatsAvailable = noOfSeatsAvailable;
        this.bookedSeats = bookedSeats;
    }

    public Schedule1Dto() {
    }
}
