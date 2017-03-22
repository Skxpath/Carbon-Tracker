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
    private double carbonEmitted = 0;

    private int year;
    private int month;
    private int day;

    public Journey(UserVehicle vehicle, Route route, double carbonEmitted, int year, int month, int day) {
        this.vehicle = vehicle;
        this.route = route;
        this.carbonEmitted = carbonEmitted;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Journey(Transportation transportation, Route route, double carbonEmitted, int year, int month, int day) {
        this.carbonEmitted = carbonEmitted;
        this.route = route;
        this.transportation = transportation;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean hasVehicle() {
        return (vehicle != null);
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

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
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

}
