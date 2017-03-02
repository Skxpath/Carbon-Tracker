package alex.carbon_tracker.UI;

/**
 * Created by Sachin on 2017-03-01.
 */

public class Route {
    private int cityDistance;
    private int highwayDistance;
    public Route(int cityDistance,int highwayDistance){
        this.cityDistance = cityDistance;
        this.highwayDistance = highwayDistance;
    }

    public int getCityDistance() {
        return cityDistance;
    }

    public int getHighwayDistance() {
        return highwayDistance;
    }
}
