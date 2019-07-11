package com.example.bouchef.tubolsillo.utiles;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FechaUtils {

    public  static Date fromStringToDate(String dtStart){
        //String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            Date date = format.parse(dtStart);
            //System.out.println(date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String fromStringToVerbose(String s){
        Date fecha = FechaUtils.fromStringToDate(s);

        Locale LocaleBylanguageTag;
        TimeAgoMessages messages;
        LocaleBylanguageTag = Locale.forLanguageTag("es");
        messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();

        //TODO: PARQUE PARA BORRAR EL GMT-3
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(fecha); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, -3); // adds one hour
        fecha = cal.getTime(); // returns new date object, one hour in the future
        String tiempo = TimeAgo.using(fecha.getTime(), messages);
        return tiempo;
    }
}
