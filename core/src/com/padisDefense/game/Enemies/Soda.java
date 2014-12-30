package com.padisDefense.game.Enemies;


public class Soda extends Enemy{

    public Soda(){
        //health, armor, texture
        super(100, 100, "bestgoblin.png");
        setName("soda");
        setRate(0.08f + (float)Math.random()*0.035f);

    }
}
