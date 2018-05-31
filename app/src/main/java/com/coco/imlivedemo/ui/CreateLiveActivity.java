package com.coco.imlivedemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coco.imlivedemo.R;
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

import java.util.List;

/**
 * Created by ydx on 18-5-31.
 */

public class CreateLiveActivity extends Activity implements View.OnClickListener, ILVLiveConfig.ILVLiveMsgListener {

    private TIMConversation conversation;
    private EditText mEd_send;
    private Button mBtn_send;
    private LinearLayout ll_sendmsg;
    private LinearLayout ll_getmsg;
    private TextView mTv_send;
    private TextView mTv_get;
    private String msg_send;

    private static final String TAG = "CreateLiveActivity";
    private String user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        MessageObservable.getInstance().addObserver(this);
        user = getIntent().getStringExtra("user");
        setTitle(user);
//        //会话类型：单聊
//        conversation = TIMManager.getInstance().getConversation(
//                TIMConversationType.C2C,    //会话类型：单聊
//                user);
        initView();
    }

    private void initView() {
        mEd_send = findViewById(R.id.mEd_send);
        mBtn_send = findViewById(R.id.mBtn_send);
        ll_sendmsg = findViewById(R.id.ll_sendmsg);
        ll_getmsg = findViewById(R.id.ll_getmsg);
        mTv_send = findViewById(R.id.mTv_send);
        mTv_get = findViewById(R.id.mTv_get);

        mBtn_send.setOnClickListener(this);
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
//        //构造一条消息
//        TIMMessage msg = new TIMMessage();
//
//        //添加文本内容
//        TIMTextElem elem = new TIMTextElem();
//        elem.setText(msg_send);
//
//        //将elem添加到消息
//        if (msg.addElement(elem) != 0) {
//            Log.d(TAG, "addElement failed");
//            return;
//        }
//
//        //发送消息
//        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
//            @Override
//            public void onError(int code, String desc) {//发送消息失败
//                //错误码 code 和错误描述 desc，可用于定位请求失败原因
//                //错误码 code 含义请参见错误码表
//                Log.d(TAG, "send message failed. code: " + code + " errmsg: " + desc);
//            }
//
//            @Override
//            public void onSuccess(TIMMessage msg) {//发送消息成功
//                String ss = msg.toString();
//                mTv_send.setText(ss);
//                Log.e(TAG, "SendMsg ok"+msg);
//            }
//        });

        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eC2CMsg,user,msg_send), new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mTv_send.setText(msg_send);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(CreateLiveActivity.this,errCode+errMsg, Toast.LENGTH_SHORT).show();

            }
        });



//        //设置消息监听器，收到新消息时，通过此监听器回调
//        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
//
//            @Override
//            public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
//                TIMMessage msg = null;
//
//                for(int i = 0; i < msg.getElementCount(); ++i) {
//                    TIMElem elem = msg.getElement(i);
//
//                    //获取当前元素的类型
//                    TIMElemType elemType = elem.getType();
//                    Log.d(TAG, "elem type: " + elemType.name());
//                    if (elemType == TIMElemType.Text) {
//                        //处理文本消息
//                        mTv_get.setText(msg.toString());
//                    } else if (elemType == TIMElemType.Image) {
//                        //处理图片消息
//                    }//...处理更多消息
//                }
//                return true; //返回 true 将终止回调链，不再调用下一个新消息监听器
//            }
//        });
    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        String getmsg = text.getText();
        mTv_get.setText(getmsg);
        Log.e(TAG, "onNewTextMsg: "+getmsg );

    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }
}
