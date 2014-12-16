package com.padisDefense.game.Towers;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class SpeedTower extends MainTower{

    public SpeedTower(Vector2 position){
        super();
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setTexture(new Texture("speedtower.png"));
        setSize(30f, 30f);

        setBulletLimit(20);
        setCost(25);
        setAttack(5f);
        setRange(140f);
        setChargeRate(0.11f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.01f);
        setID("speed");
        setBulletTexture("test5.png");
        setBulletRate(0.03f);
    }




}

