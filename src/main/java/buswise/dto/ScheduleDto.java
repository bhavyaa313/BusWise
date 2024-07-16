package buswise.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ScheduleDto {
    private int scheduleId;
    @NotNull(message = "please select route")
    private Integer route;
    @NotNull(message = "please select bus")
    private Integer bus;
    @NotBlank(message = "Please select date")
    private String date;
    @NotBlank(message = "Please select time")
    private String time;
    @NotBlank(message = "Please enter duration")
    private String duration;
    @NotNull(message = "Please enter fare")
    private long fare;


    public @NotNull(message = "please select route") Integer getRoute() {
        return route;
    }

    public void setRoute(@NotNull(message = "please select route") Integer route) {
        this.route = route;
    }

    public @NotNull(message = "please select bus") Integer getBus() {
        return bus;
    }

    public void setBus(@NotNull(message = "please select bus") Integer bus) {
        this.bus = bus;
    }

    public @NotBlank(message = "Please select date") String getDate() {
        return date;
    }

    public void setDate(@NotBlank(message = "Please select date") String date) {
        this.date = date;
    }

    public @NotBlank(message = "Please select time") String getTime() {
        return time;
    }

    public void setTime(@NotBlank(message = "Please select time") String time) {
        this.time = time;
    }

    public @NotBlank(message = "Please enter duration") String getDuration() {
        return duration;
    }

    public void setDuration(@NotBlank(message = "Please enter duration") String duration) {
        this.duration = duration;
    }

    @NotNull(message = "Please enter fare")
    public long getFare() {
        return fare;
    }

    public void setFare(@NotNull(message = "Please enter fare") long fare) {
        this.fare = fare;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public ScheduleDto(int scheduleId, Integer route, Integer bus, String date, String time, String duration, long fare) {
        this.scheduleId = scheduleId;
        this.route = route;
        this.bus = bus;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.fare = fare;
    }

    public ScheduleDto() {
    }
}
