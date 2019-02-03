package ua.biz.synergy.currencyrate.model.data.banks.privat;

import com.google.gson.annotations.SerializedName;

/**
 * mapping of JSON Object from JSON string for currency rate
 * of the "Private" bank into a data object
 */
public class CcyObject {
    private String ccy;
    @SerializedName("base_ccy")
    private String baseCcy;
    private Double buy;
    private Double sale;
    
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }
    
    public void setBaseCcy(String baseCcy) {
        this.baseCcy = baseCcy;
    }
    
    public void setBuy(Double buy) {
        this.buy = buy;
    }
    
    public void setSale(Double sale) {
        this.sale = sale;
        
    }
    
    public String getCcy() {
        return ccy;
    }
    
    public String getBaseCcy() {
        return baseCcy;
    }
    
    public Double getBuy() {
        return buy;
    }
    
    public Double getSale() {
        return sale;
    }
    
    public String toString(){
        return "ccy:" + ccy + ",base_ccy:" + baseCcy + ",buy:" + buy + ",sale:" + sale;
    }
}
