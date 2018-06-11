package com.example.wangtao.wangtao_zhoukaoyi20180611.https;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wangtao on 2018/6/11.
 * 创建日期: on 2018/5/8.
 * 描述:
 * 作者:wangtao
 */
public class HttpString {
      public static String intoString(InputStream inputStream){
        StringBuffer stringBuffer=new StringBuffer();
          InputStreamReader inputStreamReader =new InputStreamReader(inputStream);
          BufferedReader bufferedReader =new BufferedReader(inputStreamReader);
          String bte="";
          try{
              while ((bte = bufferedReader.readLine()) != null){
                   stringBuffer.append(bte);
              }
          }catch (Exception e){
                e.printStackTrace();
          }
        return  stringBuffer.toString();
      }
}
