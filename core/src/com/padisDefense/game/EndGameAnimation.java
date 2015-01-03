package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.MiscellaniousCharacters.FireBall;


public class EndGameAnimation {

    public EndGameAnimation(){
        init();
    }

    private static final int        FRAME_COLS = 3;         // #1
    private static final int        FRAME_ROWS = 4;         // #2

    Animation                       walkAnimation;          // #3
    Texture                         walkSheet;              // #4
    TextureRegion[]                 walkFrames;             // #5
    SpriteBatch                     spriteBatch;            // #6
    TextureRegion                   currentFrame;           // #7
    Array<FireBall> activeFireBall;
    Pool<FireBall> fireBallPool;
    float stateTime;                                        // #8
    Vector2 position;

    public void init() {
        walkSheet = new Texture(Gdx.files.internal("animation/explosion_1.png")); // #9

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(1/12f, walkFrames);      // #11
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;                         // #13


        activeFireBall = new Array<FireBall>();
        fireBallPool = new Pool<FireBall>() {
            @Override
            protected FireBall newObject() {
                FireBall f = new FireBall();
                f.setPosition(-100f, -100f);
                return f;
            }
        };


        for(int x = 0; x < 3; x++){
            activeFireBall.add(fireBallPool.obtain());
        }
        fireBallPool.freeAll(activeFireBall);
        activeFireBall.clear();

        position = new Vector2();
    }

    float interval = 0f;

    public void run() {
        stateTime += Gdx.graphics.getDeltaTime();           // #15

        if(activeFireBall.size < 3)//to prevent interval from infinitely increasing.
            interval += Gdx.graphics.getDeltaTime();

        currentFrame = walkAnimation.getKeyFrame(stateTime, false);  // #16
        interval += Gdx.graphics.getDeltaTime();
        spriteBatch.begin();


        for(int x = 0; x < activeFireBall.size; x++){
            activeFireBall.get(x).animate();
        }
        if(activeFireBall.size < 3 && interval >= 3f){
            FireBall f = fireBallPool.obtain();
            activeFireBall.add(f);
            interval = 0;
        }

        spriteBatch.end();



    }
}

