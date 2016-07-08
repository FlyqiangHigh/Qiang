package com.example.qiangge.util;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qiangge on 2016/5/5.
 */
public class ItemDecroation extends RecyclerView.ItemDecoration{
    private Drawable mDrable;
    public ItemDecroation(Drawable drawable){
        mDrable = drawable;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDrable.getIntrinsicHeight();
            mDrable.setBounds(left,top,right,bottom);
            mDrable.draw(c);
        }
        super.onDrawOver(c, parent);
    }
    @Override
    public void getItemOffsets(Rect outRect, int position, RecyclerView parent) {
        outRect.set(0, 0, 0, mDrable.getIntrinsicWidth());
    }
}
