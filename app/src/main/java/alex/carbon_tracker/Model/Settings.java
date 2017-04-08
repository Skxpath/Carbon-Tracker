package alex.carbon_tracker.Model;

import static alex.carbon_tracker.Model.CarbonUnitsEnum.KILOGRAMS;

/**
 * Settings for model
 */
public class Settings {
    private CarbonUnitsEnum carbonUnit = KILOGRAMS;

    public CarbonUnitsEnum getCarbonUnit() {
        return carbonUnit;
    }

    public void setCarbonUnit(CarbonUnitsEnum carbonUnit) {
        this.carbonUnit = carbonUnit;
    }

}
