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

@Entity(tableName = "markets", indices = {@Index(value = "name", unique = false)})
public class Markets extends ExchangePlace {
    @Ignore
    private final String TAG = Markets.class.getSimpleName();
    
    @Embedded(prefix = "place")
    public final Coordinates placeLocation;          //creates columns place_latitude, place_longitude
    
    @Ignore
    public Markets(String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates, Coordinates placeLocation) {
        this(UUID.randomUUID().toString(), name, title, urlSite, urlsRate, city, jsonString, natureRates, placeLocation);
    }
    
    public Markets(String id, String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates, Coordinates placeLocation) {
        super(id, name, title, urlSite, urlsRate, city, jsonString, natureRates);
        this.placeLocation = placeLocation;
    }
    
    public Markets getInstance(String id, String name, String title, String urlSite, UrlContainer urlsRate, String city, String jsonString, NatureRate[] natureRates, Coordinates placeLocation) {
        return new Markets(id, name, title, urlSite, urlsRate, city, jsonString, natureRates, placeLocation);
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
     * Compares this {@code Markets} object to the specified object. The result of this compare is {@code true}
     * if and only if argument is not {@code null} and is a {@code Markets}, that represent same fields as this object.
     *
     * @param obj The object to compare this {@code Markets} object against
     * @return {@code true} if the given object represent the object, equivalent to this {@code Markets} object,
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
        if (obj instanceof Markets) {
            Markets markets = (Markets) obj;
            Log.i(TAG, "Called public boolean equals(Markets markets)");
            if (this.hashCode() == markets.hashCode()) {
                Log.i(TAG, "MARKETS.equals(MARKETS): this.id.equals(markets.id) = " + this.id.equals(markets.id)
                        + " ; this.name.equals(markets.name) = " + this.name.equals(markets.name));
            
                return (this.id.equals(markets.id)
                        && this.name.equals(markets.name)
                        && this.title.equals(markets.title)
                        && this.urlSite.equals(markets.urlSite)
                        && this.urlsRate.equals(markets.urlsRate)
                        && this.city.equals(markets.city)
                        && this.jsonString.equals(markets.jsonString)
                        && ModelUtil.isEqualsNatureRates(this.natureRates, markets.natureRates)
                        && ModelUtil.isEqualsCoordinates(this.placeLocation, markets.placeLocation));
            } else {
                Log.i(TAG, "FALSE - MARKETS.equals(MARKETS): this.hashCode() == markets.hashCode() = " + (this.hashCode() == markets.hashCode()));
                return false;
            }
        } else return false;
    }
}
