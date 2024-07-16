package buswise.dto;

public class Bus1Dto {

    private int busId;
    private String busDetails;
    private String busNumber;


    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(String busDetails) {
        this.busDetails = busDetails;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public Bus1Dto(int busId, String busDetails, String busNumber) {
        this.busId = busId;
        this.busDetails = busDetails;
        this.busNumber = busNumber;
    }

    public Bus1Dto() {
    }
}
