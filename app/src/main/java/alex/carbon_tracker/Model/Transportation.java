package alex.carbon_tracker.Model;

/**
 * Holds a fuelTypeNumber
 */

public class Transportation {
    private double CO2InKGperDistanceInKM;

    public Transportation(double CO2InKGperDistanceInKM) {
        setCO2InKGperDistanceInKM(CO2InKGperDistanceInKM);
    }

    public void setCO2InKGperDistanceInKM(double CO2InKGperDistanceInKM) {
        this.CO2InKGperDistanceInKM = CO2InKGperDistanceInKM;
    }

    public double getCO2InKGperDistanceInKM() {
        return CO2InKGperDistanceInKM;
    }
}
