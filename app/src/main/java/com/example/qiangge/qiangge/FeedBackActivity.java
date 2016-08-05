package com.example.qiangge.qiangge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SendCallback;
import com.example.qiangge.annotation.ContentView;
import com.example.qiangge.annotation.ViewInject;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.selfview.ViewInjectUtils;
import com.example.qiangge.util.ToastShow;
import java.util.List;

/**
 * Created by qiangge on 2016/6/11.
 */
@ContentView(R.layout.feedback)
public class FeedBackActivity extends Activity {
    @ViewInject(R.id.feedback_agree)
    private Button mAgreeButton;
    @ViewInject(R.id.feedback_deny)
    private Button mDenyButton;
    private Intent intent;
    private static String contactid;
    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 1){
                Bundle bundle = msg.getData();
                contactid = bundle.getString("userid");
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);
        intent = getIntent();
        PushService.setDefaultPushCallback(this, MainActivity.class);
        mAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVQuery<AVUser> avUserAVQuery = AVUser.getQuery();
                avUserAVQuery.whereEqualTo("objectId", contactid);
                /**
                 * user的查询结果
                 */
                qureyResult(avUserAVQuery);
            }
        });


    }

    private void qureyResult(AVQuery<AVUser> avUserAVQuery) {
        avUserAVQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null){
                    final AVUser avUser = list.get(0);
                    AVPush avPush = new AVPush();
                    AVQuery avQuery = AVInstallation.getQuery();
                    avQuery.whereEqualTo("installationId",list.get(0).get("installationid"));
                    avPush.setQuery(avQuery);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action","com.avos.qiangge");
                    jsonObject.put("userid", MyApplication.userid);
                    avPush.setPushToAndroid(true);
                    avPush.setData(jsonObject);
                    /**
                     * 推送操作
                     */
                    avPush(avUser, avPush);
                }else{
                    ToastShow.toastShow(FeedBackActivity.this, e + "");
                }
            }
        });
    }

    private void avPush(final AVUser avUser, AVPush avPush) {
        avPush.sendInBackground(new SendCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    AVObject avObject = new AVObject("RelateFriend");
                    avObject.put("contactid",contactid);
                    avObject.put("contactname",avUser.getString("username"));
                    avObject.put("userid", MyApplication.userid);
                    avObject.put("installationid",avUser.getString("installationid"));
                    avObject.saveInBackground();
                    ToastShow.toastShow(FeedBackActivity.this, "请求已发送");
                } else {
                    ToastShow.toastShow(FeedBackActivity.this,""+e);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
