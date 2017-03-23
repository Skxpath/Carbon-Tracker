package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import alex.carbon_tracker.R;

/*SelectTransportationModeActivity which allows the user
* to select a transportation mode for their journey.
* */
public class SelectTransportationModeActivity extends AppCompatActivity {

    public static final double WALK_CARBON_EMISSION_GRAMS = 0.0;
    public static final double BUS_CARBON_EMISSION_GRAMS = 89.0;
    public static final double SKY_TRAIN_CARBON_EMISSION_GRAMS = 50.4;
    public static final String SELECT_WALK = "Walk";
    public static final String SELECT_BUS = "Bus";
    public static final String SELECT_SKY_TRAIN = "Sky Train";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation_mode);

        setupSelectWalkButton();
        setupSelectBusButton();
        setupSelectSkyTrainButton();
        setupSelectCarButton();

    }

    private void setupSelectWalkButton() {
        Button button = (Button) findViewById(R.id.buttonSelectWalk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationModeActivity.this);
                intent.putExtra(SELECT_WALK, WALK_CARBON_EMISSION_GRAMS);
                startActivity(intent);
                finish();

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
                startActivity(intent);
                finish();

            }
        });
    }

    private void setupSelectSkyTrainButton() {
        Button button = (Button) findViewById(R.id.buttonSelectSkyTrain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationModeActivity.this);
                intent.putExtra(SELECT_SKY_TRAIN, SKY_TRAIN_CARBON_EMISSION_GRAMS);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupSelectCarButton() {
        Button button = (Button) findViewById(R.id.buttonSelectVehicle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SelectVehicleActivity.makeIntent(SelectTransportationModeActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportationModeActivity.class);
    }

}
