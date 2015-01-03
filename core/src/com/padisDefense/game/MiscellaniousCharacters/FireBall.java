package com.padisDefense.game.MiscellaniousCharacters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

//TODO; make a ball in front of the falling sprite? make the fireball actually have a ball.
public class FireBall extends Sprite implements Pool.Poolable{


    private Texture texture;
    private Array<TextureRegion> textureRegionArray;
    private Animation animation;
    private TextureRegion currentFrame;
    private float stateTime, pathTime;
    SpriteBatch batch;
    private Array<Path<Vector2>> path;


    //these variable accessed from outside.
    public Vector2 out, explosionLocation;

    Pool<Explosion> explosionPool;
    Array<Explosion> activeExplosion;
    public boolean alive;

    public FireBall(){

        texture = new Texture("animation/fireball_1_me.png");
        textureRegionArray = new Array<TextureRegion>();
        stateTime = 0f;
        pathTime = 0f;
        //currentFrame = new TextureRegion();
        batch = new SpriteBatch();
        path = new Array<Path<Vector2>>();
        out = new Vector2();
        explosionLocation = new Vector2();

        explosionPool = new Pool<Explosion>() {
            @Override
            protected Explosion newObject() {
                return new Explosion();
            }
        };

        activeExplosion = new Array<Explosion>();

        initMovement();
    }

    public void initMovement(){

        texture = new Texture("animation/fire_02.png"); //Loading in the sprite sheet.
        int w = texture.getWidth();
        int h = texture.getHeight();
        int ROWS = 8;
        int COLS = 8;
        //height and width for a single frame.
        int wSingle = w/COLS;
        int hSingle = h/ROWS;//

        //organizing the spritesheet into an array. or vector. both?
        for(int x = 0; x < ROWS; x++)
            for(int y = 0; y < COLS; y++)
                textureRegionArray.add(new TextureRegion(texture, w * x / ROWS, h * y / COLS, wSingle, hSingle));


        //adding the array of images to an animation object.
        animation = new Animation(1/((float)(ROWS*COLS)), textureRegionArray);

        //setting the currentFrame to zero, which is the first frame.
        currentFrame = animation.getKeyFrame(0f);

        //setting a bezier path for the fireball to travel.
        //Each time the fireball reaches the end, a new path is generated.
        generatePath();
    }


    //Returns true when destination is reached.
    //every iteration, 'reachedDestination' is set to false.
    //but on the iteration where it reaches the end,
    //it will return true.
    //the 'explosionLocation' value will be retrieved to start the explosion animation.
    public void animate(){
        this.stateTime += Gdx.graphics.getDeltaTime()/1.5;
        this.pathTime += Gdx.graphics.getDeltaTime()/3;

        currentFrame = animation.getKeyFrame(this.stateTime, true);

        batch.begin();
        if(pathTime < 1f)
            batch.draw(currentFrame, this.getX(), this.getY());

        batch.end();
        if(pathTime >= 1f){
            Explosion e = explosionPool.obtain();

            e.userPosition.set(out);//out is the most current location of the fireball.

            activeExplosion.add(e);
            path.clear();
            generatePath();
            this.pathTime = 0f;
            this.stateTime = 0f;
        }

        else{
            path.get(0).valueAt(out, pathTime);


            this.setPosition(out.x, out.y);
        }

        for(int x = 0; x < activeExplosion.size; x++){
            activeExplosion.get(x).animate();
        }



        for(int x = 0; x < activeExplosion.size; x++){
            if(!activeExplosion.get(x).alive){
                Explosion pointer = activeExplosion.get(x);
                activeExplosion.removeIndex(x);
                explosionPool.free(pointer);
            }
        }


    }


    /**
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

    public void generatePath(){

        //fireball will start outside screen, and end up somewhere in a designated area
        //within screen.
        Vector2 start, end;
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        float x = (float)(Math.random()*(w*4/5) + w/10);
        //no matter where fireball starts, it should go from top to bottom.
        //the lowest it should spawn is the top third of the screen.
        start = new Vector2(x, (float)(Math.random()*100f+ h));

        end = new Vector2(x, (float)((Math.random()*(h*2/3)) + h/20));


        path.add(new Bezier<Vector2>(start, end));


    }

    public void reset(){

        alive = true;

        for(int x = 0; x < activeExplosion.size; x++){
            Explosion e = activeExplosion.get(x);
            activeExplosion.removeIndex(x);
            explosionPool.free(e);
        }

        stateTime = 0f;


    }
}
