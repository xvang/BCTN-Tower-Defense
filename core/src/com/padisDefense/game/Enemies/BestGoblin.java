package com.padisDefense.game.Enemies;


public class BestGoblin extends Enemy{

    public BestGoblin(){
        //health, armor, texture
        super(100, 100, "purpo.png");
        setName("bestgoblin");
        setRate(0.0001f + (float)Math.random()*0.0015f);//will have roughly same speed, but not bunch up.
       // userSetSize(new Vector2(15, 20f));
    }
}
