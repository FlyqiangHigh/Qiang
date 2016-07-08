package com.example.qiangge.interfaces;

import com.avos.avoscloud.AVUser;

/**
 * Created by qiangge on 2016/6/27.
 */
public interface ILoginiew {
    void showLoading();
    void hideLoading();
    void dologinSuccess(AVUser avUser);
    void showFailedError();
    void alertMessage();
}
