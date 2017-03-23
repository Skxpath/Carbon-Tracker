package alex.carbon_tracker.Model;

/*
* Vehicle class to store the different
* types of Vehicles read in from CSVReader
* and the raw vehicle.csv file
* */
public class Vehicle {
    private String make;
    private String model;
    private int year;
    private int cityDrive;
    private int highwayDrive;
    private double displacement;
    private String fuelType;
    private double fuelTypeNumber;
    private String transmission;

    public static final double ELECTRICITY = 0;
    public static final double DIESEL = 10.16;
    public static final double GASOLINE = 8.89;

    public Vehicle() {
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getCityDrive() {
        return cityDrive;
    }

    public int getHighwayDrive() {
        return highwayDrive;
    }

    public double getDisplacement() {
        return displacement;
    }

    public String getFuelType() {
        return fuelType;
    }

    public double getFuelTypeNumber() {
        return fuelTypeNumber;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCityDrive(int cityDrive) {
        this.cityDrive = cityDrive;
    }

    public void setHighwayDrive(int highwayDrive) {
        this.highwayDrive = highwayDrive;
    }

    public void setDisplacement(double displacement) {
        this.displacement = displacement;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setFuelTypeNumber(double fuelTypeNumber) {
        this.fuelTypeNumber = fuelTypeNumber;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", cityDrive=" + cityDrive +
                ", highwayDrive=" + highwayDrive +
                ", displacement=" + displacement +
                ", fuelType='" + fuelType + '\'' +
                ", transmission='" + transmission + '\'' +
                '}';
    }
}
