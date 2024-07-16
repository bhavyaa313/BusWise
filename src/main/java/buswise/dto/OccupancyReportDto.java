package buswise.dto;

public class OccupancyReportDto {

    private int scheduleId;
    private String selectedSource;
    private String selectedDestination;
    private String departureTime;
    private String route;
    private int totalSeats;
    private int seatsOccupied;
    private int seatsCancelled;
    private double occupancyPercentage;
    private int routeId;
    private Long totalBooking;
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getSelectedSource() {
        return selectedSource;
    }

    public void setSelectedSource(String selectedSource) {
        this.selectedSource = selectedSource;
    }

    public String getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(String selectedDestination) {
        this.selectedDestination = selectedDestination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getSeatsOccupied() {
        return seatsOccupied;
    }

    public void setSeatsOccupied(int seatsOccupied) {
        this.seatsOccupied = seatsOccupied;
    }

    public int getSeatsCancelled() {
        return seatsCancelled;
    }

    public void setSeatsCancelled(int seatsCancelled) {
        this.seatsCancelled = seatsCancelled;
    }

    public double getOccupancyPercentage() {
        return occupancyPercentage;
    }

    public void setOccupancyPercentage(double occupancyPercentage) {
        this.occupancyPercentage = occupancyPercentage;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }


    public Long getTotalBooking() {
        return totalBooking;
    }

    public void setTotalBooking(Long totalBooking) {
        this.totalBooking = totalBooking;
    }

    public OccupancyReportDto(int scheduleId, String selectedSource, String selectedDestination, String departureTime, String route, int totalSeats, int seatsOccupied, int seatsCancelled, double occupancyPercentage, int routeId, Long totalBooking) {
        this.scheduleId = scheduleId;
        this.selectedSource = selectedSource;
        this.selectedDestination = selectedDestination;
        this.departureTime = departureTime;
        this.route = route;
        this.totalSeats = totalSeats;
        this.seatsOccupied = seatsOccupied;
        this.seatsCancelled = seatsCancelled;
        this.occupancyPercentage = occupancyPercentage;
        this.routeId = routeId;
        this.totalBooking = totalBooking;
    }

    public OccupancyReportDto() {
    }

}
