package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonCalculator;
import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.Model.Vehicle;
import alex.carbon_tracker.Model.VehicleManager;
import alex.carbon_tracker.R;

/*
* Add Car Activity page which allows the
* user to add one of their cars to
* the system. Only lists car brands and
* models that can emit CO2.
* */
public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CarbonTrackerModel carbonTrackerModel;
    private UserVehicleManager userVehicleManager;
    private VehicleManager vehicleManager;

    private String carMake = "";
    private String carModel = "";
    private int carYear = 1997;
    private String carNickname = "";
    private int carIconId =0;
    private String carSpecList = "";
    private String carTransmission = "";
    private String carFuelType = "";
    private double getCarFuelTypeNumber = 0;

    private List<String> carMakeList;
    private int editJourneyPosition;
    List<Integer> imagesID = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        carbonTrackerModel = CarbonTrackerModel.getInstance();
        userVehicleManager = carbonTrackerModel.getUserVehicleManager();
        vehicleManager = carbonTrackerModel.getVehicleManager();
        setupCarMakeDropDown();
        setupOkButton();
        setupCarMakeDropDown();

        imagesID.add(R.drawable.automobile);
        imagesID.add(R.drawable.batmobile);
        imagesID.add(R.drawable.car_grey);
        imagesID.add(R.drawable.truck);
        imagesID.add(R.drawable.van);
        // carIconId =imagesID.get(0);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        setUpCarIconDropDown();
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
    private void setUpCarIconDropDown() {

        List<String> carNames = new ArrayList<>();
        carNames.add("AutoMobile");
        carNames.add("BatMobile");
        carNames.add("Grey Car");
        carNames.add("Truck");
        carNames.add("Van");
        Spinner carIconSpinner = (Spinner)findViewById(R.id.spinner3);
        carIconSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this, android.R.layout.simple_spinner_item, carNames);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carIconSpinner.setAdapter(adapt);
        adapt.notifyDataSetChanged();
    }


    private void setupCarNickName() {
        EditText text = (EditText) findViewById(R.id.carNicknameEditText);
        carNickname = text.getText().toString();
    }

    private void setupOkButton() {
        Button ok = (Button) findViewById(R.id.addCarOkButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking if we are editing the journey
                if (getIntent().getBooleanExtra("editJourney", false) && validateEditText(R.id.carNicknameEditText)) {
                    editJourneyPosition = getIntent().getIntExtra("journeyPosition", 0);
                    addCarToTheModel();
                    finish();
                }
                // adding new journey
                else if (validateEditText(R.id.carNicknameEditText)) {
                    addCarToTheModel();
                    Intent intent = SelectRouteActivity.makeIntent(AddCarActivity.this);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddCarActivity.this, R.string.ErrorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addCarToTheModel() {
        for (int i = 0; i < vehicleManager.getSize(); i++) {
            Vehicle newVehicle = vehicleManager.getVehicle(i);
            double fuelNumber = newVehicle.getFuelTypeNumber();
            if (newVehicle.getMake().equals(carMake)
                    && newVehicle.getModel().equals(carModel)
                    && (newVehicle.getYear()) == (carYear)
                    && newVehicle.getTransmission().equals(carTransmission)
                    && newVehicle.getFuelType().equals(carFuelType)
                    && Double.compare(fuelNumber, getCarFuelTypeNumber) == 0) {
                setupCarNickName();
                UserVehicle newUserVehicle = new UserVehicle(carMake,
                        carModel,
                        carYear,
                        carNickname,
                        carTransmission,
                        carFuelType,
                        newVehicle.getCityDrive(),
                        newVehicle.getHighwayDrive(),
                        getCarFuelTypeNumber,carIconId);
                userVehicleManager.add(newUserVehicle);
                if (getIntent().getBooleanExtra("editJourney", false)) {
                    // resetting the carbon emission
                    Route userCurrentRoute = carbonTrackerModel.getJourneyManager().
                            getJourney(editJourneyPosition).getRoute();

                    double distanceTravelledCity = userCurrentRoute.getCityDistance();
                    double distanceTravelledHighway = userCurrentRoute.getHighwayDistance();

                    double gasType = newVehicle.getFuelTypeNumber();
                    int milesPerGallonCity = newVehicle.getCityDrive();
                    int milesPerGallonHighway = newVehicle.getHighwayDrive();

                    double CO2Emissions = CarbonCalculator.calculate(gasType, distanceTravelledCity,
                            distanceTravelledHighway, milesPerGallonCity, milesPerGallonHighway);
                    //setting the new  vehicle and carbon emission value
                    carbonTrackerModel.getJourneyManager().getJourney(editJourneyPosition).setVehicle(newUserVehicle);
                    carbonTrackerModel.getJourneyManager().getJourney(editJourneyPosition).setCarbonEmitted(CO2Emissions);


                } else {
                    userVehicleManager.setCurrentVehicle(newUserVehicle);
                }
                break;
            }
        }
    }

    private boolean validateEditText(int id) {
        boolean isValid = true;
        EditText editText = (EditText) findViewById(id);
        String editTextString = editText.getText().toString().trim();
        if (editTextString.isEmpty() || editTextString.length() == 0) {
            isValid = false;
        }
        if (editTextString.matches("[0-9]+")) {
            int editTextInt = Integer.parseInt(editTextString);
            if (editTextInt < 0) {
                isValid = false;
            }
        }
        return isValid;
    }

    private void setupCarYearDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carYearDropDown);
        carMakeMenu.setOnItemSelectedListener(this);
        List<String> carYearList = new ArrayList<String>();
        for (int i = 0; i < carbonTrackerModel.getVehicleManager().getSize(); i++) {
            boolean isCarYearInTheList = false;
            String currentCarYear = carbonTrackerModel.getVehicleManager().getVehicle(i).getYear() + "";
            for (int j = 0; j < carYearList.size(); j++) {
                if (carYearList.get(j).toString().equals(currentCarYear)) {
                    isCarYearInTheList = true;
                    break;
                }
            }
            // checking if car year is already in the list?
            if (isCarYearInTheList == false &&
                    carbonTrackerModel.getVehicleManager().getVehicle(i).getMake().toString().equals(carMake)
                    && carbonTrackerModel.getVehicleManager().getVehicle(i).getModel().equals(carModel)) {
                carYearList.add(currentCarYear);
            }
        }
        // making the drop down menu
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this, android.R.layout.simple_spinner_item, carYearList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
    }

    private void setupCarModelDropDown() {
        Spinner carModelMenu = (Spinner) findViewById(R.id.carModelDropDownMenu);
        carModelMenu.setOnItemSelectedListener(this);
        List<String> carModelList = new ArrayList<String>();
        for (int i = 0; i < carbonTrackerModel.getVehicleManager().getSize(); i++) {
            boolean isCarModelInTheList = false;
            String currentCarMake = carbonTrackerModel.getVehicleManager().getVehicle(i).getModel().toString();
            for (int j = 0; j < carModelList.size(); j++) {
                if (carModelList.get(j).toString().equals(currentCarMake)) {
                    isCarModelInTheList = true;
                    break;
                }
            }
            if (isCarModelInTheList == false &&
                    carbonTrackerModel.getVehicleManager().getVehicle(i).getMake().toString().equals(carMake)) {
                carModelList.add(currentCarMake);
            }
        }
        //setting up the dropdown
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this, android.R.layout.simple_spinner_item, carModelList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModelMenu.setAdapter(adapt);
        adapt.notifyDataSetChanged();
    }

    private void setupCarMakeDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carMakeDropMenu);
        carMakeMenu.setOnItemSelectedListener(this);
        //getting the car make and saving it in a list.
        carMakeList = new ArrayList<String>();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < carbonTrackerModel.getVehicleManager().getSize(); i++) {
            if (hashSet.add(carbonTrackerModel.getVehicleManager().getVehicle(i).getMake())) {
                carMakeList.add(carbonTrackerModel.getVehicleManager().getVehicle(i).getMake());
            }
        }
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this, android.R.layout.simple_spinner_item, carMakeList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapt.notifyDataSetChanged();
        carMakeMenu.setAdapter(adapt);
    }

    private void setupCarSpecificationDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carSpecificationDropDownMenu);
        carMakeMenu.setOnItemSelectedListener(this);
        List<String> carSpecList = new ArrayList<String>();
        for (int i = 0; i < carbonTrackerModel.getVehicleManager().getSize(); i++) {
            boolean isCarYearInTheList = false;
            String currentCarSpec = carbonTrackerModel.getVehicleManager().getVehicle(i).getTransmission() + ","
                    + carbonTrackerModel.getVehicleManager().getVehicle(i).getFuelType() + ","
                    + carbonTrackerModel.getVehicleManager().getVehicle(i).getFuelTypeNumber();

            for (int j = 0; j < carSpecList.size(); j++) {
                if (carSpecList.get(j).toString().equals(currentCarSpec)) {
                    isCarYearInTheList = true;
                    break;
                }
            }
            // checking if car Spec is already in the list?
            if (isCarYearInTheList == false &&
                    carbonTrackerModel.getVehicleManager().getVehicle(i).getMake().toString().equals(carMake)
                    && carbonTrackerModel.getVehicleManager().getVehicle(i).getModel().equals(carModel)
                    && carbonTrackerModel.getVehicleManager().getVehicle(i).getYear() == carYear) {
                carSpecList.add(currentCarSpec);
            }
        }
        // making the drop down menu
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this, android.R.layout.simple_spinner_item, carSpecList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
        adapt.notifyDataSetChanged();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCarActivity.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == findViewById(R.id.carMakeDropMenu).getId()) {
            carMake = adapterView.getItemAtPosition(i).toString();
            setupCarModelDropDown();
        } else if (adapterView.getId() == findViewById(R.id.carModelDropDownMenu).getId()) {
            carModel = adapterView.getItemAtPosition(i).toString();
            setupCarYearDropDown();
        } else if (adapterView.getId() == findViewById(R.id.carYearDropDown).getId()) {
            carYear = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            setupCarSpecificationDropDown();
        }
        else if (adapterView.getId() == findViewById(R.id.carSpecificationDropDownMenu).getId()) {
            carSpecList = adapterView.getItemAtPosition(i).toString();
            String[] strings = carSpecList.split(",");
            carTransmission = strings[0];
            carFuelType = strings[1];
            getCarFuelTypeNumber = Double.parseDouble(strings[2]);
            //Toast.makeText(this, "Hello1", Toast.LENGTH_SHORT).show();


        }
        else if (adapterView.getId() == findViewById(R.id.spinner3).getId()) {
            ImageView img = (ImageView)findViewById(R.id.carIconImageView);
            img.setImageResource(imagesID.get(i));
            img.refreshDrawableState();
            carIconId = imagesID.get(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }
}
