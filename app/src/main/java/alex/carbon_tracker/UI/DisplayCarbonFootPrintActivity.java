package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
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

        TableLayout carbonTable = (TableLayout)findViewById(R.id.carbonTableLayout);

        for(int i = 0; i <5;i++){
            TableRow tableRow = new TableRow(this);
            carbonTable.addView(tableRow);
            carbonTable.setColumnStretchable(i,true);
            for(int j = 0; j< 5;j++){
                TextView textview = new TextView(this);

                textview.setText("hello");
                tableRow.addView(textview);
            }

        }
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, DisplayCarbonFootPrintActivity.class);
    }
}
