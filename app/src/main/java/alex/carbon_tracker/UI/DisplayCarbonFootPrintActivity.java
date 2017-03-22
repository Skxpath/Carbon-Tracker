package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.SaveData;
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

    public static final String CHANGE_TO_GRAPHS = "Change to graphs from tables";
    public static final String PASS_YEAR_DATA = "Pass year data";
    public static final String PASS_MONTH_DATA = "Pass month data";
    public static final String PASS_DAY_DATA = "Pass day data";

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_carbon_foot_print);

        getExtrasFromIntent(getIntent());

        setupCarbonFootPrintTable();
        setupChangeButton();
    }

    private void getExtrasFromIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        // User comes from pie chart activity
        if (intent.hasExtra(PieChartActivity.CHANGE_TO_TABLE)) {
            if (intent.hasExtra(PieChartActivity.DISPLAY_DATA_DAY)) {
                year = (int) extras.get(PASS_YEAR_DATA);
                month = (int) extras.get(PASS_MONTH_DATA);
                day = (int) extras.get(PASS_DAY_DATA);
            }
        } else if (intent.hasExtra(DateListActivity.CHANGE_TO_GRAPHS)) {

            if (intent.hasExtra(DateListActivity.DISPLAY_DATA_DAY)) {
                Log.d("DisplayActivity", extras.get(DateListActivity.SELECTED_DAY) + "");
                year = (int) extras.get(DateListActivity.SELECTED_YEAR);
                month = (int) extras.get(DateListActivity.SELECTED_MONTH);
                day = (int) extras.get(DateListActivity.SELECTED_DAY);
            }
        }
    }

    private void setupCarbonFootPrintTable() {
        TableLayout carbonTable = (TableLayout) findViewById(R.id.carbonTableLayout);
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

        displayDataOnTableLayout(carbonTable);
    }

    private void displayDataOnTableLayout(TableLayout tableLayout) {
        List<Journey> journeysOnSelectedDay = new ArrayList<>();
        addJourneysOnSelectedDay(journeysOnSelectedDay, year, month, day);
        for (int i = 0; i < journeysOnSelectedDay.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableLayout.addView(tableRow);
            for (int j = 0; j < 5; j++) {
                Journey journey = journeysOnSelectedDay.get(i);
                tableLayout.setMinimumWidth(20);
                TextView textview = new TextView(this);
                if (j == DATE) {
                    textview.setText(journey.getMonth()
                            + "/" + journey.getDay()
                            + "/" + journey.getYear());
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

    private void addJourneysOnSelectedDay(List<Journey> journeysOnSelectedDay,
                                          int year,
                                          int month,
                                          int day) {
        for (Journey journey : journeyManager.getJourneyList()) {
            boolean isSameDate = journey.getYear() == year
                    && journey.getMonth() == month
                    && journey.getDay() == day;
            if (isSameDate) {
                journeysOnSelectedDay.add(journey);
            }
        }
    }

    private void setupChangeButton() {
        Button changeButton = (Button) findViewById(R.id.buttonToGraph);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PieChartActivity.makeIntent(DisplayCarbonFootPrintActivity.this);
                intent.putExtra(PASS_YEAR_DATA, year);
                intent.putExtra(PASS_MONTH_DATA, month);
                intent.putExtra(PASS_DAY_DATA, day);
                intent.putExtra(CHANGE_TO_GRAPHS, 0);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = DateListActivity.makeIntent(DisplayCarbonFootPrintActivity.this);
        startActivity(intent);
        finish();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, DisplayCarbonFootPrintActivity.class);
    }
}
