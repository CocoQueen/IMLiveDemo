package com.coco.imlivedemo.app;

import android.app.Application;

import com.coco.imlivedemo.utils.MessageObservable;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLog;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

/**
 * Created by ydx on 18-5-30.
 */

public class ILiveApp extends Application {

    static ILiveApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initSDK();
    }

    private void initSDK() {
        if (MsfSdkUtils.isMainProcess(this)) {    // 仅在主线程初始化
            // 初始化LiveSDK
            ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);
            ILiveSDK.getInstance().initSdk(this, 	1400093719, 	18395);
            ILVLiveManager.getInstance().init(new ILVLiveConfig()
                    .setLiveMsgListener(MessageObservable.getInstance()));
//            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
//                @Override
//                public void handleNotification(TIMOfflinePushNotification notification) {
//                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
//                        //消息被设置为需要提醒
//                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
//                    }
//                }
//            });
        }

    }

    public static ILiveApp getApp() {
        return app;
    }
}
