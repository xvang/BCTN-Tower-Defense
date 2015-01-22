package com.padisDefense.game.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Duck extends Enemy{

    public Duck(){

        //health, armor, texture.
        super(true, 170,1,  new Sprite(new Texture("duck.png")));
        setName("duck");
        setRate(0.18f + (float)Math.random()*0.009f);

    }

    @Override
    public void animate(SpriteBatch batch){
        this.draw(batch, 1);
    }

}
