package ua.biz.synergy.currencyrate.model.room.enums;

public enum NatureRate {
    NULL(0), BANK_CARD(1), BANK_CASH(2), MARKET(3), MARKET_EXCHANGER(4);
    //BANK_CARD, BANK_CASH, MARKET;
    
    public final int type;
    
    //NatureRate(){}
    NatureRate(int type) {
        this.type = type;
    }
    
    //@TypeConverter
    public Integer fromNatureRate(NatureRate natureRate) {
        return natureRate.type;
    }
    
    //@TypeConverter
    public NatureRate toNatureRate(int type) {
        for (NatureRate natureRate : NatureRate.values()) {
            if (natureRate.type == type) {
                return natureRate;
            }
        }
        return null;
    }
//    @TypeConverter
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
//    @TypeConverter
    public NatureRate[] toNatureRateArray(String stringNR){
        String[] splitStringNR = stringNR.split(",");
        NatureRate[] result = new NatureRate[splitStringNR.length];
        for(int index = 0; index < splitStringNR.length; index++){
            result[index] = toNatureRate(Integer.valueOf(splitStringNR[index]));
        }
        return result;
    }
}
