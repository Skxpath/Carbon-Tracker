package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import static android.R.attr.data;
import static android.R.attr.entries;
import static android.R.attr.x;

public class BarGraphActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    private List<Double> journeyNumbers = new ArrayList<>();
    private List<Double> journeyCO2Emissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
        setJourneyData();
        setupBarChart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }


    private void setupBarChart() {

        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry( 0,5.6f));
        entries.add(new Entry(1,8.4f));
        entries.add(new Entry(2,6));
        entries.add(new Entry(3,2));
        entries.add(new Entry(7,18));
        entries.add(new Entry(5,9));

        LineDataSet dataset = new LineDataSet(entries, "Cars");

        List<ILineDataSet>sets = new ArrayList<>();
        sets.add(dataset);
        dataset.setCircleColorHole(Color.BLUE);

        LineData data = new LineData(sets);
        dataset.setColors(Color.BLACK); //
        lineChart.setData(data);
        lineChart.animateY(1000);
        lineChart.invalidate();



        /*ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry>barEntries1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if(i%2 == 0) {
                barEntries.add(new BarEntry(i,  4.8f));
                barEntries1.add(new BarEntry(i, 2.9f));
            }
            else {
                barEntries.add(new BarEntry(i,  5-2f));
                barEntries1.add(new BarEntry(i,  6-4.8f));
            }
        }
        BarDataSet dataSet2 = new BarDataSet(barEntries1,"y");
        BarDataSet dataSet = new BarDataSet(barEntries,"x");
        dataSet.setColor(Color.BLUE);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);
        BarData data = new BarData(dataSets);

        BarChart chart = (BarChart)findViewById(R.id.BarGraph) ;
        chart.setData(data);
        chart.setContentDescription("CO2 Emissions in kilograms");
        chart.animateY(1000);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.invalidate();
    */
    }




    public static Intent makeIntent(Context context) {
        return new Intent(context, BarGraphActivity.class);
    }

    public void setJourneyData() {
        for(int i =0; i<5;i++){
            journeyNumbers.add(i,.1);
            journeyCO2Emissions.add(i,.1);
        }

        /*
        for (int i = 0; i < journeyManager.getJourneyList().size(); i++) {
            Journey journey = journeyManager.getJourney(i);
            journeyNumbers.add((double) i);
            journeyCO2Emissions.add(journey.getCarbonEmitted());
        }
        */
    }
}
