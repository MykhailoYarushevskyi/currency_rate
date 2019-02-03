package ua.biz.synergy.currencyrate.testing.methods;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ua.biz.synergy.currencyrate.model.room.entity.pojo.UrlContainer;
import ua.biz.synergy.currencyrate.model.room.typetransforming.TypeTransform;

@RunWith(AndroidJUnit4.class)
public class METHODInstrumentedTest {
    
    private final String TAG = METHODInstrumentedTest.class.getSimpleName();
    private final String[] URLS_RATE_ARRAY = {"https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5",
            "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11"};
    
    @Before
    public void setUp(){
    
    }
    
    @After
    public void endUp(){
    
    }
    
    @Test
    public void base(){
        typeTransformTests();

    }

    private void typeTransformTests(){
        TypeTransform typeTransform = new TypeTransform();
        UrlContainer urlContainer = new UrlContainer();
        urlContainer.setRateUrls(URLS_RATE_ARRAY);
        Log.i(TAG, "urlContainer = " + urlContainer.getRateUrls()[0] + "\n" + urlContainer.getRateUrls()[1]);
        String jsonUrlContainer = typeTransform.fromUrlContainer(urlContainer);
        Log.i(TAG, "String jsonUrlContainer = " + jsonUrlContainer);
        UrlContainer urlContainerActual = typeTransform.toUrlContainer(jsonUrlContainer);
        Log.i(TAG, "urlContainerActual = " + urlContainerActual.getRateUrls()[0] + "\n" + urlContainerActual.getRateUrls()[1]);
        assert (urlContainer.equals(urlContainerActual));
    }
}
