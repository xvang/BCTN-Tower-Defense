package com.padisDefense.game.MiscellaniousCharacters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;


abstract class Miscellaneous extends Sprite implements Pool.Poolable{

    public Animation animation;
    public TextureRegion currentFrame;
    public float stateTime, pathTime;
    public boolean alive = true;


    public void animate(SpriteBatch batch){}


}



/** Might need this later.
 * This function allows the fireball to start from left/right of screen.
 * the animations don't match up with it, so for not the fireballs are only allowed
 * to spawn from directly above the screen.
 * */
   /* public void generatePath(){

        //fireball will start outside screen, and end up somewhere in a designated area
        //within screen.
        Vector2 start, end;
        int choice = (int)(Math.random()*2);
        float positionX = 0;
        if(choice == 1){//fireball starts to the right of screen.
            positionX = (float)(Math.random()*100f+Gdx.graphics.getWidth());
        }
        else{//fireball starts to the left of screen.
            positionX = (float)(0 - Math.random()*100f);
        }

        //no matter where fireball starts, it should go from top to bottom.
        //the lowest it should spawn is the top third of the screen.
        start = new Vector2(positionX, (float)(Math.random()*100f+ (Gdx.graphics.getHeight()*2/3)));

        end = new Vector2((float)((Math.random()*(Gdx.graphics.getWidth()-200f)) + 100f),
                (float)((Math.random()*(Gdx.graphics.getHeight()- Gdx.graphics.getHeight()/3)) + 20f));


        path.add(new Bezier<Vector2>(start, end));


    }*/