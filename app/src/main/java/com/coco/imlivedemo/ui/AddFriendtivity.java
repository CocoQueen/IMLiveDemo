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
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

public class AddFriendtivity extends AppCompatActivity {

    private EditText mEd_search;
    private Button mBtn_add;
    private static final String TAG = "AddFriendtivity";
    private TextView mTv_friendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friendtivity);
        initView();
    }

    private void initView() {
        mEd_search = findViewById(R.id.mEd_serach);
        mBtn_add = findViewById(R.id.mBtn_add);
        mTv_friendMsg = findViewById(R.id.mTv_friendMsg);
        mBtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add = mEd_search.getText().toString().trim();

                if (add.equals(getTitle())) {
                    Toast.makeText(AddFriendtivity.this, "不能添加自己为好友", Toast.LENGTH_SHORT).show();
                }

                //创建请求列表
                final List<TIMAddFriendRequest> reqList = new ArrayList<TIMAddFriendRequest>();
//添加好友请求
                TIMAddFriendRequest req = new TIMAddFriendRequest();
                req.setAddrSource("AddSource_Type_Android");
                req.setAddWording("add me");
                req.setIdentifier(add);
                req.setRemark("Cat");
                reqList.add(req);
//申请添加好友
                TIMFriendshipManager.getInstance().addFriend(reqList, new TIMValueCallBack<List<TIMFriendResult>>() {
                    @Override
                    public void onError(int code, String desc) {
                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 列表请参见错误码表
                        Log.e(TAG, "addFriend failed: " + code + " desc" + desc + reqList.size());
                    }

                    @Override
                    public void onSuccess(List<TIMFriendResult> result) {
                        Toast.makeText(AddFriendtivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "addFriend succ");
                        for (TIMFriendResult res : result) {
                            Log.e(TAG, "identifier: " + res.getIdentifer() + " status: " + res.getStatus());
                            mTv_friendMsg.setText(res.getIdentifer() + "\n" + res.getStatus());
                        }
                    }
                });
            }
        });
    }
}
