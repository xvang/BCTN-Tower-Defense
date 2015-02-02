package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;



public class YellowTower extends Tower {


    private float rangeAOE = 80f;
    public YellowTower(Vector2 position, Sprite picture, int level, Sprite bullet){
        //Sprite sprite, int attack, int chargeRate, int range, int cost, int incomeRate
        super(picture, 60f, 0.001f, 150f, 70, 4f);

        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setLevel(level);
        setBulletLimit(1);
        setCost(70);
        setAttack( 80f);
        setRange(200f);
        setChargeRate(0.01f);
        setIncomeRate(4f);
        state = true;
        setFireRate(0.4f);
        setID("YELLOW");
        setBulletSprite(bullet);
        setBulletRate(0.08f);
        setCustomArc(50f);
        setWeakAgainst("redball");
        setStrongAgainst("yellowball");
        setRotateRate(5f);

    }

    public float getRangeAOE(){return rangeAOE;}

}
