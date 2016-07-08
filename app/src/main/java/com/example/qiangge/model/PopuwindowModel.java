package com.example.qiangge.model;

import android.content.Context;

import com.example.qiangge.activeandroid.Model;
import com.example.qiangge.activeandroid.query.Select;
import com.example.qiangge.activeandroid.util.Log;
import com.example.qiangge.table.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiangge on 2016/2/28.
 */
public class PopuwindowModel extends Model{
    private static Context mContext;
    PopuwindowModel(Context context){
        this.mContext = context;
    }
    /*public static List<User> getData(){
        List<User> result = new Select().from(User.class).execute();

        if (result == null){
            return new ArrayList<User>();
        }
        return result;
    }*/
}
