package com.padisDefense.game.Enemies;


public class Goblin extends Enemy{

    public Goblin(){
        //health, armor, texture
        super(100,10, "nocto.png");
        setName("goblin");

        setRate(0.14f + (float)Math.random()*0.07f);
    }
}
