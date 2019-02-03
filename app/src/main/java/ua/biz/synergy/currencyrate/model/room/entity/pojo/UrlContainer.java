package ua.biz.synergy.currencyrate.model.room.entity.pojo;

import java.util.Arrays;

/**
 * holding URLs for different types of currency rate
 */
public class UrlContainer {
    // the strings of a URL in the array follow in the same order that
    // natureRates's objects in NatureRate[], in any class, where
    // UrlContainer and NatureRate[] occur together.
    private String[] rateUrls;
    
    public String[] getRateUrls() {
        return rateUrls;
    }
    
    public void setRateUrls(String[] rateUrls) {
        this.rateUrls = rateUrls;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlContainer)) return false;
        UrlContainer that = (UrlContainer) o;
        return Arrays.equals(getRateUrls(), that.getRateUrls());
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(getRateUrls());
    }
}
