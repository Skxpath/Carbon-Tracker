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

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.RouteManager;
import alex.carbon_tracker.R;

public class SelectRouteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_ROUTE = 101;
    public static final int REQUEST_CODE_EDIT_ROUTE = 102;

    public static final String USER_ROUTE_CITY = "userRouteCity";
    public static final String USER_ROUTE_HIGHWAY = "userRouteHighway";
    public static final String ROUTE_INDEX = "routeIndex";
    public static final String ROUTE_CITY = "routeCity";
    public static final String ROUTE_HIGHWAY = "routeHighway";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private RouteManager routeManager = carbonTrackerModel.getRouteManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        routeManager.addRoute(new Route(100, 122));
        routeManager.addRoute(new Route(40, 112322));
        routeManager.addRoute(new Route(1512100, 2));
        setupAddRouteButton();
        //setupOnItemLongClickListenerToEdit();
        populateListView();
    }

    private void setupAddRouteButton() {
        Button btn = (Button) findViewById(R.id.addRouteButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddRouteActivity.makeIntent(SelectRouteActivity.this);
                startActivity(intent);
            }
        });
        populateListView();
    }

    private void populateListView() {
        String[] routeNameList = routeManager.getRouteDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.route_item, routeNameList);
        ListView list =(ListView) findViewById(R.id.routeListView);
        list.setAdapter(adapter);
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
