package com.example.qiangge.qiangge;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.qiangge.interfaces.ILoginiew;
import com.example.qiangge.selfview.ColorDialog;
import com.example.qiangge.selfview.MyEditText;
import com.example.qiangge.util.ScreenUtils;
import com.example.qiangge.util.ToastShow;
import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity implements ILoginiew{
    public final static int MSG_UPDATE = 1;
    public final static int MSG_DELETE = 2;
    public static String userid="";
    private RelativeLayout mMoveRl;
    private PopupWindow mPopupWindow;
    private LinearLayout mFirstEditLl;
    private ImageView mSpinner;
    private PopuwindowAdapter mPopuwindowAdapter;
    private MyEditText myNameEdit,myPasswordEdit;
    private ColorDialog dialog;
    private ProgressBar progressBar;
    public static StringBuilder username;
    private MyHandler myHandler;
    private LoginPresent loginPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        ScreenUtils.initAnimate(mMoveRl);
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
        userid = avUser.getObjectId();
        username = new StringBuilder(avUser.getUsername());
        ComponentName componentName = new ComponentName(getPackageName(), MainActivity.class.getName());
        intent.setComponent(componentName);
        startActivity(intent);
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
        ShowDialog();
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

    @Override
    public void onBackPressed() {
        if (dialog != null){
            dialog.show();
        }
    }

    private void ShowDialog() {
        dialog = new ColorDialog(this);
        dialog.setTitle("Exit");
        dialog.setAnimationEnable(true);
        dialog.setContentText("是否取消");
        dialog.setPositiveListener("离开", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
                finish();
            }
        })
                .setNegativeListener("取消", new ColorDialog.OnNegativeListener() {
                            @Override
                            public void onClick(ColorDialog dialog) {
                                dialog.dismiss();
                            }
                        }
                );
    }
    public  void backgroundAlpha(float bgAlpha){
        ScreenUtils.backgroundAlpha(bgAlpha,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }

}
