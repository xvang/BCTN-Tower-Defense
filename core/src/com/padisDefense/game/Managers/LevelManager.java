package com.padisDefense.game.Managers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.BipedalDragon;
import com.padisDefense.game.Enemies.BlueImp;
import com.padisDefense.game.Enemies.BlueSpider;
import com.padisDefense.game.Enemies.Cobra;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Golem;
import com.padisDefense.game.Enemies.IronSpider;
import com.padisDefense.game.Enemies.RedSpider;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Towers.BuildableSpotSpawnStorage;

/**
 *
 * This class is suppose to determine how many enemies spawn per level, which path is chosen, etc.
 *
 * This Manager is not suppose to render anything. It just pretty much tells the other class(es)
 * what level everyone is suppose to be on.
 *
 *
 * @author Xeng.
* **/
public class LevelManager {

    Padi padi;
    GameScreen game;
    private int path = 0;
    private int enemyAmount = 0;
    private int initialMoney = 0;
    private int level = 0;

    BuildableSpotSpawnStorage bssStorage;

    public LevelManager(GameScreen g, Padi p){
        game = g;
        padi = p;



        bssStorage = new BuildableSpotSpawnStorage();



    }



    public int getEnemyAmount(){return enemyAmount;}
    public int getPath(){return path;}

    /**'level' is set by the game screen before this function runs.*/
    public void determineLevelSettings(int l){
        level = l;
        switch(level) {
            case (1):
                path = 1;
                enemyAmount = 200;
                initialMoney = 10000;
                break;
            case (2):
                path = 2;
                enemyAmount = 300;
                initialMoney = 11000;
                break;
            case (3):
                path = 3;
                enemyAmount = 310;
                initialMoney = 15000;
                break;
            case (4):
                path = 4;
                enemyAmount = 330;
                initialMoney = 15000;
                break;
            case (5):
                path = 5;
                enemyAmount = 350;
                initialMoney = 18000;
                break;

            case (6):
                path = 6;
                enemyAmount = 400;
                initialMoney = 20000;
                break;
            case (7):
                path = 7;
                enemyAmount = 555;
                initialMoney = 20000;
                break;

            default:
                path = 1;
                enemyAmount = 100;
                initialMoney = 5000;
                break;
        }
    }

    public Enemy getBoss(){

        switch(level) {
            case (1):
                return new RedSpider();
            case (2):
                return new BlueSpider();
            case (3):
                return new IronSpider();
            case (4):
                return new Cobra();
            case (5):
                return new BipedalDragon();
            case (6):
                return new BlueImp();
            case (7):
                return new Golem();
            default:
                return new RedSpider();
        }
    }

    public int getInitialMoney(){return initialMoney;}

    //Spawns buildable spots.
    //the locations of the buildable spots are stored the buildableSpotStorage object.
    public void spawnBuildableSpots(TowerManager tower, int level){

        Array<Vector2> positions = bssStorage.getBuildableLocations(level);

        //clearing any buildable spots from previous game
        for(int x = 0; x < tower.getBuildableArray().size; x++){
            tower.getBuildableArray().get(x).getTexture().dispose();
        }
        tower.getBuildableArray().clear();

        //add in new buildable spots.
        for(int x = 0; x < positions.size; x++){
            tower.addBuildableSpots(positions.get(x));
        }
    }

    public void dispose(){
    }

    public void reset(){

    }
}
