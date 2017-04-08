package alex.carbon_tracker.Model;

/**
 * UserVehicle class to store the vehicles
 * the user inputs into the system. This class
 * extends from Vehicle.
 */

public class UserVehicle extends Vehicle {
    private String nickname;
    private int iconID;
    public int getIconID(){
        return iconID;
    }
    public void setIconID(int id){
        iconID = id;
    }
    public UserVehicle(String make, String model, int year, String nickname, String transmission,
                       String fuelType, int cityDrive, int highwayDrive, Double fuelTypeValue, int iconID) {
        setMake(make);
        setModel(model);
        setYear(year);
        setNickname(nickname);
        setCityDrive(cityDrive);
        setHighwayDrive(highwayDrive);
        setFuelTypeNumber(fuelTypeValue);
        setTransmission(transmission);
        setFuelType(fuelType);
        setIconID(iconID);

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return nickname;
    }
}
