package com.example.qiangge.table;

import com.example.qiangge.activeandroid.Model;
import com.example.qiangge.activeandroid.annotation.Column;
import com.example.qiangge.activeandroid.annotation.Table;

/**
 * Created by qiangge on 2016/5/30.
 */
@Table(name = "Message")
public class Message extends Model {
    @Column(name = "contact_id")
    private String contact_id;
    @Column(name = "message")
    private String message;

    @Column(name = "messageId")
    private int messageId;

    @Column(name = "date")
    private String date;
    public String getContact_id(){
        return contact_id;
    }
    public void setContact_id(String contact_id){
        this.contact_id = contact_id;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public int getMessageId(){
        return messageId;
    }
    public void setMessageId(int messageId){
        this.messageId = messageId;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date ){
        this.date = date;
    }
}
