package alex.carbon_tracker.Model;

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

    public double totalCarbonEmissionsJourneys() {
        return totalCarbonEmissionsPublicTransportation() + totalCarbonEmissionsVehicle();
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
                descriptions[i] = String.format("Journey No.%d\nRoute Nickname: %s\nTransportation: %s\n",
                        i + 1,
                        journey.getRoute().getNickname(),
                        journey.getUserVehicle().getNickname())
                        + "Date: " + journey.getDate();

            } else {
                descriptions[i] = String.format("Journey No.%d\nRoute Nickname: %s\nTransportation: %s\n",
                        i + 1,
                        journey.getRoute().getNickname(),
                        journey.getTransportation().getType())
                        + "Date: " + journey.getDate();

            }
        }
        return descriptions;
    }

}
