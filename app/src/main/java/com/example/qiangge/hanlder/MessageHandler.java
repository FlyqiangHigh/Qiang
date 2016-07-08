package com.example.qiangge.hanlder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.example.qiangge.application.MyApplication;

import java.util.Map;

/**
 * Created by zhangxiaobo on 15/4/20.
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

  private Context context;

  public MessageHandler(Context context) {
    this.context = context;
  }

  @Override
  public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    Intent it = new Intent();
    it.setAction(MyApplication.RECEIVEMESSAGE);
    Bundle bundle = new Bundle();
    Object attributes = conversation.getAttribute("style");
    bundle.putParcelable("message", message);
    it.putExtra("bundle", bundle);
    context.sendBroadcast(it);
  }

}
