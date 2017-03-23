package alex.carbon_tracker.Model;

import android.util.Log;

/**
 * Created by Aria on 3/21/2017.
 */

public class EmissionsManager implements TipManagerObserver {

private CarbonTrackerModel carbonTrackerModel;

    private double totalEmissionsTransportation = 0;
    private double totalEmissionsVehicle = 0;

    private int totalTransportationJourneys = 0;
    private int totalVehicleJourneys = 0;

    private double totalEmissionsVehicleAndTransportation = 0;

    private int totalJourneys = 0;

    private double totalEmissionsNaturalgas = 0;
    private double totalEmissionsElectricity = 0;

    private double totalEmissionsNaturalgasAndElectricity = 0; //Add etc.

    private double totalEmissionsOverall = 0;

    public double getTotalEmissionsVehicle() {
        return totalEmissionsVehicle;
    }

    public double getTotalEmissionsTransportation() {
        return totalEmissionsTransportation;
    }

    public int getTotalTransportationJourneys() {
        return totalTransportationJourneys;
    }

    public double getTotalEmissionsVehicleAndTransportation() {
        return totalEmissionsVehicleAndTransportation;
    }

    public int getTotalVehicleJourneys() {
        return totalVehicleJourneys;
    }

    public double getTotalEmissionsNaturalgas() {
        return totalEmissionsNaturalgas;
    }

    public double getTotalEmissionsElectricity() {
        return totalEmissionsElectricity;
    }

    public double getTotalEmissionsOverall() {
        return totalEmissionsOverall;
    }

    public double getTotalEmissionsNaturalgasAndElectricity() {
        return totalEmissionsNaturalgasAndElectricity;
    }

    public int getTotalJourneys() {
        return totalJourneys;
    }

    //Stackoverflow: http://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
    private static double roundToOneDecimalPlace(double value) {
        int scale = (int) Math.pow(10, 1);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public void update() {
        carbonTrackerModel = CarbonTrackerModel.getInstance();

        totalEmissionsTransportation = roundToOneDecimalPlace(carbonTrackerModel.getJourneyManager().totalCarbonEmissionsPublicTransportation());
        Log.d("EmissionsManager", "Total emi for trans = " + totalEmissionsTransportation);
        totalEmissionsVehicle = roundToOneDecimalPlace(carbonTrackerModel.getJourneyManager().totalCarbonEmissionsVehicle());

        totalTransportationJourneys = carbonTrackerModel.getJourneyManager().totalTransportationJourneys();
        totalVehicleJourneys = carbonTrackerModel.getJourneyManager().totalVehicleJourneys();

        totalEmissionsVehicleAndTransportation = roundToOneDecimalPlace(carbonTrackerModel.getJourneyManager().totalCarbonEmissionsJourneys());

        totalEmissionsNaturalgasAndElectricity = 0; //Add etc.
        totalEmissionsNaturalgas = 0;
        totalEmissionsElectricity = 0;

        totalJourneys = carbonTrackerModel.getJourneyManager().totalJourneys();

        totalEmissionsOverall = roundToOneDecimalPlace(totalEmissionsVehicleAndTransportation+totalEmissionsNaturalgasAndElectricity);
    }
}
