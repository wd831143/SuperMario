package com.gec;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.Request;

public class HttpRequest {
    public void init(){

    }
    public void AddWebRequestGet(String requesturl) throws Exception{
        URL url = new URL(requesturl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 返回结果-字节输入流转换成字符输入流，控制台输出字符
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb);  
    }
    
    public static String AddWebRequestPost(String requestUrl, String params) throws Exception {
		System.out.println(params);
		System.out.println("发送的连接为:" + requestUrl);
		URL url = new URL(requestUrl);
		// 打开和URL之间的连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		System.out.println("打开链接，开始发送请求" + new Date().getTime() / 1000);
		connection.setRequestMethod("POST");
		// 设置通用的请求属性
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setUseCaches(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);

		// 得到请求的输出流对象
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.write(params.getBytes("UTF-8"));
		out.flush();
		out.close();

		// 建立实际的连接
		connection.connect();
		// 定义 BufferedReader输入流来读取URL的响应
		BufferedReader in = null;
		if (requestUrl.contains("nlp"))
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
		else
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		String result = "";
		String getLine;
		while ((getLine = in.readLine()) != null) {
			result += getLine;
		}
		in.close();
		System.out.println("请求结束" + new Date().getTime() / 1000);
		System.out.println("result:" + result);
		return result;
		
		
	}

}
