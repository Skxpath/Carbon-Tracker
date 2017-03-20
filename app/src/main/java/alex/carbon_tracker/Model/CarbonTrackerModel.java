package alex.carbon_tracker.Model;

/**
 * Carbon Tracker Model class which acts as a facade class for the
 * rest of the project. This class connects together JourneyManager,
 * RouteManager, VehicleManager, and UserVehicleManager.
 */
public class CarbonTrackerModel {
    private static CarbonTrackerModel ourInstance = new CarbonTrackerModel();

    private static JourneyManager journeyManager;
    private static RouteManager routeManager;
    private static VehicleManager vehicleManager;
    private static UserVehicleManager userVehicleManager;
    private static TransportationManager transportationManager;
    private static TipManager tipManager;

    private static UtilityBillManager utilityBillManager;
    public UserVehicleManager getUserVehicleManager() {
        return userVehicleManager;
    }

    public static CarbonTrackerModel getInstance() {
        return ourInstance;
    }

    private CarbonTrackerModel() {
        utilityBillManager = new UtilityBillManager();
        journeyManager = new JourneyManager();
        routeManager = new RouteManager();
        vehicleManager = new VehicleManager();
        userVehicleManager = new UserVehicleManager();
        tipManager = new TipManager();
        transportationManager = new TransportationManager();
    }
    public  UtilityBillManager getUtilityBillManager() { return  utilityBillManager; }

    public JourneyManager getJourneyManager() {
        return journeyManager;
    }

    public RouteManager getRouteManager() {
        return routeManager;
    }

    public VehicleManager getVehicleManager() {
        return vehicleManager;
    }

    public TipManager getTipManager() {
        return tipManager;
    }


    public TransportationManager getTransportationManager() {
        return transportationManager;
    }
}
