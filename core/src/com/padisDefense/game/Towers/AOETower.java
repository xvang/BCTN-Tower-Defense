package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class AOETower extends MainTower {

    private float rangeAOE = 20f;
    public AOETower(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(30f, 30f);
        setTexture(new Texture("aoetower.png"));
        setBulletLimit(2);
        setCost(80);
        setAttack(150);
        setRange(150f);
        setChargeRate(0.14f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.05f);
        setID("aoe");
        setBulletTexture("test4.png");
        setBulletRate(0.02f);
    }

    public float getRangeAOE(){return rangeAOE;}
}
