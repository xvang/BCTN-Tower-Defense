package com.padisDefense.game.Enemies;


public class Nocto extends Enemy{

    public Nocto(){
        //health, armor, texture
        super(100,10, "nocto.png");
        setName("nocto");
        //setSize(25f, 25f);
        setRate(0.15f + (float)Math.random()*0.09f);
        //userSetSize(new Vector2(12f, 18f));
    }
}
