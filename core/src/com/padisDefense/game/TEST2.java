package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


public class TEST2 extends ScreenAdapter {


    private TextButton start;
    Skin skin;
    private Table table1, table2;


    Array<Image> images;

    Image test1, test2;
    Stage stage;


    @Override
    public void show () {

        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        start = new TextButton("START", skin, "default");
        start.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                System.out.println("CLICKED THE START BUTTON!");


            }
        });
        table1 = new Table();
        table2 = new Table();
        final Table masterTable = new Table();

        images = new Array<Image>();

        masterTable.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() / 3);
        //masterTable.setVisible(false);


        test1 = new Image(new Texture("tower1drag.png"));
        test2 = new Image(new Texture("tower2drag.png"));


        test1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                System.out.println("USE ADDACTOR(), NOT ADD() DAMMIT");

            }
        });

        table1.addActor(test1);
        table2.addActor(test2);



        masterTable.add(test1).row();
        masterTable.addActor(test2);
        masterTable.setVisible(true);
        stage.addActor(start);

        stage.addActor(masterTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void dispose () {

    }
}



