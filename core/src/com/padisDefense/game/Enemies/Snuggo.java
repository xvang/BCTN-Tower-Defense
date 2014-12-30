package com.padisDefense.game.Enemies;






public class Snuggo extends Enemy {

    public Snuggo(){
        //health, armor, texture
        super(100,10, "slimo.png");
        setName("snuggo");

        setRate(0.06f + (float)Math.random()*0.09f);


    }


}
