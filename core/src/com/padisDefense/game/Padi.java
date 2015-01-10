package com.padisDefense.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.padisDefense.game.Tests.AnimationTest;
import com.padisDefense.game.Tests.AtlasTest;
import com.padisDefense.game.Tests.DefaultAnimationTest2;
import com.padisDefense.game.Tests.DefaultSpriteSheetTest;
import com.padisDefense.game.Tests.GeneralTesting;
import com.padisDefense.game.Tests.PathRunTest;
import com.padisDefense.game.Tests.PathRunTest2;
import com.padisDefense.game.Tests.RotateTest;
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



    @Override
    public void create(){

        /*assets = new Assets(this);
        assets.initGameScreen();

        main_menu = new MainMenu(this);
        setting = new Setting(this);
        worldmap = new WorldMap(this);
        player = new Player();
        store = new Store(this);
        instruction = new Instruction(this);

        this.setScreen(new Splash(this));*/
        //this.setScreen(new RotateTest());
        //this.setScreen(new GeneralTesting(this));
        this.setScreen(new PathRunTest());
        //this.setScreen(new PathRunTest2());

        //this.setScreen(new AnimationTest());
        //this.setScreen(new DefaultSpriteSheetTest());
        //this.setScreen(new AtlasTest());
        //this.setScreen(new DefaultAnimationTest2());

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