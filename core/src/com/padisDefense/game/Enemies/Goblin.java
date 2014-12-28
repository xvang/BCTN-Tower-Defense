package com.padisDefense.game.Enemies;

public class Goblin extends Enemy{


    public Goblin(){

        //health, armor, texture.
        super(300,1,  "lobbo.png");
        setName("goblin");
        setRate(0.1f + (float)Math.random()*0.06f);
        this.setSize(20f, 20f);
        //userSetSize(new Vector2(10, 15f));
    }
}
