package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Towers.SniperTower;


public class AtlasTest extends ScreenAdapter {

    final float w = Gdx.graphics.getWidth();
    final float h = Gdx.graphics.getHeight();

    SniperTower snipe;
    Sprite sprite;
    TextureAtlas atlas;
    SpriteBatch batch;


    public AtlasTest(){

        atlas = new TextureAtlas("towers/tower.pack");

        sprite = atlas.createSprite("AOE", 1);
        System.out.println(sprite.getWidth() + ", " + sprite.getHeight());

        snipe = new SniperTower(new Vector2(w/2, h/2), sprite);

        snipe.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta){


        batch.begin();
        snipe.draw(batch);
        batch.end();


        snipe.rotate(2);

    }



}
