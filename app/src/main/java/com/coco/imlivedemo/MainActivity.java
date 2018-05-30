package com.coco.imlivedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.coco.imlivedemo.adapter.ListviewAdapter;
import com.coco.imlivedemo.bean.MsgInfo;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private EditText et_meg;
    private Button btn_left;
    private Button btn_right;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        listView = findViewById(R.id.listview);
        et_meg = findViewById(R.id.et_meg);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String msg1 = et_meg.getText().toString().trim();
        final ListviewAdapter adapter = new ListviewAdapter(this);
        switch (v.getId()) {
            case R.id.btn_left:
//                adapter.addDataToAdapter(new MsgInfo(msg, null));
//                adapter.notifyDataSetChanged();
                String peer = "Cocoaaa";
                TIMConversation conversation = TIMManager.getInstance().getConversation(
                        TIMConversationType.C2C,    //会话类型：单聊
                        peer);

                //构造一条消息
                TIMMessage msg = new TIMMessage();
//添加文本内容
                TIMTextElem elem = new TIMTextElem();
                elem.setText("a new msg");
//将 Elem 添加到消息
                if (msg.addElement(elem) != 0) {
                    Log.d(TAG, "addElement failed");
                    return;
                }
//发送消息
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                    @Override
                    public void onError(int code, String desc) {//发送消息失败
                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 含义请参见错误码表
                        Log.d(TAG, "send message failed. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess(TIMMessage msg) {//发送消息成功
                        Log.e(TAG, "SendMsg ok");
                        adapter.addDataToAdapter(new MsgInfo(msg1, null));
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case R.id.btn_right:
                adapter.addDataToAdapter(new MsgInfo(null, msg1));
                adapter.notifyDataSetChanged();
                break;
        }
        listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(listView.getCount());
        et_meg.setText("");
    }
}
