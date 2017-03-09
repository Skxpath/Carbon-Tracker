package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * RouteManager class which manages the different
 * Routes instantiated and also stores them in an ArrayList.
 */

public class RouteManager {
    private List<Route> routeList = new ArrayList<>();
    private Route currentRoute;

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    public Route getRoute(int index) {
        return routeList.get(index);
    }

    public void editRoute(Route route, int indexOfRouteEditing) {
        validateIndexWithException(indexOfRouteEditing);
        deleteRoute(indexOfRouteEditing);
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
            descriptions[i] = String.format("Nickname: %s\nCity Distance: %,10d km\nHighway Distance: %,10d km",
                    route.getNickname(),
                    route.getCityDistance(),
                    route.getHighwayDistance());
        }
        return descriptions;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= routeListSize()) {
            throw new IllegalArgumentException();
        }

    }
}
