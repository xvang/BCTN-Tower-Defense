package com.padisDefense.game.Enemies;


public class Donut extends Enemy{

    public Donut(){
        //health, armor, texture
        super(100,10, "purpo.png");
        setName("donut");

        setRate(0.08f + (float)Math.random()*0.19f);


    }
}
