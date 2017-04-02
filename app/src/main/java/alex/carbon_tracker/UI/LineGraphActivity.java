package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.DateManager;
import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.MyGraphValueFormater;
import alex.carbon_tracker.Model.SaveData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        setupBarChart();
    }

    private void setupUtilityText() {
        TextView text = (TextView)findViewById(R.id.utilityText);
        DecimalFormat df = new DecimalFormat("###,###.##");
        text.setText("Total Natural Gas used: "+ df.format(utilityManager.totalCarbonEmissionsNaturalGas()/28) + "KG"+
                "\n"+ "Total Electricity Used: " +df.format(utilityManager.totalCarbonEmissionsElectricity()/28)+"KG");
    }

    private float getUtilityBill() {
        return ((float)( utilityManager.totalCarbonEmissionsElectricity()+utilityManager.totalCarbonEmissionsNaturalGas())/28);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void setupBarChart() {

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
            if (co2Em != 0) {
                Entry entry = new Entry(allBetweenDates.get(j).getDay(), co2Em);
                entries.add(entry);
                monthforEntry.add(allBetweenDates.get(j).getMonth());
            }
        }







        /*for(int i = 0; i < dateManager.getJourneyDateList().size();i++){
            Log.i("going",i+" journeylist");
            DateYMD newDate = dateManager.getJourneyDateList().get(i);
            for(int j = 0;j<allBetweenDates.size();j++){
               // Log.i("going2",j+" between dates");
                Log.i("going3",allBetweenDates.get(j).getDay()+" "+ allBetweenDates.get(j).getMonth()
                        +" "+allBetweenDates.get(j).getYear()+" ");

                float co2Em =0;

                if(newDate.getDay()==allBetweenDates.get(j).getDay() &&
                        newDate.getMonth()==allBetweenDates.get(j).getMonth()
                        &&newDate.getYear()==allBetweenDates.get(j).getYear()) {

                    for (int k = 0; k < newDate.getJourneys().getSize(); k++) {
                       co2Em+=newDate.getJourney(k).getCarbonEmitted();
                    }
                    entries.add(new Entry(newDate.getDay(), co2Em));
                    monthforEntry.add(newDate.getMonth());
                }
            }
        }*/
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        lineChart.clear();
        LineDataSet dataset = new LineDataSet(entries, "CO2 in KG");
        dataset.setDrawCircleHole(true);

        dataset.setValueTextSize(10f);
        dataset.setCircleColorHole(Color.BLACK);
        LineData data = new LineData(dataset);
        data.setValueFormatter(new MyGraphValueFormater());
        //lineChart.setBackgroundColor(Color.DKGRAY);
        dataset.setColors(Color.BLUE); //
        lineChart.setData(data);
        lineChart.invalidate();
        lineChart.notifyDataSetChanged();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new Xaxisformatter(monthforEntry, entries));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateY(3000);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupInfo(e, entries, monthforEntry);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }


    private List<DateYMD> getJourneydates() {
        List<DateYMD> dateList = new ArrayList<>();
        for (int i = 0; i < journeyManager.getSize(); i++) {
            Date date = journeyManager.getJourney(i).getDate();
            dateList.add(DateManager.getYMDFormat(date));
        }
        return dateList;
    }

    public void setupInfo(Entry entry, ArrayList<Entry> entries, ArrayList<Integer> months) {
        JourneyManager smallJM = new JourneyManager();
        for (int i = 0; i < entries.size(); i++) {
            if (entry.getX() == entries.get(i).getX()) {
                int day = (int) entry.getX();
                int month = months.get(i);
                for (int j = 0; j < journeyManager.getSize(); j++) {
                    DateYMD date = DateManager.getYMDFormat(journeyManager.getJourney(j).getDate());
                    if (date.getDay() == day && date.getMonth() == month) {
                        smallJM.add(journeyManager.getJourney(j));
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
