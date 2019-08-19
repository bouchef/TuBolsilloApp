package com.example.bouchef.tubolsillo.generics;


import android.app.Activity;
import android.content.Intent;

public class Utils {
    public static void newActivity(Activity activity, Class aClass) {
        activity.startActivity(new Intent(activity, aClass));
        activity.finish();
    }

}