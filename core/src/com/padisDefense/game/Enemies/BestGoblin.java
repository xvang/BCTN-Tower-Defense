package com.padisDefense.game.Enemies;


public class BestGoblin extends Enemy{

    public BestGoblin(){
        //health, armor, texture
        super(100, 100, "bestgoblin.png");
        setName("bestgoblin");
        setRate(0.0002f + (float)Math.random()*0.00009f);//will have roughly same speed, but not bunch up.
       // userSetSize(new Vector2(15, 20f));
    }
}
