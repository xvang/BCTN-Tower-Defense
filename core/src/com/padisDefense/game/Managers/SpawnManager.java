package com.padisDefense.game.Managers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.BestGoblin;
import com.padisDefense.game.Enemies.BiggerGoblin;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Goblin;

public class SpawnManager {


    public SpawnManager(){

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
}
