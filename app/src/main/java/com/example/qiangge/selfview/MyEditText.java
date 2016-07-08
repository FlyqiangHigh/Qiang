package com.example.qiangge.selfview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.example.qiangge.qiangge.R;


/**
 * Created by qiangge on 2016/2/27.
 */
public class MyEditText  extends EditText{
    private Drawable mRightDrawable,mLeftDrawable;
    private boolean isHasFouse;
    public MyEditText(Context context) {
        super(context);
        init();
    }
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        Drawable[] drawable = this.getCompoundDrawables();
        mRightDrawable = drawable[2];
        mLeftDrawable =drawable[0];

        if (mLeftDrawable != null){

            mLeftDrawable.setBounds(0,0,60,60);
        }
        this.setCompoundDrawables(mLeftDrawable, getCompoundDrawables()[1],
                getCompoundDrawables()[2], getCompoundDrawables()[3]);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (isHasFouse) {
                    boolean isVisible = getText().toString().length() >= 1;
                    setClearDrawableVisible(isVisible);
                } else {
                    setClearDrawableVisible(false);
                }
            }
        });
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearDrawableVisible(isVisible);
            }
        });
        setClearDrawableVisible(false);
    }
    protected void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
            if (rightDrawable != null){
                rightDrawable.setBounds(0,0,80,80);
            }
        } else {
            rightDrawable = null;
        }
        //使用代码设置该控件left, top, right, and bottom处的图标

        /*if (mLeftDrawable != null){
            mLeftDrawable.setBounds(5,5,5,5);
        }*/

        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                rightDrawable, getCompoundDrawables()[3]);
    }
    /*private void setDrableVisisble(boolean hasFocus){
        Drawable drawable;
        if (hasFocus){
            drawable = mRightDrawable;
        }else{
            drawable  = null;
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                drawable, getCompoundDrawables()[3]);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                boolean isClean = (getWidth() - getPaddingRight()) > event.getX() &&
                        event.getX() >(getWidth () - getTotalPaddingRight());
                if (isClean){
                    setText("");
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    //CycleTimes动画重复的次数
    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

}
