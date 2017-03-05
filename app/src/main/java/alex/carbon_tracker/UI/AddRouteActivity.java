package alex.carbon_tracker.UI;
/*
* after you get all the information on route, call finish which will take you back to the select route activity.
* read the usecases to see more detail.
*/


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.RouteManager;
import alex.carbon_tracker.R;

public class AddRouteActivity extends AppCompatActivity {

    private static final String ERROR_CITY_MSG = "City distance must be greater than or equal to zero.";
    private static final String ERROR_HIGHWAY_MSG = "Highway distance must be greater than or equal to zero.";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private RouteManager routeManager = carbonTrackerModel.getRouteManager();

    private static int cityDistance = 0;
    private static int highwayDistance = 0;
    private static int index = 0;

    private static boolean isEditingRoute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        Intent intent = getIntent();

        //utilizeIntentExtras(intent);

        setupSubmitBtn();
    }
/*
    private void utilizeIntentExtras(Intent intent) {
        if (intent.hasExtra(SelectRouteActivity.ROUTE_INDEX)) {
            isEditingRoute = true;
            Bundle extras = intent.getExtras();
            index = (int) extras.get(SelectRouteActivity.ROUTE_INDEX);
            Route route = routeManager.getRoute(index);
            cityDistance = route.getCityDistance();
            highwayDistance = route.getHighwayDistance();
            if (!(cityDistance == 0 && highwayDistance == 0)) {
                setNumbToEditText(R.id.cityDistanceEditText, cityDistance);
                setNumbToEditText(R.id.highwayDistanceEditText, highwayDistance);
            }
        }
    }
*/
    private void setupSubmitBtn() {
        Button submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText cityDistEditText = (EditText) findViewById(R.id.cityDistanceEditText);
                cityDistance = Integer.parseInt(cityDistEditText.getText().toString());
                EditText highwayDistEditText = (EditText) findViewById(R.id.highwayDistanceEditText);
                highwayDistance = Integer.parseInt(highwayDistEditText.getText().toString());
                Route route = new Route(cityDistance, highwayDistance);
                routeManager.addRoute(route);

                finish();
            }
        });
    }

    private void addRoute() {
        boolean isCityDistValid = validateParameters(R.id.cityDistanceEditText);
        boolean isHighwayDistValid = validateParameters(R.id.highwayDistanceEditText);
        if (isCityDistValid && isHighwayDistValid) {
            EditText cityDistEditText = (EditText) findViewById(R.id.cityDistanceEditText);
            cityDistance = Integer.parseInt(cityDistEditText.getText().toString());
            EditText highwayDistEditText = (EditText) findViewById(R.id.highwayDistanceEditText);
            highwayDistance = Integer.parseInt(highwayDistEditText.getText().toString());
            Route route = new Route(cityDistance, highwayDistance);
            routeManager.addRoute(route);
        } else {
            if (!isCityDistValid) {
                Toast.makeText(AddRouteActivity.this, ERROR_CITY_MSG, Toast.LENGTH_SHORT).show();
            }
            if (!isHighwayDistValid) {
                Toast.makeText(AddRouteActivity.this, ERROR_HIGHWAY_MSG, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void editRoute() {
        boolean isCityDistValid = validateParameters(R.id.cityDistanceEditText);
        boolean isHighwayDistValid = validateParameters(R.id.highwayDistanceEditText);
        if (isCityDistValid && isHighwayDistValid) {
            Route route = new Route(cityDistance, highwayDistance);
            routeManager.editRoute(route, index);
        } else {
            if (!isCityDistValid) {
                Toast.makeText(AddRouteActivity.this, ERROR_CITY_MSG, Toast.LENGTH_SHORT).show();
            }
            if (!isHighwayDistValid) {
                Toast.makeText(AddRouteActivity.this, ERROR_HIGHWAY_MSG, Toast.LENGTH_SHORT).show();
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
        String valueAsString = Integer.toString(value);
        editText.setText(valueAsString);
    }

    private String getEditTextAsString(int id) {
        EditText text = (EditText) findViewById(id);
        return text.getText().toString();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddRouteActivity.class);
    }
}
