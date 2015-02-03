package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

//sniper
public class BlueTower extends Tower {

    public BlueTower(Vector2 position, Sprite tower, Sprite bullet){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate
        super(tower, 100f, 0.04f, 300f, 100, 4f);
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setLevel(1);
        setBulletLimit(1);
        setCost(100);
        setAttack(80f);
        setRange(200f);
        setChargeRate(0.04f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.2f);
        setID("BLUE");
        setBulletSprite(bullet);
        setBulletRate(0.07f);
        setWeakAgainst("greenball");
        setStrongAgainst("blueball");
    }

}
