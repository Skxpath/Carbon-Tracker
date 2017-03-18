package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eli on 2017-03-18.
 */

public class UtilityBillManager {
    private List<UtilityBill> bills = new ArrayList<>();
    private UtilityBill mostRecentBill;

    public List<UtilityBill> getBills() {
        return bills;
    }

    public UtilityBill getMostRecentBill() {
        this.mostRecentBill = getBills().get(getBills().size()-1);
        return  this.mostRecentBill;
    }
}
