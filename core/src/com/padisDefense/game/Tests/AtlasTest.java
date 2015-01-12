package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.padisDefense.game.Towers.SniperTower;


public class AtlasTest extends ScreenAdapter {

    final float w = Gdx.graphics.getWidth();
    final float h = Gdx.graphics.getHeight();

    SniperTower snipe;
    Sprite sprite;
    TextureAtlas atlas;
    SpriteBatch batch;
    Skin skin2;
    TextButton charge, upgrade;
    Stage stage;
    Table table;

    Rectangle top, bottom, left, right;
    public AtlasTest(){

        skin2 = new Skin(Gdx.files.internal("pack2.json"));
        charge = new TextButton("Charge", skin2, "default");
        upgrade = new TextButton("Upgrade", skin2, "default");


        atlas = new TextureAtlas("towers/tower.pack");

        sprite = atlas.createSprite("AOE", 1);
        System.out.println(sprite.getWidth() + ", " + sprite.getHeight());

        snipe = new SniperTower(new Vector2(w/2, h/2), sprite);

        snipe.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch = new SpriteBatch();

        table = new Table();

        table.add(charge).width(50f).height(50f);
        table.add(upgrade).width(50f).height(20f).row();

        table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        charge.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
            }
        });

        top = new Rectangle();
        bottom = new Rectangle();
        left = new Rectangle();
        right = new Rectangle();

        top.setSize(w, 10f);
        bottom.setSize(w, 10f);
        left.setSize(10f, h);
        right.setSize(10f, h);

        top.setPosition(0, h);
        bottom.setPosition(0, 0-bottom.getHeight());
        left.setPosition(0-left.getWidth(), 0);
        right.setPosition(w, 0);


        //System.out.println(table.getX()+ ", " + table.getWidth() + ", " + table.getPrefWidth() + ", " + table.getMaxWidth() + ", " + table.getCells().get(0).getMaxWidth() );
        stage = new Stage();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    float counter = 0;
    @Override
    public void render(float delta){

        counter += Gdx.graphics.getDeltaTime();
        stage.draw();

        if(counter >= 2f){
            moveTable();
            counter = 0;
        }
        table.act(delta);

    }


    public void moveTable(){


        table.setPosition(table.getX(), table.getY() + 5f);
        Rectangle tableRec = new Rectangle(table.getX(), table.getY(), table.getPrefWidth(), table.getPrefHeight());
        System.out.println(table.getX()+", " + table.getY()+", " +table.getPrefWidth()+", " + table.getPrefHeight());
        if(tableRec.overlaps(top) || tableRec.overlaps(bottom) || tableRec.overlaps(left)
        || tableRec.overlaps(right)){
            table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
            System.out.println("REACHED BORDER");
        }

    }


}
