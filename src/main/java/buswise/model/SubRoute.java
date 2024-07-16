package buswise.model;

import javax.persistence.*;

@Entity
public class SubRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Routes routeId;
    private String stop;
    private int sequence;
    private int distance;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Routes getRouteId() {
        return routeId;
    }

    public void setRouteId(Routes routeId) {
        this.routeId = routeId;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public SubRoute(int id, Routes routeId, String stop, int sequence, int distance, boolean isDeleted) {
        this.id = id;
        this.routeId = routeId;
        this.stop = stop;
        this.sequence = sequence;
        this.distance = distance;
        this.isDeleted = isDeleted;
    }

    public SubRoute() {
    }
}
