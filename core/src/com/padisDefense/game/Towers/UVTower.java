package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class UVTower extends Tower {

    public UVTower(Vector2 position){
        super("ghosttower.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(50f, 70f);
        setBulletLimit(1);
        setCost(100);
        setAttack(5f);
        setRange(250f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.07f);
        setID("uv");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.07f);
        setCustomArc(40f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }
}
