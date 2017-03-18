package alex.carbon_tracker.UI;

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

import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.R;

/*
* Select Transportation Mode activity which allows
* the user to select a car for a journey, or add a new
* personal car that does not exist on the list.
* */
public class SelectTransportationMode extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private UserVehicleManager userVehicleManager = carbonTrackerModel.getUserVehicleManager();
/// currentVehicle position to use in delete and edit option
    private int currentVehiclePosition=0;

    public static final String CAR_INDEX = "carIndex";
    private String carMake = "";
    private String carModel = "";
    private int carYear = 0;
    private String carSpec = "";
    private String carNickname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_modey);
        setupAddCarButton();
        carListView();
        setupOnItemClickListener();
        ListView vehicleList = (ListView) findViewById(R.id.carListView);
        registerForContextMenu(vehicleList);
        setCurrentVehiclePosition();
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
                Intent intent = AddCarActivity.makeIntent(SelectTransportationMode.this);
                startActivity(intent);
                finish();
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
                Intent intent = SelectRouteActivity.makeIntent(SelectTransportationMode.this);
                startActivity(intent);
                finish();
            }
        });
    }

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,v.getId(),0,"Delete");
        menu.add(0,v.getId(),0,"Edit");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Delete")){
            // delete the car
            carbonTrackerModel.getUserVehicleManager().delete(currentVehiclePosition);
            carListView();
            return true;
        } else if (item.getTitle().equals("Edit")) {
            setIntent();
            Intent intent = EditCarActivity.makeIntent(this);
            intent.putExtra("make", carMake);
            intent.putExtra("model", carModel);
            intent.putExtra("year", carYear);
            intent.putExtra("carNickName", carNickname);
            intent.putExtra("position", currentVehiclePosition);
            intent.putExtra("carSpec",carSpec);
            startActivity(intent);
            return true;
        } else {
            return false;
        }

    }
    public void setIntent(){
        carMake = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getMake();
        carModel = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getModel();
        carYear = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getYear();
        carNickname = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getNickname();
        carSpec = carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getTransmission()
                +","+ carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getFuelType()+
                ","+ carbonTrackerModel.getUserVehicleManager().getUserVehicle(currentVehiclePosition).getFuelTypeNumber();
    }
}
