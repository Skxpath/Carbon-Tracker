package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles conversion from KG to Tree-days
 */
public class UnitConversion {
    private static final double KGtoTreeDays = 0.05;

    public static List<Double> convertListToTreeUnits(List<Double> originalList) {
        List<Double> newList = new ArrayList<>();
        for (Double numb : originalList) {
            double convertedNumb = numb * KGtoTreeDays;
            newList.add(convertedNumb);
        }
        return newList;
    }

    public static double convertDoubleToTreeUnits(double numb) {
        return numb * KGtoTreeDays;
    }
}
