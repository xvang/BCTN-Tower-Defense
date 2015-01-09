package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class DefaultSpriteSheetTest extends ScreenAdapter {


    public DefaultSpriteSheetTest(){}


    Animation walkAnimation;
    SpriteBatch spriteBatch;
    TextureRegion currentFrame;

    Bezier<Vector2> path;
    Vector2 out;
    float time = 0;
    float stateTime;

    @Override
    public void show() {
        final int        FRAME_COLS = 19;
        final int        FRAME_ROWS = 1;
        Texture walkSheet = new Texture(Gdx.files.internal("towers/tower.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10

        //walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS*2];
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new Animation(1/((float)(FRAME_COLS*FRAME_ROWS)), walkFrames);      // #11

        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;                         // #13

        path = new Bezier<Vector2>();
        path.set(new Vector2(0,0),
                new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2),
                new Vector2(Gdx.graphics.getWidth(), 0));
        out = new Vector2();

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   // #14
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16

        time += Gdx.graphics.getDeltaTime();

        if(time >= 1)
            time = 0;
        path.valueAt(out, time);

        spriteBatch.begin();
        //spriteBatch.draw(currentFrame, out.x, out.y);             // #17
        spriteBatch.draw(currentFrame, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        spriteBatch.end();
    }
}
