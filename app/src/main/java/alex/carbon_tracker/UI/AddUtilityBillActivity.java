package alex.carbon_tracker.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.R;

/*
* AddUtilityBillActivity which allows the user
* to add a Utility Bill.
* */
public class AddUtilityBillActivity extends AppCompatActivity {
    CarbonTrackerModel model = CarbonTrackerModel.getInstance();
    UtilityBillManager manager = model.getUtilityBillManager();

    boolean gasConsumptionFieldIsFilled = true;
    boolean electricalConsumptionFieldIsFilled = true;
    boolean householdSizeFieldIsFilled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utility_bill);
        setupBtn();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.carbontrackerlogo5);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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
                Intent intent = new Intent(this, UtilitylistActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private float getElectricalConsumptionEditText() {
        EditText electricalConsumptionEditText;
        electricalConsumptionEditText = (EditText) findViewById(R.id.electricalConsumptionEditText);
        float consumption;
        if (electricalConsumptionEditText.getText().toString().isEmpty()) {
            electricalConsumptionFieldIsFilled = false;
            consumption = manager.getMostRecentBill().getHouseholdElectricalConsumption();
        } else {
            electricalConsumptionFieldIsFilled = true;
            consumption = Float.parseFloat(electricalConsumptionEditText.getText().toString());
        }
        return consumption;
    }

    private float getGasConsumptionEditText() {
        EditText gasConsumptionEditText;
        gasConsumptionEditText = (EditText) findViewById(R.id.gasConsumptionEditText);
        float consumption;
        if (gasConsumptionEditText.getText().toString().isEmpty()) {
            gasConsumptionFieldIsFilled = false;
            consumption = manager.getMostRecentBill().getHouseholdGasConsumption();
        } else {
            gasConsumptionFieldIsFilled = true;
            consumption = Float.parseFloat(gasConsumptionEditText.getText().toString());
        }
        return consumption;
    }

    private int getHouseholdSizeEditText() {
        EditText householdSizeEditText;
        householdSizeEditText = (EditText) findViewById(R.id.householdSizeEditText);
        int householdSize = 1;
        if (householdSizeEditText.getText().toString().isEmpty()) {
            householdSizeFieldIsFilled = false;
            householdSize = 1;
        } else {
            householdSizeFieldIsFilled = true;
            householdSize = Integer.parseInt(householdSizeEditText.getText().toString());
        }
        return householdSize;
    }

    private void setupBtn() {
        Button nextBtn;
        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int householdSize = getHouseholdSizeEditText();
                float gasConsumption = getGasConsumptionEditText();
                float electricalConsumption = getElectricalConsumptionEditText();

                if ((electricalConsumptionFieldIsFilled || gasConsumptionFieldIsFilled) && householdSizeFieldIsFilled) {
                    Intent intent = new Intent(AddUtilityBillActivity.this, AddUtilityBillPart2.class);
                    intent.putExtra("householdSize", householdSize);
                    intent.putExtra("gasConsumption", gasConsumption);
                    intent.putExtra("electricalConsumption", electricalConsumption);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddUtilityBillActivity.this, "Invalid Field Values! Please Re-Input!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
