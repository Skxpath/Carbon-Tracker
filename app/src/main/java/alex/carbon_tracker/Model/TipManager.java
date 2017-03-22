package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alex on 3/18/2017.
 */

public class TipManager {

    //Identifying the type of tip it is (Vehicle tip = too much carbon emission by vehicle.)

    private List<TipManagerObserver> observers = new ArrayList<>();
    private ArrayList<Tip> Tips = new ArrayList<>();
    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();

    private int totalTransportationJourneys = 0;
    private int totalEmissionsTransportation = 0;
    private int totalVehicleJourneys = 0;
    private int totalEmissionsVehicle = 0;
    private int totalEmissionsVehicleAndTransportation = 0;

    private int totalJourneys = 0;

   private int totalEmissionsNaturalgasAndElectricity = 0; //Add etc.
    private int totalEmissionsNaturalgas = 0;
   private int totalEmissionsElectricity = 0;

    private int totalEmissionsOverall = 0;

    private int identifier = 0;


    public TipManager() {
        generateAllTips();
        Collections.sort(Tips);
        //updateTipValues();
    }

    private void updateTipValues() {
        carbonTrackerModel.getEmissionsManager().update();
        totalTransportationJourneys = carbonTrackerModel.getEmissionsManager().getTotalTransportationJourneys();
        totalEmissionsTransportation = carbonTrackerModel.getEmissionsManager().getTotalEmissionsTransportation();
        totalVehicleJourneys = carbonTrackerModel.getEmissionsManager().getTotalVehicleJourneys();
        totalEmissionsVehicle = carbonTrackerModel.getEmissionsManager().getTotalEmissionsVehicle();
        totalEmissionsVehicleAndTransportation = carbonTrackerModel.getEmissionsManager().getTotalEmissionsVehicleAndTransportation();

        totalEmissionsOverall = carbonTrackerModel.getEmissionsManager().getTotalEmissionsOverall(); //Add natural gas and electrictiy later

        totalJourneys = carbonTrackerModel.getEmissionsManager().getTotalJourneys();

        totalEmissionsNaturalgasAndElectricity = carbonTrackerModel.getEmissionsManager().getTotalEmissionsNaturalgasAndElectricity(); //Add etc.
        totalEmissionsNaturalgas = carbonTrackerModel.getEmissionsManager().getTotalEmissionsNaturalgas();
        totalEmissionsElectricity = carbonTrackerModel.getEmissionsManager().getTotalEmissionsElectricity();

        for (Tip tip : Tips) {
            for (int i = 0; i <= identifier; i++) {
                if (tip.getIdentifier() == i) {
                    tip.setTip(updatedTipString(i));
                }
            }
        }

    }

    public String getTip() {

        updateTipValues();

        for (Tip tip : Tips) {
            if (tip.checkCooldowns() && checkKey(tip.getKey())) {
                notifyAllObservers();
                tip.startCounter();
                return tip.getTip();
            }
        }
        return "Good job! We have no tips to give you at the moment!";
    }

    private boolean checkKey(TipEnum key) {
        switch (key) {
            case VEHICLE_TIPS:
             if (totalEmissionsVehicle == 0) {
                 return false;
             } else {
                 return true;
             }
            case MISC_TIPS:
               return true;
            case NATURALGAS_TIPS:
                if (totalEmissionsNaturalgas == 0) {
                    return false;
                } else {
                    return true;
                }
            case TRANSPORTATION_TIPS:
                if (totalEmissionsTransportation == 0) {
                    return false;
                } else {
                    return true;
                }
            case ELECTRICITY_TIPS:
                if (totalEmissionsElectricity == 0) {
                    return false;
                } else {
                    return true;
                }
        }
        return true;
    }

    /*
    * First find which is largest carbon emission
    * Then display a tip relative to the largest sub-emission inefficiency
    * */
    private void generateAllTips() {

        //Each tip is assigned an identifier incrementing from 0.
        makeVehicleTip(updatedTipString(0));
        makeVehicleTip(updatedTipString(1));
        makeVehicleTip(updatedTipString(2));
        makeVehicleTip(updatedTipString(3));
        makeTransportationTip(updatedTipString(4));
        makeTransportationTip(updatedTipString(5));
        makeNaturalGasTip(updatedTipString(6));
        makeElectricityTip(updatedTipString(7));
        makeElectricityTip(updatedTipString(8));
        makeMiscTip(updatedTipString(9));
        makeElectricityTip(updatedTipString(10));

    }

    private String updatedTipString(int identifier) {
        switch (identifier) {

            case 0:
                return "You have taken " + totalVehicleJourneys + " vehicle trips recently, generating " +
                        totalEmissionsVehicle + "kg of CO2. Consider using public transit instead!";
            case 1:
                return "You have generated " + totalEmissionsVehicle + " kg of CO2 through vehicle trips." +
                        " Consider combining trips to reduce your emissions!";

            case 2:
                return "You have generated " + totalEmissionsVehicle + " kg of CO2 from vehicle trips and " + totalEmissionsTransportation +
                        " kg of CO2 from public transportation. By taking the bus or Skytrain more frequently " +
                        "you can reduce your vehicle emissions a lot!";

            case 3:
                return "Out of a total of " + totalEmissionsVehicleAndTransportation + " kg of CO2 generated from your journeys, " + totalEmissionsVehicle + " kg of CO2 were" +
                        " from vehicle trips. You can reduce this by walking or taking the bus as an alternative!";

            case 4:
                return "You have taken " + totalTransportationJourneys +
                        " trips using public transportation recently, generating a total of " + totalEmissionsTransportation
                        + " Kg of CO2. Consider walking to closer destinations!";

            case 5:
                return "You have generated a total of " + totalEmissionsTransportation
                        + " Kg of CO2 recently from public transportation. If it is possible to reroute " +
                        "your public transportation path to using the Skytrain more, it can help reduce your emissions!";

            case 6:
                return "You have generated a total of " + totalEmissionsNaturalgas + " Kg of CO2 recently. Consider reducing heating usage to save natural gas!";

            case 7:
                return "Out of a total of " + totalEmissionsOverall + " Kg of CO2 generated. You have generated a total of "+ "totalCO2GeneratedElectricity" + " recently." +
                        " Consider reducing your electricity usage to improve your carbon footprint!";

            case 8:
                return "You should consider warming your clothing outside in the summer to save electricity on your dryer, as " +
                        "you have generated " + totalEmissionsElectricity + " Kg of CO2 from electricity recently!";

            case 9:
                return "You have made " + totalJourneys + " recently. You should consider walking instead of taking a form of transportation when possible!";

            case 10:
                return "Out of a total of " + totalEmissionsOverall + " Kg of CO2 generated, " + totalEmissionsNaturalgasAndElectricity +
                        " were from utility usage. Consider reducing your natural gas and electricity usage to improve your carbon footprint!";

            case 11:
                return "case 11";

            case 12:
                return "case 12";

            case 13:
                return "case 13";

            case 14:
                return "case 14";

            default:
                return "This tip was overwritten because no case for it was given! Tip #: " + identifier;
        }
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
        Tip newTip = new Tip(tip, key, identifier);
        this.addObserver(newTip);
        Tips.add(newTip);
        identifier++;
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
