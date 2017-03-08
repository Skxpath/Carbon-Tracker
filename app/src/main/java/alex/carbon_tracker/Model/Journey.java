package alex.carbon_tracker.Model;

import java.util.Date;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * Journey class that uses route and vehicle classes to
 * make Journey instances.
 */
public class Journey {
    private Vehicle vehicle;
    private Route route;
    private Date date;
    private float carbonEmitted = 0;

    public Journey(Vehicle vehicle, Route route) {
        this.vehicle = vehicle;
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Date getDate() {
        return date;
    }

    public float getCarbonEmitted() {
        return carbonEmitted;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCarbonEmitted(float carbonEmitted) {
        this.carbonEmitted = carbonEmitted;
    }

}
