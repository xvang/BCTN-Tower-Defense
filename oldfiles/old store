package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * This class won't stay for long like this.
 * This is suppose to be the store class
 * User can view and unlock items.
 *
 * @author Xeng
 * @param 'padi'
 * */
public class Store extends ScreenAdapter {

    static final float item_width = 150f;
    static final float item_height = 150f;
    static final float padd = 20f;
    boolean toTheTopOnce = false;
    Padi padi;
    SpriteBatch spritebatch, HUDbatch;
    Stage stage, HUDstage;
    Texture texture;
    Skin skin;

    Image image, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10;
    Group background, foreground;

    Table table;

    //For table background.
    Sprite s;
    SpriteDrawable sd;

    //Textbox for User's money.
    Label label_money;


    Store(Padi p){
        padi = p;
    }


    @Override
    public void show() {

        spritebatch = new SpriteBatch();
        HUDbatch = new SpriteBatch();
        stage = new Stage();
        HUDstage = new Stage();
        background = new Group();
        foreground = new Group();
        texture = new Texture("test1.png");
        table = new Table();
        skin = new Skin(Gdx.files.internal("uiskin.json"));



        s = new  Sprite(new Texture("test5.png"));
        sd = new SpriteDrawable(s);

        //all the images
        image = new Image(texture);
        item1 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item2 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item3 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item4 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item5 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item6 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item7 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item8 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item9 = new Image(new Texture((String)padi.assets.getRandomPic()));
        item10 = new Image(new Texture((String)padi.assets.getRandomPic()));


        //Adding items to table.
        table.add(item1).width(item_width).height(item_height).pad(padd);
        table.add(item2).width(item_width).height(item_height).pad(padd);
        table.add(item3).width(item_width).height(item_height).pad(padd); table.row();
        table.add(item4).width(item_width).height(item_height).pad(padd);
        table.add(item5).width(item_width).height(item_height).pad(padd);
        table.add(item6).width(item_width).height(item_height).pad(padd);table.row();
        table.add(item7).width(item_width).height(item_height).pad(padd);
        table.add(item8).width(item_width).height(item_height).pad(padd);
        table.add(item9).width(item_width).height(item_height).pad(padd); table.row();
        table.add(item10).width(item_width).height(item_height).pad(padd);


        //Setting background of table.
        table.setBackground(sd, true);

        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight()*2);




        image.setHeight(Gdx.graphics.getHeight()*2);
        image.setWidth(Gdx.graphics.getWidth());


        //Building background/foreground.
        background.addActor(image);

        //Building foreground.
        foreground.addActor(table);
        //foreground.addActor(label_money);

        //Adding to stage.
        // stage.addActor(background);
        stage.addActor(foreground);




        //Now for the second screen(?)
        label_money = new Label("$ " + padi.player.getMoney(), skin);
        label_money.setPosition(table.getWidth() - label_money.getWidth(), 200);
        label_money.setColor(new Color(210,0, 0, 200));
        label_money.setSize(400f, 400f);

        HUDstage.addActor(label_money);



        stage.addListener(new ActorGestureListener() {


            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {

                stage.getCamera().translate(0, -deltaY/2, 0);
                stage.getCamera().update();

                //System.out.println(x + ", " + y + ", " + "      " + deltaX + ", " + deltaY);

                scrollLimit(y);
            }


            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
               // System.out.println("Touchdown: " + x + ", " + y);
                //System.out.println("stage.getCamera() = " + stage.getCamera().position.x + ", " +
               // stage.getCamera().position.y);
                scrollLimit(y);
            }
        });


        Gdx.input.setInputProcessor(stage);


        System.out.println("First stage.camera.y = " + stage.getCamera().position.y);
    }


    public void toTheTop(){

        while(stage.getCamera().position.y < 899 && !toTheTopOnce){
            stage.getCamera().translate(0,1,0);
            stage.getCamera().update();

        }
        toTheTopOnce = true;
    }

    public void scrollLimit(float y){

        if (y <= 301){
            while(stage.getCamera().position.y <= 301){
                stage.getCamera().translate(0, 1, 0);
                stage.getCamera().update();
            }
        }

        else if(y >= 899){
            while(stage.getCamera().position.y >= 899){
                stage.getCamera().translate(0, - 1, 0);
                stage.getCamera().update();
            }

        }
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spritebatch.setProjectionMatrix(stage.getCamera().combined);
        //spritebatch.setProjectionMatrix(HUDstage.getCamera().combined);
        toTheTop();

        //Gdx.gl.glViewport(0,0,(Gdx.graphics.getWidth()/4)*3, Gdx.graphics.getHeight());

        spritebatch.begin();
        stage.draw();
        HUDstage.draw();
        spritebatch.end();

        //Gdx.gl.glViewport((Gdx.graphics.getWidth()/4)*3, 0, Gdx.graphics.getWidth(), 0);

        HUDbatch.begin();
        HUDstage.draw();
        HUDbatch.end();






        scrollLimit(stage.getCamera().position.y);
    }

    @Override
    public void resize(int width, int height){

        //System.out.println("resize() executed.");
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose(){

        stage.dispose();
        texture.dispose();
        spritebatch.dispose();
    }

    @Override
    public void pause(){

    }

    @Override
    public void hide(){
    }

}