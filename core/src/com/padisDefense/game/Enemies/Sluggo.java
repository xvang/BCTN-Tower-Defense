package com.padisDefense.game.Enemies;



public class Sluggo extends Enemy{


    public Sluggo(){

        //health, armor, texture
        super(100,1, "sluggo.png");
        setName("sluggo");
        //setSize(10f, 10f);
        setRate(0.08f + (float)Math.random()*0.05f);
        //userSetSize(new Vector2(12f, 18f));
    }

}
