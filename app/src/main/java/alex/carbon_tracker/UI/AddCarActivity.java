package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.R;

public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private  CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private String carMake = "";
    private String carModel = "";
    boolean listLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        setupCarMakeDropDown();
        setupOkButton();
        setupCarMakeDropDown();
    }

    private void setupOkButton() {
        Button ok = (Button)findViewById(R.id.addCarOkButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save carMake information
                //start route activity
                //Intent intent = SelectRouteActivity.makeIntent(AddCarActivity.this);
                //startActivity(intent);
                //finish();
            }
        });
    }

    private void addCarToTheModel() {
    // add the carMake to the carbonModel

    }

    private void setupCarYearDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carYearDropDown);
        carMakeMenu.setOnItemSelectedListener(this);
        // getting information from carbonTrackerModel
        List<String> carYearList = new ArrayList<String>();
        for(int i = 0; i<carbonTrackerModel.getVehicleManager().getSize();i++){
            boolean isCarYearInTheList = false;
            String currentCarYear = carbonTrackerModel.getVehicleManager().getVehicle(i).getYear()+"";
            for(int j = 0; j< carYearList.size(); j++){
                if(carYearList.get(j).toString().equals(currentCarYear)){
                    isCarYearInTheList = true;
                    break;
                }
            }
            // checking if car year is already in the list?
            if(isCarYearInTheList == false &&
                    carbonTrackerModel.getVehicleManager().getVehicle(i).getMake().toString().equals(carMake)
                    && carbonTrackerModel.getVehicleManager().getVehicle(i).getModel().equals(carModel)) {

                carYearList.add(currentCarYear);
            }
        }
        // making the drop down menu
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this,android.R.layout.simple_spinner_item, carYearList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
    }

    private void setupCarModelDropDown() {
        Spinner carModelMenu = (Spinner) findViewById(R.id.carModelDropDownMenu);
        carModelMenu.setOnItemSelectedListener(this);
        List<String> carModelList = new ArrayList<String>();
        for(int i = 0; i<carbonTrackerModel.getVehicleManager().getSize();i++){
            boolean isCarModelInTheList = false;
           String currentCarMake = carbonTrackerModel.getVehicleManager().getVehicle(i).getModel().toString();
            for(int j = 0; j< carModelList.size(); j++){
                if(carModelList.get(j).toString().equals(currentCarMake)){
                    isCarModelInTheList = true;
                    break;
                }
            }
            if(isCarModelInTheList == false &&
                    carbonTrackerModel.getVehicleManager().getVehicle(i).getMake().toString().equals(carMake) ) {
                carModelList.add(currentCarMake);
            }
        }
        //setting up the dropdown
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this,android.R.layout.simple_spinner_item, carModelList);
       adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModelMenu.setAdapter(adapt);
        adapt.notifyDataSetChanged();
    }

    private void setupCarMakeDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carMakeDropMenu);
        carMakeMenu.setOnItemSelectedListener(this);
        //getting the car make and saving it in a list.
         List<String> carMakeList = new ArrayList<String>();
        HashSet hashSet = new HashSet();
        for(int i = 0; i<carbonTrackerModel.getVehicleManager().getSize();i++){
            if(hashSet.add(carbonTrackerModel.getVehicleManager().getVehicle(i).getMake())){
                carMakeList.add(carbonTrackerModel.getVehicleManager().getVehicle(i).getMake());
            }
        }
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this,android.R.layout.simple_spinner_item, carMakeList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapt.notifyDataSetChanged();
        carMakeMenu.setAdapter(adapt);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCarActivity.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == findViewById(R.id.carMakeDropMenu).getId()) {
            if(listLoaded == false){
                listLoaded = true;
                setupCarMakeDropDown();
            }
            carMake = adapterView.getItemAtPosition(i).toString();
          Toast.makeText(adapterView.getContext(), carMake, Toast.LENGTH_LONG).show();
        setupCarModelDropDown();

        }
        else if(adapterView.getId() == findViewById(R.id.carModelDropDownMenu).getId()) {
            carModel = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), carMake, Toast.LENGTH_LONG).show();
            setupCarYearDropDown();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        setupCarMakeDropDown();
    }
}
