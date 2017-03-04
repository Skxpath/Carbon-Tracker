package alex.carbon_tracker.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import alex.carbon_tracker.UI.MainActivity;

public class VehicleManager {
    private List<Vehicle> vehicleList = new ArrayList<>();

    public void writeDataToList(Context context, int vehicles) {
        CSVReader csvReader = new CSVReader();
        csvReader.startNewThreadForReadingData(context, vehicles, this);
    }

    public Vehicle getVehicle(int index) {
        return vehicleList.get(index);
    }

    public void add(Vehicle vehicle){
        vehicleList.add(vehicle);
    }
}
