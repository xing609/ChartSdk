package com.star.app.api;

import com.gjmetal.star.net.XApi;

import com.star.app.manager.GjOptions;

public class Api {
    private static volatile MarketService marketService;
    public static MarketService getMarketService() {
            if (marketService == null) {
                synchronized (Api.class) {
                    if (marketService == null) {
                        marketService = XApi.getInstance().getRetrofit(GjOptions.getInstance().getmReqUrl()+"/mapi/", true).create(MarketService.class);
                    }
                }
            }

        return marketService;
    }
}
