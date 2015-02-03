package com.padisDefense.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.padisDefense.game.TransitionScreens.Splash;

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
    public LoadSaveGame loadsave;
    public Profile profile;
    Credit credit;
    GameScreen gameScreen;
    //public LogIn login;


    //These are global variables?
    //Every screen should have access to them.



    @Override
    public void create(){



        assets = new Assets(this);
        gameScreen = new GameScreen(this);
        main_menu = new MainMenu(this);
        setting = new Setting(this);
        worldmap = new WorldMap(this);
        store = new Store(this);
        instruction = new Instruction(this);
        profile = new Profile(this);
        loadsave = new LoadSaveGame();
        player = loadsave.loadPlayer();
        credit = new Credit(this);


        this.setScreen(new Splash(this, main_menu));
       // this.setScreen(new Test1());
        //this.setScreen(new ItemTest());
        //this.setScreen(credit);
        //this.setScreen(new LocalSaveTest());
        //this.setScreen(new ChangeTextureInSpriteTest());
        //this.setScreen(new BallSpawnTest());
        //this.setScreen(new RotateTest());
        //this.setScreen(new WidgetTest());
        //this.setScreen(new GeneralTesting(this));
        //this.setScreen(new PathRunTest());
        //this.setScreen(new PathRunTest2());

        //this.setScreen(new BuildableSpotSpawnTest());
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
        assets.dispose();
        //the dispose() function in Asset is not called automatically, so we need to call it.
        //the other objects are screens, and their dispose() function is called automatically.

    }

    @Override
    public void resume(){
        super.resume();
    }
}
