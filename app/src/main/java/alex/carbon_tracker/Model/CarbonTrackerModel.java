package alex.carbon_tracker.Model;

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

    public RouteManager getRouteManager() {
        return routeManager;
    }

    public JourneyManager getJourneyManager() {
        return journeyManager;
    }

    public VehicleManager getVehicleManager() {
        return vehicleManager;
    }
}
