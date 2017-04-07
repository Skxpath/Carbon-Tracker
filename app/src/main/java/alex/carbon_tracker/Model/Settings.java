package alex.carbon_tracker.Model;

import java.util.ArrayList;
import java.util.List;

import static alex.carbon_tracker.Model.CarbonUnitsEnum.KILOGRAMS;
import static alex.carbon_tracker.Model.CarbonUnitsEnum.TREE_DAYS;

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
