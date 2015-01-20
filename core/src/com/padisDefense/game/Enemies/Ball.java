package com.padisDefense.game.Enemies;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball extends Enemy {

    public Ball(String type, Sprite sprite){

        super(true, 100, 1, sprite);
        setRate(0.08f + (float)Math.random()*0.035f);

        //some of the stats are declared in the constructor in enemy.java
        //here, specific stats for each ball is set, if needed.
        if(type.equals("orange")) initOrange();
        else if(type.equals("blue")) initBlue();
        else if(type.equals("green")) initGreen();
        else if(type.equals("pinkball")) initPink();
        else if(type.equals("purple")) initPurple();
        else if(type.equals("violet")) initViolet();
        else if(type.equals("army")) initArmy();
        else if(type.equals("yellow")) initYellow();





    }


    @Override
    public void animate(SpriteBatch batch){
        this.draw(batch, 1);
    }

    public void initOrange(){
        setName("orangeball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }

    public void initGreen(){
        setName("greenball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }

    public void initViolet(){
        setName("violetball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }

    public void initArmy(){
        setName("armyball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }

    public void initBlue(){
        setName("blueball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }

    public void initYellow(){
        setName("yellowball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }

    public void initPink(){
        setName("pinkball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }

    public void initPurple(){
        setName("purpleball");
        setRate(0.25f + (float)Math.random()*0.035f);
    }


}
