package alex.carbon_tracker.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.R;

public class AddUtilityBillPart2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utility_bill_part2);
        Intent intent = getIntent();
        int householdSize = intent.getIntExtra("householdSize", 1);
        float electricalConsumption = intent.getFloatExtra("electricalConsumption", CarbonTrackerModel.getInstance().getUtilityBillManager().getMostRecentBill().getHouseholdElectricalConsumption());
        float gasConsumption = intent.getFloatExtra("gasConsumption", CarbonTrackerModel.getInstance().getUtilityBillManager().getMostRecentBill().getHouseholdGasConsumption());
        setupSubmitBtn();
    }

    private void setupSubmitBtn() {
        Button submitBtn;
        submitBtn = (Button) findViewById(R.id.submitBill);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
            }
        });
    }
}
