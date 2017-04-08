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

import alex.carbon_tracker.Model.CarbonCalculator;
import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.Transportation;
import alex.carbon_tracker.R;

/*SelectTransportationModeActivity which allows the user
* to select a transportation mode for their journey.
* */
public class SelectTransportationModeActivity extends AppCompatActivity {

    private static final String WALK = "Walk";
    private static final String BUS = "Bus";
    private static final String SKY_TRAIN = "Sky Train";
    public static final double WALK_CARBON_EMISSION_GRAMS = 0.0;
    public static final double BUS_CARBON_EMISSION_GRAMS = 89.0;
    public static final double SKY_TRAIN_CARBON_EMISSION_GRAMS = 50.4;
    public static final String SELECT_WALK = "Walk";
    public static final String SELECT_BUS = "Bus";
    public static final String SELECT_SKY_TRAIN = "Sky Train";
    public static final String EDIT_JOURNEY = "editJourney";
    public static final String JOURNEY_POSITION = "journeyPosition";
    private boolean isEditingJourney = false;
    private int editJourneyPosition;
    CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation_mode);

        setupSelectWalkButton();
        setupSelectBusButton();
        setupSelectSkyTrainButton();
        setupSelectCarButton();
        getIntentFromJourney();
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
                Intent intent = new Intent(this, JourneyListActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getIntentFromJourney() {
        Intent intent = getIntent();
        isEditingJourney = intent.getBooleanExtra(EDIT_JOURNEY, false);
        if (isEditingJourney) {
            editJourneyPosition = intent.getIntExtra(JOURNEY_POSITION, 0);
        }
    }

    private void setupSelectWalkButton() {
        Button button = (Button) findViewById(R.id.buttonSelectWalk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationModeActivity.this);
                intent.putExtra(SELECT_WALK, WALK_CARBON_EMISSION_GRAMS);
                editJourneyPosition
                        = getIntent().getIntExtra("journeyPosition", 0);


                // if editing journey
                if (isEditingJourney) {
                    resetTheVehicle(WALK_CARBON_EMISSION_GRAMS, WALK);
                    finish();
                } else {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setupSelectBusButton() {
        Button button = (Button) findViewById(R.id.buttonSelectBus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationModeActivity.this);
                intent.putExtra(SELECT_BUS, BUS_CARBON_EMISSION_GRAMS);
                intent.putExtra("editJourney", isEditingJourney);
                editJourneyPosition = getIntent().getIntExtra("journeyPosition", 0);
                if (isEditingJourney) {
                    resetTheVehicle(BUS_CARBON_EMISSION_GRAMS, BUS);
                    finish();
                } else {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    public void resetTheVehicle(double emissionInGram, String transportation) {
        editJourneyPosition = getIntent().getIntExtra("journeyPosition", 0);
        Transportation trans = new Transportation(emissionInGram, transportation);
        journeyManager.getJourney(editJourneyPosition).setVehicle(null);
        journeyManager.getJourney(editJourneyPosition).setTransportation(trans);
        //recalculating co2 emmision
        Route currRoute = journeyManager.getJourney(editJourneyPosition).getRoute();
        double distanceTravelledCity = currRoute.getCityDistance();
        double distanceTravelledHighway = currRoute.getHighwayDistance();
        double CO2Emissions = CarbonCalculator.calculate(trans.getCO2InKGperDistanceInKM(),
                distanceTravelledCity, distanceTravelledHighway);
        journeyManager.getJourney(editJourneyPosition).setCarbonEmitted(CO2Emissions);
    }

    private void setupSelectSkyTrainButton() {
        Button button = (Button) findViewById(R.id.buttonSelectSkyTrain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationModeActivity.this);
                intent.putExtra(SELECT_SKY_TRAIN, SKY_TRAIN_CARBON_EMISSION_GRAMS);
                intent.putExtra("editJourney", isEditingJourney);
                editJourneyPosition = getIntent().getIntExtra("journeyPosition", 0);
                if (isEditingJourney) {
                    resetTheVehicle(SKY_TRAIN_CARBON_EMISSION_GRAMS, SKY_TRAIN);
                    finish();
                } else {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setupSelectCarButton() {
        Button button = (Button) findViewById(R.id.buttonSelectVehicle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectVehicleActivity.makeIntent(SelectTransportationModeActivity.this);
                intent.putExtra("editJourney", isEditingJourney);
                if (isEditingJourney) {
                    intent.putExtra("journeyPosition", editJourneyPosition);
                }
                startActivity(intent);
                finish();
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportationModeActivity.class);
    }

}
