package com.padisDefense.game.Tests;

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
import com.padisDefense.game.MiscellaniousCharacters.Explosion;
import com.padisDefense.game.MiscellaniousCharacters.FireBall;
import com.padisDefense.game.MiscellaniousCharacters.FirePlace;


public class AnimationTest extends ScreenAdapter {

    public AnimationTest(){
    }

    Array<FireBall> activeFireBall;
    Pool<FireBall> fireBallPool;


    Pool<Explosion> explosionPool;
    Array<Explosion> activeExplosion;
    Vector2 explosionPosition;

    Pool<FirePlace> firePlacePool;
    Array<FirePlace> activeFirePlace;
    Vector2 fireplacePosition;

    SpriteBatch batch;
    @Override
    public void show() {
        batch = new SpriteBatch();
        //FIREBALL
        activeFireBall = new Array<FireBall>();
        fireBallPool = new Pool<FireBall>() {
            @Override
            protected FireBall newObject() {
                FireBall f = new FireBall();
                f.setPosition(-100f, -100f);
                return f;
            }
        };

        for(int x = 0; x < 8; x++){
            activeFireBall.add(fireBallPool.obtain());
        }
        fireBallPool.freeAll(activeFireBall);
        activeFireBall.clear();

        //EXPLOSION
        explosionPool = new Pool<Explosion>() {
            @Override
            protected Explosion newObject() {
                return new Explosion();
            }
        };
        activeExplosion = new Array<Explosion>();
        explosionPosition = new Vector2();

        for(int x = 0; x < 20; x++){
            activeExplosion.add(explosionPool.obtain());
        }
        explosionPool.freeAll(activeExplosion);
        activeExplosion.clear();

        //FIREPLACE
        firePlacePool = new Pool<FirePlace>() {
            @Override
            protected FirePlace newObject() {
                return new FirePlace();
            }
        };

        activeFirePlace = new Array<FirePlace>();
        fireplacePosition = new Vector2();

        for(int x = 0; x < 20; x++){
            activeFirePlace.add(firePlacePool.obtain());
        }

        firePlacePool.freeAll(activeFirePlace);
        activeFirePlace.clear();
    }

    @Override
    public void render(float delta) {

        //drawing fireballs
        for(int x = 0; x < activeFireBall.size; x++){
            activeFireBall.get(x).animate(batch);
        }

        //checking for fireballs that reached destination.
        for(int x = 0; x < activeFireBall.size; x++){
            FireBall f = activeFireBall.get(x);

            if(!f.alive){

                Explosion e = explosionPool.obtain();
                e.explosionPosition.set(f.out);
                activeExplosion.add(e);

                FirePlace fire = firePlacePool.obtain();
                fire.firePlacePosition.set(f.out);
                activeFirePlace.add(fire);

                activeFireBall.removeIndex(x);

                fireBallPool.free(f);
            }
        }

        int r = (int)(Math.random()*3);
        for(int x=  0; x < r; x++){
            if(activeFireBall.size < 8){
                FireBall f = fireBallPool.obtain();
                activeFireBall.add(f);
            }
        }


        for(int x = 0; x < activeFirePlace.size; x++){
            activeFirePlace.get(x).animate();
        }

        for(int x = 0; x < activeExplosion.size; x++){
            activeExplosion.get(x).animate(batch);
        }

        for(int x = 0; x < activeFirePlace.size; x++){
            if(!activeFirePlace.get(x).alive){
                FirePlace pointer = activeFirePlace.get(x);
                activeFirePlace.removeIndex(x);
                firePlacePool.free(pointer);
            }
        }

        for(int x = 0; x < activeExplosion.size; x++){
            if(!activeExplosion.get(x).alive){
                Explosion pointer = activeExplosion.get(x);
                activeExplosion.removeIndex(x);
                explosionPool.free(pointer);
            }
        }
    }
}