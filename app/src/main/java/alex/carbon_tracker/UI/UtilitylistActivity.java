package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.SaveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.UtilityBill;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.R;

/*UtilityListActivity which displays the users
* utility bills and consumption
* */
public class UtilitylistActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private UtilityBillManager utilityBillManager = carbonTrackerModel.getUtilityBillManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilitylist);
        setupAddBtn();
        populateListView();
    }

    private void populateListView() {
        List<UtilityBill> utilityList = utilityBillManager.getBills();
        List<String> utilityDescriptionsList = convertUtilitiesToStrings(utilityList);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.jouney_list,
                utilityDescriptionsList);
        ListView listView = (ListView) findViewById(R.id.listViewUtilities);
        listView.setAdapter(arrayAdapter);

    }

    private List<String> convertUtilitiesToStrings(List<UtilityBill> utilityList) {
        List<String> utilityDescriptionsList = new ArrayList<>();
        for (UtilityBill bill : utilityList) {
            Date startDate = bill.getStartDate();
            Date endDate = bill.getEndDate();
            SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd");
            int i = utilityDescriptionsList.size() + 1;
            String description = String.format(String.format("Utility Bill No. %d\n" +
                            "Gas Per Person: %.02f\n" +
                            "Electricity Per Person: %.02f\n" +
                            "Date: %s to %s\n" +
                            "HouseHold Size: %d",
                    i,
                    bill.getHouseholdGasConsumption(),
                    bill.getHouseholdElectricalConsumption(),
                    st.format(startDate),
                    end.format(endDate),
                    bill.getHouseholdSize())
            );
            utilityDescriptionsList.add(description);
        }

        return utilityDescriptionsList;
    }

    private void setupAddBtn() {
        Button addBtn = (Button) findViewById(R.id.addUtilityButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UtilitylistActivity.this, AddUtilityBillActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, UtilitylistActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }
}
