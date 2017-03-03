package alex.carbon_tracker.Model;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * Journey class that uses route and vehicle classes to
 * make Journey instances.
 */
public class Journey {
    private Vehicle vehicle;
    private Route route;

    public Journey(Vehicle vehicle, Route route) {
        this.route = route;
        this.vehicle = vehicle;
    }

    public Route getRoute() {
        return route;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

}
