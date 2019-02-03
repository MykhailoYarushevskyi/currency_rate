package ua.biz.synergy.currencyrate.testutil.dataforentitygenerate;

import android.os.SystemClock;
import android.util.Log;

import java.util.Date;
import java.util.Random;

import ua.biz.synergy.currencyrate.model.room.entity.Banks;
import ua.biz.synergy.currencyrate.model.room.entity.BanksRate;
import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

public class BanksRateEntityGenerator {
    private static final String TAG = BanksRateEntityGenerator.class.getSimpleName();
    private static final String JSONSTRING_HEADER = "[{\"id\":\"";
    private static final String JSONSTRING_TAIL =
            "\"},{\"ccy\":\"USD\",\"base_ccy\":\"UAH\",\"buy\":\"26.10000\",\"sale\":\"26.45503\"}," +
                    "{\"ccy\":\"EUR\",\"base_ccy\":\"UAH\",\"buy\":\"31.00000\",\"sale\":\"31.54574\"}," +
                    "{\"ccy\":\"RUR\",\"base_ccy\":\"UAH\",\"buy\":\"0.44500\",\"sale\":\"0.47001\"}," +
                    "{\"ccy\":\"BTC\",\"base_ccy\":\"USD\",\"buy\":\"3387.4329\",\"sale\":\"3744.0047\"}]";
    private static final double LONGITUDE = -122.0840;
    private static final double LATITUDE = 37.4220;

    /**
     *
     * @param idNumber - an order number of an {@code id} in generation time
     * @param sequenceNumber - an order number of an entity  in the generation time
     * @return an entity of the {@code BanksRate} class for recording into a database
     */
    public static BanksRate generateBanksRateEntityForRecord(int sequenceNumber, int idNumber) {
        //entity fields
        String id;
        Date date;
        NatureRate natureRate;
        String jsonRate;
        Coordinates userLocation = null;
        String bankId;
        
        id = String.valueOf(idNumber);
        date = new Date(System.currentTimeMillis());
        SystemClock.sleep(1000);
        natureRate = sequenceNumber % 2 != 0 ? NatureRate.BANK_CASH : NatureRate.BANK_CARD;
        jsonRate = JSONSTRING_HEADER + id + JSONSTRING_TAIL;
        userLocation = new Coordinates(LONGITUDE, LATITUDE);
//        userLocation = new Coordinates(new Location("").getLatitude(), new Location("").getLongitude());
        bankId = getBankId(Banks.class);
 
        Log.i(TAG, "FOR RECORD: " + "id= " + id + "; date= " + date.toString()
                + "; natureRate= " + natureRate
                + "; userLocation= " + userLocation.toString()
                + "; bankId= " + bankId);
        return new BanksRate(id, date, natureRate, jsonRate, userLocation, bankId);
    }
    
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
    
    private static String getBankId(Class classEntity) {
        int ind = getIndexForClassEntity(classEntity);
        return String.valueOf(new Random().nextInt(ArraysEntityClassAndDataForGenerate.NUMBER_ENTITY[ind]));
    }
}
