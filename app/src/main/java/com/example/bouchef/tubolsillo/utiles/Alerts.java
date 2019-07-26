package com.example.bouchef.tubolsillo.utiles;

import android.content.Context;
import android.widget.Toast;

public class Alerts {

    public static void newToast(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    public static void newToastLarge(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }
/*

    //public static MaterialDialog getInputDialog(Context context, String titulo, String contenido, int icon, MaterialDialog.SingleButtonCallback callbackAceptar){
    public static MaterialDialog getInputDialog(Context context, String titulo, String contenido,String defaultText, MaterialDialog.SingleButtonCallback callbackAceptar){
        return new MaterialDialog.Builder(context)
                .title(titulo)
                .content(contenido)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE )
                .positiveText("Aceptar")
                .negativeText("Cancelar")
                //.iconRes(icon)
                .onPositive(callbackAceptar)
                .input("Ingrese un texto", defaultText, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                    }
                }).show();
    }



    public static void ConfirmSiNo(Context context, int icon, String titulo, String mensaje, DialogInterface.OnClickListener callbackSi, final DialogInterface.OnClickListener callBackNo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(titulo);

        if(!mensaje.equals(""))
            builder.setMessage(mensaje);

        builder.setIcon(icon);
        builder.setPositiveButton(android.R.string.yes,callbackSi);
        builder.setNegativeButton(android.R.string.cancel, callBackNo);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void ConfirmSiNoCancel(Context context, int icon, String titulo, String mensaje, DialogInterface.OnClickListener callbackSi, final DialogInterface.OnClickListener callBackNo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //builder.setCancelable(false);
        builder.setTitle(titulo);

        if(!mensaje.equals(""))
            builder.setMessage(mensaje);

        builder.setIcon(icon);
        builder.setPositiveButton("SI",callbackSi);
        builder.setNegativeButton("NO", callBackNo);
        builder.setNeutralButton("CANCELAR", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void ConfirmEliminar(Context context, String titulo, String mensaje, DialogInterface.OnClickListener callbackSi, final DialogInterface.OnClickListener callBackNo){
        Alerts.ConfirmSiNo(
                context,
                android.R.drawable.ic_delete,
                titulo,
                mensaje,
                callbackSi,
                callBackNo
        );
    }

    public static void ConfirmSincronizar(Context context, int icon, String titulo, String mensaje, DialogInterface.OnClickListener callbackSi, final DialogInterface.OnClickListener callBackNo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setIcon(icon);
        builder.setPositiveButton(android.R.string.yes,callbackSi);
        builder.setNegativeButton("Mas tarde", callBackNo);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void AlertOk(Context context, String titulo, String descripcion, DialogInterface.OnClickListener onClickListener) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(descripcion);
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK", onClickListener);

        alertDialog.show();

    }
*/

}
