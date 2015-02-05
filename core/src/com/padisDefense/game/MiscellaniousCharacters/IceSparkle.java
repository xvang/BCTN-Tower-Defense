package com.padisDefense.game.MiscellaniousCharacters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;




public class IceSparkle{

    public Animation walkAnimation;
    TextureRegion currentFrame;
    public Texture walkSheet;
    public float stateTime;

    public IceSparkle(){
        final int        FRAME_ROWS = 8;
        final int        FRAME_COLS = 8;

        walkSheet = new Texture(Gdx.files.internal("animation/particlefx_07.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10

        //walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS*2];
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new Animation(1/((float)(FRAME_COLS*FRAME_ROWS)), walkFrames);

        stateTime = 0f;

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16

    }

    public void animate(SpriteBatch batch, Vector2 position) {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);

    }

}
