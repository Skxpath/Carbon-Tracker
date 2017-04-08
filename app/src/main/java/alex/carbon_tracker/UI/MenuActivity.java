package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.R;

/*MenuActivity class that serves
* for the main menu when the user first
* enters the application*/
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        /*int journeyListID = findViewById(R.id.journeyListButton).getId();
        int utilityButtonID = findViewById(R.id.utilityListButton).getId();
        int graphListID = findViewById(R.id.graphListButton).getId();
        int newTipID = findViewById(R.id.tipsButton).getId();
*/
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


    /*    setupButton(journeyListID);
        setupButton(utilityButtonID);
        setupButton(graphListID);
        setupButton(newTipID);*/

        setuphelpbutton();
        updateTipTextview();
    }

    public void setuphelpbutton() {
        int helpButtonID = findViewById(R.id.aboutButton).getId();
        final Button helpButton = (Button) findViewById(R.id.aboutButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = HelpPageActivity.makeIntent(MenuActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.graphs, menu);
        menuInflater.inflate(R.menu.tips, menu);
        menuInflater.inflate(R.menu.utility, menu);
        menuInflater.inflate(R.menu.journey, menu);
        menuInflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tips_actionbar_icon:
                updateTipTextview();
                return true;
            case R.id.graphs_actionbar_icon:
                Intent intent = DateListActivity.makeIntent(MenuActivity.this);
                startActivity(intent);
                return true;
            case R.id.utility_actionbar_icon:
                intent = UtilitylistActivity.makeIntent(MenuActivity.this);
                startActivity(intent);
                return true;
            case R.id.journey_actionbar_icon:
                intent = JourneyListActivity.makeIntent(MenuActivity.this);
                startActivity(intent);
                return true;
            case R.id.settings_actionbar_icon:
                intent = SettingsActivity.makeIntent(MenuActivity.this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


/*private void setupButton(final int buttonID) {
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
                }
            }

        });
    }*/

    private void updateTipTextview() {
        CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
        TextView tips = (TextView) (findViewById(R.id.textView19));
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
