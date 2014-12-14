package com.padisDefense.game.Enemies;


import com.badlogic.gdx.math.Vector2;

public class BestGoblin extends Enemy{

    public BestGoblin(){

        //health, armor, location
        super(40, 80, new Vector2(20f,20f));
        setName("bestgoblin");
        setRate(0.0006f);
    }
}
