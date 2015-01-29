package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;


public class Credit extends ScreenAdapter {


    Stage stage;
    SpriteBatch batch;
    Array<CreditAnimation> animations;

    public Credit(){

    }



    @Override
    public void show(){
        batch = new SpriteBatch();
        animations = new Array<CreditAnimation>();

        animations.add(new CreditAnimation("enemies/bipedalDragon_all.png",6,4));
        animations.add(new CreditAnimation("enemies/blue_walk_updated.png",4,4));
        animations.add(new CreditAnimation("enemies/spider02.png",10,5));
        animations.add(new CreditAnimation("enemies/spider03.png",10,5));
        animations.add(new CreditAnimation("enemies/spider05.png",10,5));
        animations.add(new CreditAnimation("enemies/king_cobra.png",3 ,4));
        animations.add(new CreditAnimation("enemies/golem-walk.png",7,4));
        animations.add(new CreditAnimation("enemies/mage.png", 8,2));
        animations.add(new CreditAnimation("animation/fire_02.png", 8,8));
        animations.add(new CreditAnimation("animation/particlefx_06.png", 8,8));
        animations.add(new CreditAnimation("animation/particlefx_07.png", 8,8));


        Table table = new Table();
        for(int x = 0; x < animations.size; x++){
            table.add(new CreditAnimation("animation/particlefx_07.png", 8,8));
            table.add(new Image(new Texture("duck.png"))).pad(20f).row();

        }

        final ScrollPane scrollPane = new ScrollPane(table);

        final Table scrollWrap = new Table();
        scrollWrap.setFillParent(true);
        scrollWrap.add(scrollPane);



        stage = new Stage();


        stage.addActor(scrollWrap);

        /*for(int x = 0; x < animations.size; x++){
            stage.addActor(animations.get(x));
        }*/
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.draw();

        batch.begin();
        for(int x = 0 ;x < animations.size; x++){
            animations.get(x).animate(batch);

        }

        batch.end();
    }
}
