package com.padisDefense.game.Enemies;

public class Duck extends Enemy{

    public Duck(){

        //health, armor, texture.
        super(10,10,  "duck.png");
        setName("duck");
        setRate(0.001f + (float)Math.random()*0.0009f);

    }

}
