package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;



public class OrangeTower extends Tower {

    public OrangeTower(Vector2 position, Sprite picture, Sprite bullet){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate

        super(picture, 15, 0.025f, 150f, 60, 1f);
        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setLevel(1);
        setBulletLimit(1);
        setCost(60);
        setAttack(50f);
        setRange(200f);
        setChargeRate(0.025f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.08f);
        setID("ORANGE");
        setBulletSprite(bullet);
        setBulletRate(0.10f);
        setCustomArc(40f);
        setWeakAgainst("pinkball");
        setStrongAgainst("orangeball");
    }
}
