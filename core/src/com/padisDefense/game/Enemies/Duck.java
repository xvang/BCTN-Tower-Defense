package com.padisDefense.game.Enemies;

public class Duck extends Enemy{

    public Duck(){

        //health, armor, texture.
        super(true, 170,100,  "duck.png");
        setName("duck");
        setRate(0.11f + (float)Math.random()*0.009f);

    }

}
