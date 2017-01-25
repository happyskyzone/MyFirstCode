package com.zhuoyue.uicostomviews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**自定义控件TitleLayout创建
 * Created by zhuoyue on 2017/1/23.
 */

public class TitleLayout extends LinearLayout {

    public TitleLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载自定义布局
        LayoutInflater.from(context).inflate(R.layout.title,this);

        Button btn_back = (Button) findViewById(R.id.title_back);
        Button btn_edit = (Button) findViewById(R.id.title_edit);

        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //back按钮事件为关闭当前Activity
                ((Activity)getContext()).finish();
            }
        });

        btn_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击Edit按钮事件
                Toast.makeText(context,"You click EditButton",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
