package com.padisDefense.game.Enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * the main enemy class.
 *
 * @author Xeng.
 *
 * */
public class Enemy extends Sprite{

    private String name;
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

        rate = (float)(Math.random() % 0.001f);
        time = 0;
    }

    public Enemy(){
       // rate = (float)(Math.random() / (double)50);
        rate = (float)(Math.random() % 0.001f );
        time = 0;
        health = 1;
        alive = true;
    }

    public Enemy(Vector2 p){
        setPosition(p.x, p.y);
        health = 1;
        rate = (float)(Math.random() % 0.001f );

    }

    public void goTo(Vector2 p){
        setPosition(p.x, p.y);
    }

    public void setChosenPath(int p){chosenPath = p;}
    public void setName(String n){name = n;}
    public void setArmor(int newArmor){armor = newArmor;}
    public void setRate(float r){rate = r;}
    public void setAlive(Boolean newAlive){alive = newAlive;}
    public void setTime(float t){time = t;}


    public int getChosenPath(){return chosenPath;}
    public String getName(){return name;}
    public float getHealth(){return health;}
    public float getArmor(){return armor;}
    public float  getRate(){return rate;}
    public float getTime(){return time;}
    public Boolean getAlive(){return alive;}
    public Vector2 getLocation(){

        return new Vector2(getX()+(getWidth() / 2), getY() + (getHeight()/2));
    }
    public Vector2 getBulletLocation(){
        return new Vector2(getX() + (getWidth()/2), getY() + (getHeight()/2));
    }


    public void updateHealth(float damage){
        health -= (damage / armor);
    }
    public Boolean isDead(){alive = (health > 0); return (health < 0);}





    public void dispose(){
        getTexture().dispose();
    }
}
