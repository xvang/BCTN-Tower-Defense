package com.padisDefense.game.Towers;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class TowerA extends MainTower{

    public TowerA(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setTexture(new Texture("test2.png"));
        setSize(30f, 30f);
        setBulletLimit(15);
        setCost(50);
        setAttack(2f);
        setRange(150f);
        setChargeRate(0.1f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.1f);
        setID("A");
        setBulletTexture("test5.png");


    }




}

