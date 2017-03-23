package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.UtilityBill;
import alex.carbon_tracker.R;

/*SelectDateActivity which allows
* the user to select a date for their
* Journey*/
public class SelectDateActivity extends AppCompatActivity {

    public static final String DATE_PICKER = "datePicker";
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);


        setupOnClickCalendar(R.id.calendarViewJourneyDate);
        setupSubmitBtn();
        setupDateButton();
    }


    private void setupSubmitBtn() {
        Button submitBtn;
        submitBtn = (Button) findViewById(R.id.buttonAddSelectedDate);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarView calendarView = (CalendarView) findViewById(R.id.calendarViewJourneyDate);
                long dateVal = getDateFromCalendar(R.id.calendarViewJourneyDate);
                if (dateVal < 0) {
                    Date date = new Date(calendarView.getDate());
                    journeyManager.setDate(date);
                    Log.d("selectDateActivity", journeyManager.getDate() + " pooooop");
                } else {
                    Date date = new Date(dateVal);
                    journeyManager.setDate(date);
                    Log.d("selectDateActivity", journeyManager.getDate() + " pooooop");
                }
                Intent intent = new Intent(SelectDateActivity.this, SelectTransportationModeActivity.class);
                startActivity(intent);
                finish();
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

    private long getDateFromCalendar(int id) {
        final CalendarView calendarView = (CalendarView) findViewById(id);
        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gc.clear();
        gc.set(selectedYear, selectedMonth, selectedDay);

        long date_InMilSec = gc.getTimeInMillis();

        Log.d("selectDateActivity", date_InMilSec + "*************************************");
        return date_InMilSec;
    }

    private void setupDateButton() {
        Button button = (Button) findViewById(R.id.buttonSelectDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journeyManager.setHasDate(false);
                displayDateFragment(v);
            }
        });
    }

    public void displayDateFragment(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), DATE_PICKER);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectDateActivity.class);
    }
}
