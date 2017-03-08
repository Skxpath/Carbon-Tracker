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
    private String nickname;

    public Route(int cityDistance, int highwayDistance, String nickname) {
        this.cityDistance = cityDistance;
        this.highwayDistance = highwayDistance;
        this.nickname = nickname;
    }

    public void setCityDistance(int cityDistance) {
        this.cityDistance = cityDistance;
    }

    public void setHighwayDistance(int highwayDistance) {
        this.highwayDistance = highwayDistance;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCityDistance() {
        return cityDistance;
    }

    public int getHighwayDistance() {
        return highwayDistance;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
