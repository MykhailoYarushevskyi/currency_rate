package ua.biz.synergy.currencyrate.testutil.dataforentitygenerate;

public interface ConstantFieldForBanksGenerate {
    
    static final String NAME_HEADER = "Bank number ";
    static final String TITLE_HEADER = "Title of the bank number ";
    static final String URL_HEADER = "https://www.bank.number.";
    static final String[] URLS_RATE_HEADER = {"https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5&",
            "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11&"};
    static final String CITY_HEADER = "City of the bank number ";
    static final String JSONSTRING_HEADER = "[{\"id\":\"";
    static final String JSONSTRING_TAIL = "\"},{\"USD\":\"USD\"},{\"EUR\":\"EUR\"},{\"UAH\":\"UAH\"}]";
    static final double LONGITUDE = -100.0500;
    static final double LATITUDE = 40.4020;
}
