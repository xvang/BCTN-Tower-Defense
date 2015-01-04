package com.padisDefense.game.MiscellaniousCharacters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


//TODO: maybe create a class that FirePlace, Explosion, and others can inherit from?
//Seems to be a lot of repeating code.
public class FirePlace extends Sprite implements Pool.Poolable{

    public FirePlace(){
        initialize();
    }
    private static final int        FRAME_COLS = 10;         // #1
    private static final int        FRAME_ROWS = 4;         // #2

    Animation walkAnimation;          // #3
    Texture walkSheet;              // #4
    SpriteBatch spriteBatch;            // #6
    TextureRegion currentFrame;           // #7

    float stateTime;                                        // #8
    public Vector2 firePlacePosition;
    public boolean alive = true;


    public void initialize(){
        walkSheet = new Texture(Gdx.files.internal("animation/fire1b_all_me.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(1/((float)(FRAME_ROWS*FRAME_COLS)), walkFrames);      // #11
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;                         // #13

        firePlacePosition = new Vector2();

    }


    public void animate(){
        if(alive){
            stateTime += Gdx.graphics.getDeltaTime()/2;           // #15

            currentFrame = walkAnimation.getKeyFrame(stateTime, false);  // #16


            spriteBatch.begin();

            spriteBatch.draw(currentFrame, firePlacePosition.x, firePlacePosition.y);             // #17

            spriteBatch.end();

            if(walkAnimation.isAnimationFinished(stateTime)){
                alive = false;
                stateTime = 0f;

            }
        }

    }


    public void reset(){
        alive = true;
        stateTime = 0f;

    }
}
