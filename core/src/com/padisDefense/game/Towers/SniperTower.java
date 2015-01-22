package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class SniperTower extends Tower {

    public SniperTower(Vector2 position, Sprite sprite, int level){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate
        super(sprite, 100f, 0.04f, 300f, 100, 4f);
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setLevel(level);
        setBulletLimit(1);
        setCost(100);
        setAttack(100f);
        setRange(300f);
        setChargeRate(0.04f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.2f);
        setID("SNIPER");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.07f);
        setWeakAgainst("orangeball", "blueball");
        setStrongAgainst("goblin");
    }

    public SniperTower(int level){
        setLevel(level);
    }
}
