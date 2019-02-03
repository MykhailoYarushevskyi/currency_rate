package ua.biz.synergy.currencyrate.model.network.webwork;

import java.net.URL;

import ua.biz.synergy.currencyrate.model.network.api.BankRateService;

public class WebRetrofitWork {
    BankRateService bankRateService;
    URL baseUrl;
WebRetrofitWork(URL baseUrl){
    this.baseUrl = baseUrl;
}
}
