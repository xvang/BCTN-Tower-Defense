package com.padisDefense.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class IceCream extends Enemy{

    public IceCream(){
        //health, armor, texture
        super(100,1, "biggergoblin.png");
        setName("icecream");

        setRate(0.07f + (float)Math.random()*0.1f);

        initMovement();
    }

    public void initMovement(){

        texture = new Texture("animation/red_walk.png");
        int w = texture.getWidth();
        int h = texture.getHeight();

        leftToRight = new Array<TextureRegion>();
        leftToRight.add(new TextureRegion(texture, 0,h*3/4, w/4, texture.getHeight()/4));
        leftToRight.add(new TextureRegion(texture, w/4,h*3/4, texture.getWidth()/4, h/4));
        leftToRight.add(new TextureRegion(texture, w/2,h*3/4, texture.getWidth()/4, h/4));
        leftToRight.add(new TextureRegion(texture, w*3/4,h*3/4, texture.getWidth()/4, h/4));

        down = new Array<TextureRegion>();
        down.add(new TextureRegion(texture, 0,h/2, w/4, h/4));
        down.add(new TextureRegion(texture, w/4,h/2, w/4, h/4));
        down.add(new TextureRegion(texture, w/2,h/2, w/4, h/4));
        down.add(new TextureRegion(texture, w*3/4,h/2, w/4, h/4));

        up = new Array<TextureRegion>();
        up.add(new TextureRegion(texture, 0,0, w/4, h/4));
        up.add(new TextureRegion(texture, w/4,0, w/4, h/4));
        up.add(new TextureRegion(texture, w/2,0, w/4, h/4));
        up.add(new TextureRegion(texture, w*3/4,0, w/4, h/4));

        rightToLeft = new Array<TextureRegion>();
        rightToLeft.add(new TextureRegion(texture, 0,h/4, w/4, h/4));
        rightToLeft.add(new TextureRegion(texture, w/4,h/4, w/4, h/4));
        rightToLeft.add(new TextureRegion(texture, w/2,h/4, w/4, h/4));
        rightToLeft.add(new TextureRegion(texture, w*3/4,h/4, w/4, h/4));


        animation = new Array<Animation>();
        animation.add(new Animation(0.25f, leftToRight));
        animation.add(new Animation(0.25f, down));
        animation.add(new Animation(0.25f, up));
        animation.add(new Animation(0.25f, rightToLeft));

        currentAnimation = animation.get(0);
    }

    float iteration = 0f;
    @Override
    public void animate(SpriteBatch batch){
        this.stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = currentAnimation.getKeyFrame(this.stateTime, true);
        batch.draw(currentFrame, this.getX(), this.getY());

        iteration += Gdx.graphics.getDeltaTime();
        this.currentAnimation = this.getAnimationDirection();
        this.oldPosition.set(this.newPosition);
        iteration = 0;


    }

    @Override
    public Animation getAnimationDirection(){

        float deltaX = Math.abs(this.oldPosition.x - this.newPosition.x);
        float deltaY = Math.abs(this.oldPosition.y - this.newPosition.y);

        //if change in 'x' is more than twice the change in 'y',
        //then that means enemy is moving mostly horizontally.
        if((deltaX > deltaY)){

            if(this.oldPosition.x > this.newPosition.x)
                return animation.get(3);//'left to right' is stored as the 3rd animation.

            else
                return animation.get(0);//'right to left'
        }

        //enemy is moving vertically.
        else if(deltaX < deltaY){

            if(this.oldPosition.y > this.newPosition.y)
                return animation.get(1);//down


            else
                return animation.get(2);//up


        }

        //if no major changes, then return the current animation.
        return currentAnimation;
    }
}
