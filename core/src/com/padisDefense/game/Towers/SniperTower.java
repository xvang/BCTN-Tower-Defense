package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class SniperTower extends Tower {

    public SniperTower(Vector2 position, Sprite sprite, int level){
        super(sprite);
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setLevel(level);
        setBulletLimit(1);
        setCost(50);
        setAttack(20f);
        setRange(200f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.1f);
        setID("SNIPER");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.07f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }

    public SniperTower(int level){
        setLevel(level);
    }
}
