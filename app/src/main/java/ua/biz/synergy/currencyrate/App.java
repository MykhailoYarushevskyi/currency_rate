package ua.biz.synergy.currencyrate;

import android.app.Application;
import android.content.Context;

import okhttp3.OkHttpClient;
import ua.biz.synergy.currencyrate.model.room.database.CatalogsDatabase;
import ua.biz.synergy.currencyrate.repository.RateRepository;
import ua.biz.synergy.currencyrate.model.network.webwork.WebOkHttpWork;
import ua.biz.synergy.currencyrate.model.room.database.RateDatabase;

public class App extends Application {
    private Context context = this;
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    /**
     * for test goal only
     * @param context
     */
    public App(Context context){
        this.context = context;
    }
    public App(){super();}

    public RateDatabase getRateDatabase() { return RateDatabase.getInstance(context);}
    public CatalogsDatabase getCatalogsDatabase(){return CatalogsDatabase.getInstance(context);}
    public WebOkHttpWork getWebHttpWork() {return WebOkHttpWork.getInstance(context, new OkHttpClient());}
    public RateRepository getRateRepository() {return RateRepository.getInstance(context, getRateDatabase(), getWebHttpWork());}
}
