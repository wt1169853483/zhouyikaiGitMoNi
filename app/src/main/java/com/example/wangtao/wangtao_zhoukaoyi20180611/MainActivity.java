package com.example.wangtao.wangtao_zhoukaoyi20180611;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangtao.wangtao_zhoukaoyi20180611.bean.UserBean;
import com.example.wangtao.wangtao_zhoukaoyi20180611.https.HttpUtil;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String utl="http://www.wanandroid.com/hotkey/json";
    private static final String TAG = "MainActivity";
    private List<String> listString =new ArrayList<>();
    private FlowLayout flowLayout;
    private int [] colors={Color.RED,Color.BLUE,Color.BLACK,Color.GRAY,Color.RED,Color.BLUE,Color.BLACK,Color.GRAY,Color.RED,Color.BLUE,Color.BLACK,Color.GRAY,Color.RED,Color.BLUE,Color.BLACK,Color.GRAY};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载初始界面
        inItView();
        InitData();
    }

    private void inItView() {
         //获取组件
        flowLayout = findViewById(R.id.main_flow);
    }
    //获取网络数据
    private void InitData(){

        HttpUtil httpUtil = HttpUtil.getHttpUtil();
        httpUtil.get(utl);
        httpUtil.getIncanInterface(new HttpUtil.IncanInterface() {
            @Override
            public void getDataSuccess(String json) {
                 //Gson解析
                Gson gson=new Gson();
                Log.d(TAG, "InitData: ++++++++++++++"+"你好");
                UserBean userBean = gson.fromJson(json, UserBean.class);
                List<UserBean.DataBean> data = userBean.getData();
                for (int i = 0; i <data.size() ; i++) {
                       listString.add(data.get(i).getName());
                }
                setViewFlow();
            }

            @Override
            public void getDataError(String error) {

            }
        });
    }

    private void setViewFlow() {
        for (int i = 0; i <listString.size() ; i++) {
            TextView textView=new TextView(MainActivity.this);
            textView.setText(listString.get(i));
            textView.setTextColor(colors[i]);
            textView.setTextSize(25);
            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
           /* textView.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);*/
           params.setMargins(10,10,10,10);
            flowLayout.addView(textView,params);
        }
    }
}
