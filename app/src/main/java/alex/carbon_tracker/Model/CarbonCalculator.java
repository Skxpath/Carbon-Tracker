package alex.carbon_tracker.Model;

/**
 * Created by Alex on 3/7/2017.
 */

public class CarbonCalculator {
    //1 km = 0.621371 miles
    private static final double KILOMETER_TO_MILES = 0.621371;

    //gasType = the amount of co2 produced per gallon of the specific gas type.
    //gives you total carbon emitted

    public static double calculate(double gasType, double distanceTravelledCity, double distanceTravelledHighway, int milesPerGallonCity, int milesPerGallonHighway) {

        double distanceTravelledMilesCity = distanceTravelledCity * KILOMETER_TO_MILES;
        double distanceTravelledMilesHighway = distanceTravelledHighway * KILOMETER_TO_MILES;

        double CO2ProducedCity = calculateCarbonEmitted(gasType, distanceTravelledMilesCity, milesPerGallonCity);
        double CO2ProducedHighway = calculateCarbonEmitted(gasType, distanceTravelledMilesHighway, milesPerGallonHighway);

        return CO2ProducedCity + CO2ProducedHighway;
    }

    private static double calculateCarbonEmitted(double gasType, double distanceTravelled, int milesPerGallon) {
        return (distanceTravelled / milesPerGallon) * gasType;
    }

}
