package com.padisDefense.game.Managers;


import com.padisDefense.game.GameScreen;

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

    GameScreen game;
    private int path = 0;
    private int enemyAmount = 0;
    private int level = 0;
    public LevelManager(GameScreen g){game = g;}


    //for determining spawn


    public int getEnemyAmount(){return enemyAmount;}
    public int getPath(){return path;}

    /**'level' is set by the game screen before this function runs.*/
    public void determineLevel(){
        switch(level) {
            case (1):
                path = 1;
                enemyAmount = 5;
                break;
            case (2):
                path = 2;
                enemyAmount = 100;
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











    public void dispose(){

    }
}
