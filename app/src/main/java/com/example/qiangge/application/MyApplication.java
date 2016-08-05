package com.example.qiangge.application;

import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.okhttp.OkHttpClient;
import com.example.qiangge.activeandroid.ActiveAndroid;
import com.example.qiangge.hanlder.MessageHandler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by qiangge on 2016/2/28.
 */
public class MyApplication  extends  com.example.qiangge.activeandroid.app.Application{
    public final static String RECEIVEMESSAGE = "com.qiangge.receivemessage";
    public static String userid="";
    public static String userName = "";
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
        Fresco.initialize(this);
        ActiveAndroid.initialize(this);
        AVOSCloud.initialize(this, "4w0uf2yBulX13ph9eLL81ISG-gzGzoHsz",
                "mzd1D5alSBygsxUp5JKCgcu7");
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

        // 启用崩溃错误统计
        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(true);
        //OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
    public void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(2 * 1024 * 1024) //缓存到内存的最大数据
                .memoryCacheSize(50 * 1024 * 1024) //设置内存缓存的大小
                .diskCacheFileCount(200)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
