package ua.biz.synergy.currencyrate.testutil.dataforentitygenerate;

import android.os.SystemClock;
import android.util.Log;

import java.util.Date;
import java.util.Random;

import ua.biz.synergy.currencyrate.model.room.entity.Markets;
import ua.biz.synergy.currencyrate.model.room.entity.MarketsRate;
import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

public class MarketsRateEntityGenerator {
    private static final String TAG = MarketsRateEntityGenerator.class.getSimpleName();
    private static final String JSONSTRING_HEADER = "[{\"id\":\"";
    private static final String JSONSTRING_TAIL =
            "\"},{\"ccy\":\"USD\",\"base_ccy\":\"UAH\",\"buy\":\"26.10000\",\"sale\":\"26.45503\"}," +
                    "{\"ccy\":\"EUR\",\"base_ccy\":\"UAH\",\"buy\":\"31.00000\",\"sale\":\"31.54574\"}," +
                    "{\"ccy\":\"RUR\",\"base_ccy\":\"UAH\",\"buy\":\"0.44500\",\"sale\":\"0.47001\"}," +
                    "{\"ccy\":\"BTC\",\"base_ccy\":\"USD\",\"buy\":\"3387.4329\",\"sale\":\"3744.0047\"}]";
    private static final double LONGITUDE = -120.0040;
    private static final double LATITUDE = 39.4000;
    
//    privat static ArrayList<String> listID = new ArrayList<>();
//    privat static List<Date> listDATE = new ArrayList<>();
//    privat static List<NatureRate> listNATURERATE = new ArrayList<>();
//    privat static final List<String> listJSONRATE = new ArrayList<>();
//    privat static final List<Coordinates> listUSERLOCATION = new ArrayList<>();
//    privat static List<String> listMARKETID = new ArrayList<>();
 
    /**
     *
     * @param idNumber - an order number of an {@code id} in generation time
     * @param sequenceNumber- an order number of an entity  in the generation time
     * @return an entity of the {@code MarketsRate} class for recording into a database
     */
    public static MarketsRate generateMarketsRateEntityForRecord(int sequenceNumber, int idNumber) {
        //entity fields
        String id;
        Date date;
        NatureRate natureRate;
        String jsonRate;
        Coordinates userLocation = null;
        String marketId;
        
        id = String.valueOf(idNumber);
        date = new Date(System.currentTimeMillis());
        SystemClock.sleep(1500);
        natureRate = sequenceNumber % 2 != 0 ? NatureRate.MARKET : NatureRate.MARKET_EXCHANGER;
        jsonRate = JSONSTRING_HEADER + id + JSONSTRING_TAIL;
        userLocation = new Coordinates(LONGITUDE, LATITUDE);
//        userLocation = new Coordinates(new Location("").getLatitude(), new Location("").getLongitude());
        marketId = getMarketId(Markets.class);
        
/*        while (listID.size() > sequenceNumber) {
            int index = listID.size() - 1;
            listID.remove(index);
            listDATE.remove(index);
            listNATURERATE.remove(index);
            listJSONRATE.remove(index);
            listUSERLOCATION.remove(index);
            listMARKETID.remove(index);
        }
        listID.add(sequenceNumber, id);
        listDATE.add(sequenceNumber, date);
        listNATURERATE.add(sequenceNumber, natureRate);
        listJSONRATE.add(sequenceNumber, jsonRate);
        listUSERLOCATION.add(sequenceNumber, userLocation);
        listMARKETID.add(sequenceNumber, marketId);

        if (sequenceNumber > 0) {
            Log.i(TAG, "FROM LIST[" + (sequenceNumber-1) + "]: " + "id= " + listID.get(sequenceNumber-1)
                    + "; date= " + listDATE.get(sequenceNumber-1).toString()
                    + "; natureRate= " + listNATURERATE.get(sequenceNumber-1)
                    + "; jsonRate= " + listJSONRATE.get(sequenceNumber-1)
                    + "; userLocation= " + listUSERLOCATION.get(sequenceNumber-1).toString()
                    + "; marketId= " + listMARKETID.get(sequenceNumber - 1));
        }
        Log.i(TAG, "FROM LIST[" + (sequenceNumber-1) + "]: " + "id= " + listID.get(sequenceNumber)
                + "; date= " + listDATE.get(sequenceNumber).toString()
                + "; natureRate= " + listNATURERATE.get(sequenceNumber)
                + "; jsonRate= " + listJSONRATE.get(sequenceNumber)
                + "; userLocation= " + listUSERLOCATION.get(sequenceNumber).toString()
                + "; marketId= " + listMARKETID.get(sequenceNumber));
  */
        Log.i(TAG, "FOR RECORD: " + "id= " + id + "; date= " + date.toString()
                + "; natureRate= " + natureRate
                + "; userLocation= " + userLocation.toString()
                + "; marketId= " + marketId);
        return new MarketsRate(id, date, natureRate, jsonRate, userLocation, marketId);
    }
    
    /**
     * @param sequenceNumber
     * @return an entity of the MarketsRate class for comparison with data that loaded from a database
     */
/*    public static MarketsRate generateMarketsRateEntityForComparison(int sequenceNumber) {
        //entity fields
        String id;
        Date date;
        NatureRate natureRate;
        String jsonRate;
        Coordinates userLocation = null;
        String marketId;
        
        id = listID.get(sequenceNumber);
        date = listDATE.get(sequenceNumber);
        natureRate = listNATURERATE.get(sequenceNumber);
        jsonRate = listJSONRATE.get(sequenceNumber);
        userLocation = listUSERLOCATION.get(sequenceNumber);
        marketId = listMARKETID.get(sequenceNumber);
        
        Log.i(TAG, "FOR COMPARISON: " + "id= " + id + "; date= " + date.toString()
                + "; natureRate= " + natureRate.toString()
                + "; userLocation= " + userLocation.toString()
                + "; marketId= " + marketId);
        
        return new MarketsRate(id, date, natureRate, jsonRate, userLocation, marketId);
    }*/
    
    /**
     * calculating index of an array of classes of entities for given value of the class
     *
     * @param classEntity
     * @return index value >=0 or =-1 if classEntity not found
     */
    private static int getIndexForClassEntity(Class classEntity) {
        int ind = -1;
        for (int index = 0; index < ArraysEntityClassAndDataForGenerate.ENTITIES_CLASS.length; index++) {
            if (ArraysEntityClassAndDataForGenerate.ENTITIES_CLASS[index] == classEntity) {
                ind = index;
                break;
            }
        }
        return ind;
    }
    
    private static String getMarketId(Class classEntity) {
        int ind = getIndexForClassEntity(classEntity);
        String result = String.valueOf(new Random().nextInt(ArraysEntityClassAndDataForGenerate.NUMBER_ENTITY[ind]));
//        Log.i(TAG, "String getMarketId(Class classEntity) = " + result);
        return result;
    }
}

