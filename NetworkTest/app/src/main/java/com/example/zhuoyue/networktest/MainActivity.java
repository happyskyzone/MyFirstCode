package com.example.zhuoyue.networktest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    TextView tvReponseData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendRequest = (Button) findViewById(R.id.btn_send_request);
        tvReponseData = (TextView) findViewById(R.id.tv_reponse_data);
        btnSendRequest.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_request) {
            //sendRequestWithHttpUrlConn();
            sendRequestWithOkHttp();
        }
    }

    /**
     * 发起网络请求,使用OkHttp
     */
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.31.249/get_data.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    showReponse(responseData);//刷新UI
                    //parseXMLWithPull(responseData); /*使用Pull解析XML*/
                    //parseXMLWithSAX(responseData);/*使用SAX解析XML*/
                    //parseJSONWithJSONObject(responseData);
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     *解析JSON使用GSON
     * @param responseData
     */
    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(responseData,new TypeToken<List<App>>(){}.getType());
        for (App app :appList){
            Log.d(TAG, "parseJSONWithGSON: id is "+app.getId());
            Log.d(TAG, "parseJSONWithGSON: name is "+app.getName());
            Log.d(TAG, "parseJSONWithGSON: version is "+app.getVersion());
        }
    }

    /**
     * 解析JSON使用JSONObject
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("MainActivity", "id is " + id);
                Log.d("MainActivity", "name is " + name);
                Log.d("MainActivity", "version is " + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private void parseJSONWithJSONObject(String jsonData) {
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(jsonData);
//            for (int i =0;i<jsonArray.length();i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String id = jsonObject.getString("id");
//                String name = jsonObject.getString("name");
//                String version = jsonObject.getString("version");
//                Log.d(TAG, "parseJSONWithJSONObject: id = " + id);
//                Log.d(TAG, "parseJSONWithJSONObject: name = " + name);
//                Log.d(TAG, "parseJSONWithJSONObject: version = " + version);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }


    /**
     * 发起网络请求,使用HttpUrlConnection
     */
    private void sendRequestWithHttpUrlConn() {
        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader br = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream is = connection.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        builder.append(line);
                    }
                    Log.d(TAG, builder.toString());
                    //调用runOnUiThread()方法更新UI
                    showReponse(builder.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭连接，关闭流
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }


    /**
     * 刷新显示UI
     *
     * @param s
     */
    private void showReponse(final String s) {
        //Android中不允许在子线程中对UI进行操作，所以调用runOnUiThread方法修改UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvReponseData.setText(s);
            }
        });
    }

    /**
     * 解析XML使用Pull解析
     *
     * @param responseData
     */
    private void parseXMLWithPull(String responseData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(responseData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //解析结束
                    case XmlPullParser.END_TAG:{
                        if ("app".equals(nodeName)){
                            Log.d(TAG, "parseXMLWithPull: id = "+id);
                            Log.d(TAG, "parseXMLWithPull: name = "+name);
                            Log.d(TAG, "parseXMLWithPull: version = "+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析XML使用SAX解析
     * @param responseData
     */
    private void parseXMLWithSAX(String responseData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();
            MyContentHandler handler = new MyContentHandler();
            //将Content的实例设置到XMLReader中
            reader.setContentHandler(handler);
            //开始解析
            reader.parse(new InputSource(new StringReader(responseData)));

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
