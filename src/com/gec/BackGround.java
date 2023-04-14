package com.gec;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {

    //当前场景显示图
    private BufferedImage bgImage=null;
    //记录当前第几个场景
    private int sort;
    //判断场景是否为最后一个
    private boolean flag;
    //所有障碍物
    private List<Obstacle> obstacleList = new ArrayList<>();
    //所有敌人
    private List<Enemy> enemyList = new ArrayList<>();

    //旗杆
    private BufferedImage gan = null;
    //城堡
    private BufferedImage tower = null;
    //判断mario是否到达旗杆位置
    private boolean isReach = false;
    //判断旗子是否落地
    private boolean isBase = false;

    public BackGround() {

    }

    public BackGround(int sort, boolean flag){
        this.sort = sort;
        this.flag = flag;

//        if (flag){
//            bgImage = StaticValue.bg;
//        }
        if (flag){
            bgImage = StaticValue.bg2;
        }else {
            bgImage = StaticValue.bg;
        }
        //判断关卡是否为第一关
        if(sort==1){
            //绘制第一关的地面，上地面type=1，下~=2
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }
            for (int j = 0; j <=120 ; j+=30) {
                for (int i = 0; i <27 ; i++) {
                    obstacleList.add(new Obstacle(i*30,570-j,2,this));
                }
            }

            //绘制砖块B-F
            for (int i = 200; i <=500 ; i+=30) {
                obstacleList.add(new Obstacle(i,300,0,this));
            }

            //绘制水管
            for (int i = 360; i <=600 ; i+=25) {
                if (i==360){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }

            //绘制1.1蘑菇敌人
            enemyList.add(new Enemy(580,385,true,1,this));
            //食人花
            enemyList.add(new Enemy(635,420,true,2,328,428,this));
        }

        //判断是否第二关
        if (sort==2){
            //绘制第一关的地面，上地面type=1，下~=2
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }
            for (int j = 0; j <=120 ; j+=30) {
                for (int i = 0; i <27 ; i++) {
                    obstacleList.add(new Obstacle(i*30,570-j,2,this));
                }
            }
            //绘制第一个水管
            for (int i = 360; i <=600 ; i+=25) {
                if (i==360){
                    obstacleList.add(new Obstacle(60,i,3,this));
                    obstacleList.add(new Obstacle(85,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(60,i,5,this));
                    obstacleList.add(new Obstacle(85,i,6,this));
                }
            }
            //绘制第二个水管
            for (int i = 330; i <=600 ; i+=25) {
                if (i==330){
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }

            //绘制砖块C
            obstacleList.add(new Obstacle(300,330,0,this));

            //绘制砖块BEG
            for (int i = 270; i <=330 ; i+=30) {
                    obstacleList.add(new Obstacle(i,360,0,this));
            }

            //绘制砖块ADFHI
            for (int i = 240; i <=360 ; i+=30) {
                    obstacleList.add(new Obstacle(i,390,0,this));
            }
            //绘制妨碍1砖块
            obstacleList.add(new Obstacle(240,300,0,this));
            //绘制空1-4砖块
            for (int i = 360; i <=540 ; i+=60) {
                obstacleList.add(new Obstacle(i,300,0,this));//270
            }

            //绘制1.1蘑菇敌人
            enemyList.add(new Enemy(200,385,true,1,this));
            enemyList.add(new Enemy(500,385,true,1,this));
            //食人花
            enemyList.add(new Enemy(75,420,true,2,328,418,this));
            enemyList.add(new Enemy(635,420,true,2,298,388,this));

        }

        //判断是否是第三关
        if(sort==3){
            //地面
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i*30,420,1,this));
            }
            for (int j = 0; j <=120 ; j+=30) {
                for (int i = 0; i <27 ; i++) {
                    obstacleList.add(new Obstacle(i*30,570-j,2,this));
                }
            }

            //绘制A-O砖块
            int temp = 290;
            for (int i = 390; i >=270 ; i-=30) {
                for (int j = temp; j <=410 ; j+=30) {
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp +=30;
            }
            //绘制P-R砖块
            temp = 60;
            for (int i = 390; i >=360 ; i-=30) {
                for (int j = temp; j <=90 ; j+=30) {
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp+=30;
            }
            //绘制旗杆
            gan = StaticValue.gan;

            //绘制城堡
            tower = StaticValue.tower;

            //绘制旗子到旗杆上
            obstacleList.add(new Obstacle(515,220,8,this));

            enemyList.add(new Enemy(150,385,true,1,this));

        }

    }


    public BufferedImage getBgImage() {
        return bgImage;
    }
    public int getSort() {
        return sort;
    }
    public boolean isFlag() {
        return flag;
    }
    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }
    public BufferedImage getGan() {
        return gan;
    }
    public BufferedImage getTower() {
        return tower;
    }

    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }

    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
        this.enemyList = enemyList;
    }

}
