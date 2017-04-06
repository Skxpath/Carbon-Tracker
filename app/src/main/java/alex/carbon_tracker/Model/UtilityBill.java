package alex.carbon_tracker.Model;

import java.util.Date;

/**
 * Created by Eli on 2017-03-18.
 * <p>
 * UtilityBill class to store different
 * UtilityBill instances the user enters.
 */

public class UtilityBill implements Comparable<UtilityBill> {
    private float householdGasConsumption;
    private float householdElectricalConsumption;
    private Date startDate;
    private Date endDate;
    private int householdSize;
    private float personalElectricalConsumption;
    private float personalGasConsumption;
    private double emissionsForElectricity;
    private double emissionsForGas;

    public UtilityBill(float gasConsumption, float electricalConsumption, Date startDate, Date endDate, int householdSize) {
        this.householdGasConsumption = gasConsumption;
        this.householdElectricalConsumption = electricalConsumption;
        this.startDate = startDate;
        this.endDate = endDate;
        this.householdSize = householdSize;
        this.personalElectricalConsumption = calculatePersonalConsumption(getHouseholdElectricalConsumption());
        this.personalGasConsumption = calculatePersonalConsumption(getHouseholdGasConsumption());
        setEmissionsForElectricity();
        setEmissionsForGas();
    }

    public float getHouseholdGasConsumption() {
        return householdGasConsumption;
    }

    public float getHouseholdElectricalConsumption() {
        return householdElectricalConsumption;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getHouseholdSize() {
        return householdSize;
    }

    public float calculatePersonalConsumption(float householdConsumption) {
        float personalConsumption;
        personalConsumption = householdConsumption / getHouseholdSize();
        return personalConsumption;
    }

    public float getPersonalElectricalConsumption() {
        return personalElectricalConsumption;
    }

    public float getPersonalGasConsumption() {
        return personalGasConsumption;
    }

    public double getEmissionsForElectricity() {
        return emissionsForElectricity;
    }

    public double getEmissionsForGas() {
        return emissionsForGas;
    }

    public void setEmissionsForElectricity() {
        int KILOWATTS_TO_GIGAWATTS = 1000000;
        int KILOGRAMS_OF_CO2_PER_GIGAWATT = 9000;
        float gigaWatts = this.getHouseholdElectricalConsumption() / KILOWATTS_TO_GIGAWATTS;
        this.emissionsForElectricity = gigaWatts * KILOGRAMS_OF_CO2_PER_GIGAWATT;
    }

    public void setEmissionsForGas() {
        //Natural Gas: Assume 56.1 kg CO2 / GJ
        double KILOGRAMS_OF_CO2_PER_GJ = 56.1;
        this.emissionsForGas = KILOGRAMS_OF_CO2_PER_GJ * getHouseholdGasConsumption();
    }

    public float getDailyConsumption(float totalConsumption) {
        int elapsedDays;
        //not sure if my math here is correct. got it online
        elapsedDays = (int) (((getEndDate().getTime() - getStartDate().getTime()) / (1000 * 60 * 60 * 24)));
        float dailyConsumption = totalConsumption / (float) elapsedDays;
        return dailyConsumption;
    }

    @Override
    public int compareTo(UtilityBill o) {
        if (getEndDate().getTime() == o.getEndDate().getTime()) {
            return 0;
        } else if (getEndDate().getTime() > o.getEndDate().getTime()) {
            return 1;
        } else {
            return -1;
        }
    }
}
