package com.padisDefense.game.Enemies;

public class BiggerGoblin extends Enemy{

    public BiggerGoblin(){
        //health, armor, texture
        super(100,10, "sluggo.png");
        setName("biggergoblin");
        setSize(25f, 25f);
        setRate(0.2f + (float)Math.random()*0.1f);
        //userSetSize(new Vector2(12f, 18f));
    }

}
