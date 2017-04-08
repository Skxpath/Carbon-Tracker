package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.CarbonUnitsEnum;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.Model.Settings;
import alex.carbon_tracker.Model.UnitConversion;
import alex.carbon_tracker.Model.UtilityBill;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.R;

import static alex.carbon_tracker.Model.CarbonUnitsEnum.TREE_DAYS;

/*
* UtilityListActivity which displays the users
* utility bills and consumption
* */
public class UtilitylistActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private UtilityBillManager utilityBillManager = carbonTrackerModel.getUtilityBillManager();

    private Settings settings = carbonTrackerModel.getSettings();
    private CarbonUnitsEnum units = settings.getCarbonUnit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilitylist);
        setupAddBtn();
        populateListView();
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
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            double houseHoldNatGas = bill.getHouseholdGasConsumption();
            double houseHoldElec = bill.getHouseholdElectricalConsumption();
            if (units == TREE_DAYS) {
                houseHoldNatGas = UnitConversion.convertDoubleToTreeUnits(houseHoldNatGas);
                houseHoldElec = UnitConversion.convertDoubleToTreeUnits(houseHoldElec);
            }
            String description = String.format(String.format("Utility Bill No. %d\n" +
                            "Gas Per Person: %.02f\n" +
                            "Electricity Per Person: %.02f\n" +
                            "Date: %s to %s\n" +
                            "HouseHold Size: %d",
                    i,
                    houseHoldNatGas,
                    houseHoldElec,
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
