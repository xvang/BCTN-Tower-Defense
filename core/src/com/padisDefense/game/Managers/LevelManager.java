package com.padisDefense.game.Managers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
    private Array<String> bgPics;
    BuildableSpotSpawnStorage bssStorage;

    public LevelManager(GameScreen g, Padi p){
        game = g;
        padi = p;

        bgPics = new Array<String>();

        bgPics.add("tiles/path1.png");
        bgPics.add("tiles/path2.png");
        bgPics.add("tiles/path3.png");
        bgPics.add("tiles/path4.png");
        bgPics.add("tiles/path5.png");
        bgPics.add("tiles/path6.png");



        bssStorage = new BuildableSpotSpawnStorage();
    }



    public int getEnemyAmount(){return enemyAmount;}
    public int getPath(){return path;}

    /**'level' is set by the game screen before this function runs.*/
    public void determineLevelSettings(){
        switch(level) {
            case (1):
                path = 1;
                enemyAmount = 800;
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

            default:
                path = 1;
                enemyAmount = 500;
                break;
        }
    }




    public void setLevel(int l){level = l;}




    public Sprite getBackground(){
        return new Sprite(new Texture(bgPics.get(level-1)));
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
