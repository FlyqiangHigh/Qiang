package com.example.qiangge.qiangge;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by qiangge on 2016/4/25.
 */
public class ShowActivity extends Activity {
    ListView listView;
    private List<String> list = new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        listView = (ListView) findViewById(R.id.lv);
        for (int i = 0; i < 30; i++) {
            list.add("苹果"+i);
        }
        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.pf);
        final StoreHouseHeader storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, 15, 0, 0);
        storeHouseHeader.initWithString("Hello");
        storeHouseHeader.setTextColor(getResources().getColor(R.color.red));
        ptrFrameLayout.setHeaderView(storeHouseHeader);
        ptrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ptrFrameLayout.setKeepHeaderWhenRefresh(true);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 2000);
            }
        });
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(ShowActivity.this).inflate(R.layout.text,null);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(list.get(position));
                return null;
            }
        });

    }
}
