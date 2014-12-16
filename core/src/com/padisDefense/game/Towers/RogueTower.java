package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class RogueTower extends MainTower{

    private float attack = 100;

    public RogueTower(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(30f, 30f);
        setTexture(new Texture("roguetower.png"));
        setBulletLimit(5);
        setCost(100);

        setRange(250f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(4f);
        setID("rogue");
        setBulletTexture("test6.png");
        setBulletRate(0.015f);

    }

    @Override
    //rogue tower has a chance of healing the enemy
    //15% chance of healing by 50% of its attack.
    public float getAttack(){
        System.out.println("We in roguetower");
        if(Math.random()*100 > 85f){
            return -attack/2;
        }

        return attack;
    }
}
