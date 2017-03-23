package alex.carbon_tracker.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * JourneyManager class to manage the different
 * Journeys present in the program.
 */

public class JourneyManager {
    private List<Journey> journeyList = new ArrayList<>();
    private String currentDate;

    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    //Getter for total carbon emissions for: vehicles, natural gas, electricity, public transportation
    private boolean hasDate;

    public double totalCarbonEmissionsJourneys() {
        return totalCarbonEmissionsPublicTransportation() + totalCarbonEmissionsVehicle();
    }

    public int totalJourneys() {
        return totalVehicleJourneys()+totalTransportationJourneys();
    }

    public int totalVehicleJourneys() {
        int totalVehicleJourneys = 0;
        for (Journey journey : journeyList) {
            if (journey.hasVehicle()) {
                totalVehicleJourneys++;
            }
        }
        return totalVehicleJourneys;
    }

    public int totalTransportationJourneys() {
        int totalTransportationJourneys = 0;
        for (Journey journey : journeyList) {
            if (journey.hasTransportation()) {
                totalTransportationJourneys++;
            }
        }
        return totalTransportationJourneys;
    }

    public double totalCarbonEmissionsVehicle() {
        double carbonEmissionsVehicle_inKG = 0;
        for (Journey journey : journeyList) {
            if (journey.hasVehicle()) {
                double carbonEmission_InKg = journey.getCarbonEmitted();
                carbonEmissionsVehicle_inKG += carbonEmission_InKg;
            }
        }

        return carbonEmissionsVehicle_inKG;
    }

    public double totalCarbonEmissionsPublicTransportation() {
        double carbonEmissionsPublicTranportation_inKG = 0;
        for (Journey journey : journeyList) {
            Log.d("JMAct", "Test1");
            if (!journey.hasVehicle()) {
                Log.d("JMAct", "Test2");
                double carbonEmission_InKg = journey.getCarbonEmitted();
                carbonEmissionsPublicTranportation_inKG += carbonEmission_InKg;
            }
        }
        Log.d("JourneyManager", "Total emi for trans = " +carbonEmissionsPublicTranportation_inKG);

        return carbonEmissionsPublicTranportation_inKG;
    }

    public boolean hasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    public int getSize() {
        return journeyList.size();
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public List<Journey> getJourneyList() {
        return journeyList;
    }

    public Journey getJourney(int index) {
        return journeyList.get(index);
    }

    public void add(Journey journey) {
        journeyList.add(journey);
    }

    public void delete(int index) {
        journeyList.remove(index);
    }

    public String[] getJourneyDescriptions() {
        String[] descriptions = new String[journeyList.size()];
        for (int i = 0; i < journeyList.size(); i++) {
            Journey journey = getJourney(i);
            if (journey.hasVehicle()) {
                descriptions[i] = String.format("Journey No.%d\nRoute Nickname: %s\nTransportation: %s",
                        i + 1,
                        journey.getRoute().getNickname(),
                        journey.getUserVehicle().getNickname());
            } else {
                descriptions[i] = String.format("Journey No.%d\nRoute Nickname: %s\nTransportation: %s",
                        i + 1,
                        journey.getRoute().getNickname(),
                        journey.getTransportation().getType());
            }
        }
        return descriptions;
    }

}
