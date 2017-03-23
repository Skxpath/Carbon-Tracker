package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.R;

/*SelectDateActivity which allows
* the user to select a date for their
* Journey*/
public class SelectDateActivity extends AppCompatActivity {

    public static final String DATE_PICKER = "datePicker";
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        setupDateButton();
        setupAddDateButton();
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

    private void setupAddDateButton() {
        Button button = (Button) findViewById(R.id.buttonAddSelectedDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (journeyManager.hasDate()) {
                    Intent intent = SelectTransportationModeActivity.makeIntent(SelectDateActivity.this);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SelectDateActivity.this, R.string.ErrorMessage, Toast.LENGTH_SHORT).show();
                }
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
