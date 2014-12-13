
package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


import java.util.LinkedList;


/**
 * @author Xeng.
 *
 * @param 'padi'
 * */
public class Instruction extends ScreenAdapter {


    private int PAGE = 0;
    Padi padi;

    Image page1, page2, page3, page4;

    LinkedList<Image> imageLinkedList;


    Group foreground, background;
    Stage stage;


    TextButton next, back, menu;

    Instruction(Padi p){
        padi = p;
    }

    @Override
    public void show(){

        foreground = new Group();
        background = new Group();
        stage = new Stage();


        page1 = new Image(new Texture(Gdx.files.internal((String)padi.assets.getRandomPic())));
        page2 = new Image(new Texture(Gdx.files.internal((String)padi.assets.getRandomPic())));
        page3 = new Image(new Texture(Gdx.files.internal((String)padi.assets.getRandomPic())));
        page4 = new Image(new Texture(Gdx.files.internal((String)padi.assets.getRandomPic())));
        imageLinkedList = new LinkedList<Image>();

        imageLinkedList.add(0, page1);
        imageLinkedList.add(1, page2);
        imageLinkedList.add(2, page3);
        imageLinkedList.add(3, page4);

        page1.setHeight(Gdx.graphics.getHeight());
        page2.setHeight(Gdx.graphics.getHeight());
        page3.setHeight(Gdx.graphics.getHeight());
        page4.setHeight(Gdx.graphics.getHeight());


        page1.setWidth(Gdx.graphics.getWidth());
        page2.setWidth(Gdx.graphics.getWidth());
        page3.setWidth(Gdx.graphics.getWidth());
        page4.setWidth(Gdx.graphics.getWidth());


        next = new TextButton("Next", padi.skin, "default");
        back = new TextButton("Back", padi.skin, "default");
        menu = new TextButton("Menu", padi.skin, "default");

        menu.setPosition(Gdx.graphics.getWidth() / 2, 10f);
        next.setPosition(Gdx.graphics.getWidth() - (next.getWidth() + 10f), 10f);
        back.setPosition(10f, 10f);

        menu.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                padi.setScreen(padi.main_menu);
            }

        });

        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                changeSlide("Next");
            }

        });

        back.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeSlide("Back");
            }

        });


        background.addActor(page1);

        foreground.addActor(back);
        foreground.addActor(next);
        foreground.addActor(menu);

        stage.addActor(background);
        stage.addActor(foreground);


        Gdx.input.setInputProcessor(stage);
    }

    public void changeSlide(String button_pressed){

        //"Next" was chosen
        if(button_pressed == "Next"){
            background.clear();
            PAGE++;
            if(PAGE > 3)
                PAGE = 3;
            background.addActor(imageLinkedList.get(PAGE));

        }

        //"Back" was chosen.
        else{
            background.clear();
            PAGE--;

            if(PAGE < 0)
                PAGE = 0;
            background.addActor(imageLinkedList.get(PAGE));

        }

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        padi.batch.begin();
        stage.draw();
        padi.batch.end();

    }

    @Override
    public void resize(int x, int y){
        super.resize(x, y);
    }


    @Override
    public void dispose(){

        stage.dispose();
    }


    @Override
    public void pause(){

    }

    @Override
    public void hide(){

    }



}
