package alex.carbon_tracker.Model;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by Sachin on 2017-04-01.
 */

public class MyGraphValueFormater implements IValueFormatter {
    private DecimalFormat df;

    public MyGraphValueFormater() {
        df = new DecimalFormat("###,###,###.##");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return df.format(value) + "KG";
    }
}
