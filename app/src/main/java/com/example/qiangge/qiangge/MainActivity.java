package com.example.qiangge.qiangge;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.avos.avoscloud.PushService;
import com.example.qiangge.Fragment.ContactFragment;
import com.example.qiangge.Fragment.MessageFragment;
import com.example.qiangge.Fragment.StarFragment;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.interfaces.Present;
import com.example.qiangge.selfview.ColorDialog;
import com.example.qiangge.selfview.SlidingMenu;
import com.example.qiangge.util.ScreenUtils;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;
/**
 * Created by qiangge on 2016/2/29.
 */
public class MainActivity extends FragmentActivity {

    enum selectEnum{
        MESSAGE,CONTACT,STAR;
    }
    public static final int REQUESTADD = 1;
    private ImageView messageiv,contactiv,stariv;
    private TextView mMessagetv,mContacttv,mStartv,mAddChange,mContactChange,mUserName;
    private ContactFragment contactFragment;
    private MessageFragment messageFragment;
    private StarFragment starFragment;
    private FragmentManager fragmentManager;
    private RelativeLayout mMainContent;
    private boolean isClick = true;
    private Drawable drawable;
    private Button mMainMessageBtn,mMainIphoneBtn;
    private LinearLayout mMainLL;
    private SlidingMenu mSlidingMenu;
    private PopupWindow popupWindow;
    private LinearLayout mExit;
    private ColorDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);
        initView();
        PushService.setDefaultPushCallback(this, FeedBackActivity.class);
        initFragment();
    }
    private void initFragment() {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        messageFragment = new MessageFragment();
        fragmentTransaction.replace(R.id.changeframe, messageFragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        isClick = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    public void onEventMainThread(Present present){
        if (isClick){
            TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,-150);
            translateAnimation.setDuration(1000);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {

                    Intent it = new Intent(MainActivity.this, SearchEdit.class);
                    startActivity(it);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mMainContent.setAnimation(translateAnimation);
            isClick =false;
        }

    }
    public void onEventMainThread(ContactFragment conF){
        Log.e("conF","conF");
        contactFragment.setUpdate();
    }

    private void initView() {
        ShowDialog();
        EventBus.getDefault().register(this);
        mSlidingMenu = (SlidingMenu) findViewById(R.id.id_menu);
        mMainContent = (RelativeLayout) findViewById(R.id.main_content);
        messageiv = (ImageView) findViewById(R.id.mesage_iv);
        mMessagetv = (TextView) findViewById(R.id.message_text);
        mUserName = (TextView) findViewById(R.id.menu_name);
        contactiv = (ImageView) findViewById(R.id.contact_iv);
        mContacttv = (TextView) findViewById(R.id.contact_text);
        stariv = (ImageView) findViewById(R.id.star_iv);
        mStartv = (TextView) findViewById(R.id.star_text);
        mAddChange = (TextView) findViewById(R.id.add_change);
        mContactChange = (TextView) findViewById(R.id.contact_change);
        mMainContent = (RelativeLayout) findViewById(R.id.main_content);
        mMainMessageBtn = (Button) findViewById(R.id.main_message);
        mMainIphoneBtn = (Button) findViewById(R.id.main_iphone);
        mMainLL = (LinearLayout) findViewById(R.id.main_ll);
        initData();
    }

    private void initData() {
        mAddChange.setText("");
        drawable = getResources().getDrawable(R.drawable.add);
        if (drawable != null){
            drawable.setBounds(0, 0, 80, 80);
        }
        mAddChange.setCompoundDrawables(drawable, null, null, null);
        mUserName.setText(MyApplication.userName);
        /*BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(contactiv);
        badgeView.setBadgeCount(1);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setBackground(8, getResources().getColor(R.color.title_btn_bg));
        badgeView.setTextSize(8);*/
    }

    private void changeSelect(selectEnum sel) {
        switch(sel){
            case CONTACT:
                contactSelect();
                break;
            case MESSAGE:
                messageSelect();
                break;
            case STAR:
                starSelect();
                break;
        }
    }

    private void starSelect() {
        messageiv.setImageDrawable(getResources().getDrawable(R.drawable.messgenoselect));
        stariv.setImageDrawable(getResources().getDrawable(R.drawable.starselect));
        contactiv.setImageDrawable(getResources().getDrawable(R.drawable.contacnotselect));
        mMessagetv.setTextColor(getResources().getColor(R.color.gray));
        mContacttv.setTextColor(getResources().getColor(R.color.gray));
        mStartv.setTextColor(getResources().getColor(R.color.hight_light_text));
        mAddChange.setText("");
        mMainLL.setVisibility(View.GONE);
        mContactChange.setVisibility(View.VISIBLE);
        mAddChange.setCompoundDrawables(null, null, null, null);
    }

    private void messageSelect() {
        messageiv.setImageDrawable(getResources().getDrawable(R.drawable.messageselect));
        stariv.setImageDrawable(getResources().getDrawable(R.drawable.starnoselect));
        contactiv.setImageDrawable(getResources().getDrawable(R.drawable.contacnotselect));
        mMessagetv.setTextColor(getResources().getColor(R.color.hight_light_text));
        mContacttv.setTextColor(getResources().getColor(R.color.gray));
        mStartv.setTextColor(getResources().getColor(R.color.gray));
        mAddChange.setText("");
        mAddChange.setCompoundDrawables(drawable, null, null, null);
        mMainLL.setVisibility(View.VISIBLE);
        mContactChange.setVisibility(View.GONE);
    }

    private void contactSelect() {
        messageiv.setImageDrawable(getResources().getDrawable(R.drawable.messgenoselect));
        stariv.setImageDrawable(getResources().getDrawable(R.drawable.starnoselect));
        contactiv.setImageDrawable(getResources().getDrawable(R.drawable.contactselects));
        mMessagetv.setTextColor(getResources().getColor(R.color.gray));
        mContacttv.setTextColor(getResources().getColor(R.color.hight_light_text));
        mStartv.setTextColor(getResources().getColor(R.color.gray));
        mAddChange.setText("添加");
        mAddChange.setCompoundDrawables(null, null, null, null);
        mMainLL.setVisibility(View.GONE);
        mContactChange.setVisibility(View.VISIBLE);
    }

    public void onClick(View view){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(view.getId()){
            case R.id.message_linear:
                changeSelect(selectEnum.MESSAGE);
                if (messageFragment == null){
                    messageFragment = new MessageFragment();
                }
                fragmentTransaction.replace(R.id.changeframe, messageFragment);
                break;
            case R.id.star_linear:
                changeSelect(selectEnum.STAR);
                if (starFragment == null){
                    starFragment = new StarFragment();
                }
                fragmentTransaction.replace(R.id.changeframe, starFragment);
                mContactChange.setText("动态");
                mAddChange.setText("更多");
                break;
            case R.id.contact_linear:
                changeSelect(selectEnum.CONTACT);
                if (contactFragment == null){
                    contactFragment = new ContactFragment();
                }
                fragmentTransaction.replace(R.id.changeframe, contactFragment);
                mContactChange.setText("联系人");
                mAddChange.setText("添加");
                break;
            case R.id.main_iphone:
                mMainMessageBtn.setBackground(getResources().getDrawable(R.drawable.baike_btn_trans_left_f_96));
                mMainIphoneBtn.setBackground(getResources().getDrawable(R.drawable.baike_btn_pink_right_f_96));
                mMainIphoneBtn.setTextColor(getResources().getColor(R.color.hight_light_text));
                mMainMessageBtn.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.main_message:
                mMainMessageBtn.setBackground(getResources().getDrawable(R.drawable.baike_btn_pink_left_f_96));
                mMainIphoneBtn.setBackground(getResources().getDrawable(R.drawable.baike_btn_trans_right_f_96));
                mMainIphoneBtn.setTextColor(getResources().getColor(R.color.white));
                mMainMessageBtn.setTextColor(getResources().getColor(R.color.hight_light_text));
                break;
            case R.id.menubottommember:

                break;
            case R.id.main_potrait:
                mSlidingMenu.openMenu();
                break;
            case R.id.add_change:
                showPopuWindow();
                break;
            case R.id.mainmenubottom_exit:
                SharedPreferences sharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username","");
                editor.putString("userid","");
                editor.putString("pwd","");
                editor.commit();
                finish();
                break;

        }
        fragmentTransaction.commit();
    }

    private void showPopuWindow() {
        View viewPopuwindow = LayoutInflater.from(MainActivity.this).inflate(R.layout.add,null);
        LinearLayout linearLayout = (LinearLayout) viewPopuwindow.findViewById(R.id.add_friend);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivityForResult(it, REQUESTADD);
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(viewPopuwindow, getWindowManager().getDefaultDisplay().getWidth()*4/10,
                    LinearLayout.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.showAsDropDown(mAddChange);
        ScreenUtils.backgroundAlpha(0.8f, this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenUtils.backgroundAlpha(1f, MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentManager = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTADD  && resultCode == RESULT_OK){
            messageFragment.updateFragment();
        }
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
                //System.exit(0);
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
}
