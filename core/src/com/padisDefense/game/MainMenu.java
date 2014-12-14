package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.padisDefense.game.TransitionScreens.FadeActor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.*;


/**
 * Menu class.
 * Another class that needs cleaning.
 *
 * @author Xeng
 * @param 'padi'
 * */
public class MainMenu extends ScreenAdapter {

    final float w = Gdx.graphics.getWidth();
    final float h = Gdx.graphics.getHeight();

    final float BUTTON_WIDTH = w / 3 - 50f;
    final float BUTTON_HEIGHT = h / 8;
    final float BUTTON_PAD = w / 80;

    private  Padi padi;

    private Stage stage;

    Group foreground;

    public Texture image;
    private Table table;






    private TweenManager tweenManager;
    public MainMenu (Padi p){

        padi = p;

    }

    Sprite bg;


    @Override
    public void show() {
        stage = new Stage();


        foreground = new Group();
        image = new Texture("limegreen.png");


        table = new Table();

        bg = padi.background;

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new FadeActor());


        final TextButton button = new TextButton("Play", padi.skin, "default");
        final TextButton button1 = new TextButton("Setting", padi.skin, "default");
        final TextButton button2 = new TextButton("Store", padi.skin, "default");
        final TextButton button3 = new TextButton("Instructions", padi.skin, "default");
        final TextButton button4 = new TextButton("Export to PDF", padi.skin, "default");
        final TextButton button5 = new TextButton("Log in", padi.skin, "default");


        table.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button1).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button2).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button3).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button4).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();

        table.setWidth(Gdx.graphics.getWidth() / 3);
        table.setHeight(Gdx.graphics.getHeight() / 3);

        //System.out.println("width = " + Gdx.graphics.getWidth() / 3);
        //System.out.println("height = " + Gdx.graphics.getHeight() / 3);

        Sprite spritey = new Sprite(new Texture((String)padi.assets.getRandomPic()));

        SpriteDrawable sd = new SpriteDrawable();
        sd.setSprite(spritey);

        table.setBackground(sd, true);

        table.background(sd).setHeight(table.getHeight() + 200f);
        table.background(sd).setWidth(table.getWidth() + 70f);
        /*
        button.setWidth(Gdx.graphics.getWidth() / 3);
        button.setHeight(40f);

        button1.setWidth(Gdx.graphics.getWidth() / 3);
        button1.setHeight(40f);

        button2.setWidth(Gdx.graphics.getWidth() / 3);
        button2.setHeight(40f);

        button3.setWidth(Gdx.graphics.getWidth() / 3);
        button3.setHeight(40f);

        button4.setWidth(Gdx.graphics.getWidth() / 3);
        button4.setHeight(40f);
*/
        button5.setWidth(Gdx.graphics.getWidth() / 9);
        button5.setHeight(60f);
        button5.pad(20f, 20f, 20f, 20f);


        /*button.setPosition(Gdx.graphics.getWidth() / 2 - 140f, Gdx.graphics.getHeight() / 2 + 70f);
        button1.setPosition(Gdx.graphics.getWidth() / 2 - 140f, Gdx.graphics.getHeight() / 2 - 0f);
        button2.setPosition(Gdx.graphics.getWidth() / 2 - 140f, Gdx.graphics.getHeight() / 2 - 70f);
        button3.setPosition(Gdx.graphics.getWidth() / 2 - 140f, Gdx.graphics.getHeight() / 2 - 140f);
        button4.setPosition(Gdx.graphics.getWidth() / 2 - 140f, Gdx.graphics.getHeight() / 2 - 210f);*/
        table.setPosition(Gdx.graphics.getWidth() / 3 - 30, Gdx.graphics.getHeight() / 6);


        button5.setPosition(Gdx.graphics.getWidth() -  120f, Gdx.graphics.getHeight() - 80f);


        //make function for this later.Get rid of repeated calls.
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setText("Play");
                padi.setScreen(padi.worldmap);
            }

        });

        button1.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                button1.setText("Setting");

                padi.setScreen(padi.setting);
            }
        });

        button2.addListener(new ClickListener() {

           @Override
            public void clicked(InputEvent event, float x, float y) {
                button2.setText("Store");
                padi.setScreen(padi.store);
            }
        });

        button3.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                button3.setText("Instruction");

                padi.setScreen(padi.instruction);

            }

        });

        button4.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                button4.setText("Export to PDF");

            }
        });

        /** LOG IN **/
        button5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                LogIn log = new LogIn();

               Gdx.input.getTextInput(log, "popup", "Hello");


            }
        });



        stage.addActor(foreground);


       /* foreground.addActor(button);
        foreground.addActor(button1);
        foreground.addActor(button2);
        foreground.addActor(button3);
        foreground.addActor(button4);*/
        foreground.addActor(table);

        foreground.addActor(button5);






        Gdx.input.setInputProcessor(stage);


        //Fading Animation
        aurelienribon.tweenengine.Timeline.createSequence().beginSequence()
                .push(Tween.from(padi.background, FadeActor.ALPHA, 3f).target(0))
                //.push(Tween.from(foreground, FadeActor.ALPHA, 2f).target(0))
                .start(tweenManager);


        //Table fade-in
        Tween.from(table, FadeActor.ALPHA, 0.1f).target(0).start(tweenManager);
        Tween.from(table, FadeActor.Y, 1f).target(Gdx.graphics.getHeight()).start(tweenManager);
    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(2f,.5f,0.88f,6);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);

        tweenManager.update(delta);
        padi.batch.begin();
        bg.draw(padi.batch);
        stage.draw();
        padi.batch.end();

        // System.out.println(".....");
        if (Gdx.input.justTouched()){
            Gdx.input.vibrate(100);

        }

    }


    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void dispose() {

    }

}
