package com.padisDefense.game.Towers;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class SpeedTower extends MainTower{

    public SpeedTower(Vector2 position){
        super("speedtower.png");
        setBulletTexture(new Texture("redbullet.png"));
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setState(true);
        setID("speed");
        setCost(25);
        setAttack(45f);
        setRange(150f);
        setIncomeRate(4f);
        setChargeRate(0.10f);
        setSize(50f, 70f);
        setBulletLimit(1);
        setFireRate(0.06f);
        setBulletRate(0.07f);
        setCustomArc(20f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }




}

