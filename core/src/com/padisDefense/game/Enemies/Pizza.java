package com.padisDefense.game.Enemies;

public class Pizza extends Enemy{


    public Pizza(){

        //health, armor, texture.
        super(100,1,  "goblin.png");
        setName("pizza");
        setRate(0.1f + (float)Math.random()*0.06f);
    }
}
