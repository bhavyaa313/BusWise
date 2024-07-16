package buswise.dto;

import buswise.model.SubRoute;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class RoutesDto {

    private int routeId;
    @NotBlank(message = "Source is mandatory")
    private String source;
    @NotBlank(message = "Destination is mandatory")
    private String destination;
    @Min(value = 1, message = "Distance must be greater than 0")
    private int distance;
    private List<SubRoute> subRouteList;
    private String subRoutes;
    private Long count;
    private List<String> name;
    private List<Integer> distancesub;
    private List<Integer> sequence;
    private List<Integer> subrouteId;
    private int subroutesCount;


    public List<Integer> getSubrouteId() {
        return subrouteId;
    }

    public void setSubrouteId(List<Integer> subrouteId) {
        this.subrouteId = subrouteId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<SubRoute> getSubRouteList() {
        return subRouteList;
    }

    public void setSubRouteList(List<SubRoute> subRouteList) {
        this.subRouteList = subRouteList;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getSubRoutes() {
        return subRoutes;
    }

    public void setSubRoutes(String subRoutes) {
        this.subRoutes = subRoutes;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Integer> getDistancesub() {
        return distancesub;
    }

    public void setDistancesub(List<Integer> distancesub) {
        this.distancesub = distancesub;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    public void setSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    public int getSubroutesCount() {
        return subroutesCount;
    }

    public void setSubroutesCount(int subroutesCount) {
        this.subroutesCount = subroutesCount;
    }

    public RoutesDto(int routeId, String source, String destination, int distance, List<SubRoute> subRouteList, String subRoutes, Long count, List<String> name, List<Integer> distancesub, List<Integer> sequence, List<Integer> subrouteId, int subroutesCount) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.subRouteList = subRouteList;
        this.subRoutes = subRoutes;
        this.count = count;
        this.name = name;
        this.distancesub = distancesub;
        this.sequence = sequence;
        this.subrouteId = subrouteId;
        this.subroutesCount = subroutesCount;
    }

    public RoutesDto() {
    }
}
