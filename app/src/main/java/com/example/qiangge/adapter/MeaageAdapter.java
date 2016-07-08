package com.example.qiangge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.example.qiangge.interfaces.Present;
import com.example.qiangge.qiangge.ChatActivity;
import com.example.qiangge.qiangge.R;
import com.example.qiangge.table.Contact;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;

/**
 * Created by qiangge on 2016/5/6.
 */
public class MeaageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Present {
    public enum ITEM_TYPE{
        ITEM_TYPE_TEXT,ITEM_TYPE_EDIT
    }
    private Context mContext;
    private String userName;
    private List<AVObject> listContact = new ArrayList<>();
    public MeaageAdapter(Context context,String userName,List<AVObject> listContact){
        mContext = context;
        this.userName = userName;
        this.listContact = listContact;
        //this.listContact = ContactModel.getContactList(LoginActivity.userid);
    }
    public MeaageAdapter(){
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return ITEM_TYPE.ITEM_TYPE_EDIT.ordinal();

        return ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
    }
    public void update(){
        //this.listContact = ContactModel.getContactList(LoginActivity.userid);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_TYPE.ITEM_TYPE_EDIT.ordinal()){
            view = LayoutInflater.from(mContext).inflate( R.layout.searchedit, null);
            return new EditViewHolder(view);
        }else{
            view = LayoutInflater.from(mContext).inflate( R.layout.historymessage, null);
            return new MessageAdapter(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof EditViewHolder){
                    ((EditViewHolder) holder).editText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            EventBus.getDefault().post(new MeaageAdapter());
                            return false;
                        }
                    });
            }else if (holder instanceof MessageAdapter){
                final String contactname = listContact.get(position-1).getString("contactname");
                final String contaxtId = listContact.get(position -1).getString("contactid");
                ((MessageAdapter) holder).historyName.setText(listContact.get(position-1).getString("contactname"));
                    ((MessageAdapter) holder).historyMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("contactname",contactname);
                            intent.putExtra("contactId",contaxtId);
                            mContext.startActivity(intent);
                        }
                    });

            }
    }
    @Override
    public int getItemCount() {
        return listContact.size()+1;
    }
    public static class EditViewHolder extends RecyclerView.ViewHolder{
        public EditText editText;
        public EditViewHolder(View ItemView){
            super(ItemView);
            this.editText = (EditText) ItemView.findViewById(R.id.searchedit);
        }
    }
    public static class MessageAdapter extends RecyclerView.ViewHolder{
        //public EditText editText;
        public LinearLayout historyMain;
        public ImageView historyImageview;
        public TextView historyName,historyMessage;
        public MessageAdapter(View ItemView){
            super(ItemView);
            historyMain = (LinearLayout) ItemView.findViewById(R.id.history_main);
            historyImageview = (ImageView) ItemView.findViewById(R.id.history_iv);
            historyMessage = (TextView) ItemView.findViewById(R.id.history_message);
            historyName = (TextView) ItemView.findViewById(R.id.history_name);
            //this.editText = (EditText) ItemView.findViewById(R.id.searchedit);
        }
    }
}
