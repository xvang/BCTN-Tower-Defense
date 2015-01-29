package com.padisDefense.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;



public class Mage extends Enemy {

    public Mage(){
        //health, armor, texture
        super(100, 1, "bestgoblin.png");
        setName("mage");
        setRate(0.26f + (float)Math.random()*0.045f);

        initMovement();
        this.setSize(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void initMovement(){

        texture = new Texture("enemies/mage.png");
        int w = texture.getWidth();
        int h = texture.getHeight();

        leftToRight = new Array<TextureRegion>();
        down = new Array<TextureRegion>();
        up = new Array<TextureRegion>();
        rightToLeft = new Array<TextureRegion>();

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 2; y++){
                if(y == 0){
                    if(x < 4)
                        down.add(new TextureRegion(texture, w*x/8, 0, w/8, h/2));
                    else
                        up.add(new TextureRegion(texture, w*x/8, 0, w/8, h/2));
                }
                else{
                    if(x < 4)
                        leftToRight.add(new TextureRegion(texture, w*x/8, h/2, w/8, h/2));
                    else
                        rightToLeft.add(new TextureRegion(texture, w*x/8, h/2, w/8, h/2));
                }
            }
        }

        //System.out.println("upSize = " + up.size);
        //spider02.png is 4x10, so every second, 10 frames have to show.
        animation = new Array<Animation>();
        animation.add(new Animation(0.25f, leftToRight));
        animation.add(new Animation(0.25f, down));
        animation.add(new Animation(0.25f, up));
        animation.add(new Animation(0.25f, rightToLeft));

        currentAnimation = animation.get(0);
        currentFrame = currentAnimation.getKeyFrame(this.stateTime, true);
    }

    float changeRate = (float)(Math.random()*0.004);

    @Override
    public void animate(SpriteBatch batch){
        this.stateTime += Gdx.graphics.getDeltaTime() + changeRate;
        currentFrame = currentAnimation.getKeyFrame(this.stateTime, true);
        batch.draw(currentFrame, this.getX(), this.getY());


        this.currentAnimation = this.getAnimationDirection();
        this.oldPosition.set(this.newPosition);



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
                return animation.get(0);//'right to left.
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
