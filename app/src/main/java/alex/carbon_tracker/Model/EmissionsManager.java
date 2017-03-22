package alex.carbon_tracker.Model;

/**
 * Created by Aria on 3/21/2017.
 */

public class EmissionsManager implements TipManagerObserver {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();

    private int totalEmissionsTransportation = 0;
    private int totalEmissionsVehicle = 0;
    private int totalTransportationJourneys = 0;
    private int totalVehicleJourneys = 0;
    private int totalEmissionsVehicleAndTransportation = 0;

    private int totalJourneys = 0;

    private int totalEmissionsNaturalgasAndElectricity = 0; //Add etc.
    private int totalEmissionsNaturalgas = 0;
    private int totalEmissionsElectricity = 0;

    private int totalEmissionsOverall = 0;

    public int getTotalEmissionsVehicle() {
        return totalEmissionsVehicle;
    }

    public int getTotalEmissionsTransportation() {
        return totalEmissionsTransportation;
    }

    public int getTotalTransportationJourneys() {
        return totalTransportationJourneys;
    }

    public int getTotalEmissionsVehicleAndTransportation() {
        return totalEmissionsVehicleAndTransportation;
    }

    public int getTotalVehicleJourneys() {
        return totalVehicleJourneys;
    }

    public int getTotalEmissionsNaturalgas() {
        return totalEmissionsNaturalgas;
    }

    public int getTotalEmissionsElectricity() {
        return totalEmissionsElectricity;
    }

    public int getTotalEmissionsOverall() {
        return totalEmissionsOverall;
    }

    public int getTotalEmissionsNaturalgasAndElectricity() {
        return totalEmissionsNaturalgasAndElectricity;
    }

    public int getTotalJourneys() {
        return totalJourneys;
    }

    @Override
    public void update() {
        totalEmissionsTransportation = (int) carbonTrackerModel.getJourneyManager().totalCarbonEmissionsPublicTransportation();
        totalEmissionsVehicle = (int) carbonTrackerModel.getJourneyManager().totalCarbonEmissionsVehicle();
        totalTransportationJourneys = carbonTrackerModel.getJourneyManager().totalTransportationJourneys();
        totalVehicleJourneys = carbonTrackerModel.getJourneyManager().totalVehicleJourneys();
        totalEmissionsVehicleAndTransportation = (int) carbonTrackerModel.getJourneyManager().totalCarbonEmissionsJourneys();

        totalEmissionsNaturalgasAndElectricity = 0; //Add etc.
        totalEmissionsNaturalgas = 0;
        totalEmissionsElectricity = 0;

        totalJourneys = carbonTrackerModel.getJourneyManager().totalJourneys();

        totalEmissionsOverall = totalEmissionsVehicleAndTransportation+totalEmissionsNaturalgasAndElectricity;
    }
}
