package com.padisDefense.game.Enemies;


public class Lobbo extends Enemy{

    public Lobbo(){
        //health, armor, texture
        super(100,10, "lobbo.png");
        setName("lobbo");
        setRate(0.11f + (float)Math.random()*0.09f);

    }
}
