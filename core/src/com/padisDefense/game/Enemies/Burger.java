package com.padisDefense.game.Enemies;


public class Burger extends Enemy{

    public Burger(){
        //health, armor, texture
        super(100,10, "nocto.png");
        setName("burger");

        setRate(0.14f + (float)Math.random()*0.07f);
    }
}
