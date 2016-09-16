package com.workfront.internship.booklibrary.controller;

/**
 * Created by ${Sona} on 9/16/2016.
 */
public class ControllerUtil {
    public static int getIntegerFromString(String str){
        int result = str == null || str == "" ? 0 : Integer.parseInt(str);
        return result;
    }
}
