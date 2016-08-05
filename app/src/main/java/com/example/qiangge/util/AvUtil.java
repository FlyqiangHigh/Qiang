package com.example.qiangge.util;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.qiangge.adapter.MeaageAdapter;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.interfaces.IAVContactService;
import com.example.qiangge.interfaces.IAVQuery;
import com.example.qiangge.interfaces.IAVQueryCS;
import com.example.qiangge.interfaces.IAVUtil;
import com.example.qiangge.qiangge.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiangge on 2016/7/5.
 */
public class AvUtil {
    public static void login(String userName,String passWord, final IAVUtil iavUtil){

        AVUser.logInInBackground(userName, passWord, new LogInCallback<AVUser>() {
            @Override
            public void done( AVUser avUser, AVException e) {
                if (null == e) {
                    AVInstallation.getCurrentInstallation().saveInBackground();
                    if (iavUtil != null)
                        iavUtil.loginSuccess(avUser);
                } else {
                    if (iavUtil != null)
                        iavUtil.loginFailed(e);
                }
            }
        });
    }

    public static void query(String userid, final IAVQuery iavUtil){
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("RelateFriend");
        avObjectAVQuery.whereEqualTo("userid",userid);
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (iavUtil != null)
                        iavUtil.querySuccess(list);
                } else {
                    if (iavUtil != null)
                        iavUtil.quetyFailed(e);
                }
            }
        });
    }

    public static  void contaxtSerice(final String userName,final String contactName, final IAVContactService iavContactService){
        AVIMClient avimClient = AVIMClient.getInstance(MyApplication.userName);
        avimClient.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    if (iavContactService != null)
                        iavContactService.contactServiceSuccess(avimClient);
                }
            }
        });
    }

    public static void queryConversation(String userName,String contactName,AVIMClient avimClient, final IAVQueryCS iavQueryCS){
        final List<String> clientIds = new ArrayList<>();
        clientIds.add(userName.toString());
        clientIds.add(contactName.toString());
        AVIMConversationQuery conversationQuery = avimClient.getQuery();
        conversationQuery.withMembers(clientIds);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) {
                        if (iavQueryCS != null)
                            iavQueryCS.queryConversationSuccess(list,clientIds);
                } else {

                }
            }
        });
    }
}
