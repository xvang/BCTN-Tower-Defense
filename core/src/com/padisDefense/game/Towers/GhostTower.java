package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class GhostTower extends MainTower{

    public GhostTower(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(30f, 30f);
        setTexture(new Texture("ghosttower.png"));
        setBulletLimit(2);
        setCost(100);
        setAttack(80);
        setRange(250f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(2f);
        setID("ghost");
        setBulletTexture("test2.png");
        setBulletRate(0.01f);
    }
}
