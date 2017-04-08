package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.Route;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.Model.Vehicle;
import alex.carbon_tracker.R;

/*
* EditCarActivity to support editing of a Vehicle
* */
public class EditCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CarbonTrackerModel carbonTrackerModel;
    private JourneyManager journeyManager;
    private UserVehicleManager userVehicleManager;
    List<Integer> imagesID = new ArrayList<>();
    private String carMakeFromIntent = "xx";
    private String carModelFromIntent = "";
    private int carYearFromIntent = 0;
    private String carNicknameFromIntent = "";
    private String carSpecListFromIntent;

    private String carMake = "";
    private String carModel = "";
    private int carYear = 0;
    private String carNickname = "";
    private String carSpec = "";
    private List<String> carMakeList;
    private String carTransmission;
    private String carFuelType;
    private double getCarFuelTypeNumber;
    private int carIconID =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        carbonTrackerModel = CarbonTrackerModel.getInstance();
        journeyManager = carbonTrackerModel.getJourneyManager();
        userVehicleManager = carbonTrackerModel.getUserVehicleManager();
        Intent intent = getIntent();
        getInfoFromIntent(intent);
        setupOkButton();
        setupCarMakeDropDown();
        setupCarNickName();
        imagesID.add(R.drawable.automobile);
        imagesID.add(R.drawable.batmobile);
        imagesID.add(R.drawable.car_grey);
        imagesID.add(R.drawable.truck);
        imagesID.add(R.drawable.van);
        setUpCarIconDropDown();
    }
    private void setUpCarIconDropDown() {

        List<String> carNames = new ArrayList<>();
        carNames.add("AutoMobile");
        carNames.add("Bat Mobile");
        carNames.add("Grey Car");
        carNames.add("Truck");
        carNames.add("Van");
        Spinner carIconSpinner = (Spinner)findViewById(R.id.spinner3);
        carIconSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(EditCarActivity.this, android.R.layout.simple_spinner_item, carNames);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carIconSpinner.setAdapter(adapt);
        adapt.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }

    private void editCarInformation(int index) {
        // add the carMake to the carbonModel
        for (int i = 0; i < carbonTrackerModel.getVehicleManager().getSize(); i++) {

            Vehicle newVehicle = carbonTrackerModel.getVehicleManager().getVehicle(i);
            double fuelNumber = newVehicle.getFuelTypeNumber();
            if (newVehicle.getMake().equals(carMake)
                    && newVehicle.getModel().equals(carModel)
                    && (newVehicle.getYear()) == (carYear)
                    && newVehicle.getFuelType().equals(carFuelType)
                    && newVehicle.getTransmission().equals(carTransmission)
                    && Double.compare(fuelNumber, getCarFuelTypeNumber) == 0) {
                setupNewCarNickName();
                UserVehicle newUserVehicle = new UserVehicle(carMake, carModel, carYear, carNickname, carTransmission, carFuelType,
                        newVehicle.getCityDrive(), newVehicle.getHighwayDrive(), getCarFuelTypeNumber,carIconID);
                UserVehicle originalVehicle = userVehicleManager.getUserVehicle(getIntent().getIntExtra("position", 0));


                for (int j = 0; j < journeyManager.getJourneyList().size(); j++) {
                    Journey journey = journeyManager.getJourney(j);
                    UserVehicle currentVehicle = journey.getUserVehicle();
                    if (currentVehicle.getMake().equals(originalVehicle.getMake())
                            && currentVehicle.getModel().equals(originalVehicle.getModel())
                            && (currentVehicle.getYear()) == (originalVehicle.getYear())
                            && currentVehicle.getNickname().equals(originalVehicle.getNickname())) {
                        if (getIntent().getBooleanExtra("editJourney", false) == true) {
                            journey.setVehicle(newUserVehicle);
                        } else {
                            userVehicleManager.replaceUserVehicle(newUserVehicle, index);
                            journey.setVehicle(newUserVehicle);
                        }

                        Route route = journey.getRoute();
                        double gasType = newUserVehicle.getFuelTypeNumber();
                        double distanceTravelledCity = route.getCityDistance();
                        double distanceTravelledHighway = route.getHighwayDistance();
                        int milesPerGallonCity = newUserVehicle.getCityDrive();
                        int milesPerGallonHighway = newUserVehicle.getHighwayDrive();

                        // double gasType, double distanceTravelledCity, double distanceTravelledHighway, int milesPerGallonCity, int milesPerGallonHighway
                        double CO2Emissions = CarbonCalculator.calculate(gasType, distanceTravelledCity, distanceTravelledHighway, milesPerGallonCity, milesPerGallonHighway);
                        journey.setCarbonEmitted(CO2Emissions);
                    }
                }
                break;
            }
        }
    }

    private void setupCarNickName() {
        EditText text = (EditText) findViewById(R.id.carNicknameEditText);
        text.setText(carNicknameFromIntent);
        carNickname = text.getText().toString();
    }

    private void setupNewCarNickName() {
        EditText text = (EditText) findViewById(R.id.carNicknameEditText);
        carNickname = text.getText().toString();
    }

    private void getInfoFromIntent(Intent intent) {
        carMakeFromIntent = intent.getStringExtra("make");
        carModelFromIntent = intent.getStringExtra("model");
        carNicknameFromIntent = intent.getStringExtra("carNickName");
        carYearFromIntent = intent.getIntExtra("year", 0);
        carSpecListFromIntent = intent.getStringExtra("carSpec");
    }

    private void setupOkButton() {
        Button ok = (Button) findViewById(R.id.addCarOkButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEditText(R.id.carNicknameEditText)) {
                    // edit the car
                    editCarInformation(getIntent().getIntExtra("position", 0));
                    finish();
                } else {
                    Toast.makeText(EditCarActivity.this, "Invalid Information Inputted. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(EditCarActivity.this, android.R.layout.simple_spinner_item, carMakeList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapt.notifyDataSetChanged();
        carMakeMenu.setAdapter(adapt);
        // find the carMake in the spinner
        int spinnerPosition = 0;
        setIntent(getIntent());
        for (int i = 0; i < carMakeList.size(); i++) {
            if (carMakeList.get(i).equals(carMakeFromIntent)) {
                spinnerPosition = i;
                carMakeMenu.setSelection(spinnerPosition);
                break;
            }
        }
    }

    private void setupCarYearDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carYearDropDown);
        carMakeMenu.setOnItemSelectedListener(this);
        // getting information from carbonTrackerModel
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
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(EditCarActivity.this, android.R.layout.simple_spinner_item, carYearList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
        int spinnerPosition;
        for (int i = 0; i < carYearList.size(); i++) {
            if (carYearList.get(i).equals(carYearFromIntent)) {
                spinnerPosition = i;
                carMakeMenu.setSelection(spinnerPosition);
                break;
            }
        }
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
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(EditCarActivity.this, android.R.layout.simple_spinner_item, carModelList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModelMenu.setAdapter(adapt);
        adapt.notifyDataSetChanged();
        int spinnerPosition;
        for (int i = 0; i < carModelList.size(); i++) {
            if (carModelList.get(i).equals(carModelFromIntent)) {
                spinnerPosition = i;
                carModelMenu.setSelection(spinnerPosition);
                break;
            }
        }
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
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(EditCarActivity.this, android.R.layout.simple_spinner_item, carSpecList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
        adapt.notifyDataSetChanged();
        setIntent(getIntent());
        for (int i = 0; i < carSpecList.size(); i++) {
            Log.i("spec", carSpecListFromIntent);
            if (carSpecList.get(i).equals(carSpecListFromIntent)) {
                carMakeMenu.setSelection(i);
                break;
            }
        }

    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, EditCarActivity.class);
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
            carYear = Integer.parseInt(
                    adapterView.getItemAtPosition(i).toString());
            setupCarSpecificationDropDown();
        } else if (adapterView.getId() == findViewById(R.id.carSpecificationDropDownMenu).getId()) {
            carSpec = adapterView.getItemAtPosition(i).toString();
            String[] strings = carSpec.split(",");
            carTransmission = strings[0];
            carFuelType = strings[1];
            getCarFuelTypeNumber = Double.parseDouble(strings[2]);
        }

        else if (adapterView.getId() == findViewById(R.id.spinner3).getId()) {
            ImageView img = (ImageView)findViewById(R.id.carIconImageView);
            img.setImageResource(imagesID.get(i));
            img.refreshDrawableState();
            carIconID = imagesID.get(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
