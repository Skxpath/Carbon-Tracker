package alex.carbon_tracker.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/*
* VehicleManager class which manages the
* Vehicle objects created by the CSVReader
* */
public class VehicleManager {
    private List<Vehicle> vehicleList = new ArrayList<>();

    public void writeDataToList(Context context, int vehicles) {
        CSVReader csvReader = new CSVReader();
        csvReader.startNewThreadForReadingData(context, vehicles, this);

    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public int getSize() {
        return vehicleList.size();
    }

    public Vehicle getVehicle(int index) {
        return vehicleList.get(index);
    }

    public void add(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

}
