package com.padisDefense.game.Enemies;


public class BestGoblin extends Enemy{

    public BestGoblin(){
        //health, armor, texture
        super(100, 100, "bestgoblin.png");
        setName("bestgoblin");
        setRate(0.08f + (float)Math.random()*0.035f);

    }
}
