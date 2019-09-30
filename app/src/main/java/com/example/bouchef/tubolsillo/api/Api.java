package com.example.bouchef.tubolsillo.api;


import android.content.Context;

public class Api {

    public static APIService getAPIService(Context context) {
        String url = "";

        url="http://104.197.165.114/api/v1/"; //facu cloud

        return RetrofitClient.getClient(url).create(APIService.class);
        /*if(ApplicationGlobal.getInstance().isProd())
            return RetrofitClient.getClient(BASE_URL_PROD).create(APIService.class);
        else
            return RetrofitClient.getClient(BASE_URL_CERT).create(APIService.class);*/

    }

}