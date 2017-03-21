package alex.carbon_tracker.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import alex.carbon_tracker.UI.WelcomeScreenActivity;

import static android.content.Context.MODE_PRIVATE;

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
    private static TipManager tipManager;
    private TransportationManager transportationManager;
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
        journeyManager = new JourneyManager();
        routeManager = new RouteManager();
        vehicleManager = new VehicleManager();
        userVehicleManager = new UserVehicleManager();
        tipManager = new TipManager();
        transportationManager = new TransportationManager();

        //userVehicleManager.testing();
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
