package alex.carbon_tracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import alex.carbon_tracker.Model.CarbonCalculator;
import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.RouteManager;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.R;

public class SelectRouteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_ROUTE = 101;
    public static final int REQUEST_CODE_EDIT_ROUTE = 102;

    public static final String ROUTE_INDEX = "routeIndex";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private RouteManager routeManager = carbonTrackerModel.getRouteManager();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();
    private UserVehicleManager userVehicleManager = carbonTrackerModel.getUserVehicleManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        setupAddRouteButton();
        setupOnItemLongClickListenerToEdit();
        populateListView();
        selectRoute();
    }

    private void setupAddRouteButton() {
        Button btn = (Button) findViewById(R.id.addRouteButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddRouteActivity.makeIntent(SelectRouteActivity.this);
                startActivityForResult(intent, REQUEST_CODE_EDIT_ROUTE);
                populateListView();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (resultCode) {
            case REQUEST_CODE_EDIT_ROUTE:
                break;
        }
        populateListView();
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

                UserVehicle userCurrentVehicle = userVehicleManager.getCurrentVehicle();
                Route userCurrentRoute = routeManager.getCurrentRoute();

                double gasType = userCurrentVehicle.getFuelTypeNumber();
                double distanceTravelledCity = userCurrentRoute.getCityDistance();
                double distanceTravelledHighway = userCurrentRoute.getHighwayDistance();
                int milesPerGallonCity = userCurrentVehicle.getCityDrive();
                int milesPerGallonHighway = userCurrentVehicle.getHighwayDrive();

                // double gasType, double distanceTravelledCity, double distanceTravelledHighway, int milesPerGallonCity, int milesPerGallonHighway
                double CO2Emissions = CarbonCalculator.calculate(gasType, distanceTravelledCity, distanceTravelledHighway, milesPerGallonCity, milesPerGallonHighway);

                Journey journey = new Journey(userCurrentVehicle, userCurrentRoute, CO2Emissions);
                journeyManager.add(journey);


                finish();
            }
        });
    }

    private void setupOnItemLongClickListenerToEdit() {
        ListView list = (ListView) findViewById(R.id.routeListView);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = AddRouteActivity.makeIntent(SelectRouteActivity.this);
                intent.putExtra(ROUTE_INDEX, position);
                startActivityForResult(intent, REQUEST_CODE_EDIT_ROUTE);
                return true;
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRouteActivity.class);
    }
}