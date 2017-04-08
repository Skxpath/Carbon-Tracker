package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.CarbonUnitsEnum;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.Settings;
import alex.carbon_tracker.R;

import static alex.carbon_tracker.Model.CarbonUnitsEnum.KILOGRAMS;
import static alex.carbon_tracker.Model.CarbonUnitsEnum.TREE_DAYS;

/*
* SettingsActivity class to display when the user
* wants to change the setting of units displayed
* on the application
* */
public class SettingsActivity extends AppCompatActivity {
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private Settings settings = carbonTrackerModel.getSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupButton(R.id.buttonChangeUnitsToKG);
        setupButton(R.id.buttonChangeUnitsToTrees);

        updateText(R.id.settingsUnitExample);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }

    private void updateText(int id) {
        TextView textView = (TextView) findViewById(id);
        CarbonUnitsEnum unit = settings.getCarbonUnit();
        switch (unit) {
            case KILOGRAMS:
                textView.setText("Example: 40.0 kg");
                return;

            case TREE_DAYS:
                textView.setText("Example: 2.0 tree-days");
                return;

            default:
                assert false;
        }
    }

    private void setupButton(int id) {
        Button button = (Button) findViewById(id);
        switch (id) {
            case R.id.buttonChangeUnitsToKG:
                setupKGButton(button);

                return;

            case R.id.buttonChangeUnitsToTrees:
                setupTreeButton(button);
                return;

            default:
                assert false;
        }
    }

    private void setupKGButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setCarbonUnit(KILOGRAMS);
                updateText(R.id.settingsUnitExample);
            }
        });
    }

    private void setupTreeButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setCarbonUnit(TREE_DAYS);
                updateText(R.id.settingsUnitExample);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}
