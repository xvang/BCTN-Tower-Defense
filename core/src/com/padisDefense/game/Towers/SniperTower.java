package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class SniperTower extends Tower {

    public SniperTower(Vector2 position){
        super("towers/strength_level_one.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setBulletLimit(1);
        setCost(50);
        setAttack(20f);
        setRange(450f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.5f);
        setID("sniper");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.07f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }
}
