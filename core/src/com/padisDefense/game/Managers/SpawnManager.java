package com.padisDefense.game.Managers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.BestGoblin;
import com.padisDefense.game.Enemies.BiggerGoblin;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Goblin;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.TowerA;
import com.padisDefense.game.Towers.TowerB;
import com.padisDefense.game.Towers.TowerC;

public class SpawnManager {

    TowerManager tower;

    public SpawnManager(TowerManager t){
        tower = t;
    }

    public void spawnEnemy(EnemyManager enemy){

        //This is more for testing purposes.
        //0 for goblin ,1 for bigger goblin, 2 for bestgoblin
        int rand = (int)(Math.random()*3);
        Enemy newEnemy;
        if(rand == 0){

            //create object, set path, set texture, add to enemy array.
            newEnemy = new Goblin();
            newEnemy.setChosenPath((int)(Math.random()*100) % enemy.getPath().getPath().size);
            newEnemy.setTexture(new Texture("test3.png"));
            enemy.getActiveEnemy().add(newEnemy);

        }

        else if (rand == 1){

            newEnemy = new BiggerGoblin();
            newEnemy.setChosenPath((int)(Math.random()*100) % enemy.getPath().getPath().size);
            newEnemy.setTexture(new Texture("test1.png"));
            enemy.getActiveEnemy().add(newEnemy);

        }

        else if(rand == 2){

            newEnemy = new BestGoblin();
            newEnemy.setChosenPath((int)(Math.random()*100) % enemy.getPath().getPath().size);
            newEnemy.setTexture(new Texture("test8.png"));
            enemy.getActiveEnemy().add(newEnemy);
        }

    }


    public void spawnBuildableSpots(TowerManager tower){
        //For testing purposes only.
        //BuildableSpots are manually spawned here.
        tower.addBuildableSpots(new Vector2(320f, 180f));
        tower.addBuildableSpots(new Vector2(500f, 240f));
        tower.addBuildableSpots(new Vector2(500f, 400f));
        tower.addBuildableSpots(new Vector2(400f, 500f));
        tower.addBuildableSpots(new Vector2(500f, 350f));
        tower.addBuildableSpots(new Vector2(550f, 500f));
        tower.addBuildableSpots(new Vector2(660f, 400f));
    }



    /**
     * tower manager implements an inputprocessor,
     * so when it is clicked the function touchedDown() executes,
     * and calls this function.
     * Currently it just builds a new tower, and upgrades that tower.
     * There are no options to sell or charge yet.
     *
     * @param 't'
     * */
    public void clickedBuildable(BuildableSpot t, String type){

        //if 'true' then nothing is built there. yet.
        if(t.emptyCurrentTower()){
            TowerA newTower = new TowerA(new Vector2(t.getX() + (t.getWidth() / 8),
                    t.getY() + (t.getHeight() / 8)));
            if(tower.getInGameMoney() >= newTower.getCost()){
                tower.getTowerArray().add(newTower);
                t.setCurrentTower(newTower);//points the buildablspot to the tower.
                tower.updateInGameMoney(-(int)newTower.getCost());
            }

        }

        //TowerA is currently there. Will delete and build Tower B.
        else if(t.getCurrentTower().getID().equals("A")) {

            TowerB newTower = new TowerB(new Vector2(t.getX() + (t.getWidth() / 8),
                    t.getY() + (t.getHeight() / 8))); //Create TowerB
            if(tower.getInGameMoney()  >= newTower.getCost()){

                tower.getTowerArray().removeValue(t.getCurrentTower(), false);//deletes TowerA from towerArray.
                tower.getTowerArray().add(newTower);
                t.setCurrentTower(newTower);//points to the tower.
                tower.updateInGameMoney(-(int)newTower.getCost());
            }
        }

        else if(t.getCurrentTower().getID().equals("B")) {

            TowerC newTower = new TowerC(new Vector2(t.getX() + (t.getWidth() / 8),
                    t.getY() + (t.getHeight() / 8))); //Create TowerB
            if(tower.getInGameMoney() >= newTower.getCost()){

                tower.getTowerArray().removeValue(t.getCurrentTower(), false);//deletes TowerA from towerArray.
                tower.getTowerArray().add(newTower);
                t.setCurrentTower(newTower);//points to the tower.
                tower.updateInGameMoney(-(int)newTower.getCost());
            }
        }



        //System.out.println("towerArray size = " + towerArray.size);
    }
}
