package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-03-06.
 */

public class UserVehicleManager {
    private List<UserVehicle> vehicleList = new ArrayList<>();
    private UserVehicle currentVehicle;

    public UserVehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(UserVehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public List<UserVehicle> getVehicleList() {
        return vehicleList;
    }

    public int getSize() {
        return vehicleList.size();
    }

    public UserVehicle getUserVehicle(int index) {
        return vehicleList.get(index);
    }

    public void add(UserVehicle userVehicle) {
        vehicleList.add(userVehicle);
    }

    public void delete(int index){
        vehicleList.remove(index);
    }

}


