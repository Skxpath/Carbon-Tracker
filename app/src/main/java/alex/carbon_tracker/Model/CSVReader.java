package alex.carbon_tracker.Model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;

import alex.carbon_tracker.R;

/*
* CSVReader class to read in the raw
* data from the vehicles.csv file for usage
* in the application
* */
public class CSVReader {

    public void startNewThreadForReadingData(final Context context,
                                             final int resource,
                                             final VehicleManager vehicleManager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                readVehicleData(context, resource, vehicleManager);
            }
        }).start();
    }

    // Base on info from: http//stackoverflow.com/a/19976110
    private void readVehicleData(Context context, int resource, VehicleManager vehicleManager) {
        InputStream is = context.getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        HashSet hashSet = new HashSet();
        try {
            // Step over headers
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                // Read the data
                storeTokensToVehicleManager(tokens, vehicleManager, hashSet);
            }
        } catch (IOException e) {
            Log.wtf("MenuActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }

    }

    private void storeTokensToVehicleManager(String[] tokens, VehicleManager vehicleManager, HashSet hashSet) {
        Vehicle vehicle = new Vehicle();
        boolean hasNaturalGas = false;
        vehicle.setMake(tokens[0]);
        vehicle.setModel(tokens[1]);
        if (tokens.length >= 2 && tokens[2].length() > 0) {
            vehicle.setYear(Integer.parseInt(tokens[2]));
        } else {
            vehicle.setYear(0);
        }
        if (tokens.length >= 3 && tokens[3].length() > 0) {
            vehicle.setCityDrive(Integer.parseInt(tokens[3]));
        } else {
            vehicle.setCityDrive(0);
        }
        if (tokens.length >= 4 && tokens[4].length() > 0) {
            vehicle.setHighwayDrive(Integer.parseInt(tokens[4]));
        } else {
            vehicle.setHighwayDrive(0);
        }
        if (tokens.length >= 5 && tokens[5].length() > 0) {
            if (tokens[5].equals("NA")) {
                vehicle.setDisplacement(0);
            } else {
                vehicle.setDisplacement(Double.parseDouble(tokens[5]));
            }
        } else {
            vehicle.setDisplacement(0);
        }
        if (tokens.length >= 6 && tokens[6].length() > 0) {
            vehicle.setFuelType(tokens[6]);

            switch (tokens[6]) {
                case "Electricity":
                    vehicle.setFuelTypeNumber(Vehicle.ELECTRICITY);
                    break;
                case "Diesel":
                    vehicle.setFuelTypeNumber(Vehicle.DIESEL);
                    break;
                case "CNG":
                    hasNaturalGas = true;
                    break;
                default:
                    vehicle.setFuelTypeNumber(Vehicle.GASOLINE);
                    break;
            }
        }
        if (tokens.length > 7 && tokens[7].length() > 0) {
            vehicle.setTransmission(tokens[7]);
        }

        if (!hasNaturalGas) {
            if (hashSet.add(vehicle)) {
                vehicleManager.add(vehicle);
            }
        }
    }
}
