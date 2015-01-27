package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

//laser
public class RedTower extends Tower {

    public RedTower(Vector2 position, Sprite picture, int level, Sprite bullet){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate

        super(picture, 15, 0.025f, 150f, 60, 1f);
        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setLevel(level);
        setBulletLimit(1);
        setCost(60);
        setAttack(80f);
        setRange(200f);
        setChargeRate(0.025f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.08f);
        setID("RED");
        setBulletSprite(bullet);
        setBulletRate(0.10f);
        setCustomArc(40f);
        setWeakAgainst("orangeball");
        setStrongAgainst("redball");
    }

}
