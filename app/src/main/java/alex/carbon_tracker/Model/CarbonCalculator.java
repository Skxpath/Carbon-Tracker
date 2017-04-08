package alex.carbon_tracker.Model;

/**
 * Created by Alex on 3/7/2017.
 * <p>
 * CarbonCalculator class to calculate the amount of
 * CO2 produced by a vehicle given necessary parameters.
 */

public class CarbonCalculator {
    //1 km = 0.621371 miles
    private static final double KILOMETER_TO_MILES = 0.621371;
    private static final double GRAMS_TO_KILOGRAMS = 0.001;

    public static double calculate(double CO2InKGperDistanceInKm, double distanceTravelledCity, double distanceTravelledHighway) {

        double CO2ProducedCity = CO2InKGperDistanceInKm * distanceTravelledCity * GRAMS_TO_KILOGRAMS;
        double CO2ProducedHighway = CO2InKGperDistanceInKm * distanceTravelledHighway * GRAMS_TO_KILOGRAMS;


        return CO2ProducedCity + CO2ProducedHighway;
    }

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
