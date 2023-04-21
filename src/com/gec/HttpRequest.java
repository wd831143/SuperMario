package com.gec;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.gec.template.MarioWebResult;
import jdk.nashorn.internal.runtime.Debug;
import org.omg.CORBA.Request;
public class HttpRequest {
    public void init(){

    }

    public static MarioWebResult AddWebRequestGet(String requesturl) throws Exception{
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
		MarioWebResult result = new MarioWebResult();
		result.IsError = false;
		result.Data = sb.toString();
		result.ErrorMas = "Succes";
		return  result;
    }
    
    public static MarioWebResult AddWebRequestPost(String requestUrl, String params) throws Exception {
		HttpURLConnection httpURLConnection = null;
		MarioWebResult result = new MarioWebResult();
		try {
			// 1. 获取访问地址URL
			URL url = new URL(requestUrl);
			// 2. 创建HttpURLConnection对象
			httpURLConnection = (HttpURLConnection) url.openConnection();
			/* 3. 设置请求参数等 */
			// 请求方式  默认 GET
			httpURLConnection.setRequestMethod("POST");
			// 超时时间
			httpURLConnection.setConnectTimeout(3000);
			// 设置是否输出
			httpURLConnection.setDoOutput(true);
			// 设置是否读入
			httpURLConnection.setDoInput(true);
			// 设置是否使用缓存
			httpURLConnection.setUseCaches(false);
			// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
			httpURLConnection.setInstanceFollowRedirects(true);
			// 设置请求头
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			// 连接
			httpURLConnection.connect();
			/* 4. 处理输入输出 */
			OutputStream out = httpURLConnection.getOutputStream();
			out.write(params.getBytes());
			// 简化
			//httpURLConnection.getOutputStream().write(params.getBytes());
			out.flush();
			out.close();
			// 从连接中读取响应信息
			String msg = "";
			int code = httpURLConnection.getResponseCode();
			if (code == 200) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					msg += line + "\n";
				}
				reader.close();

				result.IsError = false;
				result.ErrorMas = "Succes";
				result.Data = msg;
				return  result;
			}else{
				result.IsError = true;
				result.ErrorMas = "请求错误";
				return  result;
			}
			// 处理结果
		} catch (IOException e) {
			result.IsError = true;
			result.ErrorMas = "IO流出错";
			System.out.println("错误："+ e.getMessage());
			return  result;
		}finally {
			// 5. 断开连接
			if (null != httpURLConnection){
				try {
					httpURLConnection.disconnect();
				}catch (Exception e){
					result.IsError = true;
					result.ErrorMas = "httpURLConnection 流关闭异常："+ e.getLocalizedMessage();
					return  result;
				}
			}
		}
	}
	public static MarioWebResult AddWebRequestPostByFrom(String httpUrl, Map<String, String> from){
		HttpURLConnection httpURLConnection = null;
		MarioWebResult result = new MarioWebResult();
		try {
			// 1. 获取访问地址URL
			URL url = new URL(httpUrl);
			// 2. 创建HttpURLConnection对象
			httpURLConnection = (HttpURLConnection) url.openConnection();
			/* 3. 设置请求参数等 */
			// 请求方式  默认 GET
			httpURLConnection.setRequestMethod("POST");
			// 超时时间
			httpURLConnection.setConnectTimeout(3000);
			// 设置是否输出
			httpURLConnection.setDoOutput(true);
			// 设置是否读入
			httpURLConnection.setDoInput(true);
			// 设置是否使用缓存
			httpURLConnection.setUseCaches(false);
			// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
			httpURLConnection.setInstanceFollowRedirects(true);
			// 设置请求头
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 设置使用标准编码格式编码参数的名-值对
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接
			httpURLConnection.connect();
			/* 4. 处理输入输出 */
			// 写入参数到请求中
			Iterator<String> iterator=from.keySet().iterator();
			String params = "";
			int i = 0;
			while (iterator.hasNext()) {
				if(i==0){
					String key=iterator.next();
					params += key+"=" + from.get(key);
				}else{
					String key=iterator.next();
					params += "&" + key+"=" + from.get(key);
				}
				i++;
			}
			if(i==0)
				params = "{}";
			OutputStream out = httpURLConnection.getOutputStream();
			out.write(params.getBytes());
			// 简化
			//httpURLConnection.getOutputStream().write(params.getBytes());
			out.flush();
			out.close();
			// 从连接中读取响应信息
			String msg = "";
			int code = httpURLConnection.getResponseCode();
            BufferedReader reader;
			if (code == 200) {
				reader = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream()));
                result.IsError = false;
			}else{
                reader = new BufferedReader(
						new InputStreamReader(httpURLConnection.getErrorStream()));
                result.IsError = true;
			}
            String line;
            while ((line = reader.readLine()) != null) {
                msg += line + "\n";
            }
            System.out.println("请求结果:"+msg);
            reader.close();
            result.Data = msg;
            return  result;
			// 处理结果
		} catch (IOException e) {
			result.IsError = true;
			result.ErrorMas = "IO流出错";
			String str = "错误："+ e.getMessage();
			System.out.println(str);
			return  result;
		}finally {
			// 5. 断开连接
			if (null != httpURLConnection){
				try {
					httpURLConnection.disconnect();
				}catch (Exception e){
					result.IsError = true;
					result.ErrorMas = "httpURLConnection 流关闭异常："+ e.getLocalizedMessage();
					return  result;
				}
			}
		}
	}
}

