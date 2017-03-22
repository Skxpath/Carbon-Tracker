package alex.carbon_tracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import alex.carbon_tracker.Model.CarbonCalculator;
import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.RouteManager;
import alex.carbon_tracker.Model.Transportation;
import alex.carbon_tracker.Model.TransportationManager;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.R;

/*
* Select Route Activity which allows
* the user to select a route for a journey to use. Also
*  allows the user to add a new route in the interface.
*  */
public class SelectRouteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_ROUTE = 101;
    public static final int REQUEST_CODE_EDIT_ROUTE = 102;

    public static final String ROUTE_INDEX = "routeIndex";
    public static final String SELECTED_VEHICLE = "Vehicle";
    public static final String SELECTED_NON_VEHICLE = "Non Vehicle";

    private static final String WALK = "Walk";
    private static final String BUS = "Bus";
    private static final String SKY_TRAIN = "Sky Train";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private RouteManager routeManager = carbonTrackerModel.getRouteManager();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();
    private UserVehicleManager userVehicleManager = carbonTrackerModel.getUserVehicleManager();
    private TransportationManager transportationManager = carbonTrackerModel.getTransportationManager();

    private Transportation transportation;
    private int currentRoutePosition = 0;

    private boolean isVehicle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        ListView routeList = (ListView) findViewById(R.id.routeListView);
        setCurrentRoutePosition();

        Intent intent = getIntent();
        getExtrasFromIntent(intent);

        registerForContextMenu(routeList);

        setupAddRouteButton();

        populateListView();

        selectRoute();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }
    private void getExtrasFromIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (intent.hasExtra(SelectTransportationModeActivity.SELECT_BUS)) {
            double CO2 = (Double) extras.get(SelectTransportationModeActivity.SELECT_BUS);
            transportation = new Transportation(CO2, BUS);
            transportationManager.setCurrTransportation(transportation);
            Log.d("SelectRouteActivity", "" + CO2);
        } else if (intent.hasExtra(SelectTransportationModeActivity.SELECT_WALK)) {
            double CO2 = (Double) extras.get(SelectTransportationModeActivity.SELECT_WALK);
            transportation = new Transportation(CO2, WALK);
            transportationManager.setCurrTransportation(transportation);
            Log.d("SelectRouteActivity", "" + CO2);
        } else if (intent.hasExtra(SelectTransportationModeActivity.SELECT_SKY_TRAIN)) {
            double CO2 = (Double) extras.get(SelectTransportationModeActivity.SELECT_SKY_TRAIN);
            transportation = new Transportation(CO2, SKY_TRAIN);
            transportationManager.setCurrTransportation(transportation);
            Log.d("SelectRouteActivity", "" + CO2);
        } else {
            isVehicle = true;
            Log.d("SelectRouteActivity", "Car");

        }
    }

    private void setupAddRouteButton() {
        Button btn = (Button) findViewById(R.id.addRouteButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddRouteActivity.makeIntent(SelectRouteActivity.this);
                if (isVehicle) {
                    intent.putExtra(SelectRouteActivity.SELECTED_VEHICLE, 0);
                } else {
                    intent.putExtra(SelectRouteActivity.SELECTED_NON_VEHICLE, 0);
                }
                startActivity(intent);
                finish();
            }
        });
    }


    private void populateListView() {
        String[] routeNameList = routeManager.getRouteDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.route_item, routeNameList);
        ListView list = (ListView) findViewById(R.id.routeListView);
        list.setAdapter(adapter);
    }

    private void selectRoute() {
        ListView list = (ListView) findViewById(R.id.routeListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                routeManager.setCurrentRoute(routeManager.getRoute(i));
                Route userCurrentRoute = routeManager.getCurrentRoute();
                double distanceTravelledCity = userCurrentRoute.getCityDistance();
                double distanceTravelledHighway = userCurrentRoute.getHighwayDistance();

                if (isVehicle) {
                    UserVehicle userCurrentVehicle = userVehicleManager.getCurrentVehicle();
                    double gasType = userCurrentVehicle.getFuelTypeNumber();
                    int milesPerGallonCity = userCurrentVehicle.getCityDrive();
                    int milesPerGallonHighway = userCurrentVehicle.getHighwayDrive();

                    // double gasType, double distanceTravelledCity, double distanceTravelledHighway, int milesPerGallonCity, int milesPerGallonHighway
                    double CO2Emissions = CarbonCalculator.calculate(gasType, distanceTravelledCity, distanceTravelledHighway, milesPerGallonCity, milesPerGallonHighway);

                    Journey journey = new Journey(userCurrentVehicle, userCurrentRoute, CO2Emissions,
                            journeyManager.getSelectedYear(), journeyManager.getSelectedMonth(), journeyManager.getSelectedDay());
                    journeyManager.add(journey);
                } else {
                    double CO2Emissions = CarbonCalculator.calculate(transportation.getCO2InKGperDistanceInKM(), distanceTravelledCity, distanceTravelledHighway);

                    Journey journey = new Journey(transportation, userCurrentRoute, CO2Emissions,
                            journeyManager.getSelectedYear(), journeyManager.getSelectedMonth(), journeyManager.getSelectedDay());
                    journeyManager.add(journey);
                }
                Toast.makeText(SelectRouteActivity.this, carbonTrackerModel.getTipManager().getTip(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Edit");
    }

    private void setCurrentRoutePosition() {
        final ListView listView = (ListView) findViewById(R.id.routeListView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentRoutePosition = position;
                return false;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")) {
            // delete the car
            carbonTrackerModel.getRouteManager().deleteRoute(currentRoutePosition);
            populateListView();
            return true;
        } else if (item.getTitle().equals("Edit")) {
            Intent intent = EditRouteActivity.makeIntent(this);
            int cityDis = routeManager.getRoute(currentRoutePosition).getCityDistance();
            int highWayDis = routeManager.getRoute(currentRoutePosition).getHighwayDistance();
            String routeName = routeManager.getRoute(currentRoutePosition).getNickname();
            intent.putExtra("highway", highWayDis);
            intent.putExtra("city", cityDis);
            intent.putExtra("name", routeName);
            intent.putExtra("routePosition", currentRoutePosition);
            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRouteActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }
}