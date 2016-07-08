package com.example.qiangge.interfaces;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;

import java.util.List;

/**
 * Created by qiangge on 2016/7/8.
 */
public interface IAVQueryCS {
    void queryConversationSuccess(List<AVIMConversation> list,List<String> clientIds);
    void queryConversationFailed(AVException e);
}
