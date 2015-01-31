package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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


/**
 * Menu class.
 *
 * 'show()' is executed ONCE every time the mainmenu object is accessed.
 * so if everything is declared in show, then show keeps declaring every time
 * the screen is accessed.
 * */
public class MainMenu extends ScreenAdapter {



    private  Padi padi;
    private Stage stage;
    private TweenManager tweenManager;
    Table table;

    public MainMenu(Padi p){
        padi = p;
    }
    @Override
    public void show(){

        //padi.assets.rain.play();
        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();

        final float BUTTON_WIDTH = w / 3 - 50f;
        final float BUTTON_HEIGHT = h / 8;
        final float BUTTON_PAD = w / 80;
        stage = new Stage();


        Group foreground = new Group();

        table = new Table();

        //background = padi.assets.background;

        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new FadeActor());


        final TextButton button = new TextButton("Play", padi.assets.bubbleUI, "green");
        final TextButton button1 = new TextButton("Setting", padi.assets.bubbleUI, "green");
        final TextButton button2 = new TextButton("Store", padi.assets.bubbleUI, "green");
        final TextButton button3 = new TextButton("Instructions", padi.assets.bubbleUI, "green");
        final TextButton button5 = new TextButton("Profile", padi.assets.bubbleUI, "green");
        final TextButton button6 = new TextButton("Credits", padi.assets.bubbleUI, "green");

        table.add(button).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button1).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button2).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button3).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();
        table.add(button6).width(BUTTON_WIDTH).pad(BUTTON_PAD).height(BUTTON_HEIGHT).row();

        table.setWidth(w / 3);
        table.setHeight(h / 3);

        Sprite spritey = new Sprite(padi.assets.getRandomPic());

        SpriteDrawable sd = new SpriteDrawable(spritey);


        table.setBackground(sd, true);

        table.background(sd).setHeight(table.getHeight() + 200f);
        table.background(sd).setWidth(table.getWidth() + 70f);

        button5.setWidth(Gdx.graphics.getWidth() / 9);
        button5.setHeight(60f);
        button5.pad(20f, 20f, 20f, 20f);


        table.setPosition(Gdx.graphics.getWidth() / 3 - 30, Gdx.graphics.getHeight() / 6);


        button5.setPosition(Gdx.graphics.getWidth() -  button5.getWidth() - 50f,
                Gdx.graphics.getHeight() - 80f);


        //make function for this later.Get rid of repeated calls.
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                padi.setScreen(padi.worldmap);
            }

        });

        button1.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                padi.setScreen(padi.setting);
            }
        });

        button2.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                padi.setScreen(padi.store);
            }
        });

        button3.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                padi.setScreen(padi.instruction);

            }

        });

        /** LOG IN **/
        button5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                padi.setScreen(padi.profile);
            }
        });

        button6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                padi.setScreen(padi.credit);
            }
        });


        stage.addActor(foreground);

        foreground.addActor(table);

        foreground.addActor(button5);

        Gdx.input.setInputProcessor(stage);



        Gdx.input.setInputProcessor(stage);
        //Fading Animation
        aurelienribon.tweenengine.Timeline.createSequence().beginSequence()
                .push(Tween.from(padi.assets.background, FadeActor.ALPHA, 3f).target(0))
                        //.push(Tween.from(foreground, FadeActor.ALPHA, 2f).target(0))
                .start(tweenManager);

        //Table fade-in
        Tween.from(table, FadeActor.ALPHA, 0.1f).target(0).start(tweenManager);
        Tween.from(table, FadeActor.Y, 1f).target(Gdx.graphics.getHeight()).start(tweenManager);


    }

    //Sprite background;


    /*@Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        //Fading Animation
        aurelienribon.tweenengine.Timeline.createSequence().beginSequence()
                .push(Tween.from(padi.assets.background, FadeActor.ALPHA, 3f).target(0))
                        //.push(Tween.from(foreground, FadeActor.ALPHA, 2f).target(0))
                .start(tweenManager);

        //Table fade-in
        Tween.from(table, FadeActor.ALPHA, 0.1f).target(0).start(tweenManager);
        Tween.from(table, FadeActor.Y, 1f).target(Gdx.graphics.getHeight()).start(tweenManager);

    }*/


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        tweenManager.update(delta);
        padi.assets.batch.begin();
        //background.draw(padi.assets.batch);
        stage.draw();
        padi.assets.batch.end();

    }


    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void dispose() {

    }

}
