package com.padisDefense.game.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BipedalDragon extends Enemy{

    public BipedalDragon(){
        //health, armor, texture
        super(100, 1, "bestgoblin.png");
        setName("bipedaldragon");
        setRate(0.13f + (float)Math.random()*0.039f);

        initMovement();
        this.setSize(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void initMovement(){

        texture = new Texture("enemies/bipedalDragon_all.png");

        int w = texture.getWidth();
        int h = texture.getHeight();

        int wSingle = w/4;
        int hSingle = h/6;

        leftToRight = new Array<TextureRegion>();
        rightToLeft = new Array<TextureRegion>();

        //stores all the textureregion into one array.
        for(int x = 0; x < 4; x++)
            for(int y = 0; y < 6; y++){

                if(y < 3)
                    leftToRight.add(new TextureRegion(texture, x*w/4, y*h/6, wSingle, hSingle));
                else
                    rightToLeft.add(new TextureRegion(texture, x*w/4, y*h/6, wSingle, hSingle));

            }



        animation = new Array<Animation>();
        animation.add(new Animation(0.083f, leftToRight));
        animation.add(new Animation(0.083f, rightToLeft));

        currentAnimation = animation.get(0);
        currentFrame = currentAnimation.getKeyFrame(this.stateTime, true);
    }

    float iteration = 0f;
    @Override
    public void animate(SpriteBatch batch){
        this.stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = currentAnimation.getKeyFrame(this.stateTime, true);
        batch.draw(currentFrame, this.getX(), this.getY());

        iteration += Gdx.graphics.getDeltaTime();
        //this.currentAnimation = this.getAnimationDirection();
        this.oldPosition.set(this.newPosition);
        iteration = 0;


    }

    @Override
    public Animation getAnimationDirection(){


        if(oldPosition.x < newPosition.x)
            return animation.get(0);
        else
            return animation.get(1);
        //if no major changes, then return the current animation.

    }

    @Override
    public void displayHealth(SpriteBatch batch){

        float percentage = health/originalHealth;

        if(percentage <= 0f)
            healthGreen.setSize(0, healthGreen.getHeight());
        else if(percentage <= 1f)
            healthGreen.setSize(healthRed.getWidth()*percentage, healthGreen.getHeight());


        try{
            healthRed.setPosition(getX() + currentFrame.getRegionWidth()/3, getY() + currentFrame.getRegionHeight() - 5f);
            healthGreen.setPosition(getX() + currentFrame.getRegionWidth()/3, getY()+ currentFrame.getRegionHeight() - 5f);

        }catch(Exception e){
            if(currentFrame == null)
                System.out.println("CURRENTFRAME IS OUR CUPLRIT GODDAM");
        }

        healthRed.draw(batch, 1);
        healthGreen.draw(batch, 1);
    }
}
