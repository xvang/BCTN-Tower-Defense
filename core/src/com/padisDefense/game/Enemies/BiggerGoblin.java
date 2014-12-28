package com.padisDefense.game.Enemies;

public class BiggerGoblin extends Enemy{

    public BiggerGoblin(){
        //health, armor, texture
        super(1,1, "sluggo.png");
        setName("biggergoblin");
        setSize(25f, 25f);
        setRate(0.13f + (float)Math.random()*0.2f);
        //userSetSize(new Vector2(12f, 18f));
    }

}
