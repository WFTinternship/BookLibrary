package com.workfront.internship.booklibrary.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ${Sona} on 9/16/2016.
 */
public class ControllerUtil {
    public static int getIntegerFromString(String str){
        int result = str == null || str == "" ? 0 : Integer.parseInt(str);
        return result;
    }

    public static String getDateTime(int addToNow){
        DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addToNow);
        Date todate1 = cal.getTime();
        String date = dateFormat.format(todate1);
        return date;
    }

    public static String correctSearchText(String text){
        StringBuilder stringBuilder = null;

        if(text.isEmpty()) {
            stringBuilder.append("");
        }

        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123)){
                stringBuilder.append(c);
            }
        }

        return stringBuilder==null ? "" : stringBuilder.toString();
    }

    public static boolean isEmpty(String string){
        return string == null || string.trim().length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

}
