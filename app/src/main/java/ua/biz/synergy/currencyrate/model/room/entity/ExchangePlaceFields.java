package ua.biz.synergy.currencyrate.model.room.entity;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

// NOT USING YET
public interface ExchangePlaceFields {
    
    @PrimaryKey
    @NonNull
    public final String id = "";
    @NonNull
    public final String name = "";
    public final String title = "";
    public final String url = "";
    public final String city = "";
    public final String jsonString = ""; //maybe as the place the currency codes (for example, {code "ISO" : coding in current bank or market}))
    public final NatureRate[] natureRates = null;
}
