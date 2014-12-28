package com.padisDefense.game.Enemies;


public class Numbo extends Enemy{

    public Numbo(){
        //health, armor, texture
        super(100,10, "numbo.png");
        setName("numbo");
        //setSize(25f, 25f);
        setRate(0.22f + (float)Math.random()*0.03f);
        //userSetSize(new Vector2(12f, 18f));
    }
}
