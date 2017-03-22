package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.SaveData;
import alex.carbon_tracker.R;

import android.widget.TextView;

public class LineGraphActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        setupBarChart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData.storeSharePreference(this);
    }


    private void setupBarChart() {

        final LineChart lineChart = (LineChart) findViewById(R.id.chart);
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
        dataset.setDrawCircleHole(true);

        dataset.setValueTextSize(12f);
        dataset.setCircleColorHole(Color.BLACK);
        LineData data = new LineData(sets);
        lineChart.setBackgroundColor(Color.DKGRAY);
        dataset.setColors(Color.BLUE); //
        lineChart.setData(data);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateY(1000);
        lineChart.invalidate();

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setupInfo(e);
                lineChart.setHighlightPerTapEnabled(true);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public void setupInfo(Entry entry){
        TextView text = (TextView)findViewById(R.id.emissionValueText1);

        text.setText(entry.getY()+" g");
    }




    public static Intent makeIntent(Context context) {
        return new Intent(context, LineGraphActivity.class);
    }

}
