package alex.carbon_tracker;

/**
 * Created by Sachin on 2017-03-01.
 */
public class CarbonTrackerModel {
    private static CarbonTrackerModel ourInstance = new CarbonTrackerModel();

    private JourneyManager journeyManager;
    private RouteManager routeManager;
    private VehicleManager vehicleManager;

    public static CarbonTrackerModel getInstance() {
        return ourInstance;
    }

    private CarbonTrackerModel() {
        journeyManager = new JourneyManager();
        routeManager = new RouteManager();
        vehicleManager = new VehicleManager();
    }
}
