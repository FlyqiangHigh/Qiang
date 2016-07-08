package com.example.qiangge.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.qiangge.qiangge.R;

import java.lang.reflect.Type;

/**
 * Created by qiangge on 2016/5/10.
 */
public class ThreeGroup extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    private View view;
    public ThreeGroup(Context context) {
        this(context, null);
    }

    public ThreeGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.threegroup, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ThreeGroup);

        imageView = (ImageView) findViewById(R.id.threeimage);
        textView = (TextView) findViewById(R.id.threetext);
        view = findViewById(R.id.threecolor);
        int iamge = typedArray.getResourceId(R.styleable.ThreeGroup_threeimageview,R.drawable.circle);
        String textString = typedArray.getString(R.styleable.ThreeGroup_threetext);
        int color = typedArray.getResourceId(R.styleable.ThreeGroup_threecolor,R.color.stroke);
        Log.e("color", color + "");
        view.setBackgroundResource(color);
        imageView.setImageResource(iamge);
        textView.setText(textString);
        typedArray.recycle();
    }

    public ThreeGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


}
