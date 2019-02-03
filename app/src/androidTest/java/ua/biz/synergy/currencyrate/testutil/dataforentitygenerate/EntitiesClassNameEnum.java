package ua.biz.synergy.currencyrate.testutil.dataforentitygenerate;

public enum EntitiesClassNameEnum {
    BANKS(0), BANKS_RATE(1), MARKETS(2), MARKETS_RATE(3);
    public final int type;
    EntitiesClassNameEnum(int type){
        this.type = type;
    }
    public int fromEnum(){
        return type;
    }
}
