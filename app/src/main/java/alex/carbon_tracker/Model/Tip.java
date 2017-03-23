package alex.carbon_tracker.Model;

/**
 * Created by Alex on 3/18/2017.
 */

public class Tip implements TipManagerObserver, Comparable<Tip> {


    private String tip;
    private final TipEnum key;

    public int getCounter() {
        return counter;
    }

    private int counter = 0;
    private double emissions = 0;

    private int identifier = 0;

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();

    //Do not display tip if displayed within the last COOLDOWN_LENGTH tips.
    private final int COOLDOWN_LENGTH = 7;

    public Tip(String tip, TipEnum key, int identifier) {
        this.tip = tip;
        this.key = key;
        this.identifier = identifier;
    }

public void setTip (String tip) {
    this.tip = tip;
}

    private void checkEmissions(TipEnum key) {
        switch (key) {
            case VEHICLE_TIPS:
                setEmissions(carbonTrackerModel.getEmissionsManager().getTotalEmissionsVehicle());
                break;
            case MISC_TIPS:
                setEmissions(0);
                break;
            case NATURALGAS_TIPS:
                setEmissions(carbonTrackerModel.getEmissionsManager().getTotalEmissionsNaturalgas());
                break;
            case TRANSPORTATION_TIPS:
                setEmissions(carbonTrackerModel.getEmissionsManager().getTotalEmissionsTransportation());
                break;
            case ELECTRICITY_TIPS:
                setEmissions(carbonTrackerModel.getEmissionsManager().getTotalEmissionsElectricity());
                break;
        }
    }

    private void setEmissions(double emissions) {

        this.emissions = emissions;
    }

    public TipEnum getKey() {
        return key;
    }

    @Override
    public void update() {
        updateCooldowns();
        checkEmissions(getKey());
    }

    public void startCounter() {
        counter++;
    }

    private void updateCooldowns() {
        if ((counter != 0) && (counter <= COOLDOWN_LENGTH)) {
            counter++;
        }
        if (counter > COOLDOWN_LENGTH) {
            counter = 0;
        }
    }

    public boolean checkCooldowns() {
        if (counter == 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getTip() {
        return tip;
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public int compareTo(Tip o) {
        if (emissions == o.emissions) {
            return 0;
        } else if (emissions < o.emissions) {
            return 1;
        } else {
            return -1;
        }
    }
}
