package com.example.bouchef.tubolsillo.api;


import android.content.Context;

public class Api {

    //public static final String BASE_URL = "http://www.tasawap.speedy.com.ar/sgito1/mobile/";
    //public static final String BASE_URL = "http://www.segobra.speedy.com.ar/sgito/api/";
    //public static final String BASE_URL_CERT = "http://www.segobra.speedy.com.ar/sgito-cert/api/";
    //public static final String BASE_URL_PROD = "http://www.segobra.speedy.com.ar/sgito-prod/api/";


    public static APIService getAPIService(Context context) {
        String url = "";
        //url = context.getResources().getString(R.string.CONFIG_APIURL);
        url = "https://appdineritoservice201906.azurewebsites.net/api/v1/";
        return RetrofitClient.getClient(url).create(APIService.class);
        /*if(ApplicationGlobal.getInstance().isProd())
            return RetrofitClient.getClient(BASE_URL_PROD).create(APIService.class);
        else
            return RetrofitClient.getClient(BASE_URL_CERT).create(APIService.class);*/

    }

}