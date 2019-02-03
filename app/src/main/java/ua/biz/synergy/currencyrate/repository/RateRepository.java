package ua.biz.synergy.currencyrate.repository;

import android.content.Context;

import ua.biz.synergy.currencyrate.model.network.webwork.WebOkHttpWork;
import ua.biz.synergy.currencyrate.model.room.database.RateDatabase;

public class RateRepository {
    private static volatile RateRepository INSTANCE = null;
    private final RateDatabase rateDatabase;
    private final WebOkHttpWork webOkHttpWork;
    private final Context context;
    
    private RateRepository(Context context, RateDatabase rateDatabase, WebOkHttpWork webOkHttpWork) {
        this.rateDatabase = rateDatabase;
        this.webOkHttpWork = webOkHttpWork;
        this.context = context.getApplicationContext();
        
    }
    
    /**
     * obtaining RateRepository object
     * @param context Application context
     * @param rateDatabase Database object
     * @param webOkHttpWork web service for fetch data
     * @return
     */
    public synchronized static RateRepository getInstance(Context context, RateDatabase rateDatabase, WebOkHttpWork webOkHttpWork) {
        if (INSTANCE == null){
            INSTANCE = new RateRepository(context, rateDatabase, webOkHttpWork);
        }
        return INSTANCE;
    }
    
    
}
