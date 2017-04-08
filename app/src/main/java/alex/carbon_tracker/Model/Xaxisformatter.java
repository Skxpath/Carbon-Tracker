package alex.carbon_tracker.Model;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-04-01.
 * <p>
 * Formats the x axis on a graph.
 */

public class Xaxisformatter implements IAxisValueFormatter {
    List<Integer> monthNumber;
    List<Entry> entries;
    boolean isGraphMonth;
    List<Integer> yearNumber;

    public Xaxisformatter(List<Integer> monthsList, List<Entry> entries, boolean isGraphMonth, List<Integer> yearList) {
        monthNumber = new ArrayList<>();
        monthNumber = monthsList;
        this.entries = entries;
        this.isGraphMonth = isGraphMonth;
        yearNumber = new ArrayList<>();
        yearNumber = yearList;
    }

    String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int value1 = (int) value;
        if (isGraphMonth) {

            String currentMonth = mMonths[0];
            for (int i = 0; i < entries.size(); i++) {
                if (entries.get(i).getX() == value) {
                    currentMonth = mMonths[monthNumber.get(i) - 1];
                    return (value1 - monthNumber.get(i) * 30) + " " + currentMonth;
                } else {
                    int date = (value1 - monthNumber.get(i) * 30);
                    if (date > 30) {
                        date = date - 30;
                        try {
                            return date + " " + mMonths[monthNumber.get(i)];
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            if (monthNumber.get(i) >= 12) {
                                return date + " " + mMonths[0];
                            } else {
                                return date + " " + mMonths[11];
                            }
                        }
                    }
                    return date + " " + mMonths[monthNumber.get(i) - 1];
                }
            }

        } else {
            if (value < 12) {
                return mMonths[monthNumber.get(value1) - 1] + yearNumber.get(value1);
            }
            return value + "";
        }
        return "";
    }
}