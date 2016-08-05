package com.example.qiangge.model;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.AVUtils;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.qiangge.activeandroid.query.Select;
import com.example.qiangge.interfaces.IAVUtil;
import com.example.qiangge.interfaces.IUserModel;
import com.example.qiangge.qiangge.MainActivity;
import com.example.qiangge.table.User;
import com.example.qiangge.util.AvUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiangge on 2016/6/2.
 */
public class UserModel implements IUserModel{
    @Override
    public void login(String username, String password,final onLoginListener onLoginListener) {
        if (username.equals("") || password.equals("")){
            if (onLoginListener != null){
                onLoginListener.loginAlert();
            }
        }else{
            AvUtil.login(username, password, new IAVUtil() {
                @Override
                public void loginSuccess(AVUser avUser) {
                    AVInstallation.getCurrentInstallation().saveInBackground();
                    String installId = avUser.getString("installationid");
                    if (installId != null && !installId.equals(AVInstallation.getCurrentInstallation().getInstallationId())){
                        AVObject avObject = AVObject.createWithoutData("_User", avUser.getObjectId());
                        avObject.put("installationid",AVInstallation.getCurrentInstallation().getInstallationId());
                        avObject.saveInBackground();
                    }
                    onLoginListener.loginSuccess(avUser);
                }

                @Override
                public void loginFailed(AVException e) {
                    onLoginListener.loginFailed();
                }

            });
        }
    }

}
