package buswise.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "bus_location")
public class BusLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = "bus_id")
    private Buses busId;
    private float latitude;
    private float longitude;
    private String address;
    @Column(name = "timeStamp")
    private LocalTime timeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Buses getBusId() {
        return busId;
    }

    public void setBusId(Buses busId) {
        this.busId = busId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BusLocation(int id, Buses busId, float latitude, float longitude, String address, LocalTime timeStamp) {
        this.id = id;
        this.busId = busId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.timeStamp = timeStamp;
    }

    public BusLocation() {
    }
}
