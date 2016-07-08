package com.example.qiangge.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.qiangge.adapter.MeaageAdapter;
import com.example.qiangge.interfaces.IAVQuery;
import com.example.qiangge.interfaces.IAVUtil;
import com.example.qiangge.interfaces.PtrOperate;
import com.example.qiangge.qiangge.LoginActivity;
import com.example.qiangge.qiangge.R;
import com.example.qiangge.util.AvUtil;
import com.example.qiangge.util.CreatePtr;
import com.example.qiangge.util.ToastShow;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by qiangge on 2016/5/6.
 */
public class MessageFragment extends Fragment{
    private RecyclerView messageRecycler;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private View view;
    private MeaageAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.messagefragment,null);
            messageRecycler = (RecyclerView) view.findViewById(R.id.message_recyclerview);
            ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.message_pf);
            initData();
        }
        return view;
    }
    private void initData() {
        AvUtil.query(LoginActivity.userid, new IAVQuery() {
            @Override
            public void querySuccess(List<AVObject> list) {
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity());
                messageRecycler.setLayoutManager(horizontalLayoutManager);
                adapter = new MeaageAdapter(getActivity(), getActivity().getIntent().getStringExtra("username"), list);
                messageRecycler.setAdapter(adapter);
                CreatePtr.getPtr(getActivity(), ptrClassicFrameLayout, null);
            }

            @Override
            public void quetyFailed(AVException e) {
                ToastShow.toastShow(getActivity(), e + "");
            }
        } );
    }
    public void  updateFragment(){
        adapter.update();
        adapter.notifyDataSetChanged();
    }
}