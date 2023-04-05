package com.gec;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
    //存储当前坐标
    private int x,y;
    //敌人类型
    private int type;
    //敌人当前移动方向
    private boolean face_to = true;
    //显示敌人图像
    private BufferedImage show;
    //背景对象
    private BackGround bg;
    //食人花运动极限范围
    private int max_up = 0;
    private int max_down = 0;
    //定义线程对象
    private Thread thread = new Thread(this);
    //定义当前图片的状态
    private int image_type = 0;

    //构造函数
        //蘑菇
    public Enemy(int x,int y,boolean face_to,int type,BackGround bg){
        this.x=x;
        this.y=y;
        this.face_to = face_to;
        this.type = type;
        this.bg = bg;
        show = StaticValue.mogu.get(0);
        thread.start();
    }

        //食人花
    public Enemy(int x,int y,boolean face_to,int type,int max_up,int max_down,BackGround bg){
        this.x=x;
        this.y=y;
        this.face_to = face_to;
        this.type = type;
        this.max_up = max_up;
        this.max_down = max_down;
        this.bg = bg;
        show = StaticValue.flower.get(0);
        thread.start();
    }

    //死亡方法
    public void death(){
        show = StaticValue.mogu.get(2);
        this.bg.getEnemyList().remove(this);
    }

    @Override
    public void run() {
        while (true){
            //判断是否蘑菇
            if (type==1){
                if (face_to){
                    this.x -=2;
                }else {
                    this.x +=2;
                }
                image_type = image_type ==1 ? 0 : 1;

                show = StaticValue.mogu.get(image_type);
            }

            //定义两个布尔变量
            boolean canLeft = true;
            boolean canRight = true;



            for (int i = 0; i <bg.getObstacleList().size() ; i++) {
                Obstacle ob1 = bg.getObstacleList().get(i);

                //判断是否可以右走
                if (ob1.getX() == this.x +36 &&
                        (ob1.getY() + 65 >this.y&&ob1.getY()-35<this.y)){
                    canRight = false;
                }

                //判断是否可以向左走
                if(ob1.getX() == this.x - 36 &&
                        (ob1.getY() + 65 >this.y && ob1.getY()-35 <this.y)){
                    canLeft = false;
                }
            }
            if (face_to && !canLeft || this.x==0){
                face_to =false;
            }else if ((!face_to)&&(!canRight) || this.x==764){
                face_to = true;
            }

            //判断是否是食人花敌人
            if(type==2){
                if (face_to){
                    this.y -=2;
                }else {
                    this.y +=2;
                }

                image_type = image_type == 1 ? 0 :1;

                //食人花是都到达极限位置
                if(face_to && (this.y==max_up)){
                    face_to = false;
                }
                if ((!face_to) && (this.y==max_down)){
                    face_to = true;
                }

                show = StaticValue.flower.get(image_type);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public int getType() {
        return type;
    }
}
