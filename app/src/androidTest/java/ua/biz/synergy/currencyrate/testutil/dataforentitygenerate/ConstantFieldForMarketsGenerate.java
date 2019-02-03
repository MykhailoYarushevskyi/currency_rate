package ua.biz.synergy.currencyrate.testutil.dataforentitygenerate;

public interface ConstantFieldForMarketsGenerate {

    static final String NAME_HEADER = "Market number ";
    static final String TITLE_HEADER = "Title of the market number ";
    static final String URL_HEADER = "https://www.market.number.";
    static final String[] URLS_RATE_HEADER = {"https://api.privatmarket.ua/p24api/pubinfo?json&exchange&coursid=5&",
            "https://api.privatmarket.ua/p24api/pubinfo?exchange&json&coursid=11&"};
    static final String CITY_HEADER = "City of the market number ";
    static final String JSONSTRING_HEADER = "[{\"id\":\"";
    static final String JSONSTRING_TAIL = "\"},{\"USD\":\"USD\"},{\"EUR\":\"EUR\"},{\"UAH\":\"UAH\"}]";
    static final double LONGITUDE = -110.0500;
    static final double LATITUDE = 50.4020;
}
