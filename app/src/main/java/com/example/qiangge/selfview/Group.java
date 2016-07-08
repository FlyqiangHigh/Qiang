package com.example.qiangge.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qiangge.activeandroid.util.Log;
import com.example.qiangge.qiangge.R;

import java.util.jar.Attributes;
import java.util.zip.Inflater;

/**
 * Created by qiangge on 2016/4/23.
 */
public class Group extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
       public Group(Context context ){
           this(context,null);
       }
    public Group(Context context,AttributeSet attrs){
        super(context, attrs);
       LayoutInflater.from(context).inflate(R.layout.layout, this,true);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.Group);
        imageView = (ImageView) findViewById(R.id.portrait_iv);
        textView = (TextView) findViewById(R.id.text_portrait);
        int iamge = array.getResourceId(R.styleable.Group_imageview,R.drawable.circle);
        String textString = array.getString(R.styleable.Group_text);
        int size = array.getInteger(R.styleable.Group_textsize,15);
        int clolr = array.getColor(R.styleable.Group_textcolor,getResources().getColor(R.color.black_start));
        Log.e("size",size+"dsf");
        imageView.setImageResource(iamge);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        textView.setText(textString);
        textView.setTextSize(size);
        textView.setTextColor(clolr);

        array.recycle();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
