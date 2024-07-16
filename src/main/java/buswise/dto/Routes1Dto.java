package buswise.dto;

public class Routes1Dto {

    private int routeId;
    private int subrouteId;
    private String routeDetails;
    private String duration;
    private long fare;


    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getSubrouteId() {
        return subrouteId;
    }

    public void setSubrouteId(int subrouteId) {
        this.subrouteId = subrouteId;
    }

    public String getRouteDetails() {
        return routeDetails;
    }

    public void setRouteDetails(String routeDetails) {
        this.routeDetails = routeDetails;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getFare() {
        return fare;
    }

    public void setFare(long fare) {
        this.fare = fare;
    }

    public Routes1Dto(int routeId, int subrouteId, String routeDetails, String duration, long fare) {
        this.routeId = routeId;
        this.subrouteId = subrouteId;
        this.routeDetails = routeDetails;
        this.duration = duration;
        this.fare = fare;
    }

    public Routes1Dto() {
    }
}
