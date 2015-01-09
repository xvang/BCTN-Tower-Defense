package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class SpeedTower extends Tower {

    private float attack = 30;


    public SpeedTower(Vector2 position){
        super("towers/speed_level_one.png");

        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setBulletLimit(1);
        setCost(100);
        setRange(350f);
        setAttack(1f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.12f);
        setID("speed");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.09f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }

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
