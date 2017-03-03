package alex.carbon_tracker.Model;

public class Vehicle {
    private String make;
    private String model;
    private int year;
    private double carbonEmission;

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

    public double getCarbonEmission() {
        return carbonEmission;
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

    public void setCarbonEmission(double carbonEmission) {
        this.carbonEmission = carbonEmission;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", carbonEmission=" + carbonEmission +
                '}';
    }
}
