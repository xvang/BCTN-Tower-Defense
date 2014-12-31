package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * TEXTURE REGIONS HAVE (0,0) AT TOP LEFT.
 * **/
public class TEST2 extends ScreenAdapter {

    private Sprite background;
    private static final int        FRAME_COLS = 4;         // #1
    private static final int        FRAME_ROWS = 1;         // #2

    Animation                       walkAnimation;          // #3
    Texture                         walkSheet;              // #4
    TextureRegion[]                 walkFrames;             // #5
    SpriteBatch                     spriteBatch;            // #6
    TextureRegion                   currentFrame;           // #7

    Array<TextureRegion> leftToRight, down, up, rightToLeft;
    Array<Animation> allAnimations;
    Animation currentAnimation;
    int aaCounter = 0;

    float stateTime;                                        // #8

    @Override
    public void show() {
        background = new Sprite(new Texture("test6.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        walkSheet = new Texture("animation/walk_vanilla.png"); // #9


        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, (walkSheet.getHeight()/FRAME_ROWS));              // #10

        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }





        int w = walkSheet.getWidth();
        int h = walkSheet.getHeight();

        leftToRight = new Array<TextureRegion>();
        leftToRight.add(new TextureRegion(walkSheet, 0,h*3/4, w/4, walkSheet.getHeight()/4));
        leftToRight.add(new TextureRegion(walkSheet, w/4,h*3/4, walkSheet.getWidth()/4, h/4));
        leftToRight.add(new TextureRegion(walkSheet, w/2,h*3/4, walkSheet.getWidth()/4, h/4));
        leftToRight.add(new TextureRegion(walkSheet, w*3/4,h*3/4, walkSheet.getWidth()/4, h/4));

        down = new Array<TextureRegion>();
        down.add(new TextureRegion(walkSheet, 0,h/2, w/4, h/4));
        down.add(new TextureRegion(walkSheet, w/4,h/2, w/4, h/4));
        down.add(new TextureRegion(walkSheet, w/2,h/2, w/4, h/4));
        down.add(new TextureRegion(walkSheet, w*3/4,h/2, w/4, h/4));

        up = new Array<TextureRegion>();
        up.add(new TextureRegion(walkSheet, 0,0, w/4, h/4));
        up.add(new TextureRegion(walkSheet, w/4,0, w/4, h/4));
        up.add(new TextureRegion(walkSheet, w/2,0, w/4, h/4));
        up.add(new TextureRegion(walkSheet, w*3/4,0, w/4, h/4));

        rightToLeft = new Array<TextureRegion>();
        rightToLeft.add(new TextureRegion(walkSheet, 0,h/4, w/4, h/4));
        rightToLeft.add(new TextureRegion(walkSheet, w/4,h/4, w/4, h/4));
        rightToLeft.add(new TextureRegion(walkSheet, w/2,h/4, w/4, h/4));
        rightToLeft.add(new TextureRegion(walkSheet, w*3/4,h/4, w/4, h/4));


        allAnimations = new Array<Animation>();
        allAnimations.add(new Animation(0.25f, leftToRight));
        allAnimations.add(new Animation(0.25f, down));
        allAnimations.add(new Animation(0.25f, up));
        allAnimations.add(new Animation(0.25f, rightToLeft));

        walkAnimation = new Animation(0.25f, leftToRight);      // #11
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;

        currentAnimation = allAnimations.get(0);

    }



    float iteration = 0;
    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14

        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = currentAnimation.getKeyFrame(stateTime, true);  // #16
        //currentFrame.setRegionHeight(200);
        //currentFrame.setRegionWidth(200);
        spriteBatch.begin();
        //background.draw(spriteBatch, 1);
        spriteBatch.draw(currentFrame, Gdx.graphics.getWidth()/2-currentFrame.getRegionWidth()/2, Gdx.graphics.getHeight()/2 - currentFrame.getRegionHeight()/2);             // #17
        spriteBatch.end();


        iteration += Gdx.graphics.getDeltaTime();


        if(iteration >= 5f && currentAnimation.isAnimationFinished(stateTime)){
            stateTime = 0f;
            if(aaCounter+1 < allAnimations.size)
                aaCounter++;
            else
                aaCounter = 0;

            currentAnimation = allAnimations.get(aaCounter);
            iteration = 0;
        }
    }

}


//https://github.com/libgdx/libgdx/wiki/2D-Animation