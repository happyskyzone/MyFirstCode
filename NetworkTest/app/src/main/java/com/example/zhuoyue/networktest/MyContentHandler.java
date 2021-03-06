package com.example.zhuoyue.networktest;


import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by zhuoyue on 2017/2/12.
 */

public class MyContentHandler extends DefaultHandler {
    private static final String TAG = "ContentHandle";
    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    @Override
    public void startDocument() throws SAXException {
        id=new StringBuilder();
        name =new StringBuilder();
        version =new StringBuilder();
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //记录当前节点名字
        nodeName = localName;
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //根据当前节点名判断内容需要加载到哪一个StringBuilder中
        if ("id".equals(nodeName)){
            id.append(ch,start,length);
        }else if ("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if ("version".equals(nodeName)){
            version.append(ch,start,length);
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(nodeName)){
            Log.d(TAG, "id = "+id.toString().trim());
            Log.d(TAG, "name = "+name.toString().trim());
            Log.d(TAG, "version = "+version.toString().trim());
        }
        //清空StringBuilder中的内容
        id.setLength(0);
        name.setLength(0);
        version.setLength(0);
    }
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
