package alex.carbon_tracker.Model;

/**
 * Created by Chester on 2017-03-18.
 * <p>
 * TransportationManager class to manage
 * the different forms of Transportation.
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
