package com.padisDefense.game.Enemies;


public class Lollipop extends Enemy{

    public Lollipop(){
        //health, armor, texture
        super(100,10, "numbo.png");
        setName("lollipop");

        setRate(0.15f + (float)Math.random()*0.03f);
        //userSetSize(new Vector2(12f, 18f));
    }
}
