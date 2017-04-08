package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.CarbonUnitsEnum;
import alex.carbon_tracker.Model.DateManager;
import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.Settings;
import alex.carbon_tracker.Model.UnitConversion;
import alex.carbon_tracker.Model.UtilityBill;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.R;

import static alex.carbon_tracker.Model.CarbonUnitsEnum.KILOGRAMS;
import static alex.carbon_tracker.Model.CarbonUnitsEnum.TREE_DAYS;

public class PieGraphMonthYear extends AppCompatActivity {
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();
    private UtilityBillManager utilityBillManager = carbonTrackerModel.getUtilityBillManager();

    private Settings settings = carbonTrackerModel.getSettings();
    private CarbonUnitsEnum units = settings.getCarbonUnit();
    private String unitString;
    List<Journey> journeysWithVehicle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        setUnits();
        Intent intent = getIntent();
        if(intent.getBooleanExtra("monthGraph",false)){
            setupPieChart();
        }
        else {

        }

        Log.d("HERE1",   "123123");
        Log.d("HERE2",  "12312");
    }

    private void setupPieChart() {

        List<PieEntry> pieEntries = new ArrayList<>();

        DecimalFormat df = new DecimalFormat("###,###.##");
        DateYMD smallesdate = DateManager.getSmallestDateFor28Days();
        List<DateYMD> last28DaysList = DateManager.datefilterfor28Days(smallesdate);

        float totalNaturalGas = 0;
        float totalElectricity = 0;
        for (int i = 0; i < utilityBillManager.getBills().size(); i++) {
            UtilityBill currentBill = utilityBillManager.getBills().get(i);
            DateYMD startDate = DateManager.getYMDFormat(currentBill.getStartDate());
            DateYMD endDate = DateManager.getYMDFormat(currentBill.getEndDate());
            boolean isEnddateInThere = false;
            for (int j = 0; j < last28DaysList.size(); j++) {
                if (endDate.getMonth() == last28DaysList.get(j).getMonth() &&
                        endDate.getYear() == last28DaysList.get(j).getYear()
                        && last28DaysList.get(j).getDay() == endDate.getDay()) {

                    isEnddateInThere = true;
                    j = last28DaysList.size();
                }
            }
            if (isEnddateInThere) {
                for (int j = 0; j < last28DaysList.size(); j++) {

                    if (startDate.getMonth() == last28DaysList.get(j).getMonth() &&
                            startDate.getYear() == last28DaysList.get(j).getYear()
                            && last28DaysList.get(j).getDay() == startDate.getDay()) {

                        double currElec = currentBill.getDailyConsumption((float) (currentBill.getEmissionsForElectricity()
                                / currentBill.getHouseholdSize()));
                        double currNatGas = currentBill.getDailyConsumption((float) (currentBill.getEmissionsForGas()
                                / currentBill.getHouseholdSize()));
                        if (units == TREE_DAYS) {
                            currElec = UnitConversion.convertDoubleToTreeUnits(currElec);
                            currNatGas = UnitConversion.convertDoubleToTreeUnits(currNatGas);
                        }
                        totalElectricity += currElec;
                        totalNaturalGas += currNatGas;

                    }
                }
            }
        }

        if (units == TREE_DAYS) {

            totalNaturalGas = (float) UnitConversion.convertDoubleToTreeUnits((double) totalNaturalGas);
            totalElectricity = (float) UnitConversion.convertDoubleToTreeUnits((double) totalNaturalGas);
        }

        Log.d("HERE1", totalElectricity + "");
        Log.d("HERE2", totalNaturalGas + "");

        PieEntry pieEntryNatGas;
        PieEntry pieEntryElec;

        pieEntryNatGas = createNewPieEntry(totalNaturalGas, "Natural Gas");
        pieEntryElec = createNewPieEntry(totalElectricity, "Electricity");
        PieEntry pieEntryOtherTransportation = new PieEntry(getTransportationFromJourney(),"OtherTransportation");
        pieEntries.add(pieEntryNatGas);
        pieEntries.add(pieEntryElec);
        pieEntries.add(pieEntryOtherTransportation);

        for(int i =0;i<journeysWithVehicle.size();i++){
            PieEntry pieEntry = new PieEntry((float) journeysWithVehicle.get(i).getCarbonEmitted(),"Vehicle: "+
                    journeysWithVehicle.get(i).getUserVehicle().getNickname().toString());
            pieEntries.add(pieEntry);
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextSize(13);


        PieData data = new PieData(dataSet);
        PieChart chart = (PieChart) findViewById(R.id.pieChart);

        chart.setData(data);
        if (units == KILOGRAMS) {
            chart.setCenterText(getString(R.string.CO2EmissionsinKG));
        } else {
            chart.setCenterText(getString(R.string.CO2EmissionsInTD));
        }
        chart.animateY(1000);
        chart.setEntryLabelTextSize(13);
        chart.invalidate();
    }
        private  float getTransportationFromJourney(){
            float co2EM =0;
            for(int i =0;i<journeyManager.getSize();i++){
                Journey journey = journeyManager.getJourney(i);
                if(journey.hasVehicle()){
                    journeysWithVehicle.add(journey);
                }
                else{
                    co2EM+= journey.getCarbonEmitted();
                }
            }
            return co2EM;
        }

    private void setUnits() {
        if (units == KILOGRAMS) {
            unitString = "KG";
        } else {
            unitString = "Tree-days";
        }
    }

    private PieEntry createNewPieEntry(float value, String string) {
        PieEntry pieEntry = new PieEntry(value, string);
        return pieEntry;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, PieGraphMonthYear.class);
    }
}
