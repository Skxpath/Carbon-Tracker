package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * RouteManager class which manages the different
 * Routes instantiated and also stores them in an ArrayList.
 */

public class RouteManager {
    private List<Route> routeList = new ArrayList<>();

    public Route getRoute(int index) {
        return routeList.get(index);
    }

    public void editRoute(Route route, int indexOfRouteEditing) {
        validateIndexWithException(indexOfRouteEditing);
        routeList.remove(indexOfRouteEditing);
        routeList.add(indexOfRouteEditing, route);
    }

    public void addRoute(Route route) {
        routeList.add(route);
    }

    public void deleteRoute(int index) {
        routeList.remove(index);
    }

    public int routeListSize() {
        return routeList.size();
    }

    public String[] getRouteDescriptions() {
        String[] descriptions = new String[routeListSize()];
        for (int i = 0; i < routeListSize(); i++) {
            Route route = getRoute(i);
            descriptions[i] = String.format("City Distance: %,10d km\n Highway Distance: %,10d km", route.getCityDistance(), route.getHighwayDistance());
            System.out.println(descriptions[i]);
        }
        return descriptions;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= routeListSize()) {
            throw new IllegalArgumentException();
        }

    }
}
