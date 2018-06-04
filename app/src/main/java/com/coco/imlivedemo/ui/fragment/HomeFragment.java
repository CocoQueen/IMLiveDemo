package com.coco.imlivedemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coco.imlivedemo.R;
import com.coco.imlivedemo.ui.AddFriendtivity;
import com.tencent.TIMGroupBaseInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMValueCallBack;

import java.util.List;

/**
 * Created by ydx on 18-5-31.
 */

public class HomeFragment extends Fragment{

    private ListView mLv_lb;
    private Toolbar mTool;
    private Button mBtn_intent;
    private TextView mTv_group;
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        initView(view);
        return view;
    }
    private void initView(View view) {
        mLv_lb = view.findViewById(R.id.mLv_lb);
        mBtn_intent = view.findViewById(R.id.mBtn_intent);

        mTv_group = view.findViewById(R.id.mTv_group);



        TIMGroupManager.getInstance().getGroupList(new TIMValueCallBack<List<TIMGroupBaseInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "get gruop list failed: " + i + " desc");
            }

            @Override
            public void onSuccess(List<TIMGroupBaseInfo> timGroupBaseInfos) {
                Log.d(TAG, "get gruop list succ");
                for(TIMGroupBaseInfo info : timGroupBaseInfos) {
                    mTv_group.setText("我加入的群: \n"+info.getGroupId()+"\n"+info.getGroupName());
                    Log.d(TAG, "group id: " + info.getGroupId() +
                            " group name: " + info.getGroupName() +
                            " group type: " + info.getGroupType());
                }
            }
        });


//        mTool = view.findViewById(R.id.mTool);
        mBtn_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddFriendtivity.class));
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_friend,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_addfriend:
                Toast.makeText(getActivity(), "点击了添加好友", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_addgroup:
                Toast.makeText(getActivity(), "点击了添加群组", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
