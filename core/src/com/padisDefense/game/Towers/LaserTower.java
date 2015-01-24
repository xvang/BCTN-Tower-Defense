package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class LaserTower extends Tower {

    public LaserTower(Vector2 position, Sprite picture, int level){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate

        super(picture, 15, 0.025f, 150f, 60, 1f);
        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setLevel(level);
        setBulletLimit(1);
        setCost(60);
        setAttack(20f);
        setRange(200f);
        setChargeRate(0.025f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.08f);
        setID("LASER");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.10f);
        setCustomArc(40f);
        setWeakAgainst("purpleball");
        setStrongAgainst("goblin");
    }

}
