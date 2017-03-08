package alex.carbon_tracker.Model;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * Carbon Tracker Model class which acts as a facade class for the
 * rest of the project. This class connects together JourneyManager,
 * RouteManager, and VehicleManager.
 */
public class CarbonTrackerModel {
    private static CarbonTrackerModel ourInstance = new CarbonTrackerModel();

    private static JourneyManager journeyManager;
    private static RouteManager routeManager;
    private static VehicleManager vehicleManager;
    private static UserVehicleManager userVehicleManager;

    public  void addJourney(UserVehicle vehicle, Route route){
       Journey journey = new Journey(vehicle,route);
        journeyManager.add(journey);
    }

    public  void addRoute(int cityDistance, int highwayDistance){
       /* Route route = new Route(cityDistance,highwayDistance);
        routeManager.addRoute(route);*/
    }

    public UserVehicleManager getUserVehicleManager() {
        return userVehicleManager;
    }

    public static CarbonTrackerModel getInstance() {
        return ourInstance;
    }

    private CarbonTrackerModel() {
        journeyManager = new JourneyManager();
        routeManager = new RouteManager();
        vehicleManager = new VehicleManager();
        userVehicleManager = new UserVehicleManager();
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
}
