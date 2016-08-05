package com.example.qiangge.table;


import com.example.qiangge.activeandroid.Model;
import com.example.qiangge.activeandroid.annotation.Column;
import com.example.qiangge.activeandroid.annotation.Table;

/**
 * Created by qiangge on 2016/2/28.
 */
@Table(name ="User")
public class User extends Model{
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "password")
    private String password;
    @Column(name ="userid")
    private String userid;
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return nickname;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return userid;
    }
}
