package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-03-01.
 */

public class RouteManager {
    private List<Route> routeList = new ArrayList<>();

    public Route getRoute(int index) {
        return routeList.get(index);
    }
    public void addRoute(Route route){
        routeList.add(route);
    }
    public void deleteRoute(int index){
        routeList.remove(index);
    }
}
