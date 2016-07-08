package com.example.qiangge.util;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by qiangge on 2016/2/29.
 */
public class DragView extends LinearLayout{
   /* private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private int mMinDrawerMargin;
    private ViewDragHelper mViewDragHelper;*/
    private View viewMenu,viewContent;
    /**
     * drawer显示出来的占自身的百分比
     */
    private float mLeftMenuOnScrren;
    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN * density + 0.5f);
        mViewDragHelper = ViewDragHelper.create(this,1.0f,new DragCallBack());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mViewDragHelper.setMinVelocity(minVel);*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);

        viewMenu = getChildAt(0);
        LayoutParams lp = (LayoutParams) viewMenu.getLayoutParams();
        final int drawerWidthSize = getChildMeasureSpec(widthMeasureSpec,lp.leftMargin + lp.rightMargin
        , lp.width);
        final int drawerHeightSize = getChildMeasureSpec(heightMeasureSpec ,
                lp.topMargin + lp.bottomMargin, lp.height);
        viewMenu.measure(drawerWidthSize,drawerHeightSize);

        viewContent = getChildAt(1);
        lp = (LayoutParams) viewContent.getLayoutParams();
        final int contentWidthSize = MeasureSpec.makeMeasureSpec(width - lp.leftMargin - lp.rightMargin
        ,MeasureSpec.EXACTLY);
        final int contentHeightSize = MeasureSpec.makeMeasureSpec(height - lp.topMargin - lp.bottomMargin
        ,MeasureSpec.EXACTLY);
        viewContent.measure(contentWidthSize, contentHeightSize);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LayoutParams lp = (LayoutParams) viewContent.getLayoutParams();
        viewContent.layout(lp.leftMargin,lp.topMargin,lp.leftMargin + viewContent.getMeasuredWidth()
        ,lp.topMargin + viewContent.getMeasuredHeight());

        lp = (LayoutParams) viewMenu.getLayoutParams();
        viewMenu.layout((int) (-viewMenu.getMeasuredWidth() + (int) viewMenu.getMeasuredWidth() * mLeftMenuOnScrren),
                lp.topMargin, (int) (-viewMenu.getMeasuredWidth() + (int) viewMenu.getMeasuredWidth() * mLeftMenuOnScrren + viewMenu.getMeasuredWidth())
                , lp.topMargin +viewMenu.getMeasuredHeight());

    }

    /*class DragCallBack extends ViewDragHelper.Callback{

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx)
        {
            int newLeft = Math.max(-child.getWidth(), Math.min(left, 0));
            return newLeft;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId)
        {
            //L.e("tryCaptureView");
            return child == viewMenu;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId)
        {
            //L.e("onEdgeDragStarted");
            mViewDragHelper.captureChildView(viewMenu, pointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel)
        {
            //L.e("onViewReleased");
            final int childWidth = releasedChild.getWidth();
            float offset = (childWidth + releasedChild.getLeft()) * 1.0f / childWidth;
            mViewDragHelper.settleCapturedViewAt(xvel > 0 || xvel == 0 && offset > 0.5f ? 0 : -childWidth, releasedChild.getTop());
            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy)
        {
            final int childWidth = changedView.getWidth();
            float offset = (float) (childWidth + left) / childWidth;
            mLeftMenuOnScrren = offset;
            //offset can callback here
            changedView.setVisibility(offset == 0 ? View.INVISIBLE : View.VISIBLE);
            invalidate();
        }

        @Override
        public int getViewHorizontalDragRange(View child)
        {
            return viewMenu == child ? child.getWidth() : 0;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
    @Override
    public void computeScroll()
    {
        if (mViewDragHelper.continueSettling(true))
        {
            invalidate();
        }
    }*/

}
