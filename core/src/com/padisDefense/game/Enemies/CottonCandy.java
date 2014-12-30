package com.padisDefense.game.Enemies;



public class CottonCandy extends Enemy{

    public CottonCandy(){
        //health, armor, texture
        super(100,10, "turto.png");
        setName("cottoncandy");

        setRate(0.08f + (float)Math.random()*0.07f);
        //userSetSize(new Vector2(12f, 18f));
    }
}

