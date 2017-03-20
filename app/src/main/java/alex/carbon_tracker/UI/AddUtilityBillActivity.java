package alex.carbon_tracker.UI;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import alex.carbon_tracker.R;

public class AddUtilityBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utility_bill);
        setupBtn();
    }

    private float getElectricalConsumptionEditText() {
        EditText electricalConsumptionEditText;
        electricalConsumptionEditText = (EditText) findViewById(R.id.electricalConsumptionEditText);
        return Float.parseFloat(electricalConsumptionEditText.getText().toString());
    }

    private float getGasConsumptionEditText() {
        EditText gasConsumptionEditText;
        gasConsumptionEditText = (EditText) findViewById(R.id.gasConsumptionEditText);
        return Float.parseFloat(gasConsumptionEditText.getText().toString());
    }
    private int getHouseholdSizeEditText(){
        EditText householdSizeEditText;
        householdSizeEditText = (EditText) findViewById(R.id.householdSizeEditText);
        return Integer.parseInt(householdSizeEditText.getText().toString());
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
                Intent intent = new Intent(AddUtilityBillActivity.this,AddUtilityBillPart2.class);
                intent.putExtra("householdSize", householdSize);
                intent.putExtra("gasConsumption", gasConsumption);
                intent.putExtra("electricalConsumption", electricalConsumption);
                startActivity(intent);
            }
        });
    }
}
