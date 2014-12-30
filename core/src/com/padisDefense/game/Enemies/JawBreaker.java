package com.padisDefense.game.Enemies;






public class JawBreaker extends Enemy {

    public JawBreaker(){
        //health, armor, texture
        super(100,10, "slimo.png");
        setName("jawbreaker");

        setRate(0.06f + (float)Math.random()*0.09f);


    }


}
