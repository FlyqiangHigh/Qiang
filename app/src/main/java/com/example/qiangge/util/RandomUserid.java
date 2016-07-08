package com.example.qiangge.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by qiangge on 2016/6/2.
 */
public class RandomUserid {
    public static String randomID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
