package com.padisDefense.game.Enemies;

public class BiggerGoblin extends Enemy{

    public BiggerGoblin(){
        //health, armor, texture
        super(100,10, "biggergoblin.png");
        setName("biggergoblin");
        setRate(0.0006f + (float)Math.random()*0.0009f);
        //userSetSize(new Vector2(12f, 18f));
    }


}
