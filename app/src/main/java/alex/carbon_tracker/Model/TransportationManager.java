package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chester on 2017-03-18.
 */

public class TransportationManager {
    private Transportation currTransportation;

    public TransportationManager() {

    }

    public Transportation getCurrTransportation() {
        return currTransportation;
    }

    public void setCurrTransportation(Transportation currTransportation) {
        this.currTransportation = currTransportation;
    }
}
