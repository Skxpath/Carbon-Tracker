package alex.carbon_tracker.Model;

/**
 * Created by Sachin on 2017-03-06.
 */

public class UserVehicle extends Vehicle {
    private String nickname;

    public UserVehicle() {
    }

    public UserVehicle(String make, String model, int year, String nickname) {
        setMake(make);
        setModel(model);
        setYear(year);
        setNickname(nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
