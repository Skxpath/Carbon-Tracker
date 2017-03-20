package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import alex.carbon_tracker.R;

public class SelectDateActivity extends AppCompatActivity {

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
                displayDateFragment(v);
            }
        });
    }

    private void setupAddDateButton() {
        Button button = (Button) findViewById(R.id.buttonAddSelectedDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectTransportationModeActivity.makeIntent(SelectDateActivity.this);
                startActivity(intent);
            }
        });
    }


    public void displayDateFragment(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectDateActivity.class);
    }
}
