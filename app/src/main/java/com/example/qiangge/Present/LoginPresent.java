package com.example.qiangge.Present;

import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.example.qiangge.interfaces.IUserModel;
import com.example.qiangge.model.UserModel;
import com.example.qiangge.qiangge.LoginActivity;

/**
 * Created by qiangge on 2016/6/27.
 */
public class LoginPresent {
    private LoginActivity loginActivity;
    private UserModel userModel;
    public LoginPresent(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
        this.userModel = new UserModel();
    }
    public void login(){
        loginActivity.showLoading();
        loginActivity.backgroundAlpha(0.7f);
        userModel.login(loginActivity.getUserName(), loginActivity.getPassWord(), new IUserModel.onLoginListener() {
            @Override
            public void loginSuccess(AVUser avUser) {
                loginActivity.backgroundAlpha(1.0f);
                loginActivity.hideLoading();
                loginActivity.dologinSuccess(avUser);
            }

            @Override
            public void loginFailed() {
                loginActivity.backgroundAlpha(1.0f);
                loginActivity.hideLoading();
                loginActivity.showFailedError();
            }

            @Override
            public void loginAlert() {
                loginActivity.backgroundAlpha(1.0f);
                loginActivity.hideLoading();
                loginActivity.alertMessage();
            }
        });
    }
}
