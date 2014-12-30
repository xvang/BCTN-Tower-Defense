package com.padisDefense.game.Enemies;


public class Cake extends Enemy {

    public Cake(){
        //health, armor, texture
        super(100,10, "lobbo.png");
        setName("cake");
        setRate(0.11f + (float)Math.random()*0.09f);
    }
}
