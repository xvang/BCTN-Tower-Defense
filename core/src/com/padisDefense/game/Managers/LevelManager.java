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
    public void determineLevelSettings(){
        switch(level) {
            case (1):
                path = 1;
                enemyAmount = 200;
                break;
            case (2):
                path = 2;
                enemyAmount = 400;
                break;
            case (3):
                path = 3;
                enemyAmount = 200;
                break;
            case (4):
                path = 4;
                enemyAmount = 200;
                break;
            case (5):
                path = 5;
                enemyAmount = 100;
                break;

            case (6):
                path = 6;
                enemyAmount = 500;
                break;
            case (7):
                path = 7;
                enemyAmount = 500;
                break;

            default:
                path = 1;
                enemyAmount = 100;
                break;
        }
    }




    public void setLevel(int l){level = l;}


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

    public void spawnBuildableSpots(TowerManager tower, int level){

        Array<Vector2> positions = bssStorage.getBuildableLocations(level);

        //clearing any buildable spots from previous game
        for(int x = 0; x < tower.getBuildableArray().size; x++){
            tower.getBuildableArray().get(x).getTexture().dispose();
        }
        tower.getBuildableArray().clear();

        for(int x = 0; x < positions.size; x++){
            tower.addBuildableSpots(positions.get(x));
        }

    }

    public void dispose(){
    }

    public void reset(){

    }
}
