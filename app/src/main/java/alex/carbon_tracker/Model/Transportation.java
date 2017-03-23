package alex.carbon_tracker.Model;

/**
 * Transportation class that holds
 * emissions values for different transportation types
 * and helps JourneyManager differ between different
 * types in it's display and calculation.
 */

public class Transportation {
    private double CO2InKGperDistanceInKM;
    private String type;

    public Transportation(double CO2InKGperDistanceInKM, String type) {
        setCO2InKGperDistanceInKM(CO2InKGperDistanceInKM);
        setType(type);
    }

    public void setCO2InKGperDistanceInKM(double CO2InKGperDistanceInKM) {
        this.CO2InKGperDistanceInKM = CO2InKGperDistanceInKM;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCO2InKGperDistanceInKM() {
        return CO2InKGperDistanceInKM;
    }

    public String getType() {
        return type;
    }
}
