package ua.biz.synergy.currencyrate.model.room.entity;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import ua.biz.synergy.currencyrate.model.room.entity.pojo.UrlContainer;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

abstract class ExchangePlace {
    
    @PrimaryKey
    @NonNull
    public final String id;
    @NonNull
    public final String name;
    public final String title;
    public final String urlSite;
    // the strings of a URL in the array follow in the same order that
    // natureRates's objects in NatureRate[], in any class, where
    // UrlContainer and NatureRate[] occur together.
    public final UrlContainer urlsRate;
    public final String city;
    public final String jsonString; //maybe as the place the currency codes (for example, {code "ISO" : coding in current bank or market}))
    public final NatureRate[] natureRates;
    /*    The Coordinates object I must remove from here and move into Banks and Markets objects.
      Because otherwise, an operation reading of the Coordinates object from the "markets" table ("Markets entity")(only from a markets table,
      - a read operation from the others tables(Banks, BanksRate, MarketsRate, that contain the Coordinates object, performs successfully),
      cause obtaining a null Coordinates object. Note, that the operation of the write in the database performs successfully.
    */
/*    @Embedded(prefix = "place")
    public final Coordinates placeLocation;          //creates columns place_latitude, place_longitude*/
    
    
    @Ignore
    public ExchangePlace(String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates) {
        this(UUID.randomUUID().toString(), name, title, urlSite, urlsRate, city, jsonString, natureRates);
    }
    
    public ExchangePlace(@NonNull String id, @NonNull String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.urlSite = urlSite;
        this.urlsRate = urlsRate;
        this.city = city;
        this.jsonString = jsonString;
        // assign array of NatureRate
        //OR:
/*        this.natureRates = new NatureRate[natureRates.length];
        for (int index = 0; index < natureRates.length; index++) {
            this.natureRates[index] = natureRates[index];
        }*/
        this.natureRates = natureRates;
//      Look the comment about Coordinates object above
//        this.placeLocation = placeLocation;
    
        
    }
    
    @Override
    public int hashCode() {
        int hc = 14;
        hc = 33 * hc + (id == null || id.length() == 0 ? 0 : id.hashCode());
        hc = 33 * hc + (name == null ? 0 : name.hashCode());
        hc = 33 * hc + (title == null ? 0 : title.hashCode());
        hc = 33 * hc + (urlSite == null ? 0 : urlSite.hashCode());
        hc = 33 * hc + (urlsRate == null ? 0 : urlsRate.hashCode());
        hc = 33 * hc + (city == null ? 0 : city.hashCode());
        hc = 33 * hc + (jsonString == null ? 0 : jsonString.hashCode());
        if (natureRates != null) {
            for (NatureRate nr : natureRates) {
                hc = 33 * hc + nr.type;
            }
        } else {
            hc = 33 * hc;
        }
//      Look the comment about Coordinates object above
//        hc = 33 * hc + (placeLocation == null ? 0 : placeLocation.hashCode());
        
        return hc;
    }
    
    @Override
    public String toString() {
        return this.title;
    }
    
    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
