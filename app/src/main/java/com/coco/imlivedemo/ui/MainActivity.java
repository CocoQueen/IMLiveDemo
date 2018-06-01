package com.coco.imlivedemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coco.imlivedemo.R;
import com.tencent.ilivesdk.core.ILiveLoginManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEd_chat_user;
    private Button btn_start_chat;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mEd_chat_user = findViewById(R.id.mEd_chat_user);
        btn_start_chat = findViewById(R.id.mBtn_start_chat);

        btn_start_chat.setOnClickListener(this);

        setTitle(ILiveLoginManager.getInstance().getMyUserId());

    }

    @Override
    public void onClick(View v) {
        String user = mEd_chat_user.getText().toString().trim();
        switch (v.getId()) {
            case R.id.mBtn_start_chat:
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(this, "要聊天的用户不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(user.equals(getTitle())){
                    Toast.makeText(this, "不能和自己聊天", Toast.LENGTH_SHORT).show();
                    return;
                }
               Intent intent = new Intent(MainActivity.this,C2CChatActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);

                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
