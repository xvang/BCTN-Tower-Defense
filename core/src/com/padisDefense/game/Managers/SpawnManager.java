package com.padisDefense.game.Managers;


import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Assets;
import com.padisDefense.game.Enemies.BestGoblin;
import com.padisDefense.game.Enemies.BiggerGoblin;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Goblin;
import com.padisDefense.game.Enemies.SpawnStorage;
import com.padisDefense.game.Towers.AOETower;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.GhostTower;
import com.padisDefense.game.Towers.IceTower;
import com.padisDefense.game.Towers.MainTower;
import com.padisDefense.game.Towers.RogueTower;
import com.padisDefense.game.Towers.SpeedTower;
import com.padisDefense.game.Towers.StrengthTower;

import java.util.HashMap;
import java.util.Map;
/**
 * Example: User builds 10 towers: 8 speed towers, 1 ghost tower, 1 rogue tower.
 * that means there is an 80% chance of spawning an enemy that counters speed towers.
 * 10% chance for enemies that counter ghost and rogue towers, respectively.
 *
 * If there is no clear-cut dominant type of tower, bullrush mode should engage.
 * bullrush mode is when enemy spawns one type, regardless of user's towers.
 * this forces the user to build one type of tower.
 * once the tower types are unbalanced, the enemies will spawn accordingly.
 *
 *
 *
 * **/
public class SpawnManager {


    private TowerManager tower;
    private EnemyManager enemy;
    private Map<String, Integer> data;

    private Assets assets;
    private SpawnStorage storage;


    public SpawnManager(TowerManager t, EnemyManager e, Assets a ){
        tower = t;
        enemy = e;
        data = new HashMap<String, Integer>();
        assets = a;
        storage = new SpawnStorage(data, e);
    }




    private int first25 = 0;//the first 25 enemies should be random,
    //but after that, spawning will take into account the user's tower types.
    public void spawnEnemy(EnemyManager enemy){


        if(first25 < 25){
            int rand = (int)(Math.random()*3);
            Enemy newEnemy;
            if(rand == 0){
                newEnemy = new Goblin();
                newEnemy.setChosenPath((int)(Math.random()*100) % enemy.getPath().getPath().size);
                enemy.getActiveEnemy().add(newEnemy);
            }

            else if (rand == 1){
                newEnemy = new BiggerGoblin();
                newEnemy.setChosenPath((int)(Math.random()*100) % enemy.getPath().getPath().size);
                enemy.getActiveEnemy().add(newEnemy);
            }

            else if(rand == 2){
                newEnemy = new BestGoblin();
                newEnemy.setChosenPath((int)(Math.random()*100) % enemy.getPath().getPath().size);
                enemy.getActiveEnemy().add(newEnemy);
            }
            first25++;
        }
        else{
            gatherTowerData();
            spawnResponse();
        }

        data.clear();

    }


    // Counts how many of each  tower there are.
    public void gatherTowerData(){

        for(int x = 0; x < tower.getTowerArray().size; x++){
            String temp = tower.getTowerArray().get(x).getID();

            if(!data.containsKey(temp)){
                data.put(temp, 1);
            }
            else{
                for(Map.Entry<String, Integer> k: data.entrySet()){
                    if(k.getKey().equals(temp)){

                        //k.setValue(k.getValue()+1);
                        int t = k.getValue();
                        data.put(k.getKey(), t+1);
                    }
                }
            }
        }// end for-loop.

    }// end gatherTowerData();


    public void spawnResponse(){

        Enemy newEnemy;
        if(assets.getDifficulty() >= 45 && assets.getDifficulty() <= 55){System.out.println("neutral()");
            newEnemy = neutral();
        }

        else if(assets.getDifficulty() <= 10){System.out.println("easy()");
            newEnemy = easy();
        }

        else if(assets.getDifficulty() >= 90){System.out.println("hard()");
            newEnemy = hard();
        }

        else{System.out.println("custom()");
            newEnemy = custom();

        }

        enemy.getActiveEnemy().add(newEnemy);

    }


    //neutral. should just spawn random enemies.
    public Enemy neutral(){

        int x = (int)(Math.random()*3);
        Enemy newEnemy;

        if(x == 0) newEnemy = new Goblin();

        else if(x == 1) newEnemy = new BiggerGoblin();

        else if(x == 2) newEnemy = new BestGoblin();

        else newEnemy = new BestGoblin(); //Should never reach this statement.
                                      //Just here to make the warning go away.

        System.out.println("Spawn type: " + newEnemy.getName() + "  " + x);
        newEnemy.setChosenPath((int) (Math.random() * 100) % enemy.getPath().getPath().size);
        return newEnemy;
    }

    public Enemy easy(){


        return null;
    }

    public Enemy hard(){

        return null;


    }

    public Enemy custom(){


        return null;
    }
/************FOR BUILDABLE SPOTS***********************************************************/
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
