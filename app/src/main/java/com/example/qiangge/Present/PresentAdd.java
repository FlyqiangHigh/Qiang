package com.example.qiangge.Present;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.example.qiangge.interfaces.IAddModel;
import com.example.qiangge.model.AddModel;
import com.example.qiangge.qiangge.AddFriendActivity;

/**
 * Created by qiangge on 2016/8/6.
 */
public class PresentAdd {
    public AddModel addModel;
    public AddFriendActivity addFriendActivity;
    public PresentAdd( AddFriendActivity addFriendActivity){
        addModel = new AddModel();
        this.addFriendActivity = addFriendActivity;
    }
    public void query(String contactName){
        addModel.query(contactName, new IAddModel.CallBack() {
            @Override
            public void querySuccess(AVUser avUser) {
                addFriendActivity.querySuccess(avUser);
            }

            @Override
            public void queryFailed(AVException e) {
                addFriendActivity.queryFailed(e);
            }

            @Override
            public void queryUserFailed() {
                addFriendActivity.queryNoUser();
            }
        });
    }
    public void push(){

    }
}
