package com.zhuoyue.listviewtest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"Apple","Banana","Orange","Watermelon","Mango","PanApple",
            "Apple","Banana","Orange","Watermelon","Mango","PanApple",
            "Apple","Banana","Orange","Watermelon","Mango","PanApple",
            "Apple","Banana","Orange","Watermelon","Mango","PanApple"};

    private List<Fruit> fruitList = new ArrayList<>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*为ListView设置adapter,列表模式为简单仅文字模式*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
        //        android.R.layout.simple_list_item_1,data);
        //ListView lv = (ListView) findViewById(R.id.lv_fruit);
        //lv.setAdapter(adapter);

        /*自定义ListView*/
        initFruitList();//初始化FruitList
        final FruitAdapter adapter = new FruitAdapter(MainActivity.this,R.layout.layout_fruit,fruitList);
        lv = (ListView) findViewById(R.id.lv_fruit);
        lv.setAdapter(adapter);

        //点击listview子项弹出提示
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        //实现长按删除子项
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认删除?");
                builder.setCancelable(true);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fruitList.remove(pos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"已删除",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

                return true;
            }
        });

    }

    private void initFruitList() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit("Mango", R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }
}
