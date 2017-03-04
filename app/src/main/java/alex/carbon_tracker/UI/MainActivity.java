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

import alex.carbon_tracker.Model.CSVReader;
import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Vehicle;
import alex.carbon_tracker.Model.VehicleManager;
import alex.carbon_tracker.R;

public class MainActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private VehicleManager vehicleManager = carbonTrackerModel.getVehicleManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMenuButton();

        vehicleManager.writeDataToList(this, R.raw.vehicles);
    }

    private void setupMenuButton() {
        Button btn = (Button) findViewById(R.id.mainMenuButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PieChartActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

}