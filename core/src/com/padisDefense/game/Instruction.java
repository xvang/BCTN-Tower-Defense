
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
import com.badlogic.gdx.utils.Array;


/**
 * @author Xeng.
 *
 *
 * */
public class Instruction extends ScreenAdapter {

    private final float w = Gdx.graphics.getWidth();
    private final float h = Gdx.graphics.getHeight();

    private int pages = 5;//total slides
    private Padi padi;


    Array<Image> slides;//contains all images.
    Image currentSlide;//points to current image.
    int currentPage = 0;//index of current page.

    Group buttons;
    Stage stage;


    TextButton next, back, menu;

    Instruction(Padi p){
        padi = p;
    }

    @Override
    public void show(){


        slides = new Array<Image>();
        buttons = new Group();
        stage = new Stage();

        for(int x = 0; x < pages; x++){
            slides.add(new Image(new Texture(Gdx.files.internal((String)padi.assets.getRandomPic()))));
            slides.get(x).setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        currentSlide = slides.get(0);
        next = new TextButton("Next", padi.skin, "default");
        back = new TextButton("Back", padi.skin, "default");
        menu = new TextButton("Menu", padi.skin, "default");
        buttons.addActor(next);
        buttons.addActor(back);
        buttons.addActor(menu);


        menu.setPosition(w / 2, h/40);
        next.setPosition(w - (next.getWidth() + 20f), 20f);
        back.setPosition(w/45, h/40);

        menu.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                padi.setScreen(padi.main_menu);
            }

        });


        //When button is clicked, stage is cleared and repopulated.
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(currentPage < pages - 1){
                    currentPage++;
                    stage.clear();
                    currentSlide = slides.get(currentPage);

                    stage.addActor(currentSlide);
                    stage.addActor(buttons);
                }
            }

        });

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPage > 0){
                    currentPage--;
                    stage.clear();
                    currentSlide = slides.get(currentPage);
                    stage.addActor(currentSlide);
                    stage.addActor(buttons);
                }
            }

        });



        stage.addActor(currentSlide);
        stage.addActor(buttons);
        Gdx.input.setInputProcessor(stage);
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
