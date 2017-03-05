package alex.carbon_tracker.Model;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * Route class which takes in city and
 * highway driving distances from the user input.
 */

public class Route {
    private int cityDistance;
    private int highwayDistance;

    public Route(int cityDistance, int highwayDistance) {
        this.cityDistance = cityDistance;
        this.highwayDistance = highwayDistance;
    }

    public void setCityDistance(int cityDistance) {
        this.cityDistance = cityDistance;
    }

    public void setHighwayDistance(int highwayDistance) {
        this.highwayDistance = highwayDistance;
    }

    public int getCityDistance() {
        return cityDistance;
    }

    public int getHighwayDistance() {
        return highwayDistance;
    }
}
