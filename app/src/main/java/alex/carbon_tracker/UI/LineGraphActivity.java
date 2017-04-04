package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.DateManager;
import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.MyGraphValueFormater;
import alex.carbon_tracker.Model.UtilityBillManager;
import alex.carbon_tracker.Model.Xaxisformatter;
import alex.carbon_tracker.R;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        Intent intent = getIntent();
        boolean whichGraph = intent.getBooleanExtra("yearGraph",false);
        if(!whichGraph) {
            setupBarChartForMonth();
        }
        else {
            setupBarChartForYear();
        }
    }

    private void setupBarChartForYear() {
        final ArrayList<Entry> entries = new ArrayList<>();
        final ArrayList<Integer> monthforEntry = new ArrayList<>();
        final ArrayList<Integer> yearForEntry = new ArrayList<>();
        DateYMD currentDate = DateManager.getYMDFormat(new Date());
        final int MONTH = currentDate.getMonth();
        List<DateYMD> allBetweenDates = DateManager.datefilterforMonth(currentDate);
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
            Log.i("zzzzz",currentDate.getMonth()+"");
            Entry entry = new Entry(i, co2Em);
                entries.add(entry);
                monthforEntry.add(currentDate.getMonth());
                yearForEntry.add(currentDate.getYear());
           // }
            currentDate =allBetweenDates.get(allBetweenDates.size()-1);
            currentDate.setDay(30);
            currentDate.setMonth(currentDate.getMonth()-1);
            if(currentDate.getMonth()<=0){
                currentDate.setYear(currentDate.getYear()-1);
                currentDate.setMonth(12);
            }

            allBetweenDates = new ArrayList<>();
            allBetweenDates = DateManager.datefilterforMonth(currentDate);
        }
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        lineChart.clear();

        LineDataSet dataset = new LineDataSet(entries, "CO2 in KG");
        dataset.setDrawCircleHole(true);

        dataset.setValueTextSize(10f);
        dataset.setCircleColorHole(Color.BLACK);
        LineData data = new LineData(dataset);
        data.setValueFormatter(new MyGraphValueFormater());
        lineChart.setBackgroundColor(Color.DKGRAY);
        dataset.setColors(Color.BLUE); //
        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.notifyDataSetChanged();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new Xaxisformatter(monthforEntry, entries,false));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateY(3000);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupInfo(e, entries, monthforEntry,yearForEntry,false,MONTH);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void setupUtilityText() {
        TextView text = (TextView)findViewById(R.id.utilityText);
        DecimalFormat df = new DecimalFormat("###,###.##");
        text.setText("Total Natural Gas used: "+ df.format(utilityManager.totalCarbonEmissionsNaturalGas()/28) + "KG"+
                "\n"+ "Total Electricity Used: " +df.format(utilityManager.totalCarbonEmissionsElectricity()/28)+"KG");
    }

    private float getUtilityBill() {
        return ((float)( utilityManager.totalCarbonEmissionsElectricity()+
                utilityManager.totalCarbonEmissionsNaturalGas())/28);
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
                Entry entry = new Entry(allBetweenDates.get(j).getDay()+allBetweenDates.get(j).getMonth()*30, co2Em);
                entries.add(entry);
                monthforEntry.add(allBetweenDates.get(j).getMonth());
            }
        }


        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        lineChart.clear();
        LineDataSet dataset = new LineDataSet(entries, "CO2 in KG");
        dataset.setDrawCircleHole(true);

        dataset.setValueTextSize(5f);
        dataset.setCircleColorHole(Color.BLACK);
        LineData data = new LineData(dataset);
        data.setValueFormatter(new MyGraphValueFormater());
        //lineChart.setBackgroundColor(Color.DKGRAY);
        dataset.setColors(Color.BLUE); //
        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.notifyDataSetChanged();
        XAxis xAxis = lineChart.getXAxis();
       // xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new Xaxisformatter(monthforEntry, entries,true));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateY(3000);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupInfo(e, entries, monthforEntry,null,true,0);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }




    public void setupInfo(Entry entry, ArrayList<Entry> entries, ArrayList<Integer> months,ArrayList<Integer> years,boolean isMonth,int currentMonth) {
        JourneyManager smallJM = new JourneyManager();
        if(isMonth) {
            TextView text = (TextView)findViewById(R.id.xAxisGraph);
            text.setText("                             <----------------- Current Date");
            for (int i = 0; i < entries.size(); i++) {
                if (entry.getX() == entries.get(i).getX()) {
                    int day = (int) entry.getX()-(months.get(i)*30);
                    int month = months.get(i);
                    for (int j = 0; j < journeyManager.getSize(); j++) {
                        DateYMD date = DateManager.getYMDFormat(journeyManager.getJourney(j).getDate());
                        if (date.getDay() == day && date.getMonth() == month) {
                            smallJM.add(journeyManager.getJourney(j));
                        }
                    }

                }
            }
        }
        else{
            TextView text = (TextView)findViewById(R.id.xAxisGraph);
            text.setText("Current Date -------->");
            for(int i =0; i<entries.size();i++){
                    if (entry.getX()+currentMonth == entries.get(i).getX()+4){
                        int month = months.get(i);
                        int year = years.get(i);
                        for(int j =0;j<journeyManager.getSize();j++){
                            DateYMD date = DateManager.getYMDFormat(journeyManager.getJourney(j).getDate());
                            if(date.getMonth() == month && date.getYear() == year){
                                smallJM.add(journeyManager.getJourney(j));
                            }
                        }
                    }
                }
        }
        String[] list1 = smallJM.getJourneyDescriptions();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list, list1);
        ListView list = (ListView) findViewById(R.id.journeylistForGraph);
        list.setAdapter(adapter);
        setupUtilityText();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LineGraphActivity.class);
    }

}
