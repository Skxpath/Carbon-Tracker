package alex.carbon_tracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.R;

/*
* Display Carbon Footprint Activity class which
* displays the total carbon footprint created by
* the user given the journeys they travelled
* */
public class DisplayCarbonFootPrintActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    private static final int DATE = 0;
    private static final int ROUTE_NAME = 1;
    private static final int DISTANCE = 2;
    private static final int VEHICLE_NAME = 3;
    private static final int CO2_EMISSION = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_carbon_foot_print);

        setupCarbonFootPrintTable();
        setupChangeButton();
    }

    private void setupCarbonFootPrintTable() {
        TableLayout carbonTable = (TableLayout) findViewById(R.id.carbonTableLayout);
        CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
        TableRow carbonLabels = new TableRow(this);
        carbonTable.addView(carbonLabels);
        for (int i = 0; i < 5; i++) {
            carbonTable.setColumnStretchable(i, true);
            TextView text = new TextView(this);
            if (i == DATE) {
                text.setText(R.string.DisplayDate);
            } else if (i == ROUTE_NAME) {
                text.setText(R.string.DisplayName);
            } else if (i == DISTANCE) {
                text.setText(R.string.DisplayDistance);
            } else if (i == VEHICLE_NAME) {
                text.setText(R.string.DisplayVehicle);
            } else if (i == CO2_EMISSION) {
                text.setText(R.string.DisplayCO2);
            }
            text.setTextSize(12f);
            text.setTypeface(null, Typeface.BOLD);
            carbonLabels.addView(text);
        }

        for (int i = 0; i < journeyManager.getJourneyList().size(); i++) {
            TableRow tableRow = new TableRow(this);
            carbonTable.addView(tableRow);
            //tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
              //      TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            // Going through columns to fill in the information
            for (int j = 0; j < 5; j++) {
                Journey journey = journeyManager.getJourney(i);
                //carbonTable.setColumnStretchable(j, true);
                carbonTable.setMinimumWidth(20);
                TextView textview = new TextView(this);
                if (j == DATE) {
                    textview.setText(journey.getMonth() + "/" + journey.getDay() + "/" + journey.getYear());
                } else if (j == ROUTE_NAME) {
                    textview.setText(journey.getRoute().toString());
                } else if (j == DISTANCE) {
                    int distance = journey.getRoute().getCityDistance()
                            + journey.getRoute().getHighwayDistance();
                    textview.setText(distance + "");
                } else if (j == VEHICLE_NAME) {
                    if (journey.hasVehicle()) {
                        textview.setText(journey.getUserVehicle().getNickname());
                    } else {
                        textview.setText(journey.getTransportation().getType());
                    }
                } else if (j == CO2_EMISSION) {
                    String carbonEmitted = String.format("%.5f", journey.getCarbonEmitted());
                    textview.setText(carbonEmitted);
                }
                textview.setTextSize(12f);
                textview.setGravity(Gravity.CENTER);
                tableRow.addView(textview);
            }
        }
    }

    private void setupChangeButton() {
        Button changeButton = (Button) findViewById(R.id.buttonToGraph);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PieChartActivity.makeIntent(DisplayCarbonFootPrintActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }
/*
    @Override
    public void onBackPressed() {
        Intent intent = JourneyListActivity.makeIntent(DisplayCarbonFootPrintActivity.this);
        startActivity(intent);
        finish();
    }
*/
    public static Intent makeIntent(Context context) {
        return new Intent(context, DisplayCarbonFootPrintActivity.class);
    }
}
