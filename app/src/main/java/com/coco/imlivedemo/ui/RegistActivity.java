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
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import tencent.tls.platform.TLSErrInfo;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEd_user;
    private EditText mEd_pass;
    private EditText mEd_repass;
    private Button mBtn_regist;
    private String password;
    private String repassword;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }

    private void initView() {
        mEd_user = findViewById(R.id.mEd_user);
        mEd_pass = findViewById(R.id.mEd_pass);
        mEd_repass = findViewById(R.id.mEd_repass);
        mBtn_regist = findViewById(R.id.mBtn_regist);
        mBtn_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtn_regist:
                password = mEd_pass.getText().toString().trim();
                repassword = mEd_repass.getText().toString().trim();
                username = mEd_user.getText().toString().trim();

                regist(username, password, repassword);
                break;
        }
    }

    private void regist(String username, String password, String repassword) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            Toast.makeText(this, "输入项目不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() <= 4 || username.length() >= 32) {
            Toast.makeText(this, "用户名不能小于4位不能大于32位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(this, "密码需大于等于8位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this, "两个密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        ILiveLoginManager.getInstance().tlsRegister(username, password, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Toast.makeText(RegistActivity.this, "regist", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(RegistActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
