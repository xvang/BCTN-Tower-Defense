package com.padisDefense.game.Enemies;


import com.padisDefense.game.Padi;

public class BallStorage {

    Padi padi;
    public BallStorage(Padi p){
        padi = p;
    }

    public BallStorage(){}

    public void createBall(String type, Ball ball){

        if(type.equals("armyball"))initArmy(ball);

        else if(type.equals("blueball"))initBlue(ball);

        else if(type.equals("orangeball")) initOrange(ball);
        else if(type.equals("pinkball")) initPink(ball);
        else if(type.equals("purpleball")) initPurple(ball);
        else if(type.equals("redball")) initRed(ball);
        else if(type.equals("violetball")) initViolet(ball);
        else if(type.equals("yellowball")) initYellow(ball);
        else initGreen(ball);

    }


    public void initOrange(Ball ball){
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setName("orangeball");
        ball.setRate(0.25f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);
    }

    public void initGreen(Ball ball){
        ball.setName("greenball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.15f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);
    }

    public void initViolet(Ball ball){
        ball.setName("violetball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.15f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);
    }

    public void initArmy(Ball ball){
        ball.setName("armyball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.11f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);

    }

    public void initBlue(Ball ball){
        ball.setName("blueball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.21f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);
    }

    public void initYellow(Ball ball){
        ball.setName("yellowball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.09f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);
    }

    public void initPink(Ball ball){
        ball.setName("pinkball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.19f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);
    }

    public void initPurple(Ball ball){
        ball.setName("purpleball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.17f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);
    }

    public void initRed(Ball ball){
        ball.setName("redball");
        ball.set(padi.assets.skin_balls.getSprite(ball.getName()));
        ball.setRate(0.17f + (float)Math.random()*0.009f);
        ball.setOriginalRate(ball.getRate());
        ball.setOriginalArmor(5);
        ball.setHealth(30);

    }


}
