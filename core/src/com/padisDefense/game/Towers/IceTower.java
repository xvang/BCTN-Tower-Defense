package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class IceTower extends MainTower {


    private float rangeAOE = 50f;
    public IceTower(Vector2 position){
        super("icetower.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(50f, 60f);
        setBulletLimit(1);
        setCost(70);
        setAttack(0.1f);
        setRange(180f);
        setChargeRate(0.01f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.01f);
        setID("ice");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.04f);
        setCustomArc(50f);
        setWeakAgainst("goblin");
        setStrongAgainst("bestgoblin", "biggergoblin");

    }

    public float getRangeAOE(){return rangeAOE;}
}
