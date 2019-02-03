package ua.biz.synergy.currencyrate.testing.db;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ua.biz.synergy.currencyrate.App;
import ua.biz.synergy.currencyrate.model.room.dao.BanksDao;
import ua.biz.synergy.currencyrate.model.room.dao.BanksRateDao;
import ua.biz.synergy.currencyrate.model.room.dao.MarketsDao;
import ua.biz.synergy.currencyrate.model.room.dao.MarketsRateDao;
import ua.biz.synergy.currencyrate.model.room.database.RateDatabase;
import ua.biz.synergy.currencyrate.model.room.entity.Banks;
import ua.biz.synergy.currencyrate.model.room.entity.Markets;
import ua.biz.synergy.currencyrate.model.room.entity.pojo.Coordinates;
import ua.biz.synergy.currencyrate.model.room.entity.pojo.UrlContainer;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DBInstrumentedTest {
    private final String TAG = DBInstrumentedTest.class.getSimpleName();
    private RateDatabase db = null;
    private Context context;
    private MutableLiveData<Boolean> isDbCreated = new MutableLiveData<>();
    
    private BanksDao banksDao;
    private BanksRateDao banksRateDao;
    private MarketsDao marketsDao;
    private MarketsRateDao marketsRateDao;
    
    @Before
    public void setUp() {
//        db = RateDatabase.create(InstrumentationRegistry.getTargetContext(), true); // MemoryOnly
//        db = RateDatabase.getInstance(InstrumentationRegistry.getTargetContext());
//        db = new App().getRateDatabase(); // NOT WORK without CONTEXT
        db = new App(InstrumentationRegistry.getTargetContext()).getRateDatabase();
        Log.i(TAG, "db = " + db);
        //any operation with SQLite database, may be query,
        // for obtain the database file at the disk (result db.isOpen() is true)
        db.clearAllTables();
        
        isDbCreated = (MutableLiveData) db.getIsDatabaseCreated();
        isDbCreated.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {//assertTrue(aBoolean);
                Log.i(TAG, "onChange(): MutableLiveData<Boolean> isDbCreated = " + aBoolean);
            }
        });
        banksDao = db.getBanksDao();
        marketsDao = db.getMarketsDao();
    }
    
    @After
    public void endUp() {
        db.close();
        boolean result = db.deleteDB(InstrumentationRegistry.getTargetContext());
        Log.i(TAG, "result of the db.deleteDB(InstrumentationRegistry.getTargetContext()) = " + result);
        assertTrue(result);
    }
    
    @Test
    public void testIsDbCreated() {
        final String NAME_HEADER = "Market number ";
        final String TITLE_HEADER = "Title of the market number ";
        final String URL_HEADER = "https://www.market.number.";
        final String[] URLS_RATE_HEADER = {"https://api.privatmarket.ua/p24api/pubinfo?json&exchange&coursid=5&",
                "https://api.privatmarket.ua/p24api/pubinfo?exchange&json&coursid=11&"};
        final String CITY_HEADER = "City of the market number ";
        final String JSONSTRING_HEADER = "[{\"id\":\"";
        final String JSONSTRING_TAIL = "\"},{\"USD\":\"USD\"},{\"EUR\":\"EUR\"},{\"UAH\":\"UAH\"}]";
        final double LONGITUDE = -1100.500;
        final double LATITUDE = 120.400;
        
        Log.i(TAG, "======@Test: testIsDbCreated()");
        assertTrue(db != null);
//        assertTrue(db.isOpen()); //It is true if previously was performed an operation at the database and as a result database was initialized
        Log.i(TAG, "@Test: BEFORE db.updateIsDatabaseCreated()");
        db.updateIsDatabaseCreated(InstrumentationRegistry.getTargetContext());
    
   
        UrlContainer urlRates = new UrlContainer();
        urlRates.setRateUrls(URLS_RATE_HEADER);
        NatureRate[] natureRates = {NatureRate.MARKET, NatureRate.MARKET_EXCHANGER};
        Coordinates coordinates = new Coordinates(LONGITUDE, LATITUDE);
        Log.i(TAG, "============@Test: testInsertSelectMarkets()");
        Markets markets = new Markets("2", "Market 0", "Title  0", " https://www.market.number.0"
                , urlRates, "City 0", "[{\"id\":\"0\"},{\"USD\":\"USD\"},{\"EUR\":\"EUR\"},{\"UAH\":\"UAH\"}]"
                , natureRates, coordinates);
        marketsDao.insert(markets);
        List<Markets> marketsList = marketsDao.selectAllMarkets();
        Markets marketsObtainedById = marketsDao.findOneMarketsById("2");
        Log.i(TAG, "=====marketsObtainedById.placeLocation = " + marketsObtainedById.placeLocation);
        Log.i(TAG, "=== Size = " + marketsList.size() + "; marketsList.get(0): " + marketsList.get(0).toString());
        List<Banks> banksList = banksDao.selectAllBanks();
//        Log.i(TAG, "=== Size = " + banksList.size() + "; banksList.get(0): " + banksList.get(0).toString());
    }
}
