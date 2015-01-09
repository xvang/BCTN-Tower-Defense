
package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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


    private Array<Image> slides;//contains all images.
    private Image currentSlide;//points to current image.
    private int currentPage = 0;//index of current page.


    private Stage stage;

    //displays the page number and pages left to go.
    String displayPage;
    Label pagesLeft;

    Instruction(Padi p){

        padi = p;
        slides = new Array<Image>();
        final Group buttons = new Group();
        stage = new Stage();

        for(int x = 0; x < pages; x++){
            slides.add(new Image(padi.assets.getRandomPic()));
            slides.get(x).setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        currentSlide = slides.get(0);

        //declaring buttons
        TextButton next = new TextButton("Next", padi.assets.skin2, "default");
        TextButton back = new TextButton("Back", padi.assets.skin2, "default");
        TextButton menu = new TextButton("Menu", padi.assets.skin2, "default");

        next.setSize(150f, 50f);
        back.setSize(150f, 50f);
        menu.setSize(150f, 50f);

        buttons.addActor(next);
        buttons.addActor(back);
        buttons.addActor(menu);


        menu.setPosition(w / 2 - menu.getWidth()/2, h/40);
        next.setPosition(w - (next.getWidth() + 20f), h/40);
        back.setPosition(w/45, h/40);

        //display page number and pages left.
        displayPage = String.valueOf(currentPage + 1) + " / " + String.valueOf(slides.size);
        pagesLeft = new Label(displayPage, padi.assets.someUIskin, "default");
        pagesLeft.setPosition(w - pagesLeft.getWidth() - 20f, h - pagesLeft.getHeight() - 20f);


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
                    displayPage = String.valueOf(currentPage + 1) + " / " + String.valueOf(slides.size);
                    pagesLeft.setText(displayPage);

                    stage.addActor(currentSlide);
                    stage.addActor(buttons);
                    stage.addActor(pagesLeft);
                }
            }

        });

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentPage > 0){
                    currentPage--;
                    stage.clear();
                    displayPage = String.valueOf(currentPage + 1) + " / " + String.valueOf(slides.size);
                    pagesLeft.setText(displayPage);
                    currentSlide = slides.get(currentPage);

                    stage.addActor(currentSlide);
                    stage.addActor(buttons);
                    stage.addActor(pagesLeft);
                }
            }

        });




        stage.addActor(currentSlide);
        stage.addActor(buttons);
        stage.addActor(pagesLeft);

    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        padi.assets.batch.begin();
        stage.draw();
        padi.assets.batch.end();

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
        currentPage = 0;
    }



}
