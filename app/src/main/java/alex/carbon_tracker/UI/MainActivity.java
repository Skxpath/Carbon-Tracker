package alex.carbon_tracker.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Vehicle;
import alex.carbon_tracker.Model.VehicleManager;
import alex.carbon_tracker.R;

public class MainActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private VehicleManager vehicleManager = carbonTrackerModel.getVehicleManager();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMenuButton();

        startNewThreadForReadingData();
    }

    private void startNewThreadForReadingData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                readVehicleData();
            }
        }).start();
    }

    // Base on info from: http//stackoverflow.com/a/19976110
    private void readVehicleData() {
        InputStream is = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            // Step over headers
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                // Read the data
                storeTokensToVehicleManager(tokens);
            }
        } catch (IOException e) {
            Log.wtf("MenuActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    private void storeTokensToVehicleManager(String[] tokens) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(tokens[0]);
        vehicle.setModel(tokens[1]);
        if (tokens[2].length() > 0) {
            vehicle.setYear(Integer.parseInt(tokens[2]));
        } else {
            vehicle.setYear(0);
        }
        if (tokens.length >= 3 && tokens[3].length() > 0) {
            vehicle.setCarbonEmission(Double.parseDouble(tokens[3]));
        } else {
            vehicle.setCarbonEmission(0);
        }
        vehicleManager.add(vehicle);
        //Log.d("MenuActivity", "Just created: " + vehicle.toString());
    }


    private void setupMenuButton() {
        Button btn = (Button) findViewById(R.id.mainMenuButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MenuActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

}