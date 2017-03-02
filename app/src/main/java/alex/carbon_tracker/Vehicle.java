package alex.carbon_tracker;

/**
 * Created by Sachin on 2017-03-01.
 */

public class Vehicle {
    private String nickname;
    private String make;
    private String model;
    private int year;
    public Vehicle(String nickname,String make,String model,int year){
        this.make= make;
        this.model = model;
        this.nickname = nickname;
        this.year = year;

    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getNickname() {
        return nickname;
    }

    public String getModel() {
        return model;
    }
}
