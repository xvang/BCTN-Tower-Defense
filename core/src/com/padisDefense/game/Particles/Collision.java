package com.padisDefense.game.Particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Toog on 12/22/2014.
 */
public class Collision extends Sprite {


    private static final int FRAME_ROWS = 8;
    private static final int FRAME_COL = 8;
    Animation animation;
    Texture sheet;
    TextureRegion[] frames;
    TextureRegion currentFrame;
    float stateTime;
    Vector2 position;
    public Collision(Texture t){
        init();
    }

    public void init(){

    }
}
