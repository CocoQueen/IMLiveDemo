package com.coco.imlivedemo.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coco.imlivedemo.R;
import com.coco.imlivedemo.ui.fragment.HomeFragment;
import com.coco.imlivedemo.ui.fragment.MineFragment;
import com.coco.imlivedemo.ui.fragment.OtherFragment;
import com.coco.imlivedemo.utils.BottomNavigationViewHelper;
import com.tencent.ilivesdk.core.ILiveLoginManager;

public class MainActivity extends FragmentActivity {


    private static final String TAG = "MainActivity";
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private OtherFragment otherFragment;
    private BottomNavigationView bottomNavigationView;
    private Fragment[] fragments;
    private int lastShowFragment = 0;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        initFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_home:
                                if (lastShowFragment != 0) {
                                    switchFrament(lastShowFragment, 0);
                                    lastShowFragment = 0;
                                }
                                return true;
                            case R.id.item_other:
                                if (lastShowFragment != 1) {
                                    switchFrament(lastShowFragment, 1);
                                    lastShowFragment = 1;
                                }
                                return true;

                            case R.id.item_mine:
                                if (lastShowFragment != 2) {
                                    switchFrament(lastShowFragment, 2);
                                    lastShowFragment = 2;
                                }
                                return true;
                        }
                        return false;
                    }
                });
    }

    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fragment_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        otherFragment = new OtherFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[]{homeFragment, otherFragment, mineFragment};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .show(homeFragment)
                .commit();

    }

}
