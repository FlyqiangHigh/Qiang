package com.example.qiangge.hanlder;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;

import java.util.List;

/**
 * Created by qiangge on 2016/6/2.
 */
public class CustomConversationHandler extends AVIMConversationEventHandler {
    @Override
    public void onMemberLeft(AVIMClient avimClient, AVIMConversation avimConversation, List<String> list, String s) {

    }

    @Override
    public void onMemberJoined(AVIMClient avimClient, AVIMConversation avimConversation, List<String> list, String s) {

    }

    @Override
    public void onKicked(AVIMClient avimClient, AVIMConversation avimConversation, String s) {

    }

    @Override
    public void onInvited(AVIMClient avimClient, AVIMConversation avimConversation, String s) {

    }
}
