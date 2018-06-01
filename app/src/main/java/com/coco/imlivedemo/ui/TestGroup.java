package com.coco.imlivedemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.coco.imlivedemo.R;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

public class TestGroup extends AppCompatActivity {
    private static final String TAG = "TestGroup";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_group);

        String id = getIntent().getStringExtra("id");
        tv = findViewById(R.id.mTv_group);
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

        List<String>list=new ArrayList<>();
        list.add(id);
        TIMGroupManager.getInstance().getGroupDetailInfo(
                list, //需要获取信息的群组 ID 列表
                new TIMValueCallBack<List<TIMGroupDetailInfo>>() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "onError: "+i+"====="+s );
                    }

                    @Override
                    public void onSuccess(List<TIMGroupDetailInfo> timGroupDetailInfos) {
                        for(TIMGroupDetailInfo info : timGroupDetailInfos) {
                            setTitle(info.getGroupName()+"("+info.getMemberNum()+")");
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
}
