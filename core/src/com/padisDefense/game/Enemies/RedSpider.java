package com.padisDefense.game.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class RedSpider extends Enemy{

    public RedSpider(){
        //health, armor, texture
        super(100, 1, "bestgoblin.png");
        setName("redspider");
        setRate(0.08f + (float)Math.random()*0.035f);

        initMovement();
        this.setSize(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void initMovement(){

        texture = new Texture("enemies/spider03.png");
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
            System.out.println("ENEMY IS NULL SOMEHOW");
        }

        healthRed.draw(batch, 1);
        healthGreen.draw(batch, 1);
    }
}
