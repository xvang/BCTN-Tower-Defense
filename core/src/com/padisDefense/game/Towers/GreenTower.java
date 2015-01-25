package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.MiscellaniousCharacters.Explosion;

//aoe
public class GreenTower extends Tower {



    public GreenTower(Vector2 position, Sprite picture, int level){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate
        super(picture, 80f, 0.53f, 150f, 80, 4f);

        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setLevel(level);
        setRotateRate(4);
        setBulletLimit(1);
        setCost(80);
        setAttack(80f);
        setRange(200f);
        setChargeRate(0.53f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.1f);
        setID("GREEN");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.08f);
        setWeakAgainst("orangeball", "blueball");
        setStrongAgainst("bestgoblin");

    }



    private float rangeAOE = 100f;
    public float getRangeAOE(){return rangeAOE;}
}
