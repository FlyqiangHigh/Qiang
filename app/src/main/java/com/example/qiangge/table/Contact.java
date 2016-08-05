package com.example.qiangge.table;


import com.example.qiangge.activeandroid.annotation.Column;
import com.example.qiangge.activeandroid.annotation.Table;
import com.example.qiangge.qiangge.BuildConfig;

/**
 * Created by qiangge on 2016/6/3.
 */
@Table(name ="Contact")
public class Contact {
    @Column(name = "userid")
    private String userid;
    @Column(name = "contact_name")
    private String contact_name;
    @Column(name = "contact_id")
    private String contact_id;
    public Contact(Buidler buidler){
        setUserid(buidler.userid);
        setContact_name(buidler.contact_name);
        setContact_id(buidler.contact_id);
    }
    public String getUserid(){
        return userid;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getContact_name(){
        return contact_name;
    }
    public void setContact_name(String contact_name){
        this.contact_name = contact_name;
    }
    public String getContact_id(){
        return contact_id;
    }
    public void setContact_id(String contact_id){
        this.contact_id = contact_id;
    }
    public static class Buidler{
        private String userid;
        private String contact_name;
        private String contact_id;
        public Buidler userId(String userid){
            this.userid = userid;
            return this;
    }
        public Buidler contact_name(String contact_name){
            this.contact_name = contact_name;
            return this;
        }
        public Buidler contact_id(String contact_id){
            this.contact_id = contact_id;
            return this;
        }
        public Contact build(){
            return new Contact(this);
        }
    }
}
