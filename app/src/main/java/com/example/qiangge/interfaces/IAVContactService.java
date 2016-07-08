package com.example.qiangge.interfaces;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;

/**
 * Created by qiangge on 2016/7/8.
 */
public interface IAVContactService {
    void contactServiceSuccess(AVIMClient avimClient);
    void contactServiceFailed(AVException e);
}
