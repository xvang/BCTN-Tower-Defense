package com.padisDefense.game.Enemies;



public class Turto extends Enemy{

    public Turto(){
        //health, armor, texture
        super(100,10, "turto.png");
        setName("turto");
        //setSize(25f, 25f);
        setRate(0.08f + (float)Math.random()*0.07f);
        //userSetSize(new Vector2(12f, 18f));
    }
}

