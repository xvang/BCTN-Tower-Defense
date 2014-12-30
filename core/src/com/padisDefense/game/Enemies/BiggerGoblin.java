package com.padisDefense.game.Enemies;

public class BiggerGoblin extends Enemy{

    public BiggerGoblin(){
        //health, armor, texture
        super(100,1, "biggergoblin.png");
        setName("biggergoblin");

        setRate(0.07f + (float)Math.random()*0.1f);

    }

}
