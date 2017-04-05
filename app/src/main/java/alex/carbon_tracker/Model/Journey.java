package alex.carbon_tracker.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Journey class that uses route and vehicle classes to
 * make Journey instances.
 */
public class Journey {
    private Transportation transportation;
    private UserVehicle vehicle;
    private Route route;
    private double carbonEmitted = 0;

    private Date date;

    public Journey(UserVehicle vehicle, Route route, double carbonEmitted, Date date) {
        this.vehicle = vehicle;
        this.route = route;
        this.carbonEmitted = carbonEmitted;
        this.date = date;
    }

    public Journey(Transportation transportation, Route route, double carbonEmitted, Date date) {
        this.carbonEmitted = carbonEmitted;
        this.route = route;
        this.transportation = transportation;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean hasVehicle() {
        return (vehicle != null);
    }

    public boolean hasTransportation() {
        return (vehicle == null);
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

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public Route getRoute() {
        return route;
    }

    public UserVehicle getUserVehicle() {
        return vehicle;
    }

    public double getCarbonEmitted() {
        return carbonEmitted;
    }

    public void setCarbonEmitted(float carbonEmitted) {
        this.carbonEmitted = carbonEmitted;
    }

    private DateFormat getDateFormat(String filter) {
        return new SimpleDateFormat(filter);
    }

}
