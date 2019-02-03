package ua.biz.synergy.currencyrate.testing.network;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import ua.biz.synergy.currencyrate.App;
import ua.biz.synergy.currencyrate.model.data.banks.privat.CcyObject;
import ua.biz.synergy.currencyrate.model.data.banks.privat.PvCard;
import ua.biz.synergy.currencyrate.model.data.banks.privat.PvCash;
import ua.biz.synergy.currencyrate.model.network.webwork.WebOkHttpWork;

@RunWith(JUnit4.class)
public class WebOkHttpWorkTests {
    private final String TAG = WebOkHttpWorkTests.class.getSimpleName();
    private final String JSON_OBJECT = "{\"ccy\":\"USD\",\"base_ccy\":\"UAH\",\"buy\":\"27.90000\",\"sale\":\"28.15000\"}";
    private final String JSON_ARRAY_OBJECTS = "[{\"ccy\":\"USD\",\"base_ccy\":\"UAH\",\"buy\":\"28.05000\",\"sale\":\"28.40909\"},{\"ccy\":\"EUR\",\"base_ccy\":\"UAH\",\"buy\":\"32.55000\",\"sale\":\"33.22259\"},{\"ccy\":\"RUR\",\"base_ccy\":\"UAH\",\"buy\":\"0.39700\",\"sale\":\"0.42201\"},{\"ccy\":\"BTC\",\"base_ccy\":\"USD\",\"buy\":\"6693.6561\",\"sale\":\"7398.2515\"}]";
    private final String JSON_OBJECTS = "{ccyObjects:[{\"ccy\":\"USD\",\"base_ccy\":\"UAH\",\"buy\":\"28.05000\",\"sale\":\"28.40909\"},{\"ccy\":\"EUR\",\"base_ccy\":\"UAH\",\"buy\":\"32.55000\",\"sale\":\"33.22259\"},{\"ccy\":\"RUR\",\"base_ccy\":\"UAH\",\"buy\":\"0.39700\",\"sale\":\"0.42201\"},{\"ccy\":\"BTC\",\"base_ccy\":\"USD\",\"buy\":\"6693.6561\",\"sale\":\"7398.2515\"}]}";
    
    private URL urlTest = null;
    private WebOkHttpWork webOkHttpWork;
    //    privat String URL_TEST = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date=20150823&json";
//    privat String URL_TEST = "https://bank.gov.ua/NBU_BankInfo/get_data_branch?typ=0&json";
//    privat String URL_TEST = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private String URL_TEST_PRIVAT_CARD_RATE = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
    private String URL_TEST_PRIVAT_CASH_RATE = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=5";
    
    
    @Before
    public void setUp() {
        
        webOkHttpWork = new App(InstrumentationRegistry.getTargetContext()).getWebHttpWork();
        assert (webOkHttpWork != null);
        Log.i(TAG, "webOkHttpWork = " + webOkHttpWork);
    }
    
    @After
    public void endUp() {
        webOkHttpWork = null;
        
    }
    
    @Test
    public void base() {
        convertingPvTests();
//        obtainingHeadersTests();
    }
    
    private void convertingPvTests(){
        PvCard pvCard = convertCardRate(URL_TEST_PRIVAT_CARD_RATE);
        assert (pvCard != null);
        PvCash pvCash = convertCashRate(URL_TEST_PRIVAT_CASH_RATE);
        assert (pvCash != null);
    }
    
    private void obtainingHeadersTests(){
        URL url = getURL(URL_TEST_PRIVAT_CARD_RATE);
        Response response = obtainResponse(url);
        Log.i(TAG, "======== HEADERS:\n" );
        Map<String, List<String>> headersMap = response.headers().toMultimap();
        Set<String> stringSet = headersMap.keySet();
        Iterator iteratorSet = stringSet.iterator();
        while (iteratorSet.hasNext()){
            String s = (String) iteratorSet.next();
            Log.i(TAG, s + " = " + headersMap.get(s) + " ; size of List = " + headersMap.get(s).size());
        }
    }
    
    private PvCard convertCardRate(String urlString) {
        URL urlForFetching = getURL(urlString);
        if (null == urlForFetching) {
            return null;
        }
        StringBuffer data = fetchJson(urlForFetching);
        return convertJsonStringToJavaObjectsContainer(new StringReader(data.toString()), new PvCard());
    }
    
    private PvCash convertCashRate(String urlString) {
        URL urlForFetching = getURL(urlString);
        if (null == urlForFetching) {
            return null;
        }
        StringBuffer data = fetchJson(urlForFetching);
        return convertJsonStringToJavaObjectsContainer(new StringReader(data.toString()), new PvCash());
    }
    
    private URL getURL(String urlString) {
        URL url = null;
        try {
            url = new URI(urlString).toURL();
        } catch (Exception e) {
            Log.i(TAG, "ERROR: " + e.toString());
        }
        return url;
    }
    
    private StringBuffer fetchJson(URL url) {
        StringBuffer data = webOkHttpWork.getDataStream(url)
                .subscribeOn(Schedulers.io()).blockingFirst();
        Log.i(TAG, "Observer: length = " + data.length() + ";  result = " + data.toString());
        return data;
    }
    
    private Response obtainResponse(URL url){
        return webOkHttpWork.getResponse(url).subscribeOn(Schedulers.io()).blockingFirst();
    }
    
    /**
     * convert a JSON string to an object of the PvCard class or PvCash class directly
     * pointed a type of the needing class.
     * @param stringReader
     * @param javaObjectsContainer
     * @param <T> object that must be deserialised
     * @return object that was deserialized
     */
    private <T> T convertJsonStringToJavaObjectsContainer(StringReader stringReader, T javaObjectsContainer) {
        JsonReader reader = new JsonReader(stringReader);
        if (javaObjectsContainer instanceof PvCard) {
            PvCard pvCard;
            pvCard = new Gson().fromJson(reader, PvCard.class);
            Log.i(TAG, "====== convertJsonStringToJavaObjectsContainer() PvCard: ");
            for (CcyObject ccy : pvCard.getCcyObjects()) {
                Log.i(TAG, "ccy = " + ccy);
            }
            return (T) pvCard;
        } else if (javaObjectsContainer instanceof PvCash) {
            PvCash pvCash;
            pvCash = new Gson().fromJson(reader, PvCash.class);
            Log.i(TAG, "====== convertJsonStringToJavaObjectsContainer() PvCash: ");
            for (CcyObject ccy : pvCash.getCcyObjects()) {
                Log.i(TAG, "ccy = " + ccy);
            }
            return (T) pvCash;
        }
        return null;
    }
    
    /**
     * using a TypeToken, will convert a JSON string to a List<> and put it in some object
     *
     * @param stringReader         String reader that contains Json string for converting
     * @param javaObjectsContainer some object that contains the List<>
     * @param <T>
     */
    private <T> void convertJsonArrayToListOfObjects(StringReader stringReader, T javaObjectsContainer) {
        Type listCcyObjectType = new TypeToken<List<CcyObject>>() {
        }.getType();
        List<CcyObject> listCcyObject = new Gson().fromJson(stringReader, listCcyObjectType);
        if (javaObjectsContainer instanceof PvCard) {
            PvCard pvCard = (PvCard) javaObjectsContainer;
            pvCard.setCcyObjects(listCcyObject);
            Log.i(TAG, "====== convertJsonArrayToListOfObjects(): ");
            for (CcyObject ccy : pvCard.getCcyObjects()) {
                Log.i(TAG, "ccy = " + ccy);
            }
        }
        
    }
}
