package com.zhuoyue.uibestpractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhuoyue on 2017/1/25.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> msgList;

    //viewHolder用来子项中的控件,recyclerView每次生成的不是View,而是ViewHolder
    //Adapter作用是: 1.创建ViewHolder 2.为ViewHolder绑定数据
    //RecycleView需要数据时就寻找其对应的Adapter
    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout left_msg_layout;
        LinearLayout right_msg_layout;
        TextView tv_left_msg;
        TextView tv_right_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            left_msg_layout = (LinearLayout) itemView.findViewById(R.id.left_layout);
            right_msg_layout = (LinearLayout) itemView.findViewById(R.id.right_layout);
            tv_left_msg = (TextView) itemView.findViewById(R.id.left_msg);
            tv_right_msg = (TextView) itemView.findViewById(R.id.right_msg);
        }

    }

    public MsgAdapter(List<Msg> msgs){
        //将传入的数据本类存放，以便后续使用
        msgList = msgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化控件,绑定布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        //为recyclerview中控件添加点击事件
        holder.left_msg_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Msg msg = msgList.get(pos);
                Toast.makeText(v.getContext(),msg.getContent(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.right_msg_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Msg msg = msgList.get(pos);
                Toast.makeText(v.getContext(),msg.getContent(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //为holder中的控件绑定数据
        Msg msg = msgList.get(position);
        //接收消息时左侧显示,右侧隐藏
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.right_msg_layout.setVisibility(View.GONE);
            holder.left_msg_layout.setVisibility(View.VISIBLE);
            holder.tv_left_msg.setText(msg.getContent());
        }else {
            //发送消息时，左侧隐藏右侧显示
            holder.right_msg_layout.setVisibility(View.VISIBLE);
            holder.left_msg_layout.setVisibility(View.GONE);
            holder.tv_right_msg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        //返回资源大小
        return msgList.size();
    }

}
