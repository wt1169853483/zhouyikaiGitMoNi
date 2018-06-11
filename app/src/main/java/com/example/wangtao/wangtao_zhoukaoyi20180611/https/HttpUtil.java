package com.example.wangtao.wangtao_zhoukaoyi20180611.https;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangtao on 2018/6/11.
 * 创建日期: on 2018/5/8.
 * 描述:
 * 作者:wangtao
 */
public class HttpUtil {
    //单利模式
    public  static HttpUtil httpUtil=new HttpUtil();
    private static final String TAG = "HttpUtil";
    private MyHandle myHandle =new MyHandle();
    private IncanInterface incanInterface;
    public static HttpUtil getHttpUtil() {
        if (httpUtil == null){
           httpUtil =new HttpUtil();
        }
        return httpUtil;
    }

    //封装get
    public void get(final String url){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url1 =new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    connection.setConnectTimeout(3000);
                    if (connection.getResponseCode() == 200){
                        Log.d(TAG, "run: "+"网络请求成功");
                        InputStream inputStream = connection.getInputStream();
                        String json = HttpString.intoString(inputStream);
                        Log.d(TAG, "run: "+json);
                        Message message = myHandle.obtainMessage();
                        message.what=0;
                        message.obj=json;
                        myHandle.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message message = myHandle.obtainMessage();
                    message.what=1;
                    message.obj=e.getMessage();
                    myHandle.sendMessage(message);
                }
            }
        }.start();
    }
    //创建Handle
    class  MyHandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           switch (msg.what){
               case 0:
                  incanInterface.getDataSuccess((String) msg.obj);
                   break;
               case 1:
                   incanInterface.getDataError((String) msg.obj);
                   break;
           }
        }
    }
    public interface IncanInterface{
          void getDataSuccess(String json);
          void getDataError(String error);
    }
    public  void getIncanInterface(IncanInterface incanInterface){
          this.incanInterface =incanInterface;
    }
}
