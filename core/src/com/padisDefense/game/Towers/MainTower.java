package com.padisDefense.game.Towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;

import javafx.scene.text.TextBoundsType;


/**
 * The main tower class. All other towers will extend from this one.
 *
 * @author Xeng
 *
 * */
public class MainTower extends Sprite{



    private String ID;
    private float cost;
    private float attack;
    private float range;
    private float chargeRate;
    private float incomeRate;
    private Boolean state;//TRUE is shooting. FALSE is charging.
    private float fireRate;
    private Boolean hasTarget;
    private Enemy target;
    private String bulletTexture;


    //Creating a pool method thing.
    //Used for shooting. Used in enemyManager.shooting().
    private final Pool<Bullet> pool;
    private int bulletLimit = 1;
    private Array<Bullet> activeBullets;


    //Empty Constructor
    public MainTower(){
        hasTarget = false;
        ID = "";
        bulletTexture = "test2.png";
        activeBullets = new Array<Bullet>();
        pool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet(new Vector2(getLocation()));
            }
        };


    }

    public void setCost(int newCost){cost = newCost;}
    public void setAttack(float newAttack){attack = newAttack; }
    public void setRange(float newRange){range = newRange;}
    public void setChargeRate(float newCharge){chargeRate = newCharge;}
    public void setIncomeRate(float newIncome) {incomeRate = newIncome;}
    public void setState(Boolean newState){state = newState;}
    public void setFireRate(float newFire){fireRate = newFire;}
    public void setTarget(Enemy newE){target = newE;}
    public void setHasTarget(Boolean t){hasTarget = t;}
    public void setID(String id){
        ID = id;
    }
    public void setBulletLimit(int b){bulletLimit = b;}
    public void setBulletTexture(String t){bulletTexture = t;}


    public float getCost(){return cost;}
    public float getAttack(){return attack;}
    public float getRange(){return range;}
    public float getChargeRate(){return chargeRate;}
    public float getIncomeRate() {return incomeRate;}
    public Boolean getState(){return state;}
    public float getFireRate(){return fireRate;}
    public Enemy getTarget(){return target;}
    public Boolean getHasTarget(){return hasTarget;}
    public Pool<Bullet> getPool(){return pool;}
    public int getBulletLimit(){return bulletLimit;}
    public Array<Bullet> getActiveBullets(){return activeBullets;}
    public String getID(){return ID;}
    public Texture getBulletTexture(){return new Texture(bulletTexture);}

    public String getMessage(){
        if(state)
            return "Charge";

        return "Attack";
    }


    //The bullet will spawn where this function returns..?
    public Vector2 getLocation(){
        return new Vector2(getX(), getY());
    }

    public void dispose(){
        getTexture().dispose();
        activeBullets.clear();
        pool.clear();
    }


}



//TODO: make spinning useful, somehow.  It's too cool to not have.
    /*private int degrees = 1;
    public void spinning(){
        degrees += 1;
        if (degrees % 360 == 0)
            degrees = 1;

        this.rotate(degrees);
    }*/
