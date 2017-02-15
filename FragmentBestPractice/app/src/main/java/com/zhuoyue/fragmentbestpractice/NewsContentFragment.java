package com.zhuoyue.fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhuoyue on 2017/1/28.
 */

public class NewsContentFragment extends Fragment {

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    //将新闻的标题和内容显示在界面上
    public void refresh(String newsTitle,String newsContent){
        View visiabilityLayout = view.findViewById(R.id.visility_layout);
        visiabilityLayout.setVisibility(View.VISIBLE);
        TextView news_title = (TextView) view.findViewById(R.id.news_title);
        TextView news_content = (TextView) view.findViewById(R.id.news_content);
        news_title.setText(newsTitle);
        news_content.setText(newsContent);
    }
}
