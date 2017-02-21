package com.example.zhuoyue.contacttest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private List<String> contactList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String numberWillCall = null;//暂时存放将要打的电话号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView绑定数据
        ListView lvContact = (ListView) findViewById(R.id.lv_contact);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList);
        lvContact.setAdapter(adapter);

        //读取电话本是危险权限，需要申请运行时权限
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
        Toast.makeText(MainActivity.this, "点击子项直接拨打电话", Toast.LENGTH_LONG).show();

        //点击列表子项直接拨打电话
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = contactList.get(position);
                String number = info.substring(info.length() - 11);
                numberWillCall = number;//将号码存放在全局变量中
                call();
            }
        });

    }

    /**
     * 拨打电话
     */
    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + numberWillCall));
        //拨号是危险权限，需要申请运行时权限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 2);
        } else {
            startActivity(intent);
        }
    }

    /**
     * 读取联系人
     */
    private void readContacts() {
        Cursor cursor = null;
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(name + "\n" + number);
            }
        }
        adapter.notifyDataSetChanged();
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 读取申请权限结果
     * 无论申请是否成功,运行requestPermissions之后都会跳转只本方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(MainActivity.this, "读取联系人列表失败:无权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(MainActivity.this, "拨号失败:无权限", Toast.LENGTH_SHORT).show();
                }
            default:
        }
    }

}
