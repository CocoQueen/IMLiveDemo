package com.coco.imlivedemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coco.imlivedemo.R;
import com.coco.imlivedemo.adapter.HostAdapter;
import com.coco.imlivedemo.adapter.WatchAdapter;
import com.coco.imlivedemo.utils.MessageObservable;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydx on 18-5-31.
 */

public class CreateLiveActivity extends Activity implements View.OnClickListener, ILVLiveConfig.ILVLiveMsgListener {

    private TIMConversation conversation;
    private EditText mEd_send;
    private Button mBtn_send;
    //    private LinearLayout ll_sendmsg;
//    private LinearLayout ll_getmsg;
//    private TextView mTv_send;
//    private TextView mTv_get;
    private String msg_send;

    private static final String TAG = "CreateLiveActivity";
    private String user;
    private ListView mLv, mLv_send;
    List<String> list = new ArrayList<>();
    List<String> list_send = new ArrayList<>();
    private Toolbar mTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        //初始化消息接收者
        MessageObservable.getInstance().addObserver(this);
        //获取聊天对象
        user = getIntent().getStringExtra("user");

//        setTitle(user);
//        //会话类型：单聊
//        conversation = TIMManager.getInstance().getConversation(
//                TIMConversationType.C2C,    //会话类型：单聊
//                user);
        initView();//初始化控件
    }

    private void initView() {
        mTool = findViewById(R.id.mTool);
        mEd_send = findViewById(R.id.mEd_send);
        mBtn_send = findViewById(R.id.mBtn_send);
//        ll_sendmsg = findViewById(R.id.ll_sendmsg);
//        ll_getmsg = findViewById(R.id.ll_getmsg);
//        mTv_send = findViewById(R.id.mTv_send);
//        mTv_get = findViewById(R.id.mTv_get);
        mLv = findViewById(R.id.mLv);
        mLv_send = findViewById(R.id.mLv_send);

        mBtn_send.setOnClickListener(this);//发送消息按钮的监听
        mTool.setTitle("与" + user + "的聊天窗口");//设置聊天对象标题
    }

    @Override
    public void onClick(View v) {
        msg_send = mEd_send.getText().toString().trim();

        switch (v.getId()) {
            case R.id.mBtn_send:
                sendMsg();
                mEd_send.setText("");
                break;
        }
    }

    private void sendMsg() {

        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eC2CMsg, user, msg_send), new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                list_send.add(msg_send);
                WatchAdapter adapter = new WatchAdapter(list_send, CreateLiveActivity.this);
                mLv_send.setAdapter(adapter);
                mLv_send.setSelection(adapter.getCount());
//                mTv_send.setText(msg_send);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(CreateLiveActivity.this, errCode + errMsg, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        String getmsg = text.getText();
        list.add(getmsg);
        HostAdapter adapter = new HostAdapter(list, this);
        mLv.setAdapter(adapter);
        mLv.setSelection(adapter.getCount());
//        mTv_get.setText(getmsg);
        Log.e(TAG, "onNewTextMsg: " + getmsg);

    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }
}
