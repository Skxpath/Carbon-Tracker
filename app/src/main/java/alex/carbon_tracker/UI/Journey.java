package alex.carbon_tracker.UI;

/**
 * Created by Sachin on 2017-03-01.
 */

public class Journey {

    private Vehicle vehicle;
    private Route route;
    public Journey(Vehicle vehicle,Route route){
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
