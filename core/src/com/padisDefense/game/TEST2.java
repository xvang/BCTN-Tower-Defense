package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class TEST2 extends ScreenAdapter {

    Skin skin;
    Stage stage;

    Sprite sprite;
    SpriteBatch batch;


    @Override
    public void show () {

        stage = new Stage();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("icetower.png"));
        sprite.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        skin = new Skin(Gdx.files.internal("uiskin.json"));



        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        sprite.draw(batch, 1);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void dispose () {

    }
}



