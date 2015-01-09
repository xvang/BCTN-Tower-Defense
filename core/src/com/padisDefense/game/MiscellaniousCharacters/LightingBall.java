package com.padisDefense.game.MiscellaniousCharacters;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

public class LightingBall extends Miscellaneous{


    public Bezier<Vector2> path;
    public Vector2 out;

    public LightingBall(){
        initMovement();
    }

    public void initMovement() {
        final int        FRAME_COLS = 10;
        final int        FRAME_ROWS = 2;
        Texture walkSheet = new Texture(Gdx.files.internal("animation/fx8_lightingball_all_me.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10

        //walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS*2];
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        animation = new Animation(1/((float)(FRAME_COLS*FRAME_ROWS)), walkFrames);      // #11


        stateTime = 0f;                         // #13

        path = new Bezier<Vector2>();
        path.set(new Vector2(0,0),
                new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2),
                new Vector2(Gdx.graphics.getWidth(), 0));
        out = new Vector2();

        currentFrame = animation.getKeyFrame(stateTime, true);  // #16

    }


    public void animate(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = animation.getKeyFrame(stateTime, true);  // #16

        stateTime += Gdx.graphics.getDeltaTime();

        //spriteBatch.draw(currentFrame, out.x, out.y);             // #17
        //batch.draw(currentFrame, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

    }






    @Override
    public void reset(){

    }
}
