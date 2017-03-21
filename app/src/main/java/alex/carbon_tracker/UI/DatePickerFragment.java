package alex.carbon_tracker.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.R;

/**
 * Fragment for selecting a date
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView textView = (TextView) getActivity().findViewById(R.id.textViewDate);
        int selectedYear = view.getYear();
        int selectedMonth = view.getMonth();
        int selectedDay = view.getDayOfMonth();

        // Todo: Testing calendar
        long date_MilSecondsFrom1970 = view.getMaxDate();
        long test_date_MilSecondsFrom1970 = view.getMinDate();
        Date date = new Date(date_MilSecondsFrom1970);
        Log.d("DatePickerFragment", date_MilSecondsFrom1970 + "");
        Log.d("DatePickerFragment", test_date_MilSecondsFrom1970 + "");

        textView.setText("Year: " + selectedYear + " Month: " + selectedMonth + " Day: " + selectedDay);
        journeyManager.setSelectedYear(selectedYear);
        journeyManager.setSelectedMonth(selectedMonth);
        journeyManager.setSelectedDay(selectedDay);

        journeyManager.setHasDate(true);

    }
}
