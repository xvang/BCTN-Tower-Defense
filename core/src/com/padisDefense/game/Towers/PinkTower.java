package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

//speed
public class PinkTower extends Tower {


    public PinkTower(Vector2 position, Sprite picture, int level, Sprite bullet){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate
        super(picture, 5f, 0.02f, 150f, 50, 4f);
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setLevel(level);
        setBulletLimit(1);
        setCost(50);
        setRange(200f);
        setAttack(50f);
        setChargeRate(0.02f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.001f);
        setID("PINK");
        setBulletSprite(bullet);
        setBulletRate(0.12f);
        setWeakAgainst("purpleball");
        setStrongAgainst("pinkball");
    }

    public PinkTower(int level){
        setLevel(level);
    }



}
