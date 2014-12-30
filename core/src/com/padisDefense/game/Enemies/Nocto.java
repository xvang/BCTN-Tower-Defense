package com.padisDefense.game.Enemies;


public class Nocto extends Enemy{

    public Nocto(){
        //health, armor, texture
        super(100,10, "nocto.png");
        setName("nocto");

        setRate(0.14f + (float)Math.random()*0.07f);
    }
}
