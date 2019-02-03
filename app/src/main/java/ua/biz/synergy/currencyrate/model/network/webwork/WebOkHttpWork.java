package ua.biz.synergy.currencyrate.model.network.webwork;

import android.content.Context;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebOkHttpWork {
    private final String TAG = WebOkHttpWork.class.getSimpleName();
    private Context context;
    private OkHttpClient okHttpClient;
    
    private WebOkHttpWork(Context context, OkHttpClient okHttpClient) {
        this.context = context.getApplicationContext();
        this.okHttpClient = okHttpClient;
    }
    
    /**
     * obtaining an instance of the WebOkHttpWork
     *
     * @param context      Application context
     * @param okHttpClient
     * @return
     */
    public synchronized static WebOkHttpWork getInstance(Context context, OkHttpClient okHttpClient) {
        return new WebOkHttpWork(context, okHttpClient);
    }
    
    /**
     * obtaining a fetched data stream Observable<String>
     * @param url
     * @return
     */
    public Observable<StringBuffer> getDataStream(URL url) {
        return fetchData(url)
                .map(WebOkHttpWork:: getFetchedData);
    }
    
    /**
     * get Observable<ResponseData> that contains a response on a request
     *
     * @param url URL for a request fetching data
     * @return Observable<ResponseData>
     */
    public Observable<ResponseData> fetchData(URL url) {
        Request request = new Request.Builder().url(url).build();
        return Observable.fromCallable(() -> new ResponseData(okHttpClient.newCall(request).execute()));
    }
    
    /**
     * get Observable<Response>
     * @param url
     * @return Observable, that emitted Response object
     */
    public Observable<Response> getResponse(URL url){
        return fetchData(url).map(responseData -> responseData.response);
    }
    
    /**
     * get a Json String from a fetched data
     *
     * @param responseData
     * @return Json String
     * @throws IOException
     */
    public static StringBuffer getFetchedData(ResponseData responseData) throws IOException {
        return new StringBuffer(responseData.response.body().string());
    }

    
    /**
     * the data class contains in itself a result of a request
     */
    private static class ResponseData {
        final Response response;
        private ResponseData(Response response) {
            this.response = response;
        }
    }
    
    /**
     *  get for a field "name" the value
     * @param responseData
     * @param nameHeader
     * @return
     * @throws IOException
     */
    public static String getResponseHeaderValue(ResponseData responseData, String nameHeader) throws IOException{
        return responseData.response.headers().get("nameHeaders");
    }
    
    /**
     * get Headers of the response
     * @param responseData
     * @return
     * @throws IOException
     */
    public static Map<String, List<String>> getResponseHeader(ResponseData responseData) throws IOException{
        return responseData.response.headers().toMultimap();
    }
}
