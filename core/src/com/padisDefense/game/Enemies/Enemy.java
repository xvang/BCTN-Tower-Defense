package com.padisDefense.game.Enemies;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * the main enemy class.
 *
 * @author Xeng.
 *
 * */
public class Enemy extends Sprite{

    private float health;

    private float armor;
    private int chosenPath;//Path where specific enemy will travel. MainPath has 5 paths in it.

    private float rate;//Every type of enemy should have a different rate.
    private float time;//Used to determine position along the path.

    private Boolean alive;

    public Enemy(float h, float a, Vector2 size){

        this.setSize(size.x, size.y);
        health = h;
        armor = a;
        alive = true;

        rate = (float)(Math.random() % 0.002f + 0.0015f);
        time = 0;
    }

    public Enemy(){
       // rate = (float)(Math.random() / (double)50);
        rate = (float)(Math.random() % 0.002f + 0.0015f);
        time = 0;
        health = 1;
        alive = true;
    }

    public Enemy(Vector2 p){
        setPosition(p.x, p.y);
        health = 1;
    }

    public void goTo(Vector2 p){
        setPosition(p.x, p.y);
    }

    public void setChosenPath(int p){chosenPath = p;}
    public int getChosenPath(){return chosenPath;}


    public float getHealth(){return health;}

    public void updateHealth(float damage){
        health -= (damage / armor);
    }
    public Boolean isDead(){alive = (health > 0); return (health < 0);}

    public float getArmor(){return armor;}
    public void setArmor(int newArmor){armor = newArmor;}



    public float  getRate(){return rate;}
    public void setRate(float r){rate = r;}

    public float getTime(){return time;}
    public void setTime(float t){time = t;}


    public Boolean getAlive(){return alive;}
    public void setAlive(Boolean newAlive){alive = newAlive;}


    public Vector2 getBulletLocation(){
        return new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));
    }
    public Vector2 getLocation(){
        return new Vector2(getX(), getY());
    }

    public void dispose(){
        getTexture().dispose();
    }
}
