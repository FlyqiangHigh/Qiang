package com.example.qiangge.util;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by qiangge on 2016/6/21.
 */
public class FrescoShow  {
    public static void setImgeview(String path ,ImageView imageView){
        Uri uri = Uri.parse(path);
        imageView.setImageURI(uri);
    }
}
