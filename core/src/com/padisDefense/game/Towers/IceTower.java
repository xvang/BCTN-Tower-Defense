package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class IceTower extends MainTower {


    private float rangeAOE = 20f;
    public IceTower(Vector2 position){
        super("icetower.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(50f, 60f);
        setBulletLimit(2);
        setCost(70);
        setAttack(1f);
        setRange(180f);
        setChargeRate(0.01f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.01f);
        setID("ice");
        setBulletTexture(new Texture("snowball.png"));
        setBulletRate(0.02f);
        setCustomArc(50f);

    }

    public float getRangeAOE(){return rangeAOE;}
}
