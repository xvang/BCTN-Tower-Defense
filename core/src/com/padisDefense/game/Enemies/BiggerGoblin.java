package com.padisDefense.game.Enemies;

public class BiggerGoblin extends Enemy{

    public BiggerGoblin(){
        //health, armor, texture
        super(100,1, "nocto.png");
        setName("biggergoblin");
        //setSize(15f, 15f);
        setRate(0.07f + (float)Math.random()*0.1f);
        //userSetSize(new Vector2(12f, 18f));
    }

}
