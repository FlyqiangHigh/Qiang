package com.example.qiangge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.avos.avoscloud.AVObject;
import com.example.qiangge.interfaces.Present;
import com.example.qiangge.qiangge.ChatActivity;
import com.example.qiangge.qiangge.R;
import com.example.qiangge.selfview.Group;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;

/**
 * Created by qiangge on 2016/5/5.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Present{

    public enum ITEM_TYPE{
        ITEM_TYPE_TEXT,ITEM_TYPE_EDIT,ITEM_GROUP
    }
    public interface ItemClick{
         void onClick(int position);
         void onClickEdit();
    }
    public ItemClick onItemClick;
    public void setOnItemClick(ItemClick itemClick){
        this.onItemClick = itemClick;
    }
    private Context mContext;
    public List<AVObject> mContactList = new ArrayList<>();

    public static int a = 0;
    public MyAdapter(Context context,List<AVObject> list){
        this.mContext = context;
        this.mContactList = list;
    }
    public MyAdapter(){

    }
    public void updateData(List<AVObject> listContact){
        mContactList = listContact;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_TYPE.ITEM_TYPE_EDIT.ordinal()){
            view = LayoutInflater.from(mContext).inflate(R.layout.searchedit, null);
            return new EditViewHolder(view);
        }else if (viewType == ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()){
            view = LayoutInflater.from(mContext).inflate(R.layout.expandablelistview, null);
            return new ViewHolders(view);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.myrelation,null);
            return new GroupViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return ITEM_TYPE.ITEM_TYPE_EDIT.ordinal();
        else if (position == 1)
            return ITEM_TYPE.ITEM_GROUP.ordinal();
        else
            return ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof EditViewHolder){

            ((EditViewHolder) holder).editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    EventBus.getDefault().post(new MyAdapter());
                    return false;
                }
            });

        }else if (holder instanceof ViewHolders){
            for (int i = 0 ;i<mContactList.size();i++) {
                final String contactname = mContactList.get(i).getString("contactname");
                final String contaxtId = mContactList.get(i).getString("contactid");
                View view = LayoutInflater.from(mContext).inflate(R.layout.historymessage,null);
                TextView textView = (TextView) view.findViewById(R.id.history_name);
                ImageView imageView = (ImageView) view.findViewById(R.id.history_iv);
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.width = 120;
                params.height = 120;
                imageView.setLayoutParams(params);
                textView.setText(contactname);
                ((ViewHolders) holder).child_ll.addView(view);

                ((ViewHolders) holder).child_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ChatActivity.class);
                        intent.putExtra("contactname",contactname);
                        intent.putExtra("contactId",contaxtId);
                        mContext.startActivity(intent);
                    }
                });
            }

            if (onItemClick != null){
                ((ViewHolders) holder).parent_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((ViewHolders) holder).child_ll.getVisibility() == View.VISIBLE) {
                            ((ViewHolders) holder).child_ll.setVisibility(View.GONE);
                            ((ViewHolders) holder).iconImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rightiindicate));
                        } else{
                            ((ViewHolders) holder).child_ll.setVisibility(View.VISIBLE);
                            ((ViewHolders) holder).iconImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.downindicate));
                        }
                        onItemClick.onClick(position);
                    }
                });
            }


        }else if (holder instanceof GroupViewHolder){
            ((GroupViewHolder)holder).groupCare.setOnClickListener(new GroupOnclick());
            ((GroupViewHolder)holder).groupFriend.setOnClickListener(new GroupOnclick());
            ((GroupViewHolder)holder).groupGroup.setOnClickListener(new GroupOnclick());
            ((GroupViewHolder)holder).groupPublic.setOnClickListener(new GroupOnclick());
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    public static class ViewHolders extends RecyclerView.ViewHolder{
        public RelativeLayout parent_rl;
        private LinearLayout expandlist_ll,child_ll;
        public ImageView iconImage;
        public TextView parentFriend;

        public ViewHolders(View itemView){
            super(itemView);
            this.parent_rl = (RelativeLayout) itemView.findViewById(R.id.parent_rl);
            this.child_ll = (LinearLayout) itemView.findViewById(R.id.child_ll);
            //this.expandlist_ll = (LinearLayout) itemView.findViewById(R.id.expandchild_ll);
            this.parentFriend = (TextView) itemView.findViewById(R.id.myfriend);
            this.iconImage = (ImageView) itemView.findViewById(R.id.rightimageview);
        }

    }
    public static class EditViewHolder extends RecyclerView.ViewHolder{
        public EditText editText;
        public EditViewHolder(View ItemView){
            super(ItemView);
            this.editText = (EditText) ItemView.findViewById(R.id.searchedit);
            editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        }
    }
    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        public Group groupFriend,groupCare,groupGroup,groupPublic;
        public GroupViewHolder(View ItemView){
            super(ItemView);
            this.groupCare = (Group) ItemView.findViewById(R.id.myrelationcare);
            this.groupFriend = (Group) ItemView.findViewById(R.id.myrelationnewfriend);
            this.groupGroup = (Group) ItemView.findViewById(R.id.myrelationgroup);
            this.groupPublic = (Group) ItemView.findViewById(R.id.myrelationnewpublish);
        }
    }
    public class GroupOnclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
            case R.id.myrelationgroup:
               break;
                case R.id.myrelationcare:
                    break;
                case R.id.myrelationnewfriend:
                    break;
                case R.id.myrelationnewpublish:
                    break;
            }
        }
    }
}

