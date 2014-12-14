package com.padisDefense.game.Enemies;

import com.badlogic.gdx.math.Vector2;

public class Goblin extends Enemy{


    public Goblin(){

        //health, armor, location.
        super(50,2, new Vector2(10f, 10f));
        setName("goblin");
        setRate(0.001f);
    }

}
