package com.zhuoyue.listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhuoyue on 2017/1/24.
 */

public class FruitAdapter extends ArrayAdapter {
    private int resourceID;
    public FruitAdapter(Context context, int resource, List<Fruit> objects) {
        super(context, resource, objects);
        //将声明列表控件时引入的布局文件ID保存以便getView中使用
        resourceID = resource;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*未经过性能优化的ListView
        View v = null;


        //获取当前子项Fruit实例
        Fruit fruit = (Fruit) getItem(position);

        //为每一个子项绑定布局文件
        v = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);

        //为列表中每个子项设置资源文件
        ImageView iv = (ImageView) v.findViewById(R.id.iv_friut);
        TextView tv = (TextView) v.findViewById(R.id.tv_friut);
        iv.setImageResource(fruit.getPicID());
        tv.setText(fruit.getName());

        return v;
        */

        /*对ListView进行性能优化*/

        View view = null;
        Fruit fruit = (Fruit) getItem(position);
        ViewHolder vh;
        //使用convertView缓存布局文件，为view设置ViewHolder类型标签来缓存子控件的ID
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
            vh = new ViewHolder();

            vh.imageView = (ImageView) view.findViewById(R.id.iv_friut);
            vh.textView = (TextView) view.findViewById(R.id.tv_friut);

            view.setTag(vh);
        }else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }

        vh.imageView.setImageResource(fruit.getPicID());
        vh.textView.setText(fruit.getName());

        return view;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
