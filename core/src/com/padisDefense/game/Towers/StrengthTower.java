package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class StrengthTower extends Tower {


    private float rangeAOE = 50f;
    public StrengthTower(Vector2 position){
        super("towers/strength_level_one.png");
        this.setSize(30f, 30f);
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(50f, 60f);
        setBulletLimit(1);
        setCost(70);
        setAttack(4f);
        setRange(250f);
        setChargeRate(0.01f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.01f);
        setID("strength");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.04f);
        setCustomArc(50f);
        setWeakAgainst("goblin");
        setStrongAgainst("bestgoblin", "biggergoblin");

    }

    public float getRangeAOE(){return rangeAOE;}
}
