package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class GhostTower extends MainTower{

    public GhostTower(Vector2 position){
        super("ghosttower.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(50f, 70f);
        setBulletLimit(1);
        setCost(100);
        setAttack(80f);
        setRange(250f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(1f);
        setID("ghost");
        setBulletTexture(new Texture("ghostbullet.png"));
        setBulletRate(0.04f);
        setCustomArc(40f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }
}
