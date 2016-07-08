package com.example.qiangge.model;

import com.example.qiangge.activeandroid.query.Select;
import com.example.qiangge.table.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiangge on 2016/6/3.
 */
public class ContactModel  {
    public static final String USERID = "userid";
    /*public static List<Contact> getContactList(String userid){
        List<Contact> results = new Select().from(Contact.class).where(USERID+"=?",userid).execute();
        if (results == null){
            return new ArrayList<>();
        }
        return results;
    }*/
}
