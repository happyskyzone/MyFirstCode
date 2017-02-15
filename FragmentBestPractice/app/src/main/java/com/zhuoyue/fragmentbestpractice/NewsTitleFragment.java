package com.zhuoyue.fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhuoyue on 2017/1/28.
 */

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag,container,false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitle;
            public ViewHolder(View view){
                super(view);
                //绑定recyclerview中子项中的各控件ID
                newsTitle = (TextView) view.findViewById(R.id.news_title_item);
            }

        }

        public NewsAdapter(List<News> newslist){
            mNewsList = newslist;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.news_title_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);

            //为每个子项设置按下监听事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //首先获取子项内容的实例,启动展现详细新闻内容的第二个Activity
                    News news = mNewsList.get(holder.getAdapterPosition());
                    NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitle.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }


    }

    //产生消息列表
    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i=1;i<50;i++){
            News news = new News();
            news.setTitle("This is news title "+i+".");
            news.setContent(getRandomLengthContent("This is news title "+i+"."));
            newsList.add(news);
        }
        return newsList;
    }

    //获取随机不同程度字符串作为新闻的内容
    private String getRandomLengthContent(String s) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int n = random.nextInt(20)+1;
        for (int i=0;i<n;i++){
            builder.append(s);
        }
        return builder.toString();
    }
}
