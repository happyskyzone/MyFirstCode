package com.zhuoyue.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;

public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        //为Button1添加监听事件
        Button button1 = (Button) findViewById(R.id.button1);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //使用Toast输出提醒
//                Toast.makeText(FirstActivity.this,"You click Button1",Toast.LENGTH_SHORT).show();
//            }
//        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*显示intent跳转Activity*/
                //Intent intent =new Intent(FirstActivity.this,SecondActivity.class);
                //startActivity(intent);

                /*隐式intent跳转Activity*/
                //Intent intent =new Intent("com.zhuoyue.activitytest.ACTION_START");
                //intent.addCategory("com.zhuoyue.activitytest.MY_CATEGORY");
                //注意在AndroidManifest中为SecondActivity设置了intent-filter,添加自定义category和action
                //startActivity(intent);

                /*携带数据跳转至SecondActivity*/
                //Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                //intent.putExtra("data_extra","Hello SecondActivity");
                //startActivity(intent);

                /*跳转至SecondActivity并接收返回数据*/
                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                startActivityForResult(intent,1);
            }
        });

        //btn_finish按钮设置监听器
        findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//关闭Activity
            }
        });

        //btn_baidu设置监听器
        findViewById(R.id.btn_baidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW); //打开百度网页
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

        //btn_dial设置监听器
        findViewById(R.id.btn_dial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:122")); //注意data的标签tel
                startActivity(intent);
            }
        });
    }

    /**
     * 接收其他Activity返回的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String data_return=data.getStringExtra("data_return");
                    Log.d(TAG, data_return);
                }
                break;
            default:
        }
    }

    /**
     * 创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //得到MenuInflater对象并调用其inflate方法创建活动菜单
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    /**
     * 响应菜单按钮事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(FirstActivity.this,"You click ADD",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(FirstActivity.this,"You click REMOVE",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
