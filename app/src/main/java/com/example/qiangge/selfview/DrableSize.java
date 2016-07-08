package com.example.qiangge.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.qiangge.qiangge.R;

import java.lang.reflect.Type;

/**
 * Created by qiangge on 2016/4/29.
 */
public class DrableSize extends RadioButton {
    private int mDrawableSize;// xml文件中设置的大小

    public DrableSize(Context context) {
        this(context, null, 0);
    }

    public DrableSize(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrableSize(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DrableSize);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.DrableSize_drawsize:
                    mDrawableSize = a.getDimensionPixelSize(R.styleable.DrableSize_drawsize, 50);

                    break;
                case R.styleable.DrableSize_drawtop:
                    drawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.DrableSize_drawbottom:
                    drawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.DrableSize_drawright:
                    drawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.DrableSize_drawleft:
                    drawableLeft = a.getDrawable(attr);
                    break;
                default:
                    break;
            }
        }
        a.recycle();

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
}