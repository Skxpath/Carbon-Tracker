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

    public static final String ROUTE_INDEX = "routeIndex";

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private RouteManager routeManager = carbonTrackerModel.getRouteManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        setupAddRouteButton();
        setupOnItemLongClickListenerToEdit();
        populateListView();
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
        ListView list =(ListView) findViewById(R.id.routeListView);
        list.setAdapter(adapter);
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
