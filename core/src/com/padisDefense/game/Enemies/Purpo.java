package com.padisDefense.game.Enemies;


public class Purpo extends Enemy{

    public Purpo(){
        //health, armor, texture
        super(100,10, "purpo.png");
        setName("purpo");

        setRate(0.08f + (float)Math.random()*0.19f);


    }
}
