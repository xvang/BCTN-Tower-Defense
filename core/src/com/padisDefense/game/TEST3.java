package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * Created by Toog on 12/22/2014.
 */
public class TEST3 extends ScreenAdapter{

    ShapeRenderer shapeRenderer;
    Image image;


    Stage stage;
    @Override
    public void show(){
        shapeRenderer = new ShapeRenderer();

        stage = new Stage();

        image = new Image(new Texture("badlogic.jpg"));

        image.addListener(new DragListener(){
            @Override
            public void drag(InputEvent e, float x, float y, int pointer){
                image.setCenterPosition(image.getX() + x, image.getY() + y);
            }
        });


        image.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        stage.addActor(image);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(2f,.5f,0.88f,6);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.draw();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.circle(image.getCenterX(),image.getCenterY(), 200f);
        shapeRenderer.end();

       /* shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(400, 100, 50, 50);
        shapeRenderer.circle(700, 500, 30);
        shapeRenderer.end();*/

    }


}
