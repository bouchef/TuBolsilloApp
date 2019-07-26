package com.example.bouchef.tubolsillo.generics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bouchef.tubolsillo.api.APIService;
import com.example.bouchef.tubolsillo.api.Api;
import com.example.bouchef.tubolsillo.utiles.Alerts;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class AppCompatCustomActivity extends AppCompatActivity {

    protected APIService api;
    protected ApplicationGlobal applicationGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationGlobal = ApplicationGlobal.getInstance();
        api = Api.getAPIService(getApplicationContext());

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher_1);
    }

    protected void logError(Exception e){
        e.printStackTrace();

        Alerts.newToastLarge(getApplicationContext(), e.getMessage());

        Writer writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        String s = writer.toString();
        //LogOk.log(getApplicationContext(), "GEN", s);
    }

    protected  void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
