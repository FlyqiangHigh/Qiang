package com.example.qiangge.util;

import android.view.View;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.example.qiangge.table.Message;

/**
 * Created by qiangge on 2016/6/5.
 */
public class MessageStaus {
    private static AVIMMessage  message;
    public static void bindData(Object o,View progressbar,View alert){
        message = (AVIMMessage) o;
        switch (message.getMessageStatus()){
            case AVIMMessageStatusFailed:
                progressbar.setVisibility(View.GONE);
                alert.setVisibility(View.VISIBLE);
                break;
            case AVIMMessageStatusSending:
                progressbar.setVisibility(View.VISIBLE);
                alert.setVisibility(View.GONE);
                break;
            case AVIMMessageStatusSent:
                progressbar.setVisibility(View.GONE);
                alert.setVisibility(View.GONE);
                break;
            case AVIMMessageStatusNone:
            case AVIMMessageStatusReceipt:
                break;

        }
    }
}
