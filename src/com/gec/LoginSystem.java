package com.gec;

import com.gec.constant.HttpConfig;
import com.gec.template.MarioWebResult;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private HttpRequest httpreq;
    private HttpConfig config;
	public String token = "";
    public LoginSystem(){
        httpreq = new HttpRequest();
        config  = new HttpConfig();
    }
    public Boolean LoginByAccountPassword (String Account,String PassWord){
        Map<String,String> from = new HashMap<>();
        from.put("userName",Account);
        from.put("userPw",PassWord);
        try {
            MarioWebResult result = httpreq.AddWebRequestPostByFrom(config.UserLogin, from);
            if(result.IsError){
                JOptionPane.showMessageDialog(null, result.ErrorMas, "登陆失败", JOptionPane.ERROR_MESSAGE);
                return false;
            }else {
                if(result.Data.equals("登陆成功\n")){
                    LoginSuccess(result.Data);
                    return true;
                }
                else {
                    return false;
                }
            }
            //TODO:解析返回的字段判断是否登录成功
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public Boolean RegisterAccount(String Account,String PassWord){
        Map<String,String> from = new HashMap<>();
        from.put("userName",Account);
        from.put("userPw",PassWord);
        try {
            MarioWebResult result = httpreq.AddWebRequestPostByFrom(config.RegisterLogin, from);
            if(result.IsError){
                JOptionPane.showMessageDialog(null, result.ErrorMas, "注册失败", JOptionPane.ERROR_MESSAGE);
                return false;
            }else {
                if(result.Data == "注册成功"){
                    LoginSuccess(result.Data);
                    return true;
                }
                else {
                    LoginFaile(result.Data);
                    return false;
                }
            }
            //TODO:解析返回的字段判断是否登录成功
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public void LoginSuccess(String token){
        this.token = token;
    }
    public void LoginFaile(String msg){

        JOptionPane.showMessageDialog(null, msg,"登陆失败",JOptionPane.WARNING_MESSAGE);
    }
}
