package buswise.dto;

import java.util.List;

public class BusLocationDto {

    private int busId;
    private String busNumber;
    private String source;
    private String destination;
    private String date;
    private String time;
    private int scehduleId;
    private List<String> subroutes;
    private int routeId;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getScehduleId() {
        return scehduleId;
    }

    public void setScehduleId(int scehduleId) {
        this.scehduleId = scehduleId;
    }

    public List<String> getSubroutes() {
        return subroutes;
    }

    public void setSubroutes(List<String> subroutes) {
        this.subroutes = subroutes;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public BusLocationDto(int busId, String busNumber, String source, String destination, String date, String time, int scehduleId, List<String> subroutes, int routeId) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.scehduleId = scehduleId;
        this.subroutes = subroutes;
        this.routeId = routeId;
    }

    public BusLocationDto() {
    }
}
