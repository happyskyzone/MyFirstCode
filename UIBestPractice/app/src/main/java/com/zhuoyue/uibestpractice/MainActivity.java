package com.zhuoyue.uibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_msg);
        final EditText et_msg = (EditText) findViewById(R.id.et_msg);
        Button btn_msg_send = (Button) findViewById(R.id.btn_send_msg);

        initMsgs();//初始化消息列表

        //recycleView的相关设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final MsgAdapter adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);

        btn_msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_msg.getText().toString();
                //消息不为空，点击send按钮发送消息
                if (!content.equals("")){
                    Msg msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    //有新消息时刷新显示recyclerView
                    adapter.notifyItemInserted(msgList.size()-1);
                    //滚动recyclerView到最后一条
                    recyclerView.scrollToPosition(msgList.size()-1);
                    et_msg.setText("");

                }
            }
        });




    }

    /**
     * 初始话消息列表
     */
    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
