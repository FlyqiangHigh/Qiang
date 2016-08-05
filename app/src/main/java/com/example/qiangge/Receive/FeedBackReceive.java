package com.example.qiangge.Receive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.qiangge.Fragment.ContactFragment;
import com.example.qiangge.application.MyApplication;
import com.example.qiangge.qiangge.FeedBackActivity;
import com.example.qiangge.qiangge.LoginActivity;
import com.example.qiangge.qiangge.MainActivity;
import com.example.qiangge.qiangge.R;
import com.example.qiangge.table.Contact;
import com.example.qiangge.util.ToastShow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by qiangge on 2016/6/11.
 */
public class FeedBackReceive extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            if (intent.getExtras().getString("com.avos.avoscloud.Data") != null){
                JSONObject json = new JSONObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
                StringBuilder userid = new StringBuilder(json.getString("userid"));
                AVQuery<AVUser> avQuery = AVUser.getQuery();
                avQuery.whereEqualTo("objectId",userid);
                avQuery.findInBackground(new FindCallback<AVUser>() {
                    @Override
                    public void done(List<AVUser> list, AVException e) {
                        if (e == null) {
                            AVUser avUser = list.get(0);
                            AVObject avObject = new AVObject("RelateFriend");
                            avObject.put("contactid",avUser.getObjectId());
                            avObject.put("contactname",avUser.getString("username"));
                            Log.e("userid",MyApplication.userid+"fds");
                            avObject.put("userid", MyApplication.userid);
                            avObject.put("installationid", avUser.getString("installationid"));
                            avObject.put("isConversation", 0);
                            avObject.saveInBackground();

                           // Contact contact = new Contact();

                            EventBus.getDefault().post(new ContactFragment());
                            // push successfully.
                        } else {
                            // something wrong.
                            ToastShow.toastShow(context,""+e);
                        }
                    }
                });
                NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                        .setContentTitle("对方已同意你的好友请求" )
                        .setSmallIcon(R.drawable.jie)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true);
                Intent it = new Intent(context,MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,0,it,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification.build());
            }
        }catch (JSONException e){
            Log.d("error", "JSONException: " + e.getMessage());
        }
    }
}
