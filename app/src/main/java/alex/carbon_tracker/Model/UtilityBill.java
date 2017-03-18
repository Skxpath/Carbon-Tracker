package alex.carbon_tracker.Model;

import java.util.Date;

/**
 * Created by Eli on 2017-03-18.
 */

public class UtilityBill {
    private float householdGasConsumption;
    private float householdElectricalConsumption;
    private Date startDate;
    private Date endDate;
    private int householdSize;
    private float personalElectricalConsumption;
    private float personalGasConsumption;
    public UtilityBill(float gasConsumption, float electricalConsumption, Date startDate, Date endDate, int householdSize) {
        this.householdGasConsumption = gasConsumption;
        this.householdElectricalConsumption = electricalConsumption;
        this.startDate = startDate;
        this.endDate = endDate;
        this.householdSize = householdSize;
        this.personalElectricalConsumption = calculatePersonalConsumption(getHouseholdElectricalConsumption());
        this.personalGasConsumption = calculatePersonalConsumption(getHouseholdGasConsumption());
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

    public float calculatePersonalConsumption (float householdConsumption){
        float personalConsumption;
        personalConsumption =  householdConsumption/getHouseholdSize();
        return personalConsumption;
    }

    public float getPersonalElectricalConsumption() {
        return personalElectricalConsumption;
    }

    public float getPersonalGasConsumption() {
        return personalGasConsumption;
    }

    float getDailyConsumption (float totalConsumption){
        int elapsedDays;
        //not sure if my math here is correct. got it online
        elapsedDays = (int)(((getEndDate().getTime() - getStartDate().getTime())/ (1000*60*60*24)) % 7);
        float dailyConsumption = totalConsumption/elapsedDays;
        return  dailyConsumption;
    }
}
