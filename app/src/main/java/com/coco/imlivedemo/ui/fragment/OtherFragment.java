package com.coco.imlivedemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coco.imlivedemo.R;
import com.coco.imlivedemo.ui.C2CChatActivity;
import com.coco.imlivedemo.ui.MainActivity;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by ydx on 18-5-31.
 */

public class OtherFragment extends Fragment implements View.OnClickListener {
    private EditText mEd_chat_user;
    private Button btn_start_chat;
    private static final String TAG = "OtherFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_other,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mEd_chat_user = view.findViewById(R.id.mEd_chat_user);
        btn_start_chat = view.findViewById(R.id.mBtn_start_chat);

        btn_start_chat.setOnClickListener(this);
        getActivity().setTitle(ILiveLoginManager.getInstance().getMyUserId());
    }

    @Override
    public void onClick(View v) {
        String user = mEd_chat_user.getText().toString().trim();
        switch (v.getId()) {
            case R.id.mBtn_start_chat:
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(getActivity(), "要聊天的用户不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(user.equals(getActivity().getTitle())){
                    Toast.makeText(getActivity(), "不能和自己聊天", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(),C2CChatActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);

                break;

        }
    }
}
