package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.padisDefense.game.Padi;




public class GeneralTesting extends ScreenAdapter {


    private Padi padi;
    ImageButton button, button1;

    SpriteBatch batch;
    Stage stage;

    public GeneralTesting(Padi p){
        padi = p;
    }

    @Override
    public void show(){
        batch = new SpriteBatch();
        stage = new Stage();
        Image pause = new Image(new TextureRegion(padi.assets.skin3.getRegion("SYMB_PAUSE")));
        Image pause1 = new Image(new TextureRegion(padi.assets.skin3.getRegion("SYMB_PAUSE")));
        button = new ImageButton(padi.assets.bubbleUI, "red");
        button1 = new ImageButton(padi.assets.bubbleUI, "red");

        button.add(pause);
        button1.add(pause1);

        button.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        button1.setPosition(Gdx.graphics.getWidth()/2 + button.getWidth() + 10f, Gdx.graphics.getHeight()/2);



        //button.setScale(0.5f);
        stage.addActor(button);
        stage.addActor(button1);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta){

        stage.draw();
    }
}
