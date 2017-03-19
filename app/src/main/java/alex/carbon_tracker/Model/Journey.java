package alex.carbon_tracker.Model;

import java.util.Date;

/**
 * Journey class that uses route and vehicle classes to
 * make Journey instances.
 */
public class Journey {
    private Transportation transportation;
    private UserVehicle vehicle;
    private Route route;
    private String date;
    private double carbonEmitted = 0;

    public Journey(UserVehicle vehicle, Route route, double carbonEmitted, String date) {
        this.vehicle = vehicle;
        this.route = route;
        this.carbonEmitted = carbonEmitted;
        this.date = date;
    }

    public Journey(Transportation transportation, Route route, double carbonEmitted, String date) {
        this.date = date;
        this.carbonEmitted = carbonEmitted;
        this.route = route;
        this.transportation = transportation;
    }

    public void setCarbonEmitted(double carbonEmitted) {
        this.carbonEmitted = carbonEmitted;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setVehicle(UserVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Route getRoute() {
        return route;
    }

    public UserVehicle getUserVehicle() {
        return vehicle;
    }

    public String getDate() {
        return date;
    }

    public double getCarbonEmitted() {
        return carbonEmitted;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCarbonEmitted(float carbonEmitted) {
        this.carbonEmitted = carbonEmitted;
    }

}
