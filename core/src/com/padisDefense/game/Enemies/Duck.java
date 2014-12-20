package com.padisDefense.game.Enemies;

/**
 * Created by Toog on 12/20/2014.
 */
public class Duck extends Enemy{

    public Duck(){

        //health, armor, texture.
        super(200,1,  "duck.png");
        setName("duck");
        setRate(0.001f + (float)Math.random()*0.0009f);

    }

}
