package com.example.bouchef.tubolsillo.api;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    //private static String username = null;
    //private static String password = null;


    public static void clean(){

        retrofit = null;

    }


    public static Retrofit getClient(String baseUrl) {
        /*
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }*/
        if (retrofit==null ) {
/*
            if(u==null){
                u = Db.getUserLogin();
            }
*/

            OkHttpClient client = new OkHttpClient.Builder()
                    //.authenticator(new NTLMAuthenticator("hpatoher", "Agos2018"))
                    //.authenticator(new NTLMAuthenticator(u.getUsername(), u.getPassword()))
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)//NO CONVIENE QUE REINITENTE PORQUE CREO QUE BLOQUEA EL USER
                    .cache(null)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
