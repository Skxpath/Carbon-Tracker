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
    private  TransportationManager transportationManager;


    public UserVehicleManager getUserVehicleManager() {
        return userVehicleManager;
    }

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
        //emissionsManager must be instantiated before tipManager.
        emissionsManager = new EmissionsManager();
        journeyManager = new JourneyManager();
        routeManager = new RouteManager();
        vehicleManager = new VehicleManager();
        userVehicleManager = new UserVehicleManager();
        tipManager = new TipManager();
        transportationManager = new TransportationManager();
tipManager.addObserver(emissionsManager);
        //userVehicleManager.testing();
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

}
