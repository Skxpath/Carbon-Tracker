package alex.carbon_tracker.Model;

import android.util.Log;

/**
 * Created by Sachin on 2017-03-06.
 * comments
 */

public class UserVehicle extends Vehicle {
    private String nickname;

    public UserVehicle(String make, String model, int year, String nickname, int cityDrive, int highwayDrive) {
        setMake(make);
        setModel(model);
        setYear(year);
        setNickname(nickname);
        setCityDrive(cityDrive);
        setHighwayDrive(highwayDrive);
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
