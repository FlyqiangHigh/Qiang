package com.example.qiangge.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.qiangge.interfaces.IAddModel;

/**
 * Created by qiangge on 2016/8/6.
 */
public class AddModel implements IAddModel{


    @Override
    public void query(String contactName, CallBack callBack) {
        if (!contactName.equals("")) {
            AVQuery<AVUser> avUserAVQuery = new AVQuery<>("_User");
            avUserAVQuery.whereEqualTo("username", contactName);
            /**
             * 查询的结果操作
             */
            queryResult(avUserAVQuery, callBack);
        }
    }
    private void queryResult(AVQuery<AVUser> avUserAVQuery, final CallBack callBack) {
        avUserAVQuery.getFirstInBackground(new GetCallback<AVUser>() {
            @Override
            public void done(final AVUser avUser, AVException e) {
                if (e == null) {
                    if (avUser != null && callBack != null){
                       callBack.querySuccess(avUser) ;
                    }else if (callBack != null){
                        callBack.queryUserFailed();
                    }
                } else {
                    if (callBack != null)
                        callBack.queryFailed(e);
                }
            }
        });
    }

}
