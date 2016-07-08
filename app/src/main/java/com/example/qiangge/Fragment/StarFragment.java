package com.example.qiangge.Fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.qiangge.activeandroid.util.Log;
import com.example.qiangge.adapter.StarAdapter;
import com.example.qiangge.interfaces.Present;
import com.example.qiangge.qiangge.R;
import com.example.qiangge.util.CreatePtr;
import com.example.qiangge.util.ItemDecroation;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by qiangge on 2016/5/6.
 */
public class StarFragment extends Fragment implements Present{
    private EditText searchEditext;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.starfragment,null);
            searchEditext = (EditText) view.findViewById(R.id.searchedit);
            searchEditext.setPadding(300,0,0,0);
            searchEditext.setHint("搜索电影/音乐/商品");
            searchEditext.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    EventBus.getDefault().post(new StarFragment());
                    return false;
                }
            });
        }
        return view;
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.star_serarch:
                break;
        }
    }
}
