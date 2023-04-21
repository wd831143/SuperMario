package com.gec;

public class GameStart {
    public static void main(String[] args){
        try {
            LoginJFrame loginWnd = new LoginJFrame();
            while (!loginWnd.IsLoginDone()) {
                Thread.sleep(50);
            }
            MyFrame gameStart = new MyFrame();
        }catch (Exception e){
            System.exit(-1);
        }

    }
}
