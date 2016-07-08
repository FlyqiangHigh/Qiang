package com.example.qiangge.model;

import com.example.qiangge.activeandroid.query.Select;
import com.example.qiangge.table.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by qiangge on 2016/5/30.
 */
public class MessageModel {
    public static List<Message> getAllMessage(){
        return new Select().from(Message.class).execute();
    }
}
