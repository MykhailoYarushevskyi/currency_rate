package ua.biz.synergy.currencyrate.model.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.util.Log;

import java.util.UUID;

import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.entity.pojo.UrlContainer;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;
import ua.biz.synergy.currencyrate.model.util.ModelUtil;

@Entity(tableName = "banks", indices = {@Index(value = "name", unique = false)})
public class Banks extends ExchangePlace {
    @Ignore
    private final String TAG = Banks.class.getSimpleName();
    @Embedded(prefix = "place")
    public final Coordinates placeLocation;          //creates columns place_latitude, place_longitude
    
    @Ignore
    public Banks(String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates, Coordinates placeLocation) {
        this(UUID.randomUUID().toString(), name, title, urlSite, urlsRate, city, jsonString, natureRates, placeLocation);
    }
    
    public Banks(String id, String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates, Coordinates placeLocation) {
        super(id, name, title, urlSite, urlsRate, city, jsonString, natureRates);
        this.placeLocation = placeLocation;
    }
    
    public Banks getInstance(String id, String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates, Coordinates placeLocation) {
        return new Banks(id, name, title, urlSite, urlsRate, city, jsonString, natureRates, placeLocation);
    }
    
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ID= ").append(id).append(" NAME= ").append(name).append(" TITLE= ")
                .append(title).append(" urlSite= ").append(urlSite);
        stringBuffer.append(" urlsRate: ");
        for (String urlRate : urlsRate.getRateUrls()) {
            stringBuffer.append(urlRate);
            stringBuffer.append(" ");
        }
        stringBuffer.append(" city= ").append(city).append(" jsonString= ").append(jsonString);
        stringBuffer.append(" natureRates: ");
        for (NatureRate nr : natureRates) {
            stringBuffer.append(nr.toString());
            stringBuffer.append(" ");
        }
        stringBuffer.append(" placeLocation: ").append(placeLocation.longitude)
                .append(" ; ").append(placeLocation.latitude);
        
        return stringBuffer.toString();
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
        hc = 33 * hc + (placeLocation == null ? 0 : placeLocation.hashCode());
        
        return hc;
    }
    
    /**
     * Compares this {@code Banks} object to the specified object. The result of this compare is {@code true}
     * if and only if argument is not {@code null} and is a {@code Banks}, that represent same fields as this object.
     *
     * @param obj The object to compare this {@code Banks} object against
     * @return {@code true} if the given object represent the object, equivalent to this {@code Banks} object,
     * {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Banks) {
            Banks banks = (Banks) obj;
            Log.i(TAG, "Called public boolean equals(Banks banks)");
            if (this.hashCode() == banks.hashCode()) {
                Log.i(TAG, "BANKS.equals(BANKS): this.id.equals(banks.id) = " + this.id.equals(banks.id)
                        + " ; this.name.equals(banks.name) = " + this.name.equals(banks.name));
                
                return (this.id.equals(banks.id)
                        && this.name.equals(banks.name)
                        && this.title.equals(banks.title)
                        && this.urlSite.equals(banks.urlSite)
                        && this.urlsRate.equals(banks.urlsRate)
                        && this.city.equals(banks.city)
                        && this.jsonString.equals(banks.jsonString)
                        && ModelUtil.isEqualsNatureRates(this.natureRates, banks.natureRates)
                        && ModelUtil.isEqualsCoordinates(this.placeLocation, banks.placeLocation));
            } else {
                Log.i(TAG, "FALSE - BANKS.equals(BANKS): this.hashCode() == banks.hashCode() = " + (this.hashCode() == banks.hashCode()));
                return false;
            }
        } else return false;
    }
}
