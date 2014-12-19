package com.padisDefense.game.Towers;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class SpeedTower extends MainTower{

    public SpeedTower(Vector2 position){
        super("speedtower.png");
        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setSize(50f, 70f);

        setBulletLimit(2);
        setCost(25);
        setAttack(500000f);
        setRange(140f);
        setChargeRate(0.11f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(0.005f);
        setID("speed");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.07f);
        setCustomArc(70f);
    }




}

