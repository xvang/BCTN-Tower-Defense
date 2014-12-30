package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class StrengthTower extends MainTower {

    public StrengthTower(Vector2 position){
        super("strengthtower.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(50f, 70f);
        setBulletLimit(1);
        setCost(50);
        setAttack(1f);
        setRange(150f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.5f);
        setID("strength");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.07f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }
}
