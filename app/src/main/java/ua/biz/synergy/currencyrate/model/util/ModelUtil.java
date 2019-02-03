package ua.biz.synergy.currencyrate.model.util;

import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

public class ModelUtil {
    
    public static boolean isEqualsNatureRates(final NatureRate[] nrExpected, final NatureRate[] nrActual) {
        if (nrExpected.length != nrActual.length) return false;
        for (int i = 0; i < nrExpected.length; i++) {
            if (!nrExpected[i].equals(nrActual[i])) {
                return false;
            }
        }
        return true;
    }
    public static boolean isEqualsCoordinates(final Coordinates coordinatesExpected, final Coordinates coordinatesActual){
        if (coordinatesExpected == null && coordinatesActual == null) {
            return true;
        }
        if (coordinatesExpected == null || coordinatesActual == null) {
            return false;
        }
        return coordinatesExpected.equals(coordinatesActual);
    }
}
