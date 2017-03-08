package alex.carbon_tracker.UI;
/*
* after you get all the information on route, call finish which will take you back to the select route activity.
* read the usecases to see more detail.
*/


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
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
    private static String routeName = "";
    private static int index = 0;

    private static boolean isEditingRoute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        Intent intent = getIntent();

        utilizeIntentExtras(intent);

        setupSubmitBtn();
       // setupDeleteButton(intent);
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
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddRouteActivity.this, "Invalid Information Inputted. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void utilizeIntentExtras(Intent intent) {
        if (intent.hasExtra(SelectRouteActivity.SelectTransportationMode)) {
            isEditingRoute = true;
            Bundle extras = intent.getExtras();
            index = (int) extras.get(SelectRouteActivity.SelectTransportationMode);
            Route route = routeManager.getRoute(index);
            cityDistance = route.getCityDistance();
            highwayDistance = route.getHighwayDistance();
            routeName = route.getNickname();

            if (!(cityDistance == 0 && highwayDistance == 0)) {
                setNumbToEditText(R.id.cityDistanceEditText, cityDistance);
                setNumbToEditText(R.id.highwayDistanceEditText, highwayDistance);
                setStringToEditText(R.id.routeNameEditText, routeName);
            }
        }
    }
/*
    private void setupDeleteButton(Intent intent) {
        if (intent.hasExtra(SelectRouteActivity.SelectTransportationMode)) {
            isEditingRoute = true;
            Button button = new Button(this);
            button.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f));

            button.setText("Delete");
            button.setPadding(0, 0, 0, 0);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridButtonClicked();
                }
                private void gridButtonClicked() {
                    routeManager.deleteRoute(index);
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });

            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayoutButton);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.addView(button, layoutParams);
        }
    }
*/
    private void addRoute() {
        EditText cityDistEditText = (EditText) findViewById(R.id.cityDistanceEditText);
        cityDistance = Integer.parseInt(cityDistEditText.getText().toString());
        EditText highwayDistEditText = (EditText) findViewById(R.id.highwayDistanceEditText);
        highwayDistance = Integer.parseInt(highwayDistEditText.getText().toString());
        routeName = getEditTextAsString(R.id.routeNameEditText);

        Route route = new Route(cityDistance, highwayDistance, routeName);
        routeManager.addRoute(route);
    }

    private void editRoute() {
        int newCityDistance = Integer.parseInt(getEditTextAsString(R.id.cityDistanceEditText));
        int newHighwayDistance = Integer.parseInt(getEditTextAsString(R.id.highwayDistanceEditText));
        routeName = getEditTextAsString(R.id.routeNameEditText);

        Route route = new Route(newCityDistance, newHighwayDistance, routeName);
        routeManager.editRoute(route, index);
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


// Given make, model, year, traverse through