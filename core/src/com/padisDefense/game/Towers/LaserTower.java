package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class LaserTower extends Tower {

    public LaserTower(Vector2 position){
        super("towers/laser_level_one.png");

        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setBulletLimit(1);
        setCost(100);
        setAttack(5f);
        setRange(250f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.07f);
        setID("laser");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.07f);
        setCustomArc(40f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }
}
