package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Eli on 2017-03-18.
 * <p>
 * UtilityBillManager class to store and manage the different
 * UtilityBill's instantiated.
 */

public class UtilityBillManager {
    private List<UtilityBill> bills = new ArrayList<>();
    private UtilityBill mostRecentBill;

    public List<UtilityBill> getBills() {
        return bills;
    }

    public void addBill(UtilityBill bill) {
        //need to add a sort function to make bill with oldest end date first index and be sorted chronologically such that bill with newest end date is last index
        //need to get date working properly first
        getBills().add(bill);
        Collections.sort(bills);
    }

    public UtilityBill getMostRecentBill() {
        if (getBills().size() > 0) {
            this.mostRecentBill = getBills().get(getBills().size() - 1);
        } else {
            this.mostRecentBill = new UtilityBill(0, 0, new Date(), new Date(), 1);
        }
        return this.mostRecentBill;
    }

    public double totalCarbonEmissionsElectricity() {
        double totalElectricityEmissions = 0;
        for (UtilityBill bill : bills) {
            double electricityEmission = bill.getEmissionsForElectricity();
            totalElectricityEmissions += electricityEmission;
        }
        return totalElectricityEmissions;
    }

    public double totalCarbonEmissionsNaturalGas() {
        double totalGasEmissions = 0;
        for (UtilityBill bill : bills) {
            double gasEmission = bill.getEmissionsForGas();
            totalGasEmissions += gasEmission;
        }
        return totalGasEmissions;
    }

    public boolean getIfUtilityBillEnteredWithin45Days() {

        final long DAY = 24 * 60 * 60 * 1000;
        return !bills.isEmpty() && System.currentTimeMillis() - getMostRecentBill().getEndDate().getTime() <= DAY * 45;
    }
}
