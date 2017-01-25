package com.zhuoyue.activitycontroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**创建BaseActivity用来根据界面判断是哪个Activity
 * Created by zhuoyue on 2017/1/21.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断界面对应的Activity
        Log.d(TAG, "BaseActivity: "+getClass().getSimpleName());
        //创建Activity的同时将其加入到管理工具中
        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁应用同时将其从管理类中移除
        ActivityController.removeActivity(this);
    }

    //初始化菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.about_app:
                break;
            case R.id.exit_app:
                ActivityController.finishAll();
                break;
            default:

        }
        return true;
    }
}
