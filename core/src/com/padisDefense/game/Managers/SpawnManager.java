package com.padisDefense.game.Managers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.BestGoblin;
import com.padisDefense.game.Enemies.BiggerGoblin;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Goblin;
import com.padisDefense.game.Towers.AOETower;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.GhostTower;
import com.padisDefense.game.Towers.IceTower;
import com.padisDefense.game.Towers.MainTower;
import com.padisDefense.game.Towers.RogueTower;
import com.padisDefense.game.Towers.SpeedTower;
import com.padisDefense.game.Towers.StrengthTower;

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

        System.out.println("Making: " + type);


        MainTower newTower = null;
        Vector2 spawnPosition = new Vector2(t.getX() + (t.getWidth() / 8),
                t.getY() + (t.getHeight() / 8));

        //Create SpeedTower
        if(type.equals("speed")){
            newTower = new SpeedTower(spawnPosition);
        }

        //Create StrengthTower
        else if(type.equals("strength")) {
            newTower = new StrengthTower(spawnPosition); //Create StrengthTower
        }

        //Create IceTower
        else if(type.equals("ice")) {
            newTower = new IceTower(spawnPosition);
        }

        else if(type.equals("rogue")){
            newTower = new RogueTower(spawnPosition);
        }

        else if(type.equals("aoe")){
            newTower = new AOETower(spawnPosition);
        }

        else if(type.equals("ghost")){
            newTower = new GhostTower(spawnPosition);
        }


        try{
            if(tower.getInGameMoney() >= newTower.getCost()){
                tower.getTowerArray().removeValue(t.getCurrentTower(), false);//deletes SpeedTower from towerArray.
                tower.getTowerArray().add(newTower);
                t.setCurrentTower(newTower);//points to the tower.
                tower.updateInGameMoney(-(int)newTower.getCost());
            }

        }catch(Exception e){
            //If user clicks 'upgrade' then that message will be sent here,
            //and there's nothing for upgrade yet, so that will make a
            //NullpointerException pop up.
            //Hence the temporary try-catch.
        }



        //System.out.println("towerArray size = " + towerArray.size);
    }
}
