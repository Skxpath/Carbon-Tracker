package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.R;
// date routeName distance vehicle  carbon
public class DisplayCarbonFootPrintActivity extends AppCompatActivity {
    CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_carbon_foot_print);
        setupCarbonFootPrintTable();
    }

    private void setupCarbonFootPrintTable() {
        TableLayout labels = (TableLayout) findViewById(R.id.labels);
        TableRow x = (TableRow) labels.getChildAt(0);
        for(int i = 0; i< 5;i++){
            labels.setColumnStretchable(i,true);
            TextView text = new TextView(this);
            if(i == 0){
                text.setText("Date");
            }
            else if(i == 1){
                text.setText("RouteName");
            }
            else if(i == 2){
                text.setText("Distance");
            }
            else if (i == 3){
                text.setText("Vehicle");
            }
            else if(i == 4){
                text.setText("Co2 Emission");
            }
            text.setTypeface(null, Typeface.BOLD);
            x.addView(text);
        }
        TableLayout carbonTable = (TableLayout)findViewById(R.id.carbonTableLayout);
        CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
        for(int i = 0; i <carbonTrackerModel.getJourneyManager().getJourneyList().size();i++){
            TableRow tableRow = new TableRow(this);
            carbonTable.addView(tableRow);
           // tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
             //       TableLayout.LayoutParams.MATCH_PARENT,5.0f));
            for(int j = 0; j< 5;j++){
                carbonTable.setColumnStretchable(j,true);
                TextView textview = new TextView(this);
                if(j == 0){
                    textview.setText("Date");
                }
                else if(j == 1){
                    textview.setText(carbonTrackerModel.getRouteManager().getRoute(i).getNickname());
                }
                else if(j == 2){
                    textview.setText("Distance");
                }
                else if (j == 3){
                    textview.setText("Vehicle");
                    textview.setText(carbonTrackerModel.getUserVehicleManager().getUserVehicle(i).getNickname());
                }
                else if(j == 4){
                    textview.setText("Co2 Emission");
                }
                tableRow.addView(textview);

            }

        }
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, DisplayCarbonFootPrintActivity.class);
    }
}
