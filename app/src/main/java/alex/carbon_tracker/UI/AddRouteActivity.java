package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import alex.carbon_tracker.Model.Transportation;
import alex.carbon_tracker.Model.TransportationManager;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.R;

/*
* Add Route Activity page which allows the
* user to add a new route they travelled on
* in the system. We take in highway and city
* distance travelled (in km).
*/
public class AddRouteActivity extends AppCompatActivity {

    private static final String ERROR_CITY_MSG = "City distance must be greater than or equal to zero.";
    private static final String ERROR_HIGHWAY_MSG = "Highway distance must be greater than or equal to zero.";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private RouteManager routeManager = carbonTrackerModel.getRouteManager();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();
    private UserVehicleManager userVehicleManager = carbonTrackerModel.getUserVehicleManager();
    private TransportationManager transportationManager = carbonTrackerModel.getTransportationManager();

    private static int cityDistance = 0;
    private static int highwayDistance = 0;
    private static String routeName = "";
    private static int index = 0;

    private static boolean isEditingRoute = false;
    private static boolean isVehicle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        Intent intent = getIntent();
        getExtrasFromIntent(intent);

        setupSubmitBtn();
    }

    private void getExtrasFromIntent(Intent intent) {
        if (intent.hasExtra(SelectRouteActivity.SELECTED_VEHICLE)) {
            isVehicle = true;
            Log.d("AddRouteActivity", isVehicle + "");
        }
    }

    private void setupSubmitBtn() {
        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateParameters(R.id.cityDistanceEditText)
                        && validateParameters(R.id.highwayDistanceEditText)
                        && validateParameters(R.id.routeNameEditText)) {
                    if (isEditingRoute) {
                        editRoute();
                    } else {
                        addRoute();
                    }

                    Route userCurrentRoute = routeManager.getCurrentRoute();
                    double distanceTravelledCity = userCurrentRoute.getCityDistance();
                    double distanceTravelledHighway = userCurrentRoute.getHighwayDistance();

                    if (isVehicle) {
                        UserVehicle userCurrentVehicle = userVehicleManager.getCurrentVehicle();
                        double gasType = userCurrentVehicle.getFuelTypeNumber();
                        int milesPerGallonCity = userCurrentVehicle.getCityDrive();
                        int milesPerGallonHighway = userCurrentVehicle.getHighwayDrive();

                        double CO2Emissions = CarbonCalculator.calculate(gasType,
                                distanceTravelledCity,
                                distanceTravelledHighway,
                                milesPerGallonCity,
                                milesPerGallonHighway);

                        Journey journey = new Journey(userCurrentVehicle,
                                userCurrentRoute,
                                CO2Emissions,
                                journeyManager.getDate());
                        journeyManager.add(journey);
                    } else {
                        Transportation transportation = transportationManager.getCurrTransportation();
                        double CO2Emissions = CarbonCalculator.calculate(
                                transportation.getCO2InKGperDistanceInKM(),
                                distanceTravelledCity,
                                distanceTravelledHighway);

                        Journey journey = new Journey(transportation,
                                userCurrentRoute,
                                CO2Emissions,
                                journeyManager.getDate());
                        journeyManager.add(journey);
                    }

                    Intent intent = JourneyListActivity.makeIntent(AddRouteActivity.this);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddRouteActivity.this,
                            R.string.ErrorMessage,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRoute() {
        EditText cityDistEditText = (EditText) findViewById(R.id.cityDistanceEditText);
        cityDistance = Integer.parseInt(cityDistEditText.getText().toString());
        EditText highwayDistEditText = (EditText) findViewById(R.id.highwayDistanceEditText);
        highwayDistance = Integer.parseInt(highwayDistEditText.getText().toString());
        routeName = getEditTextAsString(R.id.routeNameEditText);

        Route route = new Route(cityDistance, highwayDistance, routeName);
        routeManager.addRoute(route);
        routeManager.setCurrentRoute(route);
    }

    private void editRoute() {
        int newCityDistance = Integer.parseInt(
                getEditTextAsString(R.id.cityDistanceEditText));
        int newHighwayDistance = Integer.parseInt(
                getEditTextAsString(R.id.highwayDistanceEditText));
        routeName = getEditTextAsString(R.id.routeNameEditText);

        Route route = new Route(newCityDistance, newHighwayDistance, routeName);
        routeManager.editRoute(route, index);
        routeManager.setCurrentRoute(route);
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
        return new Intent(context, AddRouteActivity.class);
    }
}
