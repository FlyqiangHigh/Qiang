package com.example.qiangge.Receive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.qiangge.qiangge.FeedBackActivity;
import com.example.qiangge.qiangge.R;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Iterator;

/**
 * Created by qiangge on 2016/6/11.
 */
public class MyPushReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
                if (intent.getExtras().getString("com.avos.avoscloud.Data") != null){
                    JSONObject json = new JSONObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
                    StringBuilder userid = new StringBuilder(json.getString("userid")) ;
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("userid",userid.toString());
                    message.arg1 = 1;
                    message.setData(bundle);
                    FeedBackActivity.handler.sendMessage(message);


                    NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                            .setContentTitle("请求添加你为好友" )
                            .setSmallIcon(R.drawable.jie)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setAutoCancel(true);
                    ;
                    Intent it = new Intent(context,FeedBackActivity.class);
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
