package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        int journeyListID = findViewById(R.id.journeyListButton).getId();
        int utilityButtonID = findViewById(R.id.utilityListButton).getId();
        int graphListID = findViewById(R.id.graphListButton).getId();
        int newTipID = findViewById(R.id.tipsButton).getId();
        setupButton(journeyListID);
        setupButton(utilityButtonID);
        setupButton(graphListID);
        setupButton(newTipID);

        updateTipTextview();

    }

    private void setupButton(final int buttonID) {
        final Button button = (Button) findViewById(buttonID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonID == findViewById(R.id.journeyListButton).getId()) {
                    Intent intent = JourneyListActivity.makeIntent(MenuActivity.this);
                    startActivity(intent);
                } else if (buttonID == findViewById(R.id.graphListButton).getId()) {
                    Intent intent = DateListActivity.makeIntent(MenuActivity.this);
                    startActivity(intent);
                } else if (buttonID == findViewById(R.id.utilityListButton).getId()) {
                    Intent intent = UtilitylistActivity.makeIntent(MenuActivity.this);
                    startActivity(intent);
                } else if (buttonID == findViewById(R.id.tipsButton).getId()) {
                    updateTipTextview();
                    Log.i("test", "tipsbutton clicked");
                }
            }

        });
    }

    private void updateTipTextview() {
        CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
        TextView tips =(TextView)(findViewById(R.id.textView19));
        if (carbonTrackerModel.getJourneyManager().totalJourneys() > 0) {
            tips.setText(carbonTrackerModel.getTipManager().getTip());
        } else {
            tips.setText("Add a new Journey before we can display to you a useful tip!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MenuActivity.class);
    }
}
