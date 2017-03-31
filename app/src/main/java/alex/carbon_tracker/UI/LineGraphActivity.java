package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.DateManager;
import alex.carbon_tracker.Model.DateYMD;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.SaveData;
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
        DateYMD smallestDate = dateManager.getSmallestDateFor28Days();

        final LineChart lineChart = (LineChart) findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();
        List<DateYMD> allBetweenDates = dateManager.datefilterfor28Days(smallestDate);
        for(int i = 0; i < dateManager.getJourneyDateList().size();i++){
            DateYMD newDate = dateManager.getJourneyDateList().get(i);
            for(int j = 0;j<allBetweenDates.size();j++){

                if(newDate.getDay()==allBetweenDates.get(j).getDay() &&
                        newDate.getMonth()==allBetweenDates.get(j).getMonth()
                        &&newDate.getYear()==allBetweenDates.get(j).getYear()){
                    entries.add(new Entry(newDate.getDay(),(float) newDate.getJourney(0).getCarbonEmitted()));
                }
            }
        }


        LineDataSet dataset = new LineDataSet(entries, "Co2");
        List<ILineDataSet> sets = new ArrayList<>();
        sets.add(dataset);
        dataset.setDrawCircleHole(true);

        dataset.setValueTextSize(1f);
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


    private List<DateYMD> getJourneydates(){
        List<DateYMD> dateList = new ArrayList<>();
        for(int i = 0;i<journeyManager.getSize();i++){
           Date date = journeyManager.getJourney(i).getDate();
            dateList.add(DateManager.getYMDFormat(date));
        }
        return dateList;
    }

    public void setupInfo(Entry entry) {
        int i = (int) entry.getX();
        String[] journeyList = carbonTrackerModel.getJourneyManager().getJourneyDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list, journeyList);
        ListView list = (ListView) findViewById(R.id.journeylistForGraph);
        list.setAdapter(adapter);
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, LineGraphActivity.class);
    }

}
