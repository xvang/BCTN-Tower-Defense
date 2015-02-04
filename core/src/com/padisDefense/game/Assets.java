package com.padisDefense.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Ball;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Towers.ArmyTower;
import com.padisDefense.game.Towers.GreenTower;
import com.padisDefense.game.Towers.BlueTower;
import com.padisDefense.game.Towers.OrangeTower;
import com.padisDefense.game.Towers.RedTower;
import com.padisDefense.game.Towers.PurpleTower;
import com.padisDefense.game.Towers.PinkTower;
import com.padisDefense.game.Towers.TowerStorage;
import com.padisDefense.game.Towers.VioletTower;
import com.padisDefense.game.Towers.YellowTower;
import com.padisDefense.game.Towers.Tower;

import aurelienribon.tweenengine.TweenManager;


public class Assets {

    Padi padi;
    public  static final int w = 900;
    public static final int h = 600;


    public static int SOUND_LEVEL = 25;
    public static int SOUND_LEVEL_ORIGINAL = 25;
    //if user mutes in game, SOUND_LEVEL is set to 1, but SOUND_LEVEL_ORIGINAL
    //should still have the original value if user wishes to un-mute.

    public static int DIFFICULTY = 25;

    public Array<String> splash_Pages;

    public SpriteBatch batch;
    public Skin skin;
    public Sprite background;
    public TweenManager tweenManager;
    public TowerStorage towerStorage;

    public Skin skin3, someUIskin, skin_balls, bubbleUI, towerButtons;
    public TextureAtlas towerAtlas, bulletAtlas;
   // public EnemyPool enemyPool;
   // public TowerPool towerCustomPool;

    public Pool<Enemy> enemyPool;
    public Pool<Tower> towerPool;


    public Music star, rain, east;
    public Assets(Padi p){

        padi = p;

        //test_comment
        splash_Pages = new Array<String>();

        //ADD MORE SPLASH SCREENS HERE
        splash_Pages.add("limegreen.png");

        //splash_Pages.add("allballs.png");

        star = Gdx.audio.newMusic(Gdx.files.internal("sound/Following_Your_Star.mp3"));
        rain = Gdx.audio.newMusic(Gdx.files.internal("sound/Raindrops_of_a_Dream.mp3"));
        east = Gdx.audio.newMusic(Gdx.files.internal("sound/Travels_to_the_East.mp3"));
        star.setLooping(true);
        rain.setLooping(true);
        east.setLooping(true);
        star.setVolume(SOUND_LEVEL/100f);
        rain.setVolume(SOUND_LEVEL/100f);
        east.setVolume(SOUND_LEVEL/100f);



        batch = new SpriteBatch();
        towerStorage = new TowerStorage();

        towerAtlas = new TextureAtlas("towers/tower.pack");
        bulletAtlas = new TextureAtlas("bullets/bullet.pack");
        someUIskin = new Skin(Gdx.files.internal("someUI.json"));
        skin3 = new Skin(new TextureAtlas("symbolsandtext.pack"));
        skin_balls = new Skin(new TextureAtlas("enemies/balls/ball.pack"));
        bubbleUI = new Skin(Gdx.files.internal("bubbleUI.json"));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        towerButtons = new Skin(Gdx.files.internal("towers/towerButtons/towerbuttons.json"));

        background = new Sprite(new Texture("limegreen.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tweenManager = new TweenManager();




        enemyPool = new Pool<Enemy>() {
            @Override
            protected Enemy newObject() {
                return new Ball();
            }
        };

        towerPool = new Pool<Tower>() {
            @Override
            protected Tower newObject() {
                return new Tower();
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
    //public int getOriginalSoundLevel(){return SOUND_LEVEL_ORIGINAL;}
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
        enemyPool.clear();
        towerPool.clear();
        star.dispose();
        rain.dispose();
        east.dispose();
    }

}
