package com.example.qiangge.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by qiangge on 2016/4/27.
 */
public class ContainRecyclerViewLinear extends PtrClassicFrameLayout{
    private View viewLinear;
    private View viewChild;
    public ContainRecyclerViewLinear(Context context) {
        super(context);
    }

    public ContainRecyclerViewLinear(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContainRecyclerViewLinear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewLinear = getChildAt(1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

                switch(event.getAction()){
                    case MotionEvent.ACTION_MOVE:

                        break;

                }
       return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
