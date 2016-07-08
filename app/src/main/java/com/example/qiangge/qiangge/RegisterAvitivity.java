package com.example.qiangge.qiangge;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.qiangge.annotation.ContentView;
import com.example.qiangge.annotation.ViewInject;
import com.example.qiangge.selfview.ViewInjectUtils;
import com.example.qiangge.util.ToastShow;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.FileNotFoundException;


/**
 * Created by qiangge on 2016/6/6.
 */
@ContentView(R.layout.register)
public class RegisterAvitivity extends Activity {
    @ViewInject(R.id.register_username)
    private EditText registerUserName;
    @ViewInject(R.id.register_password)
    private EditText registerPassword;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClick(View view) throws FileNotFoundException {
        StringBuilder account, pwd;
        switch (view.getId()) {
            case R.id.register_btn:
                account = new StringBuilder(registerUserName.getText().toString().trim());
                pwd = new StringBuilder(registerPassword.getText().toString().trim());
                if (account.toString().equals("")) {
                    toast("请输入用户名");
                    return;
                }
                if (pwd.toString().equals("")) {
                    toast("请输入密码");
                    return;
                }
                AVUser user = new AVUser();
                user.setUsername(account.toString());
                user.setPassword(pwd.toString());
                user.put("installationid", AVInstallation.getCurrentInstallation().getInstallationId());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            /**
                             * 文件上传
                             */
                           /* AVFile avFile = new AVFile("resume.txt","Working with LeanCloud is great!".getBytes());
                            avFile.saveInBackground();*/
                            /**
                             * 网络文件上传
                             */
                            /*AVFile file = new AVFile("test.gif", "http://ww3.sinaimg.cn/bmiddle/596b0666gw1ed70eavm5tg20bq06m7wi.gif", new HashMap<String, Object>());
                            file.saveInBackground();*/
                            try {
                                AVFile avFile = AVFile.withAbsoluteLocalPath("LeanCloud.jpg","/storage/emulated/0/P60427-082158.jpg");
                                avFile.saveInBackground();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            /*AVFile avFile = null;
                            try {
                                avFile = AVFile.withAbsoluteLocalPath(account + ".jpg", Environment.getExternalStorageDirectory() + "yasuo.jpg");
                                avFile.addMetaData("username", account);
                                avFile.saveInBackground();
                            } catch (FileNotFoundException e1) {
                                ToastShow.toastShow(RegisterAvitivity.this, "e1");
                                e1.printStackTrace();
                            }*/

                            ToastShow.toastShow(RegisterAvitivity.this, "注册成功");
                            finish();
                        } else {
                            ToastShow.toastShow(RegisterAvitivity.this, "注册失败"+e);
                            Log.e("e",e+"");
                        }
                    }
                });
                break;
        }
    }

    private void toast(String msg) {
        Toast.makeText(RegisterAvitivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "RegisterAvitivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.qiangge.qiangge/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "RegisterAvitivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.qiangge.qiangge/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}
