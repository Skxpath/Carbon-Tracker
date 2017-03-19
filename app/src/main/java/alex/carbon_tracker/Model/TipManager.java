package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 3/18/2017.
 */

public class TipManager {

    private List<TipManagerObserver> observers = new ArrayList<>();

    public void notifyAllObservers(int key){
        for (TipManagerObserver observer : observers) {
            observer.update(key);
        }
    }

    public void addObserver(TipManagerObserver observer){
        observers.add(observer);
    }


}
