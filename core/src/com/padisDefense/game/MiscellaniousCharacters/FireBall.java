package com.padisDefense.game.MiscellaniousCharacters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

//TODO; make a ball in front of the falling sprite? make the fireball actually have a ball.

//TODO: THERE IS A STUPID MEMORY LEAK SOMEWHERE. A TINY AMOUNT OF MEMORY(0.01MB) IS INFINITELY ALLOCATED.
public class FireBall extends Miscellaneous {

    public Bezier<Vector2> path;
    public Vector2 out;

    public FireBall(){

        stateTime = 0f;
        pathTime = 0f;
        path = new Bezier<Vector2>();
        out = new Vector2();
        initMovement();
    }

    public void initMovement(){

        Texture texture = new Texture("animation/fire_02.png"); //Loading in the sprite sheet.
        int w = texture.getWidth();
        int h = texture.getHeight();
        int ROWS = 8;
        int COLS = 8;

        //height and width for a single frame.
        int wSingle = w/COLS;
        int hSingle = h/ROWS;//

        Array<TextureRegion>  textureRegionArray = new Array<TextureRegion>();
        //organizing the spritesheet into an array. or vector. both?
        for(int x = 0; x < ROWS; x++)
            for(int y = 0; y < COLS; y++)
                textureRegionArray.add(new TextureRegion(texture, w * x / ROWS, h * y / COLS, wSingle, hSingle));

        //adding the array of images to an animation object.
        animation = new Animation(1/((float)(ROWS*COLS)), textureRegionArray);

        //setting the currentFrame to zero, which is the first frame.
        currentFrame = animation.getKeyFrame(0f);

        generatePath();
    }

    @Override
    public void animate(SpriteBatch batch){
        this.stateTime += Gdx.graphics.getDeltaTime();
        this.pathTime += Gdx.graphics.getDeltaTime()*1.5f;

        currentFrame = animation.getKeyFrame(this.stateTime, true);

        if(pathTime < 1f)
            batch.draw(currentFrame, this.getX(), this.getY());

        if(pathTime >= 1f){
            alive = false;
        }

        else{
            path.valueAt(out, pathTime);
            this.setPosition(out.x, out.y);
        }

    }




    //fireball will start outside screen, and end up somewhere in a designated area
    //within screen.
    //no matter where fireball starts, it should go from top to bottom.
    //the lowest it should spawn is the top third of the screen.
    public void generatePath(){

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        float x_pos = (float)(Math.random()*(w*4/5) + w/10);

        Vector2 start = new Vector2(x_pos,
                (float)(Math.random()*100f+ h));

        Vector2 end = new Vector2(x_pos,
                (float)((Math.random()*(h*2/3)) + h/20));

        path.set(start, end);



    }

    @Override
    public void reset(){

        this.alive = true;
        this.stateTime = 0f;
        this.pathTime = 0f;
        generatePath();
        this.setPosition( 0, Gdx.graphics.getHeight() + 200f);


    }
}
