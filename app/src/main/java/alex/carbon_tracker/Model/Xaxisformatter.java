package alex.carbon_tracker.Model;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-04-01.
 */

public class Xaxisformatter implements IAxisValueFormatter {
    List<Integer> monthNumber;
    List<Entry> entries;
    int i;

    public Xaxisformatter(List<Integer> monthsList, List<Entry> entries) {
        monthNumber = new ArrayList<>();
        monthNumber = monthsList;
        this.entries = entries;
    }

    String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        for (int i = 0; i < entries.size(); i++) {
           if( entries.get(i).getX() == value) {
               return (int)value +" "+ mMonths[monthNumber.get(i)-1];
           }
        }
        return value+"";
    }
}