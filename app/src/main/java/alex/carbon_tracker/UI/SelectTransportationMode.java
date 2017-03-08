package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.R;

public class SelectTransportationMode extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private UserVehicleManager userVehicleManager = carbonTrackerModel.getUserVehicleManager();

    public static final String CAR_INDEX = "carIndex";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_modey);
        setupAddCarButton();
        carListView();
        setupOnItemClickListener();
       // setupOnItemLongClickListener();
    }

    private void setupAddCarButton() {
        Button btn = (Button) findViewById(R.id.AddCarButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCarActivity.makeIntent(SelectTransportationMode.this);
                startActivity(intent);
            }
        });
    }

    // shows a list view of all the current cars
    private void carListView() {
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

                userVehicleManager.setCurrentVehicle(userVehicleManager.getUserVehicle(i));
                Toast.makeText(SelectTransportationMode.this, "Good", Toast.LENGTH_SHORT).show();
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationMode.this);
                startActivity(intent);
                finish();
            }
        });
    }
/*
    private void setupOnItemLongClickListener() {
        ListView list = (ListView) findViewById(R.id.carListView);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = AddCarActivity.makeIntent(SelectTransportationMode.this);
                intent.putExtra(CAR_INDEX, position);
                startActivity(intent);
                Toast.makeText(SelectTransportationMode.this, "Bad", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
*/
    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportationMode.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAddCarButton();
        carListView();
        setupOnItemClickListener();
    }
}
