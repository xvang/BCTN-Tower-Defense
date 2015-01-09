package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class AoeTower extends Tower {

    private float rangeAOE = 120f;
    public AoeTower(Vector2 position, Texture picture){
        super(picture);

        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setBulletLimit(1);
        setCost(80);
        setAttack(35f);
        setRange(550f);
        setChargeRate(0.14f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.15f);
        setID("aoe");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.06f);
        setWeakAgainst("goblin");
        setStrongAgainst("bestgoblin");
    }

    public float getRangeAOE(){return rangeAOE;}
}
