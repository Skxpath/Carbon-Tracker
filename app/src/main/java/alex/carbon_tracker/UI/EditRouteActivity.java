package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alex.carbon_tracker.Model.CarbonCalculator;
import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.RouteManager;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.R;

/*
EditRouteActivity to support editing
* of a route
* */
public class EditRouteActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();
    private UserVehicleManager userVehicleManager = carbonTrackerModel.getUserVehicleManager();
    private RouteManager routeManager = carbonTrackerModel.getRouteManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        setupDataFromIntent();
        setupSubmitBtn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }

    private void setupDataFromIntent() {
        Intent intent = getIntent();
        setNumbToEditText(R.id.cityDistanceEditText, intent.getIntExtra("city", 0));
        setNumbToEditText(R.id.highwayDistanceEditText, intent.getIntExtra("highway", 0));
        setStringToEditText(R.id.routeNameEditText, intent.getStringExtra("name"));
    }


    private void setupSubmitBtn() {
        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateParameters(R.id.cityDistanceEditText)
                        && validateParameters(R.id.highwayDistanceEditText)
                        && validateParameters(R.id.routeNameEditText)) {
                    editRoute();
                    finish();
                } else {
                    Toast.makeText(EditRouteActivity.this, "Invalid Information Inputted. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void editRoute() {
        int newCityDistance = Integer.parseInt(getEditTextAsString(R.id.cityDistanceEditText));
        int newHighwayDistance = Integer.parseInt(getEditTextAsString(R.id.highwayDistanceEditText));
        String routeName = getEditTextAsString(R.id.routeNameEditText);

        Route route = new Route(newCityDistance, newHighwayDistance, routeName);
        int routePosition = getIntent().getIntExtra("routePosition", 0);
        Route originRoute = journeyManager.getJourney(routePosition).getRoute();

        carbonTrackerModel.getRouteManager().editRoute(route, routePosition);
        for (int i = 0; i < journeyManager.getJourneyList().size(); i++) {
            Journey journey = journeyManager.getJourney(i);
            if (journey.getRoute() == originRoute) {
                journey.setRoute(route);

                UserVehicle userCurrentVehicle = userVehicleManager.getCurrentVehicle();
                Route userCurrentRoute = routeManager.getRoute(routePosition);
                double gasType = userCurrentVehicle.getFuelTypeNumber();
                double distanceTravelledCity = userCurrentRoute.getCityDistance();
                double distanceTravelledHighway = userCurrentRoute.getHighwayDistance();
                int milesPerGallonCity = userCurrentVehicle.getCityDrive();
                int milesPerGallonHighway = userCurrentVehicle.getHighwayDrive();

                // double gasType, double distanceTravelledCity, double distanceTravelledHighway, int milesPerGallonCity, int milesPerGallonHighway
                double CO2Emissions = CarbonCalculator.calculate(gasType, distanceTravelledCity, distanceTravelledHighway, milesPerGallonCity, milesPerGallonHighway);

                journey.setCarbonEmitted(CO2Emissions);
            }

        }


    }

    private boolean validateParameters(int id) {
        boolean isValid = true;
        EditText editText = (EditText) findViewById(id);
        String editTextString = editText.getText().toString().trim();
        if (editTextString.isEmpty() || editTextString.length() == 0) {
            isValid = false;
        }
        if (editTextString.matches("[0-9]+")) {
            int editTextInt = Integer.parseInt(editTextString);
            if (editTextInt < 0) {
                isValid = false;
            }
        }
        return isValid;
    }

    private void setNumbToEditText(int id, int value) {
        EditText editText = (EditText) findViewById(id);
        String finalValue = Integer.toString(value);
        editText.setText(finalValue);
    }

    private void setStringToEditText(int id, String value) {
        EditText editText = (EditText) findViewById(id);
        editText.setText(value);
    }

    private String getEditTextAsString(int id) {
        EditText text = (EditText) findViewById(id);
        return text.getText().toString();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, EditRouteActivity.class);
    }
}
