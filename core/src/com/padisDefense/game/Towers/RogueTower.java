package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;


public class RogueTower extends MainTower{

    private float attack = 100;
    private Sprite spiral;

    public RogueTower(Vector2 position){
        super("roguetower.png");
        spiral = new Sprite(new Texture("spiral.png"));
        setTarget(new Enemy());
        setPosition(position.x, position.y);
        setSize(50f, 70f);
        setBulletLimit(3);
        setCost(100);
        setRange(250f);
        setAttack(10f);
        setChargeRate(0.2f);
        setIncomeRate(4f);
        setState(true);
        setFireRate(1f);
        setID("rogue");
        setBulletTexture(new Texture("ghostbullet.png"));
        setBulletRate(0.03f);
    }

    @Override
    //rogue tower has a chance of healing the enemy
    //50% chance of healing by 50% of its attack.
    public float getAttack(){
        if(Math.random()*100f > 50f){
            return -attack/2;
        }

        return attack;
    }

    public void spin(SpriteBatch batch){
        spiral.setPosition(this.getX()+5f, this.getY() + 30f);
        spiral.draw(batch, 1);
        spiral.rotate(-5f);
    }
}
