package com.padisDefense.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Ball;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Towers.ArmyTower;
import com.padisDefense.game.Towers.GreenTower;
import com.padisDefense.game.Towers.BlueTower;
import com.padisDefense.game.Towers.OrangeTower;
import com.padisDefense.game.Towers.RedTower;
import com.padisDefense.game.Towers.PurpleTower;
import com.padisDefense.game.Towers.PinkTower;
import com.padisDefense.game.Towers.VioletTower;
import com.padisDefense.game.Towers.YellowTower;
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

    public Skin skin3, someUIskin, skin_balls, bubbleUI, towerButtons;
    public TextureAtlas towerAtlas, bulletAtlas;
    public CustomPool<Enemy> enemyCustomPoolL;
    public TowerPool towerCustomPool;

    public Music star, rain, east;
    public Assets(Padi p){

        padi = p;

        background = new Sprite(new Texture("badlogic.jpg"));
        //test_comment
        splash_Pages = new Array<String>();

        //ADD MORE SPLASH SCREENS HERE
        splash_Pages.add("limegreen.png");

        splash_Pages.add("badlogic.jpg");

        star = Gdx.audio.newMusic(Gdx.files.internal("sound/Following_Your_Star.mp3"));
        rain = Gdx.audio.newMusic(Gdx.files.internal("sound/Raindrops_of_a_Dream.mp3"));
        east = Gdx.audio.newMusic(Gdx.files.internal("sound/Travels_to_the_East.mp3"));
        star.setLooping(true);
        rain.setLooping(true);
        east.setLooping(true);


        batch = new SpriteBatch();
        towerAtlas = new TextureAtlas("towers/tower.pack");
        bulletAtlas = new TextureAtlas("bullets/bullet.pack");
        someUIskin = new Skin(Gdx.files.internal("someUI.json"));
        skin3 = new Skin(new TextureAtlas("symbolsandtext.pack"));
        skin_balls = new Skin(new TextureAtlas("enemies/balls/ball.pack"));
        bubbleUI = new Skin(Gdx.files.internal("bubbleUI.json"));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        towerButtons = new Skin(Gdx.files.internal("towers/towerButtons/towerbuttons.json"));

        background = new Sprite(new Texture("badlogic.jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tweenManager = new TweenManager();




        //createEnemyPool();
        enemyCustomPoolL = new CustomPool<Enemy>() {
            @Override
            protected Enemy newObject(String type) {

                if(type.equals("armyball")) return new Ball("army", skin_balls.getSprite("armyball"));
                else if(type.equals("blueball")) return new Ball("blue", skin_balls.getSprite("blueball"));
                else if(type.equals("greenball")) return new Ball("green", skin_balls.getSprite("greenball"));
                else if(type.equals("orangeball")) return new Ball("orange", skin_balls.getSprite("orangeball"));
                else if(type.equals("pinkball")) return new Ball("pink", skin_balls.getSprite("pinkball"));
                else if(type.equals("purpleball")) return new Ball("purple", skin_balls.getSprite("purpleball"));
                else if(type.equals("violetball")) return new Ball("violet", skin_balls.getSprite("violetball"));
                else if(type.equals("yellowball")) return new Ball("yellow", skin_balls.getSprite("yellowball"));
                else if (type.equals("redball")) return new Ball("red", skin_balls.getSprite("redball"));

                System.out.println("RETURNING NULL");
                return null;
            }

            @Override
            protected Enemy newObject(String type, int level, Vector2 spawnPosition){return null;}
        };




        //TODO: find a way to combine these two functions in pool declaration.
        //right now they use two different pools.
        towerCustomPool = new TowerPool() {
            @Override
            protected Tower newObject(String type, int level, Vector2 spawnPosition) {


                Sprite picture = padi.assets.towerAtlas.createSprite(type, level);

                if(type.equals("PURPLE")){
                    Sprite bullet = bulletAtlas.createSprite("purple_bullet");
                    return new PurpleTower(spawnPosition, picture, level, bullet);
                }
                else if(type.equals("BLUE")){
                    Sprite bullet = bulletAtlas.createSprite("blue_bullet");
                    return new BlueTower(spawnPosition, picture, level, bullet);
                }
                else if(type.equals("YELLOW")){
                    Sprite bullet = bulletAtlas.createSprite("yellow_bullet");
                    return new YellowTower(spawnPosition, picture, level, bullet);
                }
                else if(type.equals("PINK")){
                    Sprite bullet = bulletAtlas.createSprite("pink_bullet");
                    return new PinkTower(spawnPosition, picture, level, bullet);
                }
                else if(type.equals("GREEN")){
                    Sprite bullet = bulletAtlas.createSprite("green_bullet");
                    return new GreenTower(spawnPosition, picture, level, bullet);
                }
                else if(type.equals("RED")){
                    Sprite bullet = bulletAtlas.createSprite("red_bullet");
                    return new RedTower(spawnPosition, picture, level, bullet);
                }
                else if(type.equals("ARMY")){
                    Sprite bullet = bulletAtlas.createSprite("army_bullet");
                    return new ArmyTower(spawnPosition, picture, level, bullet);
                }

                else if(type.equals("VIOLET")){
                    Sprite bullet = bulletAtlas.createSprite("violet_bullet");
                    return new VioletTower(spawnPosition, picture, level, bullet);
                }
                else if(type.equals("ORANGE")){
                    Sprite bullet = bulletAtlas.createSprite("orange_bullet");
                    return new OrangeTower(spawnPosition, picture, level, bullet);
                }
                else{
                    Sprite bullet = bulletAtlas.createSprite("purple_bullet");
                    return new PurpleTower(spawnPosition, picture, 1, bullet);
                }
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
        if(diff > 0 && diff <= 100)
            DIFFICULTY = diff;
        else
            DIFFICULTY = 1;
    }



    public float getScreenWidth(){return w;}
    public float getScreenHeight(){return h;}
    public int getSoundLevel(){return SOUND_LEVEL;}
    public int getOriginalSoundLevel(){return SOUND_LEVEL_ORIGINAL;}
    public int getDifficulty(){return DIFFICULTY;}


    public void dispose(){
        batch.dispose();
        skin.dispose();
        skin3.dispose();
        someUIskin.dispose();
        skin_balls.dispose();
        bubbleUI.dispose();
        towerButtons.dispose();
        towerAtlas.dispose();
        bulletAtlas.dispose();
        enemyCustomPoolL.clear();
        towerCustomPool.clear();
        star.dispose();
        rain.dispose();
        east.dispose();
    }

}
