package com.padisDefense.game.Enemies;



public class Sluggo extends Enemy{


    public Sluggo(){

        //health, armor, texture
        super(100,10, "sluggo.png");
        setName("sluggo");
        //setSize(25f, 25f);
        setRate(0.08f + (float)Math.random()*0.05f);
        //userSetSize(new Vector2(12f, 18f));
    }

}
