package alex.carbon_tracker.Model;

import android.content.Context;

/**
 * Carbon Tracker Model class which acts as a facade class for the
 * rest of the project. This class connects together JourneyManager,
 * RouteManager, VehicleManager, and UserVehicleManager.
 */
public class CarbonTrackerModel {
    private static CarbonTrackerModel ourInstance = new CarbonTrackerModel();
    private static EmissionsManager emissionsManager;
    private JourneyManager journeyManager;
    private RouteManager routeManager;
    private static VehicleManager vehicleManager;
    private UserVehicleManager userVehicleManager;
    private static TipManager tipManager;
    private UtilityBillManager utilityBillManager;
    private TransportationManager transportationManager;
    private static NotifManager notificationManager;
    private DateManager dateManager;

    public DateManager getDateManager() {
        return dateManager;
    }

    public UserVehicleManager getUserVehicleManager() {
        return userVehicleManager;
    }

    private Settings settings;

    public static void getSavedModel(Context context) {
        if (SaveData.getSharePreference(context) != null) {
            ourInstance = SaveData.getSharePreference(context);
        } else {
            ourInstance = new CarbonTrackerModel();
        }
    }

    public static CarbonTrackerModel getInstance() {
        return ourInstance;
    }

    public TransportationManager getTransportationManager() {
        return transportationManager;
    }

    private CarbonTrackerModel() {
        emissionsManager = new EmissionsManager();
        utilityBillManager = new UtilityBillManager();
        journeyManager = new JourneyManager();
        routeManager = new RouteManager();
        vehicleManager = new VehicleManager();
        userVehicleManager = new UserVehicleManager();
        transportationManager = new TransportationManager();
        tipManager = new TipManager();
        transportationManager = new TransportationManager();
        tipManager.addObserver(emissionsManager);
        notificationManager = new NotifManager();
        dateManager = new DateManager();
        settings = new Settings();
    }

    public Settings getSettings() {
        return settings;
    }

    public UtilityBillManager getUtilityBillManager() {
        return utilityBillManager;
    }

    public EmissionsManager getEmissionsManager() {
        return emissionsManager;
    }

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

    public NotifManager getNotificationManager() {
        return notificationManager;
    }
}
