package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.DateManager;
import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.MyGraphValueFormater;
import alex.carbon_tracker.Model.UtilityBill;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.Model.Xaxisformatter;
import alex.carbon_tracker.R;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*LineGraphActivity class that displays
* the last 28 and 365 days of utility data
* in a graphical
* form*/
public class LineGraphActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();
    private DateManager dateManager = carbonTrackerModel.getDateManager();
    private UtilityBillManager utilityManager = carbonTrackerModel.getUtilityBillManager();
    final private int CO2_EMSSION2013_PER_PERSON = 13500;//KG
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        Intent intent = getIntent();
        boolean whichGraph = intent.getBooleanExtra("yearGraph", false);
        if (!whichGraph) {
            setupBarChartForMonth();
        } else {
            setupBarChartForYear();
        }
    }

    private void setupBarChartForYear() {
        final ArrayList<Entry> entries = new ArrayList<>();
        final ArrayList<Entry> entries1 = new ArrayList<>();
        final ArrayList<Integer> monthforEntry = new ArrayList<>();
        final ArrayList<Integer> yearForEntry = new ArrayList<>();
        DateYMD currentDate = DateManager.getYMDFormat(new Date());
        final int MONTH = currentDate.getMonth();
        List<DateYMD> allBetweenDates = DateManager.datefilterforMonth(currentDate,true);
        List<DateYMD> allJourneyDates = new ArrayList<>();
        HashSet hashset = new HashSet();
        for (int i = 0; i < carbonTrackerModel.getJourneyManager().getSize(); i++) {
            DateYMD date = DateManager.getYMDFormat(carbonTrackerModel.getJourneyManager().getDate());
            if (hashset.add(date)) {
                allJourneyDates.add(date);
            }
        }
        for (int i = 0; i < 12; i++) {
            float co2Em = 0;
            for (int j = 0; j < allBetweenDates.size(); j++) {
                for (int k = 0; k < carbonTrackerModel.getJourneyManager().getSize(); k++) {
                    DateYMD d = DateManager.getYMDFormat(carbonTrackerModel.getJourneyManager().getJourney(k).getDate());
                    if (d.getDay() == allBetweenDates.get(j).getDay() &&
                            d.getMonth() == allBetweenDates.get(j).getMonth()
                            && d.getYear() == allBetweenDates.get(j).getYear()) {
                        co2Em += carbonTrackerModel.getJourneyManager()
                                .getJourney(k).getCarbonEmitted();
                    }
                }

            }
            // if (co2Em != 0) {
            Entry entry = new Entry(i, co2Em);
            entries1.add(new Entry(i,CO2_EMSSION2013_PER_PERSON/365));
            entries.add(entry);
            monthforEntry.add(currentDate.getMonth());
            yearForEntry.add(currentDate.getYear());
            // }
            currentDate = allBetweenDates.get(allBetweenDates.size()-1);
            currentDate.setDay(30);
            currentDate.setMonth(currentDate.getMonth() - 1);
            if (currentDate.getMonth() <= 0) {
                currentDate.setYear(currentDate.getYear() - 1);
                currentDate.setMonth(12);
            }

            allBetweenDates = new ArrayList<>();
            allBetweenDates = DateManager.datefilterforMonth(currentDate,true);
        }
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        lineChart.clear();

        LineDataSet dataset = new LineDataSet(entries, "CO2 in KG");
        dataset.setDrawCircleHole(true);
        dataset.setValueTextSize(10f);
        dataset.setCircleColorHole(Color.BLACK);
        dataset.setDrawFilled(true);
        LineData data = new LineData(dataset);
        data.setValueFormatter(new MyGraphValueFormater());
        lineChart.setBackgroundColor(Color.DKGRAY);

        lineChart.notifyDataSetChanged();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new Xaxisformatter(monthforEntry, entries, false,yearForEntry));

        LimitLine limitLineForAvgCo2 = new LimitLine(CO2_EMSSION2013_PER_PERSON/12, getString(R.
                string.avgCo2EmissionperDay));
        limitLineForAvgCo2.setTextSize(12f);
        limitLineForAvgCo2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limitLineForAvgCo2.setLineWidth(3f);

        LimitLine targetLimitLine = new LimitLine((CO2_EMSSION2013_PER_PERSON/12)*0.7f,getString(R.
                string.targetCo2Emission));
        targetLimitLine.setTextSize(12f);
        targetLimitLine.setLineColor(Color.GREEN);
        targetLimitLine.setLineWidth(3f);
        targetLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.addLimitLine(limitLineForAvgCo2);
        yAxis.addLimitLine(targetLimitLine);
        dataset.setColors(Color.BLUE); //
        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setText("");
        lineChart.animateY(3000);
        TextView text = (TextView) findViewById(R.id.xAxisGraph);
        text.setText(R.string.xAxisLabelForYearGraph);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupInfo(e, entries, monthforEntry, yearForEntry, false, MONTH);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void setupUtilityTextForLast28Days() {
        TextView text = (TextView) findViewById(R.id.utilityText);
        DecimalFormat df = new DecimalFormat("###,###.##");
        DateYMD smallesdate = DateManager.getSmallestDateFor28Days();
        List<DateYMD> last28DaysList = DateManager.datefilterfor28Days(smallesdate);

        float totalNaturalGas =0;
        float totalElectricity =0;
        for(int i =0;i<utilityManager.getBills().size();i++){
            UtilityBill currentBill = utilityManager.getBills().get(i);
            DateYMD startDate = DateManager.getYMDFormat(currentBill.getStartDate());
            DateYMD endDate = DateManager.getYMDFormat(currentBill.getEndDate());
            boolean isEnddateInThere = false;
            for(int j =0;j<last28DaysList.size();j++){
                if(endDate.getMonth() ==last28DaysList.get(j).getMonth() &&
                        endDate.getYear() == last28DaysList.get(j).getYear()
                        && last28DaysList.get(j).getDay() == endDate.getDay()){

                    isEnddateInThere = true;
                    j = last28DaysList.size();
                }
            }
            if(isEnddateInThere){
                for(int j =0;j<last28DaysList.size();j++){

                    if(startDate.getMonth() ==last28DaysList.get(j).getMonth() &&
                            startDate.getYear() == last28DaysList.get(j).getYear()
                            && last28DaysList.get(j).getDay() == startDate.getDay()){

                        totalElectricity += currentBill.getDailyConsumption((float) (currentBill.getEmissionsForElectricity()
                                                        /currentBill.getHouseholdSize()));

                        totalNaturalGas += currentBill.getDailyConsumption((float) (currentBill.getEmissionsForGas()
                                /currentBill.getHouseholdSize()));

                    }
                }
            }
        }
        text.setText("Total Carbon Emission From Natural Gas : " + df.format(totalNaturalGas) + getString(R.string.kgUnits) +
                "\n" + "Total Carbon Emission From Electricity Used: " + df.format(totalElectricity) + getString(R.string.kgUnits));
    }

    private void getUtilityBillForMonth() {


        TextView text = (TextView) findViewById(R.id.utilityText);
        DecimalFormat df = new DecimalFormat("###,###.##");
        text.setText("Total Natural Gas used Per Month: " + df.format(utilityManager.totalCarbonEmissionsNaturalGas() / 12)
                + " KG" +
                "\n" + "Total Electricity Used Per Month: " + df.format(utilityManager.totalCarbonEmissionsElectricity() / 12)
                + " KG");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void setupBarChartForMonth() {

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
                    co2Em += carbonTrackerModel.getJourneyManager()
                            .getJourney(k).getCarbonEmitted();
                }
            }

            {
                Entry entry = new Entry(allBetweenDates.get(j).getDay() + allBetweenDates.get(j).getMonth() * 30, co2Em);
                entries.add(entry);
                monthforEntry.add(allBetweenDates.get(j).getMonth());
            }
        }


        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        lineChart.clear();
        LineDataSet dataset = new LineDataSet(entries, getString(R.string.co2EmissionInKG));
        dataset.setDrawCircleHole(true);
        dataset.setDrawFilled(true);

        dataset.setValueTextSize(5f);
        dataset.setCircleColorHole(Color.BLACK);
        LineData data = new LineData(dataset);
        data.setValueFormatter(new MyGraphValueFormater());
        lineChart.setBackgroundColor(Color.rgb(43,79,51));
        dataset.setColors(Color.BLUE); //
        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.setMinimumHeight(0);


        LimitLine limitLineForAvgCo2 = new LimitLine(CO2_EMSSION2013_PER_PERSON/365, getString(R.string.avgCo2EmissionperDay));
        limitLineForAvgCo2.setTextSize(10f);
        limitLineForAvgCo2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limitLineForAvgCo2.setLineWidth(3f);

        LimitLine targetLimitLine = new LimitLine((CO2_EMSSION2013_PER_PERSON/365)*0.7f,getString(R.string.targetCo2Emission));
        targetLimitLine.setTextSize(10f);
        targetLimitLine.setLineColor(Color.GREEN);
        targetLimitLine.setLineWidth(3f);
        targetLimitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);


        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.addLimitLine(limitLineForAvgCo2);
        yAxis.addLimitLine(targetLimitLine);
        lineChart.notifyDataSetChanged();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new Xaxisformatter(monthforEntry, entries, true,null));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateY(3000);
        TextView text = (TextView) findViewById(R.id.xAxisGraph);
        text.setText(R.string.xAxisLabelForMonthGraph);
        lineChart.getDescription().setText("");
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupInfo(e, entries, monthforEntry, null, true, 0);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }


    public void setupInfo(Entry entry, ArrayList<Entry> entries, ArrayList<Integer> months,
                          ArrayList<Integer> years, boolean isMonth, int currentMonth) {
        JourneyManager smallJM = new JourneyManager();
        if (isMonth) {
            for (int i = 0; i < entries.size(); i++) {
                if (entry.getX() == entries.get(i).getX()) {
                    int day = (int) entry.getX() - (months.get(i) * 30);
                    int month = months.get(i);
                    for (int j = 0; j < journeyManager.getSize(); j++) {
                        DateYMD date = DateManager.getYMDFormat(journeyManager.getJourney(j).getDate());
                        if (date.getDay() == day && date.getMonth() == month) {
                            smallJM.add(journeyManager.getJourney(j));
                        }
                    }

                }
            }
            setupUtilityTextForLast28Days();
        } else {
            for (int i = 0; i < entries.size(); i++) {
                if (entry.getX() + currentMonth == entries.get(i).getX() + currentMonth) {
                    int month = months.get(i);
                    int year = years.get(i);
                    for (int j = 0; j < journeyManager.getSize(); j++) {
                        DateYMD date = DateManager.getYMDFormat(journeyManager.getJourney(j).getDate());
                        if (date.getMonth() == month && date.getYear() == year) {
                            smallJM.add(journeyManager.getJourney(j));
                        }
                    }
                }
            }
            getUtilityBillForMonth();
        }
        String[] list1 = smallJM.getJourneyDescriptions();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list, list1);
        ListView list = (ListView) findViewById(R.id.journeylistForGraph);
        list.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LineGraphActivity.class);
    }

}
