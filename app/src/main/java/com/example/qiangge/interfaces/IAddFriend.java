package com.example.qiangge.interfaces;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

/**
 * Created by qiangge on 2016/8/6.
 */
public interface IAddFriend {
    void querySuccess(AVUser avUser);
    void queryNoUser();
    void queryFailed(AVException e);
}
