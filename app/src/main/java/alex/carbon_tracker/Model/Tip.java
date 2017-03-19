package alex.carbon_tracker.Model;

/**
 * Created by Alex on 3/18/2017.
 */

public class Tip implements TipManagerObserver {


    private final String tip;
    private final int key;
    private int counter = 0;
    private final int COOLDOWN_LENGTH = 7;

    public Tip(String tip, int key) {
        this.tip = tip;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    @Override
    public void update(int key) {
        if ((getKey() == key) && (checkCooldowns())) {
            startCounter();
        } else {
            updateCooldowns();
        }
    }

    private void startCounter() {
        counter++;
    }

    private void updateCooldowns() {
        if ((counter != 0) && (counter <= COOLDOWN_LENGTH)) {
            counter++;
        } else if (counter == COOLDOWN_LENGTH) {
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
}
