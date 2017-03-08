package alex.carbon_tracker.Model;

import android.util.Log;

/**
 * Created by Sachin on 2017-03-06.
 * comments
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

    @Override
    public String toString(){
        return nickname;
    }
}
