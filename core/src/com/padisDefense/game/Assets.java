package com.padisDefense.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.BipedalDragon;
import com.padisDefense.game.Enemies.BlueSpider;
import com.padisDefense.game.Enemies.Cobra;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Golem;
import com.padisDefense.game.Enemies.IronSpider;
import com.padisDefense.game.Enemies.Mage;
import com.padisDefense.game.Enemies.RedSpider;

import java.util.Vector;

import aurelienribon.tweenengine.TweenManager;


public class Assets {

    Padi padi;
    public  static final int w = 900;
    public static final int h = 600;


    public static int SOUND_LEVEL = 20;
    public static int SOUND_LEVEL_ORIGINAL = 20;
    //if user mutes in game, SOUND_LEVEL is set to zero, but SOUND_LEVEL_ORIGINAL
    //should still have the original value if user wishes to un-mute.

    public static int DIFFICULTY = 99;

    public Array<String> splash_Pages;

    public SpriteBatch batch;
    public Skin skin;
    public Sprite background;
    public TweenManager tweenManager;

    public Skin skin2, skin3, someUIskin;
    public TextureAtlas towerAtlas;
    public CustomPool<Enemy> enemyCustomPoolL;
    public GameScreen gameScreen;



    public Assets(Padi p){

        padi = p;

        background = new Sprite(new Texture("test8.png"));
        //test_comment
        splash_Pages = new Array<String>();

        //ADD MORE SPLASH SCREENS HERE


        splash_Pages.add("limegreen.png");

        splash_Pages.add("test10.png");
        splash_Pages.add("test9.png");
        splash_Pages.add("test8.png");
        splash_Pages.add("test7.png");
        splash_Pages.add("test6.png");
        splash_Pages.add("test5.png");
        splash_Pages.add("test4.png");
        splash_Pages.add("test3.png");
        splash_Pages.add("test2.png");
        splash_Pages.add("test1.png");
        splash_Pages.add("badlogic.jpg");


        batch = new SpriteBatch();
        towerAtlas = new TextureAtlas("towers/tower.pack");
        someUIskin = new Skin(Gdx.files.internal("someUI.json"));
        skin3 = new Skin(new TextureAtlas("symbolsandtext.pack"));
        skin2 = new Skin(Gdx.files.internal("pack2.json"));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        background = new Sprite(new Texture("test1.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tweenManager = new TweenManager();

        //createEnemyPool();
        enemyCustomPoolL = new CustomPool<Enemy>() {
            @Override
            protected Enemy newObject(String type) {

                if(type.equals("bluespider")) return new BlueSpider();
                else if(type.equals("ironspider")) return new IronSpider();
                else if (type.equals("redspider")) return new RedSpider();
                else if(type.equals("mage")) return new Mage();
                else if(type.equals("golem")) return new Golem();
                else if(type.equals("cobra")) return new Cobra();
                else if(type.equals("bipedaldragon")) return new BipedalDragon();

                System.out.println("RETURNING NULL");
                return null;
            }
        };
    }



    //Returns a random picture to be used as Splash screen, or background.
    public Texture getRandomPic(){
        int position = (int)(Math.random()*100 % ((splash_Pages.size)));
        return new Texture(splash_Pages.get(position));
    }

    public void setSoundLevel(int level){SOUND_LEVEL = level;}
    public void setOriginalSoundLevel(int level){SOUND_LEVEL_ORIGINAL = level;}

    public void setDifficulty(int diff){
        DIFFICULTY = diff;
    }



    public float getScreenWidth(){return w;}
    public float getScreenHeight(){return h;}
    public int getSoundLevel(){return SOUND_LEVEL;}
    public int getOriginalSoundLevel(){return SOUND_LEVEL_ORIGINAL;}
    public int getDifficulty(){return DIFFICULTY;}




    public void createEnemyPool(){
        enemyCustomPoolL = new CustomPool<Enemy>() {
            @Override
            protected Enemy newObject(String type) {

                if(type.equals("bluespider")) return new BlueSpider();
                else if(type.equals("ironspider")) return new IronSpider();
                else if (type.equals("redspider")) return new RedSpider();
                else if(type.equals("mage")) return new Mage();
                else if(type.equals("golem")) return new Golem();
                else if(type.equals("cobra")) return new Cobra();
                else if(type.equals("bipedaldragon")) return new BipedalDragon();

                System.out.println("RETURNING NULL");
                return null;
            }
        };

        Array<Enemy> eArray = new Array<Enemy>();
        for(int x = 0; x < 25; x++){

            eArray.add(enemyCustomPoolL.obtain("bipedaldragon"));
            eArray.add(enemyCustomPoolL.obtain("bluespider"));
            eArray.add(enemyCustomPoolL.obtain("cobra"));
            eArray.add(enemyCustomPoolL.obtain("golem"));
            eArray.add(enemyCustomPoolL.obtain("ironspider"));
            eArray.add(enemyCustomPoolL.obtain("mage"));
            eArray.add(enemyCustomPoolL.obtain("redspider"));

        }

        System.out.println("Size of eArray: " + eArray.size);
        enemyCustomPoolL.freeAll(eArray);
    }

    public void initGameScreen(){
        gameScreen = new GameScreen(padi);
    }
    public void dispose(){


    }

}
