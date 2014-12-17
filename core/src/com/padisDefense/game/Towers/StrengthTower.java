package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class StrengthTower extends MainTower {

    public StrengthTower(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setTexture(new Texture("strengthtower.png"));
        setSize(30f, 30f);
        setBulletLimit(5);
        setCost(50);
        setAttack(80);
        setRange(200f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.1f);
        setID("strength");
        setBulletTexture("test7.png");
        setBulletRate(0.007f);
    }
}
