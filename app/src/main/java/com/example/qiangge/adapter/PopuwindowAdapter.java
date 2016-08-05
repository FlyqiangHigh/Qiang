package com.example.qiangge.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.qiangge.activeandroid.query.Delete;
import com.example.qiangge.model.PopuwindowModel;
import com.example.qiangge.qiangge.LoginActivity;
import com.example.qiangge.qiangge.R;
import com.example.qiangge.selfview.CircleImageView;
import com.example.qiangge.table.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiangge on 2016/2/28.
 */
public class PopuwindowAdapter extends BaseAdapter{
    private Context mContext;
    private List<User> mUserList = new ArrayList<>();
    private Handler mHandler;
    public PopuwindowAdapter (Context context,Handler handler){
        this.mContext = context;
        mUserList = PopuwindowModel.getData();
        this.mHandler = handler;
    }
    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popuwindow,null);
            viewHolder.circleImageView = (CircleImageView) convertView.findViewById(R.id.popu_circle_img);
            viewHolder.deleteImageView = (ImageView) convertView.findViewById(R.id.popu_delete_imag);
            viewHolder.userTv = (TextView) convertView.findViewById(R.id.name_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.userTv.setText(mUserList.get(position).getNickname());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = LoginActivity.MSG_UPDATE;
                Bundle bundle = new Bundle();
                bundle.putString("user", mUserList.get(position).getNickname());
                bundle.putString("password", mUserList.get(position).getPassword());
                msg.setData(bundle);
                mHandler.sendMessage(msg);

            }
        });
        viewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().from(User.class).where("Id = ?",mUserList.get(position).getId()).execute();
                Message msg = Message.obtain();
                msg.what = LoginActivity.MSG_DELETE;
                mHandler.sendMessage(msg);


            }
        });

        return convertView;
    }
    class ViewHolder {
        public CircleImageView circleImageView;
        public TextView userTv;
        public ImageView deleteImageView;
    }

    @Override
    public void notifyDataSetChanged() {
        mUserList = PopuwindowModel.getData();
        super.notifyDataSetChanged();
    }
}
