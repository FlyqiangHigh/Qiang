package com.example.qiangge.qiangge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.avos.avoscloud.AVUser;
import com.example.qiangge.Present.LoginPresent;
import com.example.qiangge.adapter.PopuwindowAdapter;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.interfaces.ILoginiew;
import com.example.qiangge.model.PopuwindowModel;
import com.example.qiangge.selfview.ColorDialog;
import com.example.qiangge.selfview.MyEditText;
import com.example.qiangge.table.User;
import com.example.qiangge.util.ScreenUtils;
import com.example.qiangge.util.ToastShow;
import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity implements ILoginiew{
    public final static int MSG_UPDATE = 1;
    public final static int MSG_DELETE = 2;
    private RelativeLayout mMoveRl;
    private PopupWindow mPopupWindow;
    private LinearLayout mFirstEditLl;
    private ImageView mSpinner;
    private PopuwindowAdapter mPopuwindowAdapter;
    private MyEditText myNameEdit,myPasswordEdit;

    private ProgressBar progressBar;
    public static StringBuilder username;
    private MyHandler myHandler;
    private LoginPresent loginPresent;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("username","");
        if (user.equals("")){
            initView();
            ScreenUtils.initAnimate(mMoveRl);
        }else{
            MyApplication.userid = sharedPreferences.getString("userid","");
            MyApplication.userName = user;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void showLoading() {
            progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideLoading() {
            progressBar.setVisibility(View.GONE);
    }
    @Override
    public void showFailedError() {
        ToastShow.toastShow(this, "登录失败");
    }

    @Override
    public void alertMessage() {
        ToastShow.toastShow(this, "用户名或密码不能为空");
    }
    @Override
    public void dologinSuccess(AVUser avUser) {
        ToastShow.toastShow(this, "登录成功");
        Intent intent = new Intent();
        MyApplication.userid = avUser.getObjectId();
        MyApplication.userName = avUser.getUsername();
        ComponentName componentName = new ComponentName(getPackageName(), MainActivity.class.getName());
        intent.setComponent(componentName);
        startActivity(intent);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",avUser.getUsername());
        editor.putString("pwd",getPassWord());
        editor.putString("userid",avUser.getObjectId());
        editor.commit();
        if (PopuwindowModel.findUser(avUser.getUsername())){
            User user = new User();
            user.setUserid(avUser.getObjectId());
            user.setNickname(avUser.getUsername());
            user.setPassword(myPasswordEdit.getText().toString());
            user.save();
        }
        finish();
    }
    public String getUserName(){
        return myNameEdit.getText().toString();
    }
    public String getPassWord(){
        return myPasswordEdit.getText().toString();
    }

    /**
     * 此处Handler应为static，防止Handler持有Activity照成OOM
     */
    static class MyHandler extends Handler{
        private WeakReference<LoginActivity> mAcitity;
        public MyHandler(LoginActivity loginActivity){
            mAcitity = new WeakReference<>(loginActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity loginActivity = mAcitity.get();
            if (loginActivity != null){
                switch (msg.what){
                    case MSG_UPDATE:
                        if (msg.getData().get("user") != null){
                            loginActivity.myNameEdit.setText(msg.getData().get("user").toString());
                            loginActivity.myPasswordEdit.setText(msg.getData().get("password").toString());
                            loginActivity.mPopupWindow.dismiss();
                        }
                        break;
                    case MSG_DELETE:
                        loginActivity.mPopuwindowAdapter.notifyDataSetChanged();
                        if (loginActivity.mPopuwindowAdapter.getCount() == 0){
                            loginActivity.mPopupWindow.dismiss();
                        }
                        break;
                }
            }
        }
    }

    private void initView() {
        setContentView(R.layout.login);

        loginPresent = new LoginPresent(this);
        myHandler = new MyHandler(this);
        mMoveRl = (RelativeLayout) findViewById(R.id.login_move_rl);
        mFirstEditLl = (LinearLayout) findViewById(R.id.login_firstedit_ll);
        myNameEdit = (MyEditText) findViewById(R.id.name_edit);
        myPasswordEdit = (MyEditText) findViewById(R.id.password_edit);
        mSpinner = (ImageView) findViewById(R.id.login_down_select_iv);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_down_select_iv:
                operatePopuWindow();
                break;
            case R.id.login_tv:

                break;
            case R.id.login_newuser_tv:
                startActivity(new Intent(LoginActivity.this,RegisterAvitivity.class));
                break;
            case R.id.login_btn:
                loginPresent.login();
                break;
            default:
                break;
        }
    }

    private void operatePopuWindow() {
        initPopuWindow();
        mPopupWindow.showAsDropDown(mFirstEditLl);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mSpinner.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner));
            }
        });
        mSpinner.setImageDrawable(getResources().getDrawable(R.drawable.icon_spinner_up));
    }

    private void initPopuWindow() {
        ListView lv;
        View viewPopuwindow = LayoutInflater.from(this).inflate(R.layout.popuwindowlist, null);
        lv = (ListView) viewPopuwindow.findViewById(R.id.popu_lv);
        lv.setAdapter(mPopuwindowAdapter = new PopuwindowAdapter(LoginActivity.this, myHandler));
        mPopupWindow = new PopupWindow(viewPopuwindow, myPasswordEdit.getWidth()
                , LinearLayout.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
    }


    public  void backgroundAlpha(float bgAlpha){
        ScreenUtils.backgroundAlpha(bgAlpha,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null)
            myHandler.removeCallbacksAndMessages(null);
    }
}
