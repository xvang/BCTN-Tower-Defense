/**
 *
 * Author: Xeng Vang
 *
 * */


package com.padisDefense.game.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


/**
 * bullet class.
 *
 *
 * @param 'p'
 * */
public class Bullet extends Sprite implements Pool.Poolable {

    public Vector2 position;
    public boolean alive;
    private float time;


    public Bullet (Vector2 p, Texture t){

        super(t);
        this.setSize(16f, 16f);
        this.setPosition(p.x, p.y);
        this.alive = false;
        position = new Vector2();
        time = 0f;

    }

    public void init(float x, float y){
        position.x = x;
        position.y = y;
        this.setPosition(x,y);
        alive = true;
    }


    @Override
    public void reset(){
        ///position.set(0,0);
        this.setPosition(position.x, position.y);
        alive = false;
    }

    public boolean isOutOfScreen() {
        return (this.getX() > 0 && this.getX() < Gdx.graphics.getWidth() &&
                this.getY() < Gdx.graphics.getHeight() && this.getY() > 0);
    }

    public void goTo(Vector2 p){
        this.setPosition(p.x, p.y);
    }

    public void update(float delta){
        position.add(1*delta*60, 1*delta*60);

        if (isOutOfScreen()) alive = false;
    }






    public void setTime(float t){time = t;}
    public float getTime(){return time;}

    public Vector2 getLocation(){return new Vector2(getX(), getY());}
    public void dispose(){

        getTexture().dispose();
    }

}
