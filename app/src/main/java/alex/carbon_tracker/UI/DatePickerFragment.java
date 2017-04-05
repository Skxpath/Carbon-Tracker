package alex.carbon_tracker.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

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

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView textView = (TextView) getActivity().findViewById(R.id.textViewDate);
        int selectedYear = view.getYear();
        int selectedMonth = view.getMonth();
        int selectedDay = view.getDayOfMonth();

        textView.setText("Year: " + selectedYear
                + " Month: " + selectedMonth
                + " Day: " + selectedDay);
//        journeyManager.setSelectedYear(selectedYear);
//        journeyManager.setSelectedMonth(selectedMonth);
//        journeyManager.setSelectedDay(selectedDay);
//
//        journeyManager.setHasDate(true);
    }
}
