package ua.biz.synergy.currencyrate.model.room.typetransforming;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import java.util.Date;

import ua.biz.synergy.currencyrate.model.room.entity.pojo.UrlContainer;
import ua.biz.synergy.currencyrate.model.room.enums.NatureRate;

public class TypeTransform {

    @TypeConverter
    public static Long toTimestamp(Date date){
        if (date != null) {
            return date.getTime();
        }
        return null;
    }
    
    @TypeConverter
    public static Date toDate(Long timestamp){
        if(timestamp != null){
            return new Date(timestamp);
        }
        return null;
    }
    
    @TypeConverter
    public Integer fromNatureRate(NatureRate natureRate) {
        return natureRate.type;
    }
    
    @TypeConverter
    public NatureRate toNatureRate(int type) {
        for (NatureRate natureRate : NatureRate.values()) {
            if (natureRate.type == type) {
                return natureRate;
            }
        }
        return null;
    }
    
    @TypeConverter
    public String fromNatureRateArray(NatureRate[] natureRates){
        String result = "";
        for (int index = 0; index < natureRates.length; index++) {
            result += String.valueOf(fromNatureRate(natureRates[index]));
            if(index < (natureRates.length - 1)){
                result += ",";
            }
        }
        return result;
    }
    @TypeConverter
    public NatureRate[] toNatureRateArray(String stringNR){
        String[] splitStringNR = stringNR.split(",");
        NatureRate[] result = new NatureRate[splitStringNR.length];
        for(int index = 0; index < splitStringNR.length; index++){
            result[index] = toNatureRate(Integer.valueOf(splitStringNR[index]));
        }
        return result;
    }
    
    @TypeConverter
    public String fromUrlContainer(UrlContainer urlContainer){
        return new Gson().toJson(urlContainer);
    }
    @TypeConverter
    public UrlContainer toUrlContainer(String jsonUrlContainer){
        return new Gson().fromJson(jsonUrlContainer, UrlContainer.class);
    }
}
