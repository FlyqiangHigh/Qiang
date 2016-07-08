package com.example.qiangge.util;

import android.content.Context;
import android.view.View;

import com.example.qiangge.interfaces.PtrOperate;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by qiangge on 2016/7/1.
 */
public class CreatePtr {
    public static void getPtr(Context mContext,PtrFrameLayout ptrFrameLayout, final PtrOperate ptrOperate){
        final MaterialHeader storeHouseHeader = new MaterialHeader(mContext);
        ptrFrameLayout.setHeaderView(storeHouseHeader);
        ptrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (ptrOperate != null){
                            ptrOperate.doSomething();
                        }
                        frame.refreshComplete();
                    }
                } ,2000);
            }
        });
    }
}
