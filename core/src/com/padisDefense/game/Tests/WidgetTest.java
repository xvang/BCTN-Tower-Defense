package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;




public class WidgetTest extends ScreenAdapter {

    Skin skin2;
    ImageButton button1, button2;
    TextureAtlas atlas;
    Table table;

    Stage stage;
    public WidgetTest(){

        skin2 = new Skin(Gdx.files.internal("pack2.json"));
        atlas = new TextureAtlas("symbolsandtext.pack");
        button1 = new ImageButton(skin2, "pause");

        Sprite sprite = atlas.createSprite("SYMB_PAUSE");
        NinePatch image = atlas.createPatch("SYMB_PAUSE");


        //button1.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //button2.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);

        button2 = new ImageButton(skin2, "pause");

        button1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                System.out.println("1 clicked");
            }
        });

        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                System.out.println("2 clicked");
            }
        });

        table = new Table();

        table.add(button1).width(50f).height(50f).padRight(100f);
        table.add(button2).width(40f).height(40f);
        table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        stage = new Stage();
        stage.addActor(table);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){

        Gdx.gl20.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
}
