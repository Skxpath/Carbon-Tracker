package alex.carbon_tracker.Model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * VehicleManager class which manages created Vehicles
 * and stores them in an ArrayList.
 */
import alex.carbon_tracker.UI.MainActivity;

public class VehicleManager {
    private List<Vehicle> vehicleList = new ArrayList<>();
    private List<String> vehicleMakeList = new ArrayList<>();

    public void writeDataToList(Context context, int vehicles) {
        CSVReader csvReader = new CSVReader();
        csvReader.startNewThreadForReadingData(context, vehicles, this);

    }

    public int getSize(){
        return vehicleList.size();
    }

    public Vehicle getVehicle(int index) {
        return vehicleList.get(index);
    }

    public void add(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    public void createVehicleMakeList(){
        HashSet hashSet = new HashSet();
        for (int i = 0; i < vehicleList.size(); i++) {
            String make = getVehicle(i).getMake();
            if (hashSet.add(make)) {
                vehicleMakeList.add(make);
                Log.i("123",make + ", " + i);
            }
        }
/*
        for(int i = 0; i<vehicleList.size();i++){
            boolean isCarInTheList = false;
            String currentCarMake = getVehicle(i).getMake();
            Log.i("123",currentCarMake + ", " + i);
            for(int j = 0; j< vehicleMakeList.size(); j++){
                if(vehicleMakeList.get(j).equals(currentCarMake)){
                    isCarInTheList = true;
                    j = vehicleMakeList.size();
                }
            }
            if(isCarInTheList == false) {
                vehicleMakeList.add(currentCarMake);
                Log.i("Make","hi");
            }
        }
        */
        Log.i("makeList","hello");
    }

    public List<String> getVehicleMakeList() {
        return vehicleMakeList;
    }
}
