package com.coco.imlivedemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coco.imlivedemo.MainActivity;
import com.coco.imlivedemo.R;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEd_user;
    private EditText mEd_pass;
    private Button mBtn_login;
    private Button mBtn_regist;
    private String password;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


    }

    private void initView() {
        mEd_user = findViewById(R.id.mEd_user);
        mEd_pass = findViewById(R.id.mEd_pass);
        mBtn_login = findViewById(R.id.mBtn_login);
        mBtn_regist = findViewById(R.id.mBtn_regist);

        mBtn_login.setOnClickListener(this);
        mBtn_regist.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mBtn_login:
                login();
                break;
            case R.id.mBtn_regist:
                regist();
                break;
        }

    }
    private void regist() {
        Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {
                String username = data.getStringExtra("username");
                mEd_user.setText(username);
            }
        }
    }
    private void login() {
        password = mEd_pass.getText().toString().trim();
        username = mEd_user.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名密码不能为空~", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() < 4 || username.length() > 32) {
            Toast.makeText(this, "用户名需在4位到32位之间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "密码需大于等于8位", Toast.LENGTH_SHORT).show();
        }
        ILiveLoginManager.getInstance().tlsLoginAll(username, password, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Toast.makeText(LoginActivity.this,"login",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
