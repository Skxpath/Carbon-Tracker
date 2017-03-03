package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * JourneyManager class to manage the different
 * Journeys present in the program.
 */

public class JourneyManager {
    private List<Journey> journeyList = new ArrayList<>();

    public Journey getJourney(int index) {
        return journeyList.get(index);
    }

    public void add(Journey journey) {
        journeyList.add(journey);
    }

    public void delete(int index) {
        journeyList.remove(index);
    }


}
