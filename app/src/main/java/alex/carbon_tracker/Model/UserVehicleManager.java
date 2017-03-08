package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-03-06.
 */

public class UserVehicleManager {
    private List<NotUserVehicle> vehicleList = new ArrayList<>();
    private NotUserVehicle currentVehicle;

    public NotUserVehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(NotUserVehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public List<NotUserVehicle> getVehicleList() {
        return vehicleList;
    }

    public int getSize() {
        return vehicleList.size();
    }

    public NotUserVehicle getUserVehicle(int index) {
        return vehicleList.get(index);
    }

    public void add(NotUserVehicle notUserVehicle) {
        vehicleList.add(notUserVehicle);
    }

    public void delete(int index){
        vehicleList.remove(index);
    }

}


