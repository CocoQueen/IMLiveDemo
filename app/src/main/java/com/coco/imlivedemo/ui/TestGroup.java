package com.coco.imlivedemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coco.imlivedemo.R;
import com.coco.imlivedemo.utils.MessageObservable;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupMemberResult;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.livesdk.ILVText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TestGroup extends AppCompatActivity implements View.OnClickListener, ILVLiveConfig.ILVLiveMsgListener {
    private static final String TAG = "TestGroup";
    private TextView tv;
    private Button mBtn_invite;
    private TextView mTv_all;
    private String id;
    private Button mBtn_getFriend;
    private TextView mTv_all_friend;
    private EditText mEd_invite, mEd_send;
    private Button mBtn_send;
    private TextView mTv_send, mTv_get;
    private String msg_send;
    private String user;
    private TIMConversation conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_group);
        MessageObservable.getInstance().addObserver(this);
        id = getIntent().getStringExtra("id");

        tv = findViewById(R.id.mTv_group);
        mBtn_invite = findViewById(R.id.mBtn_invite);
        mTv_all = findViewById(R.id.mTv_all_people);
        mBtn_getFriend = findViewById(R.id.mBtn_getFriend);
        mTv_all_friend = findViewById(R.id.mTv_all_friend);
        mEd_invite = findViewById(R.id.mEd_invite);
        mBtn_send = findViewById(R.id.mBtn_send);
        mTv_send = findViewById(R.id.mTv_send);
        mTv_send = findViewById(R.id.mTv_send);
        mTv_get = findViewById(R.id.mTv_get);
        mEd_send = findViewById(R.id.mEd_send);


        mBtn_invite.setOnClickListener(this);
        mBtn_getFriend.setOnClickListener(this);
        mBtn_send.setOnClickListener(this);

        TIMGroupManager.getInstance().getGroupMembers(
                id, //群组 ID
                new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                        for (TIMGroupMemberInfo info : timGroupMemberInfos) {
                            user = info.getUser();
                            tv.setText(info.getUser() + "======" + info.getJoinTime());
                            Log.d(TAG, "user: " + info.getUser() +
                                    "join time: " + info.getJoinTime() +
                                    "role: " + info.getRole());
                        }

                    }
                });

        List<String> list = new ArrayList<>();
        list.add(id);
        TIMGroupManager.getInstance().getGroupDetailInfo(
                list, //需要获取信息的群组 ID 列表
                new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "onError: " + i + "=====" + s);
                    }

                    @Override
                    public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                        for (TIMGroupDetailInfo info : timGroupDetailInfos) {
                            setTitle(info.getGroupName() + "(" + info.getMemberNum() + ")");
                            Log.d(TAG, "groupId: " + info.getGroupId()           //群组 ID
                                    + " group name: " + info.getGroupName()              //群组名称
                                    + " group owner: " + info.getGroupOwner()            //群组创建者帐号
                                    + " group create time: " + info.getCreateTime()      //群组创建时间
                                    + " group last info time: " + info.getLastInfoTime() //群组信息最后修改时间
                                    + " group last msg time: " + info.getLastMsgTime()  //最新群组消息时间
                                    + " group member num: " + info.getMemberNum());      //群组成员数量
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_invite:
                invite();
                mEd_invite.setText("");
                break;
            case R.id.mBtn_getFriend:
                getFriend();
                break;
            case R.id.mBtn_send:
                sendText();
                mEd_send.setText("");
                break;

        }
    }

    private void sendText() {

        msg_send = mEd_send.getText().toString().trim();
//        ILVLiveManager.getInstance().sendText(new ILVText(ILVText.ILVTextType.eGroupMsg, user, msg_send), new ILiveCallBack() {
//            @Override
//            public void onSuccess(Object data) {
//                mTv_send.setText(msg_send);
//                Toast.makeText(TestGroup.this, user+"fasongle"+ msg_send, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(String module, int errCode, String errMsg) {
//                Toast.makeText(TestGroup.this, errCode + errMsg, Toast.LENGTH_SHORT).show();
//            }
//        });
//        //会话类型：群组
//        conversation = TIMManager.getInstance().getConversation(
//                TIMConversationType.Group,      //会话类型：群组
//                id);
//        TIMMessage msg = new TIMMessage();
////添加文本内容
//        TIMTextElem elem = new TIMTextElem();
//        elem.setText(msg_send);
////将 Elem 添加到消息
//        if (msg.addElement(elem) != 0) {
//            Log.d(TAG, "addElement failed");
//            return;
//        }
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
//                Log.e(TAG, "SendMsg ok");
//
//
//            }
//        });
        ILVLiveManager.getInstance().sendGroupTextMsg(msg_send, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mTv_send.setText(msg_send);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Log.e(TAG, "onError: "+errMsg+errCode);
            }
        });

    }

    private void getFriend() {
        //获取好友列表
        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.e(TAG, "getFriendList failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                for (TIMUserProfile res : result) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < result.size(); i++) {//通过for循环进行数据的添加
                        builder.append(res.getIdentifier() + "\n");
                    }
                    String str = builder.toString();

                    mTv_all_friend.setText(str);
                    Log.e(TAG, "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                            + " remark: " + res.getRemark());
                }
            }
        });
    }

    private void invite() {
        final List<String> list = new ArrayList<>();
        String user = mEd_invite.getText().toString().trim();
        list.add(user);
        //将 list 中的用户加入群组
        TIMGroupManager.getInstance().inviteGroupMember(
                id,   //群组 ID
                list,      //待加入群组的用户列表
                new TIMValueCallBack<List<TIMGroupMemberResult>>() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "onError: " + i + "====" + s);
                    }

                    @Override
                    public void onSuccess(List<TIMGroupMemberResult> timGroupMemberResults) {
                        for (TIMGroupMemberResult r : timGroupMemberResults) {
                            StringBuilder builder = new StringBuilder();
                            for (int i = 0; i < list.size(); i++) {//通过for循环进行数据的添加
                                builder.append(r.getUser() + "\n");
                            }
                            String str = builder.toString();
                            mTv_all.setText(str);

                            Log.d(TAG, "result: " + r.getResult()  //操作结果:  0:添加失败；1：添加成功；2：原本是群成员
                                    + " user: " + r.getUser());    //用户帐号


                            TIMGroupManager.getInstance().getGroupMembers(
                                    id, //群组 ID
                                    new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {

                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                                            for (TIMGroupMemberInfo info : timGroupMemberInfos) {
                                                tv.setText(info.getUser() + "======" + info.getJoinTime());
                                                Log.d(TAG, "user: " + info.getUser() +
                                                        "join time: " + info.getJoinTime() +
                                                        "role: " + info.getRole());
                                            }


                                            List<String> list = new ArrayList<>();
                                            list.add(id);
                                            TIMGroupManager.getInstance().getGroupDetailInfo(
                                                    list, //需要获取信息的群组 ID 列表
                                                    new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                                                        @Override
                                                        public void onError(int i, String s) {
                                                            Log.e(TAG, "onError: " + i + "=====" + s);
                                                        }

                                                        @Override
                                                        public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                                                            for (TIMGroupDetailInfo info : timGroupDetailInfos) {
                                                                setTitle(info.getGroupName() + "(" + info.getMemberNum() + ")");
                                                                Log.d(TAG, "groupId: " + info.getGroupId()           //群组 ID
                                                                        + " group name: " + info.getGroupName()              //群组名称
                                                                        + " group owner: " + info.getGroupOwner()            //群组创建者帐号
                                                                        + " group create time: " + info.getCreateTime()      //群组创建时间
                                                                        + " group last info time: " + info.getLastInfoTime() //群组信息最后修改时间
                                                                        + " group last msg time: " + info.getLastMsgTime()  //最新群组消息时间
                                                                        + " group member num: " + info.getMemberNum());      //群组成员数量
                                                            }
                                                        }
                                                    });


                                        }
                                    });


                        }
                    }
                });       //回调

    }

    @Override
    public void onNewTextMsg(ILVText text, String SenderId, TIMUserProfile userProfile) {
        String getmsg = text.getText();
        String nickName = userProfile.getNickName();
        mTv_get.setText(getmsg);
        Toast.makeText(this, nickName + "发来了" + getmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewCustomMsg(ILVCustomCmd cmd, String id, TIMUserProfile userProfile) {

    }

    @Override
    public void onNewOtherMsg(TIMMessage message) {

    }
}
