package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.BuildableSpotSpawnStorage;


public class BuildableSpotSpawnTest extends ScreenAdapter {


    final float w = Gdx.graphics.getWidth()/100;
    final float h = Gdx.graphics.getHeight()/100;
    SpriteBatch batch;
    Sprite background;

    Array<BuildableSpot> billy;
    BuildableSpotSpawnStorage storage;
    int level;
    public BuildableSpotSpawnTest(){
        batch = new SpriteBatch();

        background = new Sprite(new Texture("tiles/path6.png"));

        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        billy = new Array<BuildableSpot>();
        storage = new BuildableSpotSpawnStorage();

        addBuildableSpots();
    }


    @Override
    public void render(float delta){


        batch.begin();
        background.draw(batch);

        for(int x = 0; x < billy.size; x++){
            billy.get(x).draw(batch);
        }
        batch.end();
    }


    public void addBuildableSpots(){

        billy.add(new BuildableSpot(new Vector2()));
    }
}
