package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class StrengthTower extends Tower {


    private float rangeAOE = 80f;
    public StrengthTower(Vector2 position, Sprite picture, int level){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate
        super(picture, 60f, 0.001f, 150f, 70, 4f);

        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setLevel(level);
        setBulletLimit(1);
        setCost(70);
        setAttack(60f);
        setRange(200f);
        setChargeRate(0.001f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.4f);
        setID("STRENGTH");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.08f);
        setCustomArc(50f);
        setWeakAgainst("pinkball");
        setStrongAgainst("bestgoblin", "biggergoblin");

        setRotateRate(5f);

    }

    public float getRangeAOE(){return rangeAOE;}

    @Override
    public void userReset(){
        setLevel(1);
        setBulletLimit(1);
        setCost(70);
        setAttack(4f);
        setRange(150f);
        setChargeRate(0.01f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.01f);
        setID("STRENGTH");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.08f);
        setCustomArc(50f);
        setWeakAgainst("goblin");
        setStrongAgainst("bestgoblin", "biggergoblin");

    }
}
