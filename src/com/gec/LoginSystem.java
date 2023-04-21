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
                LoginSuccess(result.Data);
                return true;
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
}
