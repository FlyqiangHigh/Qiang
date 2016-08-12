package com.example.qiangge.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.qiangge.adapter.MyAdapter;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.interfaces.IAVQuery;

import com.example.qiangge.qiangge.R;
import com.example.qiangge.util.AvUtil;
import com.example.qiangge.util.CreatePtr;
import com.example.qiangge.util.ItemDecroation;

import java.util.ArrayList;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by qiangge on 2016/5/6.
 */

public class ContactFragment extends Fragment {
    private RecyclerView contact_recyclerview;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private static List<AVObject> list = new ArrayList<>();

    private MyAdapter adapter;
    private View view;
    public void setUpdate(){
        AvUtil.query(MyApplication.userid ,new IAVQuery() {
            @Override
            public void querySuccess(List<AVObject> list) {
                adapter.updateData(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void quetyFailed(AVException e) {

            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.contactfragment,null);
            contact_recyclerview = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
            ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.contact_pf);
            initData();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        AvUtil.query(MyApplication.userid, new IAVQuery() {
            @Override
            public void querySuccess(List<AVObject> list) {
                Log.e("fdsfs",list.size()+"");
                adapter.updateData(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void quetyFailed(AVException e) {

            }
        });
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity());
        contact_recyclerview.setLayoutManager(horizontalLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new ItemDecroation(getResources().getDrawable(R.drawable.dividelinear));
        contact_recyclerview.addItemDecoration(itemDecoration);
        adapter = new MyAdapter(getActivity(), list);
        contact_recyclerview.setAdapter(adapter);
        adapter.setOnItemClick(new MyAdapter.ItemClick() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onClickEdit() {

            }
        });
        CreatePtr.getPtr(getActivity(),ptrClassicFrameLayout,null);


    }
}
