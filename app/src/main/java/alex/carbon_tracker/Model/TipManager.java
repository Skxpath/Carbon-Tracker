package alex.carbon_tracker.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alex on 3/18/2017.
 *
 *
 */

public class TipManager {

    //Identifying the type of tip it is (Vehicle tip = too much carbon emission by vehicle.)

    private List<TipManagerObserver> observers = new ArrayList<>();
    private ArrayList<Tip> Tips = new ArrayList<>();

    public TipManager() {
        generateAllTips();
        Collections.sort(Tips);
    }

    public String getTip() {

        /*for (Tip tip: Tips) {
            Log.i("TipManagerCounter", "String: " + tip.getTip() + "\nCounter:" + tip.getCounter());
        }*/
        for (Tip tip : Tips) {
            if (tip.checkCooldowns()) {
                notifyAllObservers();
                tip.startCounter();
                return tip.getTip();
            }
        }
        return "Good job! We have no tips to give you at the moment!";
    }
    /*
    * First find which is largest carbon emission
    * Then display a tip relative to the largest sub-emission inefficiency
    * */
    private void generateAllTips() {

        generateVehicleTips();
        generateTransportationTips();
        generateNaturalGasTips();
        generateElectricityTips();
        generateMiscTips();

    }

    private void generateNaturalGasTips() {
        makeNaturalGasTip("You should consider saving on heating! You are using a lot of natural gas!");
    }

    private void generateTransportationTips() {
        makeTransportationTip("You have taken many transit trips recently. Consider walking to closer destinations!");
    }

    private void generateElectricityTips() {
        makeElectricityTip("You have generated 'totalCO2GeneratedElectricity' recently. Consider reducing your electricity usage to improve your carbon footprint!");
        makeElectricityTip("You should consider warming your clothing outside in the summer to save electricity on your dryer!");
    }

    private void generateVehicleTips() {
        makeVehicleTip("You have taken 'numOfVehicleTrips' vehicle trips recently, generating 'totalCO2GeneratedVehicle' kg of CO2. Consider using public transit instead!");
    }

    private void generateMiscTips() {
        makeMiscTip("You should consider walking instead of taking a form of transportation when your distance is less than 1 kilometer!");
        makeMiscTip("You should consider taking the bus instead of your vehicle when the distance is less than 5 kilometers!");
        makeMiscTip("You should consider taking the Skytrain when the distance is more than 50 km instead of driving! It can save you a lot of emissions.");
    }

    private void makeVehicleTip(String tip) {
        generateTip(tip, TipEnum.VEHICLE_TIPS);
    }

    private void makeElectricityTip(String tip) {
        generateTip(tip, TipEnum.ELECTRICITY_TIPS);
    }

    private void makeNaturalGasTip(String tip) {
        generateTip(tip, TipEnum.NATURALGAS_TIPS);
    }

    private void makeTransportationTip(String tip) {
        generateTip(tip, TipEnum.TRANSPORTATION_TIPS);
    }
    private void makeMiscTip(String tip) {
        generateTip(tip, TipEnum.MISC_TIPS);
    }

    private void generateTip(String tip, TipEnum key) {
        Tip newTip = new Tip(tip, key);
        this.addObserver(newTip);
        Tips.add(newTip);
    }

    public void notifyAllObservers() {
        for (TipManagerObserver observer : observers) {
            observer.update();
        }
        Collections.sort(Tips);
    }

    public void addObserver(TipManagerObserver observer) {
        observers.add(observer);
    }


}
