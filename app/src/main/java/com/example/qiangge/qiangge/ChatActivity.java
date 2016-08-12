package com.example.qiangge.qiangge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.qiangge.adapter.ChatAdapter;
import com.example.qiangge.adapter.FaceVPAdapter;
import com.example.qiangge.annotation.ContentView;
import com.example.qiangge.annotation.ViewInject;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.interfaces.IAVContactService;
import com.example.qiangge.interfaces.IAVQueryCS;
import com.example.qiangge.interfaces.PtrOperate;
import com.example.qiangge.model.ImageInfo;
import com.example.qiangge.selfview.ViewInjectUtils;
import com.example.qiangge.util.AvUtil;
import com.example.qiangge.util.CreatePtr;
import com.example.qiangge.util.ImageLoaders;
import com.example.qiangge.util.LGImgCompressor;
import com.example.qiangge.util.ScreenUtils;
import com.example.qiangge.util.ToastShow;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by qiangge on 2016/5/25.
 */
@ContentView(R.layout.chat)
public class ChatActivity extends Activity  implements LGImgCompressor.CompressListener{
    public static final int REQUESTIAMGE = 1;
    @ViewInject(R.id.chat_pf)
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    @ViewInject(R.id.chat_recyclerview)
    private RecyclerView recyclerView;
    @ViewInject(R.id.chat_edit)
    private EditText mEditText;
    @ViewInject(R.id.chat_send)
    private Button mSendBtn;
    @ViewInject(R.id.chat_contactname)
    private TextView mContactName;

    private ChatAdapter mChatAdpater;
    private InputMethodManager inputMethodManager;
    private AVIMConversation mAVimConversation;
    private StringBuilder userName,contactName,contactId;
    private MessageReveiver messageReveiver;
    private LinearLayoutManager linearLayoutManager;
    private List<ImageInfo> data = new ArrayList<>();
    private Map<String,Integer> map = new HashMap<>();
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);
        initData();
    }

    private void initData() {
        contactName = new StringBuilder(getIntent().getStringExtra("contactname"));
        contactId = new StringBuilder(getIntent().getStringExtra("contactId"));

        /**
         * 此处是对本layout的载入
         */

        //initViewPager();
        mContactName.setText(contactName);
        /**
         * 对recyclerview的初始化处理
         */
        initRecyclerview();
        /**
         * 建立对话
         */
        createConversation();
        /**
         * 收到消息时的处理
         */
        reciveMessage();
        /**
         * 下拉控件的初始化
         */
        CreatePtr.getPtr(this, ptrClassicFrameLayout, new PtrOperate() {
            @Override
            public void doSomething() {
                if (mChatAdpater != null) {
                    mChatAdpater.pullDown();
                    mChatAdpater.notifyDataSetChanged();
                }
            }
        });
       // initPullToRefresh();

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mSendBtn.setBackground(getResources().getDrawable(R.drawable.chatnobtn));
                } else {
                    mSendBtn.setBackground(getResources().getDrawable(R.drawable.chatbtn));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mChatAdpater != null && mChatAdpater.getItemCount() > 0) {
                    recyclerView.smoothScrollToPosition(mChatAdpater.getItemCount() - 1);
                }
                return false;
            }
        });
    }

    private void reciveMessage() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyApplication.RECEIVEMESSAGE);
        messageReveiver = new MessageReveiver();
        registerReceiver(messageReveiver, intentFilter);
    }

    private void initRecyclerview() {
        linearLayoutManager = new LinearLayoutManager(this);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        userName = new StringBuilder(MyApplication.userName);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setOnTouchListener(new RecylcerviewTouch());
    }

    private void createConversation() {
        AvUtil.contaxtSerice(userName.toString(), contactName.toString(), new IAVContactService() {
            @Override
            public void contactServiceSuccess(AVIMClient avimClient) {
                AvUtil.queryConversation(userName.toString(), contactName.toString(), avimClient, new IAVQueryCS() {
                    @Override
                    public void queryConversationSuccess(List<AVIMConversation> list, List<String> clientIds) {
                        if (null != list && list.size() > 0) {
                            //**
                            //是否之前有以创建的对话
                            //*
                            hasConversation(list);
                        } else {
                            //**
                            //创建新对话
                            //*
                            ChatActivity.this.createdConversation(clientIds);
                        }
                    }

                    @Override
                    public void queryConversationFailed(AVException e) {
                        ToastShow.toastShow(ChatActivity.this, e + "");
                    }
                });
            }

            @Override
            public void contactServiceFailed(AVException e) {

            }
        });
    }

    private void createdConversation(List<String> clientIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("customConversationType", 0);
        AVIMClient.getInstance(userName.toString()).createConversation(clientIds, userName.toString(), null, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (null != avimConversation) {
                    mAVimConversation = avimConversation;
                    avimConversation.queryMessages(new AVIMMessagesQueryCallback() {
                        @Override
                        public void done(List<AVIMMessage> list, AVIMException e) {

                            mChatAdpater = new ChatAdapter(ChatActivity.this, list, userName.toString());
                            recyclerView.setAdapter(mChatAdpater);
                            if (mChatAdpater.getItemCount() > 0) {
                                recyclerView.smoothScrollToPosition(mChatAdpater.getItemCount() - 1);
                            }

                        }
                    });
                } else {
                    Toast.makeText(ChatActivity.this, e + "", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void hasConversation(List<AVIMConversation> list) {
        mAVimConversation = list.get(0);
        list.get(0).queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (e == null) {
                    for (AVIMMessage avimMessage : list){
                        if (avimMessage instanceof AVIMImageMessage){
                            data.add(ImageLoaders.getImageInfo(((AVIMImageMessage) avimMessage).getFileUrl()));
                            map.put(((AVIMImageMessage) avimMessage).getFileUrl(), count++);
                        }
                    }
                    mChatAdpater = new ChatAdapter(ChatActivity.this, list, contactName.toString(),data,map,count);
                    recyclerView.setAdapter(mChatAdpater);
                    if (mChatAdpater.getItemCount() > 0) {
                        recyclerView.smoothScrollToPosition(mChatAdpater.getItemCount() - 1);
                    }
                }else{
                    Toast.makeText(ChatActivity.this,""+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClick(View view) throws IOException {
        switch(view.getId()){
        case R.id.chat_back:
            finish();
           break;
            case R.id.chat_phone:
                break;
            case R.id.chat_send:
                if (!mEditText.getText().toString().equals("")){
                        AVIMTextMessage message = new AVIMTextMessage();
                        message.setText(mEditText.getText().toString());

                        if (mChatAdpater != null){
                            mChatAdpater.updateMessage(message);
                            mChatAdpater.notifyDataSetChanged();
                            if (mChatAdpater.getItemCount() > 0) {
                                recyclerView.smoothScrollToPosition(mChatAdpater.getItemCount() - 1);
                            }
                        }

                        mAVimConversation.sendMessage(message, new AVIMConversationCallback() {
                            @Override
                            public void done(AVIMException e) {
                                if (e == null){
                                    mChatAdpater.notifyDataSetChanged();
                                    updateRelate();
                                }else{
                                    Toast.makeText(ChatActivity.this,"发送失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    mEditText.setText("");
                }

                break;
            case R.id.chat_picture:
                //MultiImageSelector.create(this).start(ChatActivity.this,REQUESTIAMGE);
                Intent intent = new Intent(ChatActivity.this, MultiImageSelectorActivity.class);
                // 是否显示拍摄图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // 最大可选择图片数量
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                // 选择模式
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);

                startActivityForResult(intent, REQUESTIAMGE);
                break;
            /*case R.id.chat_smile:
                hideSoftInputView();//隐藏软键盘
                if (mFaceLL.getVisibility() == View.VISIBLE){
                    mFaceLL.setVisibility(View.GONE);
                }else{
                    mFaceLL.setVisibility(View.VISIBLE);
                }
                break;*/
            case R.id.chat_video:
                break;
        }
    }

    private void updateRelate() {
        AVQuery<AVObject> userQuery= new AVQuery<>("RelateFriend");
        userQuery.whereEqualTo("userid", MyApplication.userid);
        userQuery.whereEqualTo("contactname",contactName);
        AVQuery<AVObject> contactQuery = new AVQuery<>("RelateFriend");
        contactQuery.whereEqualTo("userid", contactId);
        contactQuery.whereEqualTo("contactname", LoginActivity.username);
        AVQuery<AVObject> query= AVQuery.or(Arrays.asList(userQuery,contactQuery));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null){
                    for (int i = 0;i<list.size();i++){
                        AVObject avObject = AVObject.createWithoutData("RelateFriend", list.get(i).getObjectId());
                        avObject.put("isConversation", 1);
                        avObject.saveInBackground();
                    }

                }else{
                    ToastShow.toastShow(ChatActivity.this,e+"");
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTIAMGE){
            if (resultCode == RESULT_OK){
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (String p : path){
                        LGImgCompressor.getInstance(this).withListener(this).
                                starCompress(p, 600, 800, 100);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReveiver);
    }

    @Override
    public void onCompressStart() {

    }

    @Override
    public void onCompressEnd(LGImgCompressor.CompressResult compressResult) throws IOException {
        if (compressResult.getStatus() == LGImgCompressor.CompressResult.RESULT_ERROR)//压缩失败
        {
            Toast.makeText(ChatActivity.this, "压缩失败", Toast.LENGTH_SHORT).show();
            return;
        }

        final AVIMImageMessage avimImageMessage = new AVIMImageMessage(compressResult.getOutPath());
        avimImageMessage.setText("图像");
        Map<String,Object> attributes = new HashMap<>();
        attributes.put("style",2);
        avimImageMessage.setAttrs(attributes);
        mAVimConversation.sendMessage(avimImageMessage, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                mChatAdpater.updateMessage(avimImageMessage);

                if (mChatAdpater.getItemCount() > 0) {
                    recyclerView.smoothScrollToPosition(mChatAdpater.getItemCount() - 1);
                }
            }
        });
    }

    class RecylcerviewTouch implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
            }
            return false;
        }
    }
    class MessageReveiver extends android.content.BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mChatAdpater.updateMessage((AVIMTypedMessage) intent.getBundleExtra("bundle").getParcelable("message"));
            if (mChatAdpater.getItemCount() > 0) {
                recyclerView.smoothScrollToPosition(mChatAdpater.getItemCount() - 1);
            }
        }
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
