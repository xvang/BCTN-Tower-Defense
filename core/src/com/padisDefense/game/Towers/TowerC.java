package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

/**
 * Created by Toog on 12/14/2014.
 */
public class TowerC extends MainTower {


    public TowerC(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setTexture(new Texture("test5.png"));
        setSize(30f, 30f);
        setBulletLimit(40);
        setCost(70);
        setAttack(3f);
        setRange(180f);
        setChargeRate(0.01f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.2f);
        setID("C");
        setBulletTexture("test2.png");
    }
}
