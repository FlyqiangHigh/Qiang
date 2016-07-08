package com.example.qiangge.interfaces;

import com.avos.avoscloud.AVUser;

/**
 * Created by qiangge on 2016/6/27.
 */
public interface IUserModel {
    interface onLoginListener{
        void loginSuccess(AVUser avUser);
        void loginFailed();
        void loginAlert();
    }
    void login(String username, String password, onLoginListener onLoginListener);
}
