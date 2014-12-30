package com.padisDefense.game.Enemies;



public class Cookie extends Enemy{


    public Cookie(){

        //health, armor, texture
        super(100,1, "sluggo.png");
        setName("cookie");

        setRate(0.08f + (float)Math.random()*0.05f);

    }

}
