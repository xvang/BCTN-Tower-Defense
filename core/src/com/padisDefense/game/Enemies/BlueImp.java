package com.padisDefense.game.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BlueImp extends Enemy{

    public BlueImp(){
        //todo: delete texture parameter.
        //health, armor, texture
        super(750, 50, "bestgoblin.png");
        setName("golem");
        setRate(0.07f + (float)Math.random()*0.035f);
        setOriginalRate(getRate());
        isBoss = true;
        initMovement();
        this.setSize(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void initMovement(){

        texture = new Texture("enemies/blue_walk.png");

        int w = texture.getWidth();
        int h = texture.getHeight();

        int wSingle = w/4;
        int hSingle = h/4;

        leftToRight = new Array<TextureRegion>();
        rightToLeft = new Array<TextureRegion>();
        up = new Array<TextureRegion>();
        down = new Array<TextureRegion>();


        for(int x = 0; x < 4; x++){
            leftToRight.add(new TextureRegion(texture, w*x/4,h*3/4,wSingle, hSingle));
            rightToLeft.add(new TextureRegion(texture, w*x/4,h/4,wSingle, hSingle));
            up.add(new TextureRegion(texture, w*x/4,0,wSingle, hSingle));
            down.add(new TextureRegion(texture, w*x/4,h*2/4,wSingle, hSingle));
        }

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

