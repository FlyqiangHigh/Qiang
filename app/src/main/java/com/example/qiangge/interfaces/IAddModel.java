package com.example.qiangge.interfaces;

import android.telecom.Call;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

/**
 * Created by qiangge on 2016/8/6.
 */
public interface IAddModel {
    void query(String contactName,CallBack callBack);
    interface CallBack{
        void querySuccess(AVUser avUser);
        void queryFailed(AVException e);
        void queryUserFailed();

    }
}
