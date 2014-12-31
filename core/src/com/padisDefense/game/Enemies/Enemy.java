package com.padisDefense.game.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * the main enemy class.
 *
 * //TODO: try to get rid of extending Sprite?
 * Sprite extends TextureRegion, but I still declare a TextureRegion object in Enemy.
 * That doesn't sound right.
 *
 * */
public class Enemy extends Sprite implements Pool.Poolable{

    private String name;
    private float health, originalHealth;

    private float armor;

    //finalRate is original rate, rate is current rate,
    //rateTimer is how long altered rates stay.
    private float rate, finalRate, rateTimer;//How fast enemy travels
    private float time;//Used to determine position along the path.

    //Every type of enemy should have the same pathing concept,
    //so the values are set here instead of in a specific enemy class.
    private int currentPath = 0;
    private float strayAmount =0;
    private float wait = (float) Math.random()*3f;

    public boolean alive;

    //Displays the health.
    private Skin skin;
    private Sprite healthRed;
    private Sprite healthGreen;


    //Movement
    public Array<Animation> animation;//all the animation.
    public Animation currentAnimation;//points to current animation: up/down/left/right
    public Texture texture; //contains all the animation frames.
    public TextureRegion currentFrame;//points to section on texture
    public Array<TextureRegion> leftToRight, rightToLeft, up, down;//holds animations according to their names.
    public float stateTime = 0;//determines when to switch to next frame.
    public Vector2 oldPosition, newPosition;//determines which direction enemy is going, and what animation should play.

    protected Enemy(float h, float a, String picture){
        //super(new Texture(picture));
        health = h;
        originalHealth = h;
        armor = a;
        alive = true;

        rate = (float)(Math.random() % 0.01f);
        time = 0;
        skin = new Skin(Gdx.files.internal("uiskin.json"));


        healthGreen = new Sprite(new Texture("healthbargreen.png"));
        healthRed = new Sprite(new Texture("healthbarred.png"));
        healthGreen.setSize(20f, 2f);
        healthRed.setSize(20f, 2f);
        oldPosition = new Vector2();
        newPosition = new Vector2();



    }

    public Enemy(){
       // rate = (float)(Math.random() / (double)50);
        rate = Gdx.graphics.getDeltaTime();
        time = 0;
        health = 100;
        originalHealth = 100;
        alive = true;
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        healthGreen = new Sprite(new Texture("healthbargreen.png"));
        healthRed = new Sprite(new Texture("healthbarred.png"));
        healthGreen.setSize(this.getWidth()+5f, 2f);
        healthRed.setSize(this.getWidth()+5f, 2f);
        oldPosition = new Vector2();
        newPosition = new Vector2();
    }

    public Enemy(Vector2 p){
        setPosition(p.x, p.y);
        health = 100;
        originalHealth = 100;
        alive = true;
        rate = (float)(Math.random() % 0.001f );
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        healthGreen = new Sprite(new Texture("healthbargreen.png"));
        healthRed = new Sprite(new Texture("healthbarred.png"));

        healthGreen.setSize(this.getWidth()+5f, 2f);
        healthRed.setSize(this.getWidth()+5f, 2f);
        oldPosition = new Vector2();
        newPosition = new Vector2();


    }

    public void goTo(Vector2 p){
        this.setPosition(p.x, p.y);
    }


    public void setName(String n){name = n;}
    public void setArmor(int newArmor){armor = newArmor;}
    public void setRate(float r){rate = r;finalRate = r;}
    public void setAlive(Boolean newAlive){alive = newAlive;}
    public void setTime(float t){time = t;}
    public void userSetSize(Vector2 size){this.setSize(size.x, size.y);}
    public void setCurrentPath(int c){currentPath = c;}
    public void setStrayAmount(float s){strayAmount = s;}
    public void setWait(float w){wait = w;}
    public void setHealth(float h){health = h;}
    public void setOriginalHealth(float o){originalHealth = o;}




    public String getName(){return name;}
    public float getHealth(){return health;}
    public float getArmor(){return armor;}
    public float  getRate(){return rate;}
    public float getTime(){return time;}
    public Boolean getAlive(){return alive;}
    public int getCurrentPath(){return currentPath;}
    public float getStrayAmount(){return strayAmount;}
    public float getWait(){return wait;}
    public float getOriginalHealth(){return originalHealth;}
    public Vector2 getLocation(){
        return new Vector2(getX()+(getWidth() / 2), getY() + (getHeight()/2));
    }
    public Vector2 getBulletLocation(){
        return new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));
    }


    public void updateHealth(float damage){

        health -= (damage / armor);
        if(health <= 0f)
            alive = false;
    }
    public void affectRate(float newRate, float time){
        rateTimer = time;

        //if rate was already lowered to less than half,
        //no further decrease should take place.
        if(rate > finalRate / 2)
            rate = newRate;
    }
    public Boolean isDead(){
        alive = ((int)health >= 0);
        return (health <= 0);
    }


    public void updateAlteredStats(){
        //if rate==oldRate, then no rate was not changed.
        //no need to enter if-statement.
        if(rate != finalRate){
            rateTimer -= Gdx.graphics.getDeltaTime();
            if(rateTimer <= 0f){
                rate = finalRate;
                rateTimer = 0;
            }
        }
    }


    //TODO: make the health bar display properly. Or, make a cooler looking health bar.
    public void displayHealth(SpriteBatch batch){

        float percentage = health/originalHealth;


        //we want healthRed's width, because it's the one that doesn't change.
        //Example: if health goes down by 50%, then width of 'healthGreen' also goes down by 50%.
        //and if percentage is greater than 1, than it means rogue must have increased
        //its health. In that case, we don't want the healthbar to grow.
        if(percentage <= 0f)
            healthGreen.setSize(0, healthGreen.getHeight());
        else if(percentage <= 1f)
            healthGreen.setSize(healthRed.getWidth()*percentage, healthGreen.getHeight());

        healthRed.setPosition(this.getX() + this.currentFrame.getRegionWidth()/3, this.getY() + this.currentFrame.getRegionHeight() - 5f);
        healthGreen.setPosition(this.getX() + this.currentFrame.getRegionWidth()/3, this.getY()+ this.currentFrame.getRegionHeight() - 5f);

        healthRed.draw(batch, 1);
        healthGreen.draw(batch, 1);
    }


    public void animate(SpriteBatch batch){}
    public Animation getAnimationDirection(){return null;}

    public void init(float x, float y){

        health = originalHealth;
        healthGreen.setSize(healthRed.getWidth(), healthRed.getHeight());
        this.setPosition(x,y);
        alive = true;
    }




    @Override
    public void reset(){

        stateTime = 0f;
        setPosition(-50f, 0);
        health = originalHealth;
        currentPath = 0;
        time = 0f;
        alive = false;
    }



   /** private Animation animation;
    private Texture texture;
    private TextureRegion currentFrame;
    private Array<TextureRegion> leftRight, rightLeft, up, down;*/


    public void dispose(){
        getTexture().dispose();
    }
}
