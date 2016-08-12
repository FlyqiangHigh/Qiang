package com.example.qiangge.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.qiangge.model.ImageBDInfo;
import com.example.qiangge.model.ImageInfo;
import com.example.qiangge.qiangge.R;
import com.example.qiangge.selfview.PreviewImage;
import com.example.qiangge.util.ImageLoaders;
import com.example.qiangge.util.MessageStaus;
import com.example.qiangge.util.ToastShow;
import com.facebook.drawee.view.SimpleDraweeView;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by qiangge on 2016/5/26.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int multipleMaxSize = 0;
    private int listCount;
    private int index;
    private List<AVIMMessage> listMessage = new ArrayList<>();
    private LinkedList<AVIMMessage> linkedMessage = new LinkedList<>();
    private Context mContext;
    private StringBuilder userName;
    private List<ImageInfo> data = new ArrayList<>();
    private Map<String,Integer> map = new HashMap<>();
    public int count = 0;
    private ImageBDInfo bdInfo;
    public ChatAdapter(Context mContext,List<AVIMMessage> listMessage,String userName){
        this.userName = new StringBuilder(userName);
        this.mContext = mContext;
        this.listMessage = listMessage;
        listCount = listMessage.size();
        this.mContext = mContext;

        handleFirst(listMessage);
        bdInfo = new ImageBDInfo();
    }
    public ChatAdapter(Context mContext,List<AVIMMessage> listMessage,String userName,List<ImageInfo> data,Map<String,Integer> map
    ,int count){
        this.userName = new StringBuilder(userName);
        this.mContext = mContext;
        this.listMessage = listMessage;
        listCount = listMessage.size();
        this.mContext = mContext;
        this.data = data;
        this.map = map;

        this.count = count;
        /**
         * 首次进入加载以前的聊天记录
         */
        handleFirst(listMessage);
        bdInfo = new ImageBDInfo();
    }

    private void handleFirst(List<AVIMMessage> listMessage) {
        AVIMMessage avimMessage;
        if (listCount > 15){
            index = listCount - 15;
            for (int i = 0 ; i< 15;i++){
                listCount -- ;
                avimMessage = listMessage.get(index++);
                linkedMessage.add(avimMessage);
            }
        }else{
            for (int i = 0 ; i< listCount;i++){
                linkedMessage.add(listMessage.get(i));
            }
            listCount = 0;
        }
    }

    public void updateMessage(AVIMTypedMessage avimTypedMessage){
        linkedMessage.addAll(Arrays.asList(avimTypedMessage));
        if (avimTypedMessage instanceof AVIMImageMessage){
            data.add(ImageLoaders.getImageInfo(((AVIMImageMessage) avimTypedMessage).getFileUrl()));
            map.put(((AVIMImageMessage) avimTypedMessage).getFileUrl(),count);
            count++;
        }
        notifyDataSetChanged();
    }

    public void pullDown(){
        while(listCount  >0 && multipleMaxSize != 15){
            linkedMessage.addFirst(listMessage.get(--listCount));
            multipleMaxSize++ ;
        }
        multipleMaxSize = 0;
    }
    @Override
    public int getItemViewType(int position) {
        if (linkedMessage.get(position).getFrom().equals(AVIMClient.getInstance(userName.toString()).getClientId()))
            return 0;
        else
            return 1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if (viewType == 1){
            view = LayoutInflater.from(mContext).inflate(R.layout.inchat,null);
            return new inChat(view);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.outchat,null);
            return new outChat(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof inChat){
                if (linkedMessage.get(position) instanceof AVIMTextMessage){
                    bindInchatText((inChat) holder, position);
                }else if (linkedMessage.get(position) instanceof AVIMImageMessage){
                    bindinChatImage((inChat) holder, position);
                }
            }else if (holder instanceof  outChat){
                if (linkedMessage.get(position) instanceof AVIMTextMessage){
                    bindoutChatText((outChat) holder, position);
                }else if (linkedMessage.get(position) instanceof AVIMImageMessage){
                    bindoutChatImage((outChat) holder, position);

                }
            }
    }

    private void bindoutChatImage(outChat holder, int position) {
        AVIMImageMessage avimMessage =(AVIMImageMessage) linkedMessage.get(position);
        holder.mOutText.setVisibility(View.GONE);
        holder.mOutPicture.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse(avimMessage.getFileUrl());
        Picasso.with(mContext).load(uri).placeholder(mContext.getResources().getDrawable(R.drawable.placeholder)).into(holder.mOutPicture);
        holder.mOutPicture.setOnClickListener(new Onclick(holder.mOutPicture, map.get(avimMessage.getFileUrl())));
        //holder.mOutPicture.setImageURI(uri);
    }

    private void bindoutChatText(outChat holder, int position) {
        AVIMTextMessage avimMessage = (AVIMTextMessage)linkedMessage.get(position);
        holder.mOutText.setText(avimMessage.getText());
        holder.mOutPicture.setVisibility(View.GONE);
        holder.mOutText.setVisibility(View.VISIBLE);
    }

    private void bindinChatImage(inChat holder, int position) {
        AVIMImageMessage avimMessage =(AVIMImageMessage) linkedMessage.get(position);
        holder.mInText.setVisibility(View.GONE);
        holder.mInPicture.setVisibility(View.VISIBLE);

        Uri uri = Uri.parse(avimMessage.getFileUrl());
        Picasso.with(mContext).load(uri).placeholder(mContext.getResources().getDrawable(R.drawable.placeholder)).into(holder.mInPicture);
         holder.mInPicture.setOnClickListener(new Onclick(holder.mInPicture, map.get(avimMessage.getFileUrl())));
         //holder.mInPicture.setImageURI(uri);
    }
    private void bindInchatText(inChat holder, int position) {
        AVIMTextMessage avimMessage = (AVIMTextMessage)linkedMessage.get(position);
        View viewProgress = holder.mInProgressBar;
        View viewAlert = holder.mInAlert;
        MessageStaus.bindData(avimMessage, viewProgress, viewAlert);
        holder.mInText.setText(((AVIMTextMessage) linkedMessage.get(position)).getText());
        holder.mInPicture.setVisibility(View.GONE);
        holder.mInText.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return linkedMessage.size();
    }
    public class inChat extends RecyclerView.ViewHolder{
        private TextView mInText;
        private TextView mInDate;
        private ProgressBar mInProgressBar;
        private SimpleDraweeView mInPicture;
        private ImageView mInAlert,mInPortrait;
        public inChat(View ItemView){
            super(ItemView);
            mInText = (TextView) ItemView.findViewById(R.id.inchat_tv);
            mInDate = (TextView) ItemView.findViewById(R.id.inchat_time);
            mInProgressBar = (ProgressBar) ItemView.findViewById(R.id.inchat_progress);
            mInAlert = (ImageView) ItemView.findViewById(R.id.inchat_alert);
            mInPicture = (SimpleDraweeView) ItemView.findViewById(R.id.inchat_picture);
            mInPortrait = (ImageView) ItemView.findViewById(R.id.inchat_iv);
        }
    }
    public class outChat extends RecyclerView.ViewHolder{
        private TextView mOutText;
        private TextView mOutDate;
        private SimpleDraweeView mOutPicture;
        private ImageView mOutPortrait;
        public outChat(View ItemView){
            super(ItemView);
            mOutText = (TextView) ItemView.findViewById(R.id.outchat_tv);
            mOutDate = (TextView) ItemView.findViewById(R.id.outchat_time);
            mOutPicture = (SimpleDraweeView) ItemView.findViewById(R.id.outchat_picture);
            mOutPortrait = (ImageView) ItemView.findViewById(R.id.outchat_iv);

        }
    }
    private class Onclick implements View.OnClickListener{
        private ImageView imageView;
        private int count;
        public Onclick(ImageView imageView,int count){
            this.imageView = imageView;
            this.count = count;
        }
        @Override
        public void onClick(View v) {
            bdInfo.width = imageView.getLayoutParams().width;
            bdInfo.height = imageView.getLayoutParams().height;
            Intent intent = new Intent(mContext, PreviewImage.class);
            intent.putExtra("data", (Serializable) data);
            intent.putExtra("bdinfo", bdInfo);
            intent.putExtra("count",count);
            intent.putExtra("type",1);
            mContext.startActivity(intent);
        }
    }
}
