package buswise.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
public class Routes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int routeId;
    private String source;
    private String destination;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    private int distance;
    @Column(name = "no_of_subroutes")
    private int noOfSubroutes;


    @ManyToOne
    @JoinColumn(name = "route_id")
    private SubRoute subRoute;

    public int getNoOfSubroutes() {
        return noOfSubroutes;
    }

    public void setNoOfSubroutes(int noOfSubroutes) {
        this.noOfSubroutes = noOfSubroutes;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    public SubRoute getSubRoute() {
        return subRoute;
    }

    public void setSubRoute(SubRoute subRoute) {
        this.subRoute = subRoute;
    }

    public Routes(int routeId, String source, String destination, User createdBy, User modifiedBy, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean isDeleted, int distance, int noOfSubroutes, SubRoute subRoute) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
        this.distance = distance;
        this.noOfSubroutes = noOfSubroutes;
        this.subRoute = subRoute;
    }

    public Routes() {
    }
}
