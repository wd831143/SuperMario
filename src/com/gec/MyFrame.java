package com.gec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements KeyListener,Runnable {
    //用于存储所有背景
    private List<BackGround> allBg = new ArrayList<>();
    //用于存储当前背景
    private BackGround nowBg = new BackGround();
    //用于双缓存
    private Image offScreenImage = null;
    //马里奥对象
    private Mario mario = new Mario();
    //定义一个线程对象，用于马里奥的运动
    private Thread thread;
    private Boolean EndGame = false;
    public void Init(){
        mario = new Mario(10,355);
        allBg.clear();
        for (int i = 1; i <=3 ; i++) {
            allBg.add(new BackGround(i,i==3?true:false));
        }
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);

        //绘制图像
        repaint();
        this.setVisible(true);
        EndGame = false;
    }

    public MyFrame(){
        thread = new Thread(this);
        //窗口大小:800,600
        this.setSize(800,600);
        //设置窗口居中
        this.setLocationRelativeTo(null);
        //设置窗口可见性
        this.setVisible(true);
        //设置点击窗口上的关闭键
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);
        //向窗口对象添加键盘监听
        this.addKeyListener(this);
        //设置窗口名称
        this.setTitle("莫方杰的超级玛丽游戏设计");
        //初始化图片
        StaticValue.init();
        //初始化马里奥对象
        mario = new Mario(10,355);
        //创建全部场景
        for (int i = 1; i <=3 ; i++) {
            allBg.add(new BackGround(i,i==3?true:false));
        }
        //将第一个场景设置为当前场景
        nowBg = allBg.get(2);
        mario.setBackGround(nowBg);
        //绘制图像
        repaint();
        thread.start();
    }
    @Override
    public void paint(Graphics g) {
        if (offScreenImage==null){
            //与窗口大小保持一致
            offScreenImage = createImage(800,600);
        }

        Graphics graphics = offScreenImage.getGraphics();
        //调用fillRect方法对图像填充
        graphics.fillRect(0,0,800,600);

        //绘制背景--->缓冲区
        graphics.drawImage(nowBg.getBgImage(),0,0,this);

        //绘制敌人
        for (Enemy e :nowBg.getEnemyList()){
            graphics.drawImage(e.getShow(),e.getX(),e.getY(),this);
        }

        //绘制障碍物
        for(Obstacle ob : nowBg.getObstacleList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }
        //绘制城堡
        graphics.drawImage(nowBg.getTower(),620,270,this);
        //绘制旗杆
        graphics.drawImage(nowBg.getGan(),500,220,this);
        //绘制马里奥
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);
        //加分数
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("宋体",Font.BOLD,20));
        graphics.drawString("Core:"+mario.getScore(),50,80);
        graphics.setColor(c);
        //绘制背景--->缓冲区--->窗口
        g.drawImage(offScreenImage,0,0,this);
    }

//    public static void main(String[] args){
//        //TODO:web检查登录，如果验证登录成功则进入游戏，否则要求登录
//        LoginJFrame loginWnd = new LoginJFrame();
//
//    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        //右移动
        if (e.getKeyCode()==39){
            mario.rightMove();
        }
        //左移动
        if (e.getKeyCode()==37){
            mario.leftMove();
        }
        //跳跃
        if (e.getKeyCode()==38){
            mario.jump();
        }
        //暂停
//        if(e.getKeyCode()==32){
//            mario
//        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        //向左停止
        if(e.getKeyCode()==37){
            mario.leftStop();
        }
        //向右停止
        if (e.getKeyCode()==39){
            mario.rightStop();
        }
    }

    @Override
    public void run() {
        while (!EndGame){
            repaint();
            try {
                Thread.sleep(50);
                if(mario.getX()>=775){
                    nowBg=allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(395);
                }
                //判断mario是否死亡
                if (mario.isDeath()){
                    JOptionPane.showMessageDialog(this,"马里奥死亡");

                    EndGame();
                }
                //判断游戏是否结束
                if (mario.isOK()){
                    JOptionPane.showMessageDialog(this,"恭喜你成功通关");

                    EndGame();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public Boolean IsEndGame(){
        return  EndGame;
    }
    private void EndGame(){
        EndGame = true;
        this.setVisible(false);
        Init();
    }
}
