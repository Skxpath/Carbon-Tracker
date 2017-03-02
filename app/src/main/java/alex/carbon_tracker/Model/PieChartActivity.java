package alex.carbon_tracker.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import alex.carbon_tracker.R;

public class PieChartActivity extends AppCompatActivity {
    float rainfall[]= {9,7,6,4,2};
    String month[] = {"Jan","Feb","Mar","April","June"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        setupPieChart();
    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0;i<rainfall.length;i++){
            pieEntries.add(new PieEntry(rainfall[i],month[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries,"Months");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);
        PieChart chart = (PieChart) findViewById(R.id.pieChart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, PieChartActivity.class);
    }
}