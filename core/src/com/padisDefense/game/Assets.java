package com.padisDefense.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Ball;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Towers.AoeTower;
import com.padisDefense.game.Towers.LaserTower;
import com.padisDefense.game.Towers.RogueTower;
import com.padisDefense.game.Towers.SniperTower;
import com.padisDefense.game.Towers.SpeedTower;
import com.padisDefense.game.Towers.StrengthTower;
import com.padisDefense.game.Towers.Tower;

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

    public Skin skin2, skin3, someUIskin, skin_balls;
    public TextureAtlas towerAtlas;
    public CustomPool<Enemy> enemyCustomPoolL;
    public TowerPool towerCustomPool;


    public Assets(Padi p){

        padi = p;

        background = new Sprite(new Texture("badlogic.jpg"));
        //test_comment
        splash_Pages = new Array<String>();

        //ADD MORE SPLASH SCREENS HERE
        splash_Pages.add("limegreen.png");

        splash_Pages.add("badlogic.jpg");

        batch = new SpriteBatch();
        towerAtlas = new TextureAtlas("towers/tower.pack");
        someUIskin = new Skin(Gdx.files.internal("someUI.json"));
        skin3 = new Skin(new TextureAtlas("symbolsandtext.pack"));
        skin2 = new Skin(Gdx.files.internal("pack2.json"));
        skin_balls = new Skin(new TextureAtlas("enemies/balls/balls.pack"));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        background = new Sprite(new Texture("badlogic.jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tweenManager = new TweenManager();




        //createEnemyPool();
        enemyCustomPoolL = new CustomPool<Enemy>() {
            @Override
            protected Enemy newObject(String type) {

                /*if(type.equals("bluespider")) return new BlueSpider();
                else if(type.equals("ironspider")) return new IronSpider();
                else if (type.equals("redspider")) return new RedSpider();
                else if(type.equals("mage")) return new Mage();
                else if(type.equals("golem")) return new Golem();
                else if(type.equals("cobra")) return new Cobra();
                else if(type.equals("bipedaldragon")) return new BipedalDragon();
                else if(type.equals("blueimp")) return new BlueImp();*/
                if(type.equals("armyball")) return new Ball("orange", skin_balls.getSprite("orangeball"));
                else if(type.equals("blueball")) return new Ball("blue", skin_balls.getSprite("blueball"));
                else if(type.equals("greenball")) return new Ball("green", skin_balls.getSprite("greenball"));
                else if(type.equals("orangeball")) return new Ball("orange", skin_balls.getSprite("orangeball"));
                else if(type.equals("pinkball")) return new Ball("pink", skin_balls.getSprite("pinkball"));
                else if(type.equals("purpleball")) return new Ball("purple", skin_balls.getSprite("purpleball"));
                else if(type.equals("violetball")) return new Ball("violet", skin_balls.getSprite("violetball"));
                else if(type.equals("yellowball")) return new Ball("yellow", skin_balls.getSprite("yellowball"));


                System.out.println("RETURNING NULL");
                return null;
            }

            @Override
            protected Enemy newObject(String type, int level, Vector2 spawnPosition){return null;}
        };




        //TODO: find a way to combine these two functions in pool declaration.
        towerCustomPool = new TowerPool() {
            @Override
            protected Tower newObject(String type, int level, Vector2 spawnPosition) {


                Sprite picture = padi.assets.towerAtlas.createSprite(type, level);

                if(type.equals("ROGUE")) return new RogueTower(spawnPosition, picture, level);
                else if(type.equals("SNIPER")) return new SniperTower(spawnPosition, picture, level);
                else if(type.equals("STRENGTH")) return new StrengthTower(spawnPosition, picture, level);
                else if(type.equals("SPEED")) return new SpeedTower(spawnPosition, picture, level);
                else if(type.equals("AOE")) return new AoeTower(spawnPosition, picture, level);
                else if(type.equals("LASER")) return new LaserTower(spawnPosition, picture, level);
                else  return new RogueTower(spawnPosition, picture, 1);
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





    public void dispose(){

    }

}
