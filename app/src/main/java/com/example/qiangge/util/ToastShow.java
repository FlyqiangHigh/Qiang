package com.example.qiangge.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by qiangge on 2016/6/7.
 */
public class ToastShow {

    public static void toastShow(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
