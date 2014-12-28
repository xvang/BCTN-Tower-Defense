package com.padisDefense.game.Enemies;


public class Purpo extends Enemy{

    public Purpo(){
        //health, armor, texture
        super(100,10, "purpo.png");
        setName("purpo");
        //setSize(25f, 25f);
        setRate(0.05f + (float)Math.random()*0.09f);
        //userSetSize(new Vector2(12f, 18f));

    }
}
