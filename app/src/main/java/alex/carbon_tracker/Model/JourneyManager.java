package alex.carbon_tracker.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JourneyManager class to manage the different
 * Journeys present in the program.
 */

public class JourneyManager {
    private List<Journey> journeyList = new ArrayList<>();

    private Date date;

    //Getter for total carbon emissions for: vehicles, natural gas, electricity, public transportation
    private boolean hasDate;

    private int totalJourneysToday = 0;

    public double totalCarbonEmissionsJourneys() {
        return totalCarbonEmissionsPublicTransportation() + totalCarbonEmissionsVehicle();
    }

    public int journeyListSize() {
        return journeyList.size();
    }

    public int totalJourneys() {
        return totalVehicleJourneys() + totalTransportationJourneys();
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
            if (!journey.hasVehicle()) {
                double carbonEmission_InKg = journey.getCarbonEmitted();
                carbonEmissionsPublicTranportation_inKG += carbonEmission_InKg;
            }
        }

        return carbonEmissionsPublicTranportation_inKG;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public List<Journey> getJourneyList() {
        return journeyList;
    }

    public Journey getJourney(int index) {
        return journeyList.get(index);
    }

    public void add(Journey journey) {
        totalJourneysToday++;
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
                Date date = journey.getDate();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                f.format(date);
                descriptions[i] = String.format("Journey No.%d\nRoute Nickname: %s\nTransportation: %s\n",
                        i + 1,
                        journey.getRoute().getNickname(),
                        journey.getUserVehicle().getNickname())
                        + "Date: " + f.format(date);

            } else {
                Date date = journey.getDate();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                f.format(date);
                descriptions[i] = String.format("Journey No.%d\nRoute Nickname: %s\nTransportation: %s\n",
                        i + 1,
                        journey.getRoute().getNickname(),
                        journey.getTransportation().getType())
                        + "Date: " + f.format(date);

            }
        }
        return descriptions;
    }

    public int getTotalJourneysToday() {
        return totalJourneysToday;
    }

    public void setTotalJourneysToday(int totalJourneysToday) {
        this.totalJourneysToday = totalJourneysToday;
    }
}
