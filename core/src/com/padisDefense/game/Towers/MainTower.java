package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;



/**
 * The main tower class. All other towers will extend from this one.
 *
 * @author Xeng
 *
 * */
public class MainTower extends Sprite {



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



    public MainTower(float a, float r, float cr, float ir, Boolean s, float fr, String id){

        attack = a;
        range = r;
        chargeRate = cr;
        incomeRate = ir;
        state = s;
        fireRate = fr;
        hasTarget = false;
        ID = id;

        target = new Enemy();
        this.setTexture(new Texture("badlogic.jpg"));
        this.setSize(50f, 50f);





    }


    //Empty Constructor
    public MainTower(){
        hasTarget = false;
        ID = "";
    }

    public void setCost(int newCost){cost = newCost;}
    public void setAttack(int newAttack){attack = newAttack; }
    public void setRange(int newRange){range = newRange;}
    public void setChargeRate(float newCharge){chargeRate = newCharge;}
    public void setIncomeRate(float newIncome) {incomeRate = newIncome;}
    public void setState(Boolean newState){state = newState;}
    public void setFireRate(float newFire){fireRate = newFire;}


    public float getCost(){return cost;}
    public float getAttack(){return attack;}
    public float getRange(){return range;}
    public float getChargeRate(){return chargeRate;}
    public float getIncomeRate() {return incomeRate;}
    public Boolean getState(){return state;}
    public float getFireRate(){return fireRate;}




    public void setTarget(Enemy newE){target = newE;}
    public Enemy getTarget(){return target;}

    public void setHasTarget(Boolean t){hasTarget = t;}
    public Boolean getHasTarget(){return hasTarget;}


    public Vector2 getBulletLocation(){
        return new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));
    }

    //The bullet will spawn where this function returns..?
    public Vector2 getLocation(){
        return new Vector2(getX(), getY());
    }

    public void setID(String id){
        ID = id;
    }

    public String getID(){return ID;}


    //TODO: make spinning useful, somehow.  It's too cool to not have.
    /*private int degrees = 1;
    public void spinning(){
        degrees += 1;
        if (degrees % 360 == 0)
            degrees = 1;

        this.rotate(degrees);
    }*/


    public void dispose(){
        getTexture().dispose();
    }


}

