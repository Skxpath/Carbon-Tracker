package alex.carbon_tracker.Model;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Formats points on graphs
 */

public class MyGraphValueFormatter implements IValueFormatter {
    private DecimalFormat df;

    public MyGraphValueFormatter() {
        df = new DecimalFormat("###,###,###.##");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return df.format(value);
    }
}
