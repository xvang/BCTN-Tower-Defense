package com.padisDefense.game.Tests;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.padisDefense.game.Enemies.Ball;
import com.padisDefense.game.Enemies.Enemy;



public class BallSpawnTest extends ScreenAdapter {


    Skin skin;
    Ball a,b,c;
    SpriteBatch batch;
    public BallSpawnTest(){
        skin = new Skin(new TextureAtlas("enemies/balls/balls.pack"));

        a = new Ball("orange", skin.getSprite("orangeball"));
        b = new Ball("violet", skin.getSprite("violetball"));
        c = new Ball("blue", skin.getSprite("blueball"));

        a.setPosition(100, 200);
        b.setPosition(300, 400);
        c.setPosition(400, 200);
        batch = new SpriteBatch();
    }


    @Override
    public void render(float delta){
        batch.begin();
        a.draw(batch, 1);
        b.draw(batch, 1);
        c.draw(batch, 1);
        batch.end();
    }
}
