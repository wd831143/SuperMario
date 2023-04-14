package com.gec;

import java.awt.image.BufferedImage;
public class Mario implements Runnable{
    public enum MarioMoveState{
        run_right,
        run_left,
        jump_right,
        jump_left
    }
    public enum MarioBodyState{
        Normal,
        Big,
        diaodiao
    }
    //用于表示横纵坐标
    private int x;
    private int y;
    //用于表示当前状态
    private String status;
    //用于表示当前状态对应的图像
    private BufferedImage show = null;
    //定义一个背景对象，获取障碍物信息
    private BackGround backGround = new BackGround();
    //实现马里奥动作
    private Thread thread = null;
    //马里奥的移动速度
    private int xSpeed;
    //马里奥的跳跃速度
    private int ySpeed;
    //定义一个索引
    private int index;
    //表示马里奥上升时间
    private int upTime = 0;
    //马里奥与门口
    private  boolean isOK;
    //mario死亡
    private boolean isDeath = false;


    public Mario(){}
    public Mario(int x,int y){
        this.x=x;
        this.y=y;
        show = StaticValue.stand_R;
        this.status = "stand--right";
        thread = new Thread(this);
        thread.start();
    }

    //mario 死亡方法
    public void death(){
        isDeath = true;
    }

    //mario左移
    public void leftMove(){
        //改变速度
        xSpeed = -5;
        //判断马里奥是否碰到了旗子
        if (backGround.isReach()){
            xSpeed=0;
        }
        //判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        }else {
            status = "move--left";
        }
    }

    //mario右移
    public void rightMove(){
        //改变速度
        xSpeed = 5;
        //判断马里奥是否碰到了旗子
        if (backGround.isReach()){
            xSpeed=0;
        }
        //判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        }else {
            status = "move--right";
        }
    }

    //mario左停
    public void leftStop(){
        xSpeed = 0;
        if (status.indexOf("jump")!=-1){
            status = "jump--left";
        }else {
            status = "stop--left";
        }
    }

    //mario右停
    public void rightStop(){
        xSpeed = 0;
        if (status.indexOf("jump")!=-1){
            status = "jump--right";
        }else {
            status = "stop--right";
        }
    }

    //mario jump
    public void jump(){
        if (status.indexOf("jump") == -1){
            if (status.indexOf("left")!=-1){
                status = "jump--left";
            }else {
                status="jump==right";
            }
            ySpeed = -10;
            upTime = 7;
        }
        //判断马里奥是否碰到了旗子
        if (backGround.isReach()){
            ySpeed=0;
        }
    }

    //mario fall
    private void fall(){
        if (status.indexOf("left")!=-1) {
            status ="jump--left";
        } else {
            status="jump==right";
        }
            ySpeed = 10;
        }

    @Override
    public void run() {
        while (true){
            //判断是否处于障碍物上
            boolean onObstacle = false;
            //判断是否可以往右走
            boolean canRight = true;
            //判断是否可以往左走
            boolean canLeft = true;
            //判断马里奥是否到达旗杆位置
            if (backGround.isFlag() && this.x>=500){
                this.backGround.setReach(true);
                //判断旗子是否下落完成
                if (this.backGround.isBase()){
                    status = "move--right";
                    if (x<690){
                        x +=5;
                    }else {
                        isOK=true;
                    }
                }else {
                    if (y < 395){
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }
                    if (y>395){
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            }else {
                //遍历当前场景所有障碍物
                for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                    Obstacle ob = backGround.getObstacleList().get(i);
                    //判断马里奥是否位于障碍物上
                    if (ob.getY() == this.y + 25 &&
                            (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        onObstacle = true;
                    }

                    //判断是否跳起来顶到砖块
                    if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) &&
                            (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        if (ob.getType() == 0) {
                            backGround.getObstacleList().remove(ob);
                        }
                        upTime = 0;
                    }

                    //判断是否可以往右走
                    if (ob.getX() == this.x + 25 &&
                            (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                        canRight = false;
                    }

                    //判断是否可以往左走
                    if (ob.getX() == this.x + 30 &&
                            (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) {
                        canLeft = false;
                    }
                }
                
                //判断mario是否碰到敌人或踩死蘑菇敌人
                for (int i = 0; i < backGround.getEnemyList().size(); i++) {
                    Enemy e = backGround.getEnemyList().get(i);

                    if (e.getY() == this.y +20 && (e.getX() - 25 < this.x &&
                            e.getX() + 35 >=this.x)){
                        if (e.getType()==1){
                            e.death();
                            upTime = 3;
                            ySpeed = -10;
                        }else if (e.getType()==2){
                            //mario死亡
                            death();
                        }
                    }
                    if ((e.getX() + 35 >this.x && e.getX() - 25 < this.x) &&
                            (e.getY() + 35 > this.y && e.getY()-20 < this.y)){
                        //mario死亡
                        death();
                    }
                }
                
                //进行mario跳跃操作
                if (onObstacle && upTime == 0) {
                    if (status.indexOf("left") != -1) {
                        if (xSpeed != 0) {
                            status = "move--left";
                        } else {
                            status = "stop-left";
                        }
                    } else {
                        if (xSpeed != 0) {
                            status = "move--right";
                        } else {
                            status = "stop--right";
                        }
                    }
                } else {
                    if (upTime != 0) {
                        upTime--;
                    } else {
                        fall();
                    }
                    y += ySpeed;
                }
            }

        if ((canLeft && xSpeed<0)||(canRight && xSpeed>0)){
            x+=xSpeed;
            //判断马里奥是否到了最左边
            if (x<0){
                x=0;
            }
        }

        //判断当前是否是移动状态
            if (status.contains("move")){
                index=index==0?1:0;
            }
            //判断是否向左移动
            if ("move--left".equals(status)){
                show = StaticValue.run_L.get(index);
            }
            //判断是否向右移动
            if ("move--right".equals(status)){
                show = StaticValue.run_R.get(index);
            }
            //判断是否向左停止
            if ("stop--left".equals(status)){
                show = StaticValue.stand_L;
            }
            //判断是否向右停止
            if ("stop--right".equals(status)){
                show = StaticValue.stand_R;
            }
            //判断是否向左跳跃
            if ("jump-left".equals(status)){
                show = StaticValue.jump_L;
            }
            //判断是否向左跳跃
            if ("jump-right".equals(status)){
                show = StaticValue.jump_R;
            }

            try {
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public BufferedImage getShow() {
        return show;
    }
    public void setShow(BufferedImage show) {
        this.show = show;
    }
    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
    public boolean isOK() {
        return isOK;
    }
    public void setOK(boolean OK) {
        isOK = OK;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }
}
