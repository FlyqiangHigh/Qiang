package com.example.qiangge.interfaces;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by qiangge on 2016/7/8.
 */
public interface IAVQuery {
    void querySuccess(List<AVObject> list);
    void quetyFailed(AVException e);
}
