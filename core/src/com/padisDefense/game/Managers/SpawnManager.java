package com.padisDefense.game.Managers;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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

import java.util.Collections;
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
    private boolean bullRushing = false; //TODO: bullrushing.


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

        //TODO: check the case where user has no towers.
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

        Integer mostValue = Collections.max(data.values());//gets the max value.
        Array<String> mostType = new Array<String>();//this is an array because the "most" type could be more than one
                               //there could be 2 speed towers and 2 rogue towers.
                               //They store the name of towers.

        Integer leastValue = Collections.min(data.values());//same as above, but for min.
        Array<String> leastType = new Array<String>();

        for(Map.Entry<String, Integer> k: data.entrySet()){
            if(k.getValue() == mostValue) mostType.add(k.getKey());

            else if(k.getValue() == leastValue) leastType.add(k.getKey());
        }

        Enemy newEnemy;
        if(assets.getDifficulty() >= 45 && assets.getDifficulty() <= 55) newEnemy = neutral();

        else if(assets.getDifficulty() <= 10) newEnemy = easy();

        else if(assets.getDifficulty() >= 90) newEnemy = hard(mostType, leastType);

        else newEnemy = custom();


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
                                      //Just here to make the null pointer warning go away.

        newEnemy.setChosenPath((int) (Math.random() * 100) % enemy.getPath().getPath().size);
        return newEnemy;
    }




    //If 8/10 towers are speed, then 80% chance spawn will be goblin.
    //speed towers are great against goblins.
    public Enemy easy(){


        return null;
    }

    //TODO: try to move the stuff in hard() into spawnResponse()
    //TODO: it will eliminate a lot of the repetitiveness. I think.
    //If 8/10 towers are speed, then 80% chance spawn will be bestGoblin.
    //speed towers are terrible against bestGoblins.
    //'least' is the least built tower(s). we want to spawn enemies that are weak against those.
    //'most' is the most build tower(s). we want to spawn enemies that are strong against those.
    public Enemy hard(Array<String> most, Array<String> least){

        if(most.size < 3){bullRushing = true;}

        else{//Gather list of enemies that are bad against the 'least', and good against the 'most'.

            MainTower temp;
            Array<String> enemyList = new Array<String>();

            for(int x = 0; x < least.size; x++){    //TODO: find a way around converting to towers.
                temp = convertToTower(least.get(x));//need to convert to tower, so we can access list of enemies weak against.

                for(int y = 0; y < temp.getWeakAgainst().size; y++){
                    enemyList.add(temp.getWeakAgainst().get(y));
                }
            }
            for(int x = 0; x < most.size; x++){
                temp = convertToTower(most.get(x));

                for(int y = 0;y < temp.getStrongAgainst().size; y++){
                    enemyList.add(temp.getStrongAgainst().get(y));
                }
            }

            //enemyList should now contain a list of enemies that are weak against
            //the least built towers, and great against the most built towers.

            String chosen;
            if(enemyList.size == 1)
                chosen = enemyList.get(0);
            else
                chosen = enemyList.get((int)(Math.random()*enemyList.size));

            return convertToEnemy(chosen);
        }
        return new Goblin();// TODO: return the duck here?
    }



    public Enemy custom(){


        return null;
    }

    public MainTower convertToTower(String type){

        if(type.equals("speed"))
            return new SpeedTower(new Vector2());
        else if(type.equals("rogue"))
            return new RogueTower(new Vector2());
        else if(type.equals("strength"))
            return new StrengthTower(new Vector2());
        else if(type.equals("aoe"))
            return new AOETower(new Vector2());
        else if(type.equals("ghost"))
            return new GhostTower(new Vector2());
        else if(type.equals("ice"))
            return new IceTower(new Vector2());
        else
            return null;

    }

    public Enemy convertToEnemy(String type){
        if(type.equals("goblin"))
            return new Goblin();
        else if (type.equals("biggergoblin"))
            return new BiggerGoblin();
        else if (type.equals("bestgoblin"))
            return new BestGoblin();
        else
            return new Goblin();
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
