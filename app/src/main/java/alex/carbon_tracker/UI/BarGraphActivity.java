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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
        lineChart.setBackgroundColor(Color.CYAN);
        dataset.setColors(Color.BLACK); //
        lineChart.setData(data);
        lineChart.animateY(1000);
        lineChart.invalidate();

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupInfo(e);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public void setupInfo(Entry entry){
        CardView cardView = (CardView)findViewById(R.id.graphInfoCard);
        TextView text = (TextView)findViewById(R.id.emissionValueText);

        text.setText(entry.getY()+" g");
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
