package alex.carbon_tracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.UtilityBill;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.R;

/*Second screen of AddUtilityBill for the user to enter
* the values for their Utility Bill.
* */
public class AddUtilityBillPart2 extends AppCompatActivity {

    CarbonTrackerModel model = CarbonTrackerModel.getInstance();
    UtilityBillManager manager = model.getUtilityBillManager();
    int selectedYear;
    int selectedMonth;
    int selectedDay;
    int selectedYear2;
    int selectedMonth2;
    int selectedDay2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utility_bill_part2);

        setupOnClickCalendar(R.id.startDateCalendar);
        setupOnClickCalendar2(R.id.endDateCalendar);

        UtilityBill mostRecentBill = manager.getMostRecentBill();

        Intent intent = getIntent();

        int householdSize = intent.getIntExtra("householdSize", 1);
        float electricalConsumption = intent.getFloatExtra("electricalConsumption", mostRecentBill.getHouseholdElectricalConsumption());
        float gasConsumption = intent.getFloatExtra("gasConsumption", mostRecentBill.getHouseholdGasConsumption());

        setupSubmitBtn(householdSize, electricalConsumption, gasConsumption);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.back_actionbar_icon:
                Intent intent = new Intent(this, UtilitylistActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                long endDateVal = getDateFromCalendar2(R.id.endDateCalendar);
                Date startDate;
                Date endDate;
                CalendarView startDateCalendar = (CalendarView) findViewById(R.id.startDateCalendar);
                CalendarView endDateCalendar = (CalendarView) findViewById(R.id.endDateCalendar);
                if (startDateVal < 0) {
                    startDate = new Date(startDateCalendar.getDate());
                } else {
                    startDate = new Date(startDateVal);
                }
                if (endDateVal < 0) {
                    endDate = new Date(endDateCalendar.getDate());
                } else {
                    endDate = new Date(endDateVal);
                }
                if (startDate.getTime() < endDate.getTime()) {
                    manager.addBill(new UtilityBill(gasConsumption, electricalConsumption, startDate, endDate, householdSize));
                    Intent intent = new Intent(AddUtilityBillPart2.this, UtilitylistActivity.class);
                    Toast.makeText(AddUtilityBillPart2.this, model.getTipManager().getTip(), Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddUtilityBillPart2.this, "Invalid Dates! Please Re-Input!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setupOnClickCalendar(int id) {
        final CalendarView calendarView = (CalendarView) findViewById(id);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth + 1;
            }
        });
    }

    private void setupOnClickCalendar2(int id) {
        final CalendarView calendarView = (CalendarView) findViewById(id);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedYear2 = year;
                selectedMonth2 = month;
                selectedDay2 = dayOfMonth + 1;
            }
        });
    }

    private long getDateFromCalendar(int id) {
        final CalendarView calendarView = (CalendarView) findViewById(id);
        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gc.clear();
        gc.set(selectedYear, selectedMonth, selectedDay);

        long date_InMilSec = gc.getTimeInMillis();

        Log.d("selectDateActivity", date_InMilSec + "*************************************");
        return date_InMilSec;
    }

    private long getDateFromCalendar2(int id) {
        final CalendarView calendarView = (CalendarView) findViewById(id);
        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gc.clear();
        gc.set(selectedYear2, selectedMonth2, selectedDay2);

        long date_InMilSec = gc.getTimeInMillis();

        Log.d("selectDateActivity", date_InMilSec + "*************************************");
        return date_InMilSec;
    }

}
