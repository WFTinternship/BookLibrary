package com.workfront.internship.booklibrary.dao;

import java.util.UUID;

/**
 * Created by ${Sona} on 7/13/2016.
 */
public class TestUtil {
    public static void closeResource(AutoCloseable closeable){
        try{
            if (closeable != null ) closeable.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
