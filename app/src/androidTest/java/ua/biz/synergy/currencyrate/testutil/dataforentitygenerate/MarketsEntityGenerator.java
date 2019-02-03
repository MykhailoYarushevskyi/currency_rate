package ua.biz.synergy.currencyrate.testutil.dataforentitygenerate;

import android.util.Log;

import ua.biz.synergy.currencyrate.model.room.entity.Markets;
import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.entity.pojo.UrlContainer;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

public class MarketsEntityGenerator implements ConstantFieldForMarketsGenerate {
    static final String TAG = MarketsEntityGenerator.class.getSimpleName();
    
    /**
     * @param idNumber       - an order number of an {@code id} in generation time
     * @param sequenceNumber an order number of an entity  in the generation time
     * @return an entity of the {@code Markets} class for recording into a database
     */
    public static Markets generateMarketsEntityForRecord(int sequenceNumber, int idNumber) {
        // fields of the Markets entity
        String id;
        String name;
        String title;
        String urlSite;
        UrlContainer urlsRate = new UrlContainer();
        String city;
        String jsonString;
        NatureRate[] natureRates = new NatureRate[2];
        Coordinates placeLocation;
        
        id = String.valueOf(idNumber);
        name = NAME_HEADER + sequenceNumber;
        title = TITLE_HEADER + sequenceNumber;
        urlSite = URL_HEADER + sequenceNumber;
        String[] strings = new String[2];
        strings[0] = URLS_RATE_HEADER[0] + sequenceNumber;
        strings[1] = URLS_RATE_HEADER[1] + sequenceNumber;
        urlsRate.setRateUrls(strings);
        city = CITY_HEADER + sequenceNumber;
        jsonString = JSONSTRING_HEADER + sequenceNumber + JSONSTRING_TAIL;
        natureRates[0] = NatureRate.MARKET;
        natureRates[1] = NatureRate.MARKET_EXCHANGER;
        placeLocation = new Coordinates(LONGITUDE + sequenceNumber, LATITUDE + sequenceNumber);
    
    
        Log.i(TAG, "FOR RECORD: " + " id= " + id + "; name= " + name
                + "; title= " + title + "; url= " + urlSite
                + "; city= " + city + "; jsonString= " + jsonString
                + "; natureRates= " + natureRates.toString()
                + "; placeLocation= " + placeLocation.longitude + " ; " + placeLocation.latitude);
        
        return new Markets(id, name, title, urlSite, urlsRate, city, jsonString, natureRates, placeLocation);
    }
}
