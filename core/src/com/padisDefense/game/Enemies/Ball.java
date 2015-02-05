package com.padisDefense.game.Enemies;



import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball extends Enemy {

    public Ball(){
    }


    @Override
    public void animate(SpriteBatch batch){
        this.draw(batch, 1);
    }



}
