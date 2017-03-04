package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import alex.carbon_tracker.R;

public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        setupCarMakeDropDown();
        setupCarModelDropDown();
        setupCarYearDropDown();
        setupOkButton();
        addCarToTheModel();
    }

    private void setupOkButton() {
        Button ok = (Button)findViewById(R.id.addCarOkButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save car information

                //start route activity
                Intent intent = SelectRouteActivity.makeIntent(AddCarActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addCarToTheModel() {
    // add the car to the carbonModel


    }

    private void setupCarYearDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carYearDropDown);
        carMakeMenu.setOnItemSelectedListener(this);

        // setting up dummy data
        List<String> carName = new ArrayList<String>();
        carName.add("car year");
        for(int i = 0; i<10;i++){
            carName.add(2000+i+"");
        }
        // making the drop down menu
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this,android.R.layout.simple_spinner_item,carName);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
    }

    private void setupCarModelDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carModelDropDownMenu);
        carMakeMenu.setOnItemSelectedListener(this);
        List<String> carName = new ArrayList<String>();
        carName.add("car model");
        carName.add("car2");
        for(int i = 0; i<10;i++){
            carName.add(i+i+"");
        }
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this,android.R.layout.simple_spinner_item,carName);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
    }

    private void setupCarMakeDropDown() {
        Spinner carMakeMenu = (Spinner) findViewById(R.id.carMakeDropMenu);
        carMakeMenu.setOnItemSelectedListener(this);
        carMakeMenu.setPrompt("Car Make");
         List<String> carName = new ArrayList<String>();
        carName.add("car make");
        carName.add("car2");
        for(int i = 0; i<10;i++){
            carName.add(i+"");
        }
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(AddCarActivity.this,android.R.layout.simple_spinner_item,carName);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMakeMenu.setAdapter(adapt);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCarActivity.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
