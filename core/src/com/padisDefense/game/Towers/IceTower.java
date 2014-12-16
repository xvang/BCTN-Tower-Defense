package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class IceTower extends MainTower {


    private float rangeAOE = 20f;
    public IceTower(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(30f, 30f);
        setTexture(new Texture("icetower.png"));

        setBulletLimit(40);
        setCost(70);
        setAttack(1f);
        setRange(180f);
        setChargeRate(0.01f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(8f);
        setID("ice");
        setBulletTexture("test3.png");
        setBulletRate(0.02f);
    }

    public float getRangeAOE(){return rangeAOE;}
}
