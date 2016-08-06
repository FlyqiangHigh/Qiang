package com.example.qiangge.qiangge;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SendCallback;
import com.example.qiangge.Present.PresentAdd;
import com.example.qiangge.annotation.ContentView;
import com.example.qiangge.annotation.ViewInject;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.interfaces.IAddFriend;
import com.example.qiangge.selfview.ViewInjectUtils;
import com.example.qiangge.util.ToastShow;

import java.util.Observable;

/**
 * Created by qiangge on 2016/6/3.
 */
@ContentView(R.layout.addfriend)
public class AddFriendActivity extends Activity implements IAddFriend {
    @ViewInject(R.id.add_btn)
    private Button addButton;
    @ViewInject(R.id.add_edit)
    private EditText addEdit;
    private String contactName;
    private PresentAdd presentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentAdd = new PresentAdd(this);
        ViewInjectUtils.inject(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactName = addEdit.getText().toString().trim();
                presentAdd.query(contactName);
            }
        });
    }

    private void avPush(AVPush push) {
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    ToastShow.toastShow(AddFriendActivity.this, "请求已发送");
                    finish();
                    // push successfully.
                } else {
                    // something wrong.
                    ToastShow.toastShow(AddFriendActivity.this, "" + e);
                }
            }
        });
    }

    @Override
    public void querySuccess(AVUser avUser) {
        String installationId = avUser.getString("installationid");
        AVPush push = new AVPush();
        AVQuery avQuery = AVInstallation.getQuery();
        avQuery.whereEqualTo("installationId", installationId);
        JSONObject object = new JSONObject();
        object.put("userid", MyApplication.userid);
        object.put("titles", "你好");
        object.put("installationid", installationId);
        object.put("action", "com.avos.UPDATE_STATUS");
        push.setQuery(avQuery);
        push.setPushToAndroid(true);
        push.setData(object);
        avPush(push);
    }

    @Override
    public void queryNoUser() {
        ToastShow.toastShow(AddFriendActivity.this, "没有此人的信息");
    }

    @Override
    public void queryFailed(AVException e) {
        ToastShow.toastShow(AddFriendActivity.this, "查询失败:" + e);
    }

}