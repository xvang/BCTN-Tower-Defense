package com.padisDefense.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BlueSpider extends Enemy{

    public BlueSpider(){
        //health, armor, texture
        super(400,40, "biggergoblin.png");
        setName("bluespider");
        isBoss = true;
        setRate(0.09f + (float)Math.random()*0.17f);
        setOriginalRate(getRate());

        initMovement();
        this.setSize(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void initMovement(){

        texture = new Texture("enemies/spider02.png");
        int w = texture.getWidth();
        int h = texture.getHeight();

        //height and width for a single frame.
        int wSingle = w/10;
        int hSingle = h/5;


        leftToRight = new Array<TextureRegion>();
        for(int x = 0; x < 10; x++)
            leftToRight.add(new TextureRegion(texture, w*x/10,h*3/5,wSingle, hSingle));


        down = new Array<TextureRegion>();
        for(int x = 0; x < 10; x++)
            down.add(new TextureRegion(texture, w*x/10,h*2/5, wSingle, hSingle ));



        up = new Array<TextureRegion>();
        for(int x = 0; x < 10; x++)
            up.add(new TextureRegion(texture, w*x/10, 0, wSingle, hSingle));


        rightToLeft = new Array<TextureRegion>();
        for(int x = 0; x < 10; x++)
            rightToLeft.add(new TextureRegion(texture, w*x/10, h/5, wSingle, hSingle));



        //System.out.println("upSize = " + up.size);
        //spider02.png is 4x10, so every second, 10 frames have to show.
        animation = new Array<Animation>();
        animation.add(new Animation(0.1f, leftToRight));
        animation.add(new Animation(0.1f, down));
        animation.add(new Animation(0.1f, up));
        animation.add(new Animation(0.1f, rightToLeft));

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
/**
 * int w = texture.getWidth();
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
 currentFrame = currentAnimation.getKeyFrame(this.stateTime, true);
 * */