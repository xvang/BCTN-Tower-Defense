package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Toog on 1/3/2015.
 */
public class DefaultSpriteSheetTest extends ScreenAdapter {


    public DefaultSpriteSheetTest(){}


    Animation walkAnimation;
    SpriteBatch spriteBatch;
    TextureRegion currentFrame;

    float stateTime;

    @Override
    public void show() {
        final int        FRAME_COLS = 5;
        final int        FRAME_ROWS = 4;
        Texture walkSheet = new Texture(Gdx.files.internal("animation/fireball_1_me.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10

        //walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS*2];
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        Array<TextureRegion> chosenFrames = new Array<TextureRegion>();

        for(int x = 5; x < 15; x++)
            chosenFrames.add(walkFrames[x]);
        //copies the frames again, but in reverse order.
       /* for(int x = 0; x < walkFrames.length/2; x++){
            walkFrames[index++] = walkFrames[(walkFrames.length-1)/2-x];
        }*/


        walkAnimation = new Animation(1/((float)(FRAME_COLS*FRAME_ROWS)), walkFrames);      // #11
        //walkAnimation = new Animation(1/10f, chosenFrames);
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;                         // #13
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   // #14
        stateTime += Gdx.graphics.getDeltaTime()*2;           // #15
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16

        spriteBatch.begin();
        spriteBatch.draw(currentFrame, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);             // #17
        spriteBatch.end();
    }
}
