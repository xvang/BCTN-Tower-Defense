package com.padisDefense.game.Enemies;


public class Nocto extends Enemy{

    public Nocto(){
        //health, armor, texture
        super(100,10, "nocto.png");
        setName("nocto");
        //setSize(25f, 25f);
        setRate(0.0006f + (float)Math.random()*0.0009f);
        //userSetSize(new Vector2(12f, 18f));
    }
}
