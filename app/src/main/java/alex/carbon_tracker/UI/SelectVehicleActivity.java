package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import alex.carbon_tracker.Model.CarbonCalculator;
import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.R;

/*
* SelectVehicleActivity activity which allows
* the user to select a car for a journey, or add a new
* personal car that does not exist on the list.
* */
public class SelectVehicleActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel;
    private UserVehicleManager userVehicleManager;
    /// currentVehicle position to use in delete and edit option
    private int currentVehiclePosition = 0;

    public static final String CAR_INDEX = "carIndex";
    private String carMake = "";
    private String carModel = "";
    private int carYear = 0;
    private String carSpec = "";
    private String carNickname = "";
    private int editJourneyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        carbonTrackerModel = CarbonTrackerModel.getInstance();
        userVehicleManager = carbonTrackerModel.getUserVehicleManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        setupAddCarButton();
        populateCarListView();
        setupOnItemClickListener();
        ListView vehicleList = (ListView) findViewById(R.id.carListView);
        registerForContextMenu(vehicleList);
        setCurrentVehiclePosition();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        // userVehicleManager.add(new UserVehicle(carMake,carModel,88,));
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }

    private void setCurrentVehiclePosition() {
        final ListView listView = (ListView) findViewById(R.id.carListView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentVehiclePosition = position;
                return false;
            }
        });
    }

    private void setupAddCarButton() {
        Button btn = (Button) findViewById(R.id.AddCarButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getBooleanExtra("editJourney", false)) {
                    editJourneyPosition = getIntent().getIntExtra("journeyPosition", 0);
                    Intent intent = AddCarActivity.makeIntent(SelectVehicleActivity.this);
                    intent.putExtra("editJourney", true);
                    intent.putExtra("journeyPosition", editJourneyPosition);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = AddCarActivity.makeIntent(SelectVehicleActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // shows a list view of all the current cars
    private void populateCarListView() {
        String[] carList = CarbonTrackerModel.getInstance().getUserVehicleManager().getUserVehicleDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.jouney_list, carList);
        ListView list = (ListView) findViewById(R.id.carListView);
        list.setAdapter(adapter);
    }

    private void setupOnItemClickListener() {
        ListView list = (ListView) findViewById(R.id.carListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //editing journey
                Intent intent = getIntent();
                if (intent.getBooleanExtra("editJourney", false)) {
                    editJourneyPosition = intent.getIntExtra("journeyPosition", 0);
                    UserVehicle newVehicle = userVehicleManager.getUserVehicle(i);
                    carbonTrackerModel.getJourneyManager().getJourney(editJourneyPosition)
                            .setVehicle(newVehicle);
                    //resetting carbon emmission
                    Route userCurrentRoute = carbonTrackerModel.getJourneyManager().
                            getJourney(editJourneyPosition).getRoute();

                    double distanceTravelledCity = userCurrentRoute.getCityDistance();
                    double distanceTravelledHighway = userCurrentRoute.getHighwayDistance();

                    double gasType = newVehicle.getFuelTypeNumber();
                    int milesPerGallonCity = newVehicle.getCityDrive();
                    int milesPerGallonHighway = newVehicle.getHighwayDrive();

                    double CO2Emissions = CarbonCalculator.calculate(gasType, distanceTravelledCity,
                            distanceTravelledHighway, milesPerGallonCity, milesPerGallonHighway);
                    //setting the new carbon emission value
                    carbonTrackerModel.getJourneyManager().getJourney(editJourneyPosition).setCarbonEmitted(CO2Emissions);
                    finish();
                } else {
                    userVehicleManager.setCurrentVehicle(userVehicleManager.getUserVehicle(i));
                    Intent intent1 = SelectRouteActivity.makeIntent(SelectVehicleActivity.this);
                    startActivity(intent1);
                    finish();

                }
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectVehicleActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAddCarButton();
        populateCarListView();
        setupOnItemClickListener();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (!getIntent().getBooleanExtra("editJourney", false)) {
            menu.add(0, v.getId(), 0, "Delete");
            menu.add(0, v.getId(), 0, "Edit");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")) {
            // delete the car
            carbonTrackerModel.getUserVehicleManager().delete(currentVehiclePosition);
            populateCarListView();
            return true;
        } else if (item.getTitle().equals("Edit")) {
            setIntent();
            Intent intent = EditCarActivity.makeIntent(this);
            intent.putExtra("make", carMake);
            intent.putExtra("model", carModel);
            intent.putExtra("year", carYear);
            intent.putExtra("carNickName", carNickname);
            intent.putExtra("position", currentVehiclePosition);
            intent.putExtra("carSpec", carSpec);
            startActivity(intent);
            return true;
        } else {
            return false;
        }

    }

    public void setIntent() {
        carMake = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getMake();
        carModel = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getModel();
        carYear = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getYear();
        carNickname = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getNickname();
        carSpec = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getTransmission()
                + "," + carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getFuelType() +
                "," + carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getFuelTypeNumber();
    }
}
