package alex.carbon_tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-03-01.
 */

public class VehicleManager {
    private List<Vehicle> vehicleList = new ArrayList<>();

    public Vehicle getVehicle(int index) {
        return vehicleList.get(index);
    }
    public void delete(int index){
        vehicleList.remove(index);
    }
    public void add(Vehicle vehicle){
        vehicleList.add(vehicle);
    }

}
