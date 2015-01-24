package com.padisDefense.game.Towers;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class RogueTower extends Tower {

    public RogueTower(Vector2 position, Sprite picture, int level){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate
        super(picture, 20f, 0.021f, 150f, 25, 4f);
        setBulletTexture(new Texture("redbullet.png"));
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        state = true;
        setID("ROGUE");
        setCost(25);
        setAttack(20);
        setRange(200f);
        setIncomeRate(4f);
        setChargeRate(0.021f);
        setLevel(level);
        setBulletLimit(1);
        setFireRate(0.3f);
        setBulletRate(0.08f);
        setCustomArc(120f);
        setWeakAgainst("violetball");
        setStrongAgainst("goblin");
    }


    private float attack = 20;
    @Override
    //rogue tower has a chance of healing the enemy
    //50% chance of healing by 50% of its attack.
    public float getAttack(){
        if(Math.random()*100f > 60f){
            return -attack/2;
        }


        return attack;
    }
}

