package com.example.qiangge.adapter;

import android.content.Context;
import android.os.Messenger;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.qiangge.qiangge.R;

/**
 * Created by qiangge on 2016/5/9.
 */
public class StarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public StarAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return MyAdapter.ITEM_TYPE.ITEM_TYPE_EDIT.ordinal();
        else
            return MyAdapter.ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MyAdapter.ITEM_TYPE.ITEM_TYPE_EDIT.ordinal()){
            view = LayoutInflater.from(mContext).inflate( R.layout.searchedit, null);
            return new EditViewHolder(view);
        }else{
            view = LayoutInflater.from(mContext).inflate( R.layout.dynical, null);
            return new GroupViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public static class EditViewHolder extends RecyclerView.ViewHolder{
        public EditText editText;
        public EditViewHolder(View ItemView){
            super(ItemView);
            this.editText = (EditText) ItemView.findViewById(R.id.searchedit);
            editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            editText.setPadding(300,0,0,0);

            editText.setHint("搜索电影/音乐/商品");

        }
    }
    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;

        public GroupViewHolder(View ItemView){
            super(ItemView);
            //this.linearLayout = (EditText) ItemView.findViewById(R.id.searchedit);
        }
    }
}
