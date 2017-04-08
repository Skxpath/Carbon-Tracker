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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        setUnits();
        setupPieChart();

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

        pieEntries.add(pieEntryNatGas);
        pieEntries.add(pieEntryElec);

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

    private void setupPieGraphForMonth() {

        DateYMD smallestDate = DateManager.getSmallestDateFor28Days();
        final ArrayList<Entry> entries = new ArrayList<>();
        final ArrayList<Integer> monthforEntry = new ArrayList<>();
        List<DateYMD> allBetweenDates = DateManager.datefilterfor28Days(smallestDate);
        List<DateYMD> allJourneyDates = new ArrayList<>();
        HashSet hashset = new HashSet();
        for (int i = 0; i < carbonTrackerModel.getJourneyManager().getSize(); i++) {
            DateYMD date = DateManager.getYMDFormat(carbonTrackerModel.getJourneyManager().getDate());
            if (hashset.add(date)) {
                allJourneyDates.add(date);
            }
        }

        for (int j = 0; j < allBetweenDates.size(); j++) {
            float co2Em = 0;
            for (int k = 0; k < carbonTrackerModel.getJourneyManager().getSize(); k++) {
                DateYMD d = DateManager.getYMDFormat(carbonTrackerModel.getJourneyManager().getJourney(k).getDate());
                if (d.getDay() == allBetweenDates.get(j).getDay() &&
                        d.getMonth() == allBetweenDates.get(j).getMonth()
                        && d.getYear() == allBetweenDates.get(j).getYear()) {
                    Journey journey = journeyManager.getJourney(k);
                    double CO2Journey = journey.getCarbonEmitted();
                    if (units == TREE_DAYS) {
                        CO2Journey = UnitConversion.convertDoubleToTreeUnits(CO2Journey);
                    }
                    co2Em += CO2Journey;
                }
            }

            Entry entry = new Entry(allBetweenDates.get(j).getDay() + allBetweenDates.get(j).getMonth() * 30, co2Em);
            entries.add(entry);
            monthforEntry.add(allBetweenDates.get(j).getMonth());

        }


//        LineChart lineChart = (LineChart) findViewById(R.id.chart);
//
//        String lineDataString = "CO2 in " + unitString;
//        LineDataSet dataset = new LineDataSet(entries, lineDataString);
//
//        dataset.setDrawCircleHole(true);
//        dataset.setDrawFilled(true);
//
//        dataset.setValueTextSize(5f);
//        dataset.setCircleColorHole(Color.BLACK);
//        LineData data = new LineData(dataset);
//        data.setValueFormatter(new MyGraphValueFormatter());
//        lineChart.setBackgroundColor(Color.rgb(43, 79, 51));
//        dataset.setColors(Color.BLUE); //
//        lineChart.setData(data);
//        lineChart.invalidate();
//        lineChart.setMinimumHeight(0);
//
//
//        LimitLine limitLineForAvgCo2 = new LimitLine((float) CO2PerPerson / 365, getString(R.string.avgCo2EmissionperDay));
//        limitLineForAvgCo2.setTextSize(10f);
//        limitLineForAvgCo2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        limitLineForAvgCo2.setLineWidth(3f);
//
//        LimitLine targetLimitLine = new LimitLine(((float) CO2PerPerson / 365) * 0.7f, getString(R.string.targetCo2Emission));
//        targetLimitLine.setTextSize(10f);
//        targetLimitLine.setLineColor(Color.GREEN);
//        targetLimitLine.setLineWidth(3f);
//        targetLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
//
//
//        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.addLimitLine(limitLineForAvgCo2);
//        yAxis.addLimitLine(targetLimitLine);
//        lineChart.notifyDataSetChanged();
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setValueFormatter(new Xaxisformatter(monthforEntry, entries, true, null));
//        lineChart.getAxisRight().setEnabled(false);
//        lineChart.animateY(3000);
//        TextView text = (TextView) findViewById(R.id.xAxisGraph);
//        text.setText(R.string.xAxisLabelForMonthGraph);
//        lineChart.getDescription().setText("");
//        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                setupInfo(e, entries, monthforEntry, null, true, 0);
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
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
