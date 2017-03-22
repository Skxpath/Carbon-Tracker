package alex.carbon_tracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.UtilityBill;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.R;

public class AddUtilityBillPart2 extends AppCompatActivity {

    CarbonTrackerModel model = CarbonTrackerModel.getInstance();
    UtilityBillManager manager = model.getUtilityBillManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utility_bill_part2);

        UtilityBill mostRecentBill = manager.getMostRecentBill();

        Intent intent = getIntent();

        int householdSize = intent.getIntExtra("householdSize", 1);
        float electricalConsumption = intent.getFloatExtra("electricalConsumption", mostRecentBill.getHouseholdElectricalConsumption());
        float gasConsumption = intent.getFloatExtra("gasConsumption", mostRecentBill.getHouseholdGasConsumption());

        setupSubmitBtn(householdSize, electricalConsumption, gasConsumption);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }

    private void setupSubmitBtn(final int householdSize, final float electricalConsumption, final float gasConsumption) {
        Button submitBtn;
        submitBtn = (Button) findViewById(R.id.submitBill);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startDateVal = getDateFromCalendar(R.id.startDateCalendar);
                long endDateVal = getDateFromCalendar(R.id.endDateCalendar);
                if (startDateVal < endDateVal) {
                    Date startDate = new Date(startDateVal);
                    Date endDate = new Date(endDateVal);
                    manager.addBill(new UtilityBill(gasConsumption, electricalConsumption, startDate, endDate, householdSize));
                    Intent intent = new Intent(AddUtilityBillPart2.this, UtilitylistActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddUtilityBillPart2.this, "Invalid Dates! Please Re-Input!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private long getDateFromCalendar(int id) {
        CalendarView calendarView = (CalendarView) findViewById(id);
        Date date = new Date(calendarView.getDate());
        return calendarView.getDate();
    }

}
