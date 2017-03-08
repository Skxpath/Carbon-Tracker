package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.UserVehicle;
import alex.carbon_tracker.Model.UserVehicleManager;
import alex.carbon_tracker.Model.Vehicle;
import alex.carbon_tracker.Model.VehicleManager;
import alex.carbon_tracker.R;

public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private UserVehicleManager userVehicleManager = carbonTrackerModel.getUserVehicleManager();
    private VehicleManager vehicleManager = carbonTrackerModel.getVehicleManager();

    private String carMake = "";
    private String carModel = "";
    private int carYear = 0;
    private String carNickname = "";
    private boolean listLoaded = false;
    private boolean isEditingCar = false;

    private static String make = "";
    private static String model = "";
    private static int year = 0;
    private static String nickname = "";
    private static int index = 0;

    private List<String> carMakeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        //Intent intent = getIntent();
       // utilizeIntentExtras(intent);
        //setupDeleteButton(intent);

        setupCarMakeDropDown();
        setupOkButton();
        setupCarMakeDropDown();
    }
/*
    private void utilizeIntentExtras(Intent intent) {
        if (intent.hasExtra(SelectTransportationMode.CAR_INDEX)) {
            isEditingCar = true;
            Bundle extras = intent.getExtras();
            index = (int) extras.get(SelectTransportationMode.CAR_INDEX);

            UserVehicle userVehicle = userVehicleManager.getUserVehicle(index);
            make = userVehicle.getMake();
            model = userVehicle.getModel();
            year = userVehicle.getYear();
            nickname = userVehicle.getNickname();


           // if (!(cityDistance == 0 && highwayDistance == 0)) {
            Spinner spinner = (Spinner) findViewById(R.id.carMakeDropMenu);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCarActivity.this, android.R.layout.simple_spinner_item, carMakeList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            if (!make.equals(null)) {
                int spinnerPosition = adapter.getPosition(make);
                spinner.setSelection(spinnerPosition);
            }

          //  }
        }
    }

    private void setupDeleteButton(Intent intent) {
        if (intent.hasExtra(SelectTransportationMode.CAR_INDEX)) {
            isEditingCar = true;
            Button button = new Button(this);
            button.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f));

            button.setText("Delete");
            button.setPadding(0, 0, 0, 0);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridButtonClicked();
                }
                private void gridButtonClicked() {
                    userVehicleManager.delete(index);
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });

            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayoutForCarDelete);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayout.addView(button, layoutParams);
        }
    }
*/
    private void setupCarNickName() {
        EditText text = (EditText) findViewById(R.id.carNicknameEditText);
        carNickname = text.getText().toString();
    }

    private void setupOkButton() {
        Button ok = (Button) findViewById(R.id.addCarOkButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEditText(R.id.carNicknameEditText)) {
                    addCarToTheModel();
                    finish();
                } else {
                    Toast.makeText(AddCarActivity.this, "Invalid Information Inputted. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addCarToTheModel() {
        // add the carMake to the carbonModel
        for (int i = 0; i < carbonTrackerModel.getVehicleManager().getSize(); i++) {

            Vehicle newVehicle = carbonTrackerModel.getVehicleManager().getVehicle(i);
            if (newVehicle.getMake().equals(carMake)
                    && newVehicle.getModel().equals(carModel)
                    && (newVehicle.getYear()) == (carYear)) {
                setupCarNickName();

                UserVehicle newUserVehicle = new UserVehicle(carMake, carModel, carYear, carNickname, newVehicle.getCityDrive(), newVehicle.getHighwayDrive());
                carbonTrackerModel.getUserVehicleManager().add(newUserVehicle);
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
            carYear = Integer.parseInt(
                    adapterView.getItemAtPosition(i).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        setupCarMakeDropDown();
    }

    private void setNumbToEditText(int id, int value) {
        EditText editText = (EditText) findViewById(id);
        String finalValue = Integer.toString(value);
        editText.setText(finalValue);
    }

    private void setStringToEditText(int id, String value) {
        EditText editText = (EditText) findViewById(id);
        editText.setText(value);
    }

    private String getEditTextAsString(int id) {
        EditText text = (EditText) findViewById(id);
        return text.getText().toString();
    }
}
