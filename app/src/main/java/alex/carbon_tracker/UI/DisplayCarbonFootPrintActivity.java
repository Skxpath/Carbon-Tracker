package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.R;
public class DisplayCarbonFootPrintActivity extends AppCompatActivity {
    CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private final int DATE = 0;
    private final int ROUTE_NAME = 1;
    private final int DISTANCE = 2;
    private final int VEHICLE_NAME = 3;
    private final int CO2_EMISSION = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_carbon_foot_print);
        setupCarbonFootPrintTable();
    }

    private void setupCarbonFootPrintTable() {
        TableLayout carbonTable = (TableLayout)findViewById(R.id.carbonTableLayout);
        CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
        TableRow carbonlabels = new TableRow(this);
        carbonTable.addView(carbonlabels);
        for (int i = 0; i < 5; i++) {
            carbonTable.setColumnStretchable(i, true);
            TextView text = new TextView(this);
            if (i == DATE) {
                text.setText("Date");
            } else if (i == ROUTE_NAME) {
                text.setText("RouteName");
            } else if (i == DISTANCE) {
                text.setText("Distance(KM)");
            } else if (i == VEHICLE_NAME) {
                text.setText("Vehicle");
            } else if (i == CO2_EMISSION) {
                    text.setText("Co2 Emission");
                }
                text.setTextSize(12f);
                text.setTypeface(null, Typeface.BOLD);
                carbonlabels.addView(text);
            }
        for(int i = 0; i <carbonTrackerModel.getJourneyManager().getJourneyList().size();i++){

            TableRow tableRow = new TableRow(this);
            carbonTable.addView(tableRow);
          tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,1.0f));
           // Going through colums to fill in the information
            for(int j = 0; j< 5;j++){
                carbonTable.setColumnStretchable(j,true);
                TextView textview = new TextView(this);
                if(j == DATE){
                    textview.setText("Date");
                }
                else if(j == ROUTE_NAME){
                    textview.setText(carbonTrackerModel.getJourneyManager().getJourney(i).getRoute().toString());
                }
                else if(j == DISTANCE){
                    int distance = carbonTrackerModel.getJourneyManager().getJourney(i).getRoute().getCityDistance()
                            + carbonTrackerModel.getJourneyManager().getJourney(i).getRoute().getHighwayDistance();
                    textview.setText(distance+"");
                }
                else if (j == VEHICLE_NAME){
                    textview.setText(carbonTrackerModel.getJourneyManager().getJourney(i).getUserVehicle().getNickname());
                }
                else if(j == CO2_EMISSION){
                    textview.setText("co2");
                }
                textview.setTextSize(12f);
                tableRow.addView(textview);

            }

        }
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, DisplayCarbonFootPrintActivity.class);
    }
}
