//*
//package com.ergorenova.tasa.sgitomobilegestion.generics;

//import android.Manifest;
//import android.app.Activity;
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.net.Uri;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.NotificationCompat;
//import android.webkit.MimeTypeMap;
//import android.widget.ImageView;
//import android.widget.Spinner;

//import com.ergorenova.tasa.sgitomobilegestion.R;
//import com.ergorenova.tasa.sgitomobilegestion.adapters.SpinnerArrayAdapter;
//import com.ergorenova.tasa.sgitomobilegestion.login.PreLoginActivity;
//import com.ergorenova.tasa.sgitomobilegestion.models.CodigoDescripcion;
//import com.squareup.picasso.Picasso;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.List;

//import io.nlopez.smartlocation.SmartLocation;
/*
public class Utils {
    public static void newActivity(Activity activity, Class aClass) {
        activity.startActivity(new Intent(activity, aClass));
    }

    public static boolean isConnected(Context context) {
        return true;//TODO: chequear en cell de Miss
        /*
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        return isConnected;

    }

    public static float sizeInMB(File f) {
        long fileSizeInBytes = f.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        float fileSizeInKB = fileSizeInBytes / 1024.0f;
        //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        float fileSizeInMB = fileSizeInKB / 1024.0f;

        String s = String.format("%.2f", fileSizeInMB ).replace(",", ".");
        return Float.parseFloat(s);
    }


    public static String sizeInMBString(File f) {
        long fileSizeInBytes = f.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        float fileSizeInKB = fileSizeInBytes / 1024.0f;
        //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        float fileSizeInMB = fileSizeInKB / 1024.0f;

        String s = String.format("%.2f", fileSizeInMB ).replace(",", ".") + "MB";
        return s;
    }
    public static float roundFloatTo2Decimals(float f){
        float d = f;
        String s = String.format("%.2f", d).replace(",", ".");
        return Float.parseFloat(s);
    }

    public static Location getGPSPosition(Context context) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);



            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            long GPSLocationTime = 0;
            if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

            long NetLocationTime = 0;

            if (null != locationNet) {
                NetLocationTime = locationNet.getTime();
            }

            if ( 0 < GPSLocationTime - NetLocationTime ) {
                return locationGPS;
            }
            else {
                return locationNet;
            }


    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    public static SpinnerArrayAdapter populateSpinnerAdapter(Context context, Spinner spinner, List<CodigoDescripcion> lista ){
        SpinnerArrayAdapter adapter =
                new SpinnerArrayAdapter(
                        context,
                        R.layout.spinner_item_ok ,
                        lista);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return adapter;
    }
    public static String getApplicationPath(final Context context){
        return "/data/data/" + context.getPackageName() ;
    }
    public static String getApplicationPathSitioAdjuntos(final Context context, String carpeta, String subcarpeta ){
        //String path = Utils.getApplicationPath(context) + "/" + sitio + "/adjuntos/preguntas/" + pregunta.getIdpre() ;
        String path = Utils.getApplicationPath(context) + "/" + carpeta + "/adjuntos/" + subcarpeta;
        new File(path).mkdirs();
        return path;
    }

    private static String _fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }
    public static String getMimeFromPath(String path){
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(_fileExt(path));
    }
    public static Intent getIntentForOpenFile(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String mimeType = Utils.getMimeFromPath(file.getAbsolutePath());
        intent.setDataAndType(uri,mimeType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
    public static Notification getNotificationForOpenFile(final Context context, File file){
        Intent intent = Utils.getIntentForOpenFile(file);
        //////////////////////////////////////////////////////////
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context,
                Utils.getApplicationName(context))
                .setSmallIcon(R.drawable.ic_logo_1)
                .setContentTitle(Utils.getApplicationName(context))
                .setContentText(file.getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setOngoing(false)
                .setAutoCancel(true);

        return mBuilder.build();
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    public static String guardarAdjuntoLocalmente(Context context, String carpeta, String nombre, InputStream is, String subcarpeta) {
        try {

            //String directoryPath ="/data/data/" + getPackageName() + "/" + _sitio.getSitio() + "/adjuntos";
            String directoryPath;
            directoryPath  =Utils.getApplicationPathSitioAdjuntos(context, carpeta, subcarpeta) ;


            //File destinationFile = new File(directoryPath + "/" + adjuntoBase.getNombre());
            File destinationFile = new File(directoryPath + "/" + nombre    );

            //InputStream is = null;
            OutputStream os = null;

            try {
                //is = body.byteStream();
                os = new FileOutputStream(destinationFile);

                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                while ((count = is.read(data)) != -1) {
                    os.write(data, 0, count);
                    progress +=count;
                }

                os.flush();

                return destinationFile.getPath();
            } catch (IOException e) {
                e.printStackTrace();
                return destinationFile.getAbsolutePath();
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getVersion(Context context){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo( context.getPackageName(), 0);
            String version = pInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static void loadImagen(File f, ImageView imagen) {
        Picasso.get().load(f)
                .fit().into(imagen);
    }
    public static boolean fileMayorAXMB(File f, int mb) {
        long fileSizeInBytes = f.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        float fileSizeInKB = fileSizeInBytes / 1024.0f;
        //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        float fileSizeInMB = fileSizeInKB / 1024.0f;
        return fileSizeInMB>mb;
    }


    public static double distanciaEnKM(Location loc1, Location loc2){
        double d =  loc1.distanceTo(loc2) / 1000.0f;
        String s = String.format("%.2f", d).replace(",", ".");
        return Double.parseDouble(s);
    }


    public static boolean localizacionHabilitada(Context context){
        try{
            return SmartLocation.with(context).location().state().locationServicesEnabled() &&
                    SmartLocation.with(context).location().state().isAnyProviderAvailable() &&
                    SmartLocation.with(context).location().state().isGpsAvailable();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
*/