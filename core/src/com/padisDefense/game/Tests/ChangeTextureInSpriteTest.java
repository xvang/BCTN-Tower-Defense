package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Towers.BlueTower;
import com.padisDefense.game.Towers.YellowTower;


public class ChangeTextureInSpriteTest extends ScreenAdapter {


    TextureAtlas towerAtlas, bulletAtlas;

    SpriteBatch batch;

    BlueTower sniper;

    YellowTower strength;


    public ChangeTextureInSpriteTest(){
        towerAtlas = new TextureAtlas("towers/tower.pack");


        batch = new SpriteBatch();
        Sprite sprite = towerAtlas.createSprite("SNIPER", 3);

        Sprite sprite2 = towerAtlas.createSprite("STRENGTH", 3);

        bulletAtlas = new TextureAtlas("bullets/bullet.pack");
        Sprite bullet1 = bulletAtlas.createSprite("blue_bullet");
        Sprite bullet2 = bulletAtlas.createSprite("red_bullet");

        strength = new YellowTower(new Vector2(Gdx.graphics.getWidth()/2 - 100f, Gdx.graphics.getHeight()/2 - 100f),
                sprite2,bullet1);
        sniper = new BlueTower(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2),
                sprite, bullet2);


        strength.setAttack(123f);
        System.out.println("initial attack = " + strength.getAttack());
    }



    float wait = 5f;
    @Override
    public void render(float delta){


        if(wait > 0f)
            wait -= Gdx.graphics.getDeltaTime();
        else
            changeTextureNow();
        batch.begin();

        sniper.draw(batch);
        strength.draw(batch);
        batch.end();
    }


    public void changeTextureNow(){
        wait = 5f;

        Sprite sprite = towerAtlas.createSprite("AOE", 3);
        strength.set(sprite);


        System.out.println("after attack = " + strength.getAttack());
    }
}


