package com.example.aldop.uas_webtoon;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by aldop on 12/7/2017.
 */

public class Connection {
    //public static final String Ip = "http://192.168.100.3:23232/webtoon/";
    public static final String Ip = "http://100.104.97.107/webtoon/";

    public static String GetDateString(java.util.Date date){
        SimpleDateFormat fmt = new SimpleDateFormat("MMMM-dd-yyyy HH:mm");

        String dateString = fmt.format(date);
        return  dateString;
    }
}
