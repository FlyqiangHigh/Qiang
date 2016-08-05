package com.example.qiangge.model;

import android.content.Context;

import com.example.qiangge.activeandroid.Model;
import com.example.qiangge.activeandroid.query.Select;
import com.example.qiangge.activeandroid.util.Log;
import com.example.qiangge.table.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qiangge on 2016/2/28.
 */
public class PopuwindowModel extends Model{
    public static List<User> getData(){
        List<User> result = new Select().from(User.class).execute();

        if (result == null){
            return new ArrayList<>();
        }
        return result;
    }
    public static boolean findUser(String name){
        List<User> result = new Select().from(User.class).where("nickname=?",name).execute();
        Iterator iterator = result.iterator();
        while(iterator.hasNext()){
            return false;
        }
        return true;
    }
}
