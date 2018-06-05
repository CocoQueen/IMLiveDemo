package com.coco.imlivedemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coco.imlivedemo.R;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupMemberResult;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

public class TestGroup extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TestGroup";
    private TextView tv;
    private Button mBtn_invite;
    private TextView mTv_all;
    private String id;
    private Button mBtn_getFriend;
    private TextView mTv_all_friend;
    private EditText mEd_invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_group);

        id = getIntent().getStringExtra("id");

        tv = findViewById(R.id.mTv_group);
        mBtn_invite = findViewById(R.id.mBtn_invite);
        mTv_all = findViewById(R.id.mTv_all_people);
        mBtn_getFriend = findViewById(R.id.mBtn_getFriend);
        mTv_all_friend = findViewById(R.id.mTv_all_friend);
        mEd_invite = findViewById(R.id.mEd_invite);

        mBtn_invite.setOnClickListener(this);
        mBtn_getFriend.setOnClickListener(this);

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

        }
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
                    for (int i = 0; i <result.size(); i++) {//通过for循环进行数据的添加
                        builder.append(res.getIdentifier()+ "\n");
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
                            for (int i = 0; i <list.size(); i++) {//通过for循环进行数据的添加
                                builder.append(r.getUser()+ "\n");
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
}
