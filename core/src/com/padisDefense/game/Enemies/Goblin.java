package com.padisDefense.game.Enemies;

public class Goblin extends Enemy{


    public Goblin(){

        //health, armor, texture.
        super(100,1,  "goblin.png");
        setName("goblin");
        setRate(0.002f + (float)Math.random()*0.00009f);
        //userSetSize(new Vector2(10, 15f));
    }

}
