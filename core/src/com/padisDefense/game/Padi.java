package com.padisDefense.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.padisDefense.game.TransitionScreens.Splash;

import aurelienribon.tweenengine.TweenManager;

/**
 *
 * This is the main game object.
 * This class controls the UI navigation.
 *@author  Xeng
 *
 *
 * **/
public class Padi extends Game implements ApplicationListener{

    //Declaring the Screens.
    public MainMenu main_menu;
    public Instruction instruction;
    public Assets assets;
    public Setting setting;
    public WorldMap worldmap;
    public Player player;
    public Store store;
    //public LogIn login;


    //These are global variables?
    //Every screen should have access to them.
    public SpriteBatch batch;
    public Skin skin;
    public Sprite background;
    public TweenManager tweenManager;


    //When I need to test something, I'll set the screen
    //to test(setScreen(test)).  The setScreen is at the end
    //of the create() method.
    TEST2 TEST2;

    @Override
    public void create(){

        assets = new Assets(this);
        main_menu = new MainMenu(this);
        setting = new Setting(this);
        worldmap = new WorldMap(this);
        player = new Player();
        store = new Store(this);
        TEST2 = new TEST2();
        instruction = new Instruction(this);

        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        background = new Sprite(new Texture("test1.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tweenManager = new TweenManager();



        this.setScreen(new Splash(this));
       //this.setScreen(TEST2);
        //this.setScreen(new TEST3());
        //this.setScreen(new TEST4());
        //this.setScreen(new TEST5());
        //this.setScreen(new AnimationTest());

    }



    @Override
    public void render(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }


    @Override
    public void resize(int x, int y){
        super.resize(x, y);
    }

    @Override
    public void pause(){
        super.pause();
    }
    @Override
    public void dispose(){

        super.dispose();

    }

    @Override
    public void resume(){
        super.resume();
    }
}


//TODO: Leaderboard Screen, Login Screen, StoreScreen, .... a lot of screens. maybe too many screens.