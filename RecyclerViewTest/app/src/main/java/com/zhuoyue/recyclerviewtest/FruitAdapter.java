package com.zhuoyue.recyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhuoyue on 2017/1/24.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    //全局变量用来保存传进来的数据
    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view){
            super(view);
            fruitView=view;
            imageView = (ImageView) view.findViewById(R.id.fruit_image);
            textView = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;
    }

    //加载布局
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*纵向滚动加载布局文件*/
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
        /*横向滚动加载布局文件*/
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item_vertical,parent,false);
        /*纵向 瀑布流 滚动时加载布局文件*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item_staggered_grid_layout,parent,false);
        final ViewHolder  holder = new ViewHolder(view);

        //为子项中的图片设置点击事件
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(pos);
                Toast.makeText(v.getContext(),"You click the img "+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        //为整个子项设置点击事件
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(pos);
                Toast.makeText(v.getContext(),"You click the whole view "+fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    //绑定子空间数据
    @Override
    public void onBindViewHolder(FruitAdapter.ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.textView.setText(fruit.getName());
        holder.imageView.setImageResource(fruit.getImageId());
    }

    //列表长度
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
