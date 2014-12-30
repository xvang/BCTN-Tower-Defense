package com.padisDefense.game.Enemies;

public class IceCream extends Enemy{

    public IceCream(){
        //health, armor, texture
        super(100,1, "biggergoblin.png");
        setName("icecream");

        setRate(0.07f + (float)Math.random()*0.1f);

    }

}
