package com.padisDefense.game.MiscellaniousCharacters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;


/**
 * Maybe this dragon will fly across the screen, and if the user
 * clicks on it, user will get some sort of bonus?
 * a smiling dragon is not fitting for an enemy, but I still want to use it somehow.
 * */
public class HappyDragon extends Sprite {

    private Texture texture;
    private Animation animation;
    private Array<TextureRegion> animationArray;
    private TextureRegion currentFrame;
    float stateTime = 0;

    public HappyDragon(){
        texture = new Texture("enemies/dragon.png");
        animationArray = new Array<TextureRegion>();
        int w = texture.getWidth();
        int h = texture.getHeight();

        for(int x = 0; x < 3; x++)
            animationArray.add(new TextureRegion(texture, w*x/3, 0, w/3, h));

        animation = new Animation(0.33f, animationArray);

    }


    public void run(){


    }


}
