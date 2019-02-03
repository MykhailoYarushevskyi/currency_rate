package ua.biz.synergy.currencyrate.model.data.banks.privat;

import com.google.gson.annotations.JsonAdapter;

import java.util.List;

import ua.biz.synergy.currencyrate.model.data.jsonadapters.privat.PvCashTypeAdapter;

/**
 * Privatbank
 * Data model class
 * model of a data that fetching from a site of the Privatbank
 * for a cash currency exchange
 */
@JsonAdapter(PvCashTypeAdapter.class)
public class PvCash {
    List<CcyObject> ccyObjects;
    
    public List<CcyObject> getCcyObjects() {
        return ccyObjects;
    }
    
    public void setCcyObjects(List<CcyObject> ccyObjects) {
        this.ccyObjects = ccyObjects;
    }
    
    public String toString() {
        return "ccy0:" + ccyObjects.get(0).getCcy() + ",ccy1:" + ccyObjects.get(1).getCcy();
    }
}
