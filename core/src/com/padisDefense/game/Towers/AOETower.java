package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class AOETower extends MainTower {

    private float rangeAOE = 100f;
    public AOETower(Vector2 position){
        super("aoetower.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(30f, 30f);
        setBulletLimit(1);
        setCost(80);
        setAttack(8f);
        setRange(150f);
        setChargeRate(0.14f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.15f);
        setID("aoe");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.08f);
        setWeakAgainst("goblin");
        setStrongAgainst("bestgoblin");
    }

    public float getRangeAOE(){return rangeAOE;}
}
