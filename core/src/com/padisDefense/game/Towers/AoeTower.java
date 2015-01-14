package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.MiscellaniousCharacters.Explosion;


public class AoeTower extends Tower {

    private float rangeAOE = 100f;

    public AoeTower(Vector2 position, Sprite picture){
        super(picture);

        setTarget(new Enemy());
        setPosition(position.x, position.y);

        setBulletLimit(1);
        setCost(80);
        setAttack(20f);
        setRange(150f);
        setChargeRate(0.14f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.15f);
        setID("AOE");
        setBulletTexture(new Texture("redbullet.png"));
        setBulletRate(0.06f);
        setWeakAgainst("goblin");
        setStrongAgainst("bestgoblin");

    }




    public float getRangeAOE(){return rangeAOE;}
}
