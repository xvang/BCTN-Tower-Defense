package com.padisDefense.game.Towers;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;

public class RogueTower extends Tower {

    public RogueTower(Vector2 position, Sprite picture, int level){
        super(picture);
        setBulletTexture(new Texture("redbullet.png"));
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        state = true;
        setID("ROGUE");
        setCost(25);
        setAttack(45f);
        setRange(300f);
        setIncomeRate(4f);
        setChargeRate(0.10f);
        setLevel(level);
        setBulletLimit(1);
        setFireRate(0.9f);
        setBulletRate(0.07f);
        setCustomArc(120f);
        setWeakAgainst("bestgoblin");
        setStrongAgainst("goblin");
    }


    public RogueTower(int level){
        setLevel(level);
    }


}

