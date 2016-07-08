package com.example.qiangge.interfaces;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;

import java.util.List;

/**
 * Created by qiangge on 2016/7/7.
 */
public interface IAVUtil {
    void loginSuccess(AVUser avUser);
    void loginFailed(AVException e);
}
