package com.gec;

import com.gec.constant.HttpConfig;

public class LoginSystem {
    private HttpRequest httpreq;
    private HttpConfig config;
	public String token = "";
    public LoginSystem(){
        httpreq = new HttpRequest();
        config  = new HttpConfig();
    }
    public Boolean LoginByAccountPassword (String Account,String PassWord){
        String json = "{ "+
            "\"Account\":" + Account +
            "\"PassWord\":" + PassWord +
        "}";
        try {
            String data = httpreq.AddWebRequestPost(config.ServerAddress + ":" + config.ServerPort, json);
            //TODO:解析返回的字段判断是否登录成功
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    public void LoginSuccess(String token){
        this.token = token;
    }
}
