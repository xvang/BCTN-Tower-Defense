package com.padisDefense.game.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * the main enemy class.
 *
 * @author Xeng.
 *
 * */
public class Enemy extends Sprite implements Pool.Poolable{

    private String name;
    private float health;

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

    public Boolean alive;
    public Label label;
    private Skin skin;

    public Enemy(float h, float a, String picture){
        super(new Texture(picture));
        health = h;
        armor = a;
        alive = true;

        rate = (float)(Math.random() % 0.01f);
        time = 0;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        label = new Label("",skin, "default");
    }

    public Enemy(){
       // rate = (float)(Math.random() / (double)50);
        rate = Gdx.graphics.getDeltaTime();
        time = 0;
        health = 1;
        alive = true;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        label = new Label("",skin, "default");
    }

    public Enemy(Vector2 p){
        setPosition(p.x, p.y);
        health = 1;
        alive = true;
        rate = (float)(Math.random() % 0.001f );
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        label = new Label("",skin, "default");
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



    public String getName(){return name;}
    public float getHealth(){return health;}
    public float getArmor(){return armor;}
    public float  getRate(){return rate;}
    public float getTime(){return time;}
    public Boolean getAlive(){return alive;}
    public int getCurrentPath(){return currentPath;}
    public float getStrayAmount(){return strayAmount;}
    public float getWait(){return wait;}
    public Vector2 getLocation(){

        return new Vector2(getX()+(getWidth() / 2), getY() + (getHeight()/2));
    }
    public Vector2 getBulletLocation(){
        return new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));
    }


    public void updateHealth(float damage){
        health -= (damage / armor);
    }
    public void affectRate(float newRate, float time){
        rateTimer = time;

        //if rate was already lowered to less than half,
        //no further decrease should take place.
        if(rate > finalRate / 2)
            rate = newRate;
    }
    public Boolean isDead(){alive = (health > 0); return (health < 0);}


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

    public void updateMessage(){
        label.setText(String.valueOf(round(this.getHealth(), 2)));
        label.setPosition(this.getX() + this.getWidth()/2, this.getY() + 15f);
    }


    public void move(){
    }


    //Not mine. some answer on Stackoverflow.
    public static double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public void init(float x, float y){

        this.setPosition(x,y);
        alive = true;
    }


    @Override
    public void reset(){
        ///position.set(0,0);
        //this.setPosition(position.x, position.y);
        currentPath = 0;
        time = 0f;
        alive = false;
    }
    public void dispose(){
        getTexture().dispose();
    }
}
