package com.example.bouchef.tubolsillo.generics;

import android.content.Context;

import com.example.bouchef.tubolsillo.R;

import dmax.dialog.SpotsDialog;

public class Progress {




    public static android.app.AlertDialog getProgressBar(Context context, String message){
        return new SpotsDialog.Builder().setContext(context).setTheme(R.style.CustomDialogLoading).setCancelable(false).setMessage(message).build();
    }




}
