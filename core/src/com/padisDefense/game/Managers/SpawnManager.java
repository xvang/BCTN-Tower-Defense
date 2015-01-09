package com.padisDefense.game.Managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.CustomPool;
import com.padisDefense.game.Enemies.BipedalDragon;
import com.padisDefense.game.Enemies.BlueSpider;
import com.padisDefense.game.Enemies.Cobra;
import com.padisDefense.game.Enemies.Golem;
import com.padisDefense.game.Enemies.IronSpider;
import com.padisDefense.game.Enemies.Mage;
import com.padisDefense.game.Enemies.RedSpider;
import com.padisDefense.game.Enemies.Duck;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Player;
import com.padisDefense.game.Towers.AoeTower;
import com.padisDefense.game.Towers.StrengthTower;
import com.padisDefense.game.Towers.Tower;
import com.padisDefense.game.Towers.LaserTower;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.SpeedTower;
import com.padisDefense.game.Towers.RogueTower;
import com.padisDefense.game.Towers.SniperTower;

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
 * this should force the user to build one type of tower.
 * once a dominant tower type is established, the enemies will spawn accordingly.
 *
 *
 *
 * enemyManager calls spawnEnemy();
 * spawnEnemy() randomly spawns 50 initial enemy.
 * After that, it gathers tower data.
 * If no tower is built, it keeps spawning random until it exceeds enemy limit.(at the moment, limit is 25)
 * spawnEnemy() calls on spawnResponse();
 * spawnResponse() creates an array of enemies that would be strong against the user's towers.
 * if no dominant tower, spawnResponse() will pick one type of enemy and keep spawning it until user reacts.
 * once a dominant tower is established, based on difficulty settings,
 * spawn will be 'random' or 'custom' to counter the user's towers.
 * For example, if the difficulty setting is 40, then there is a 40% chance of a 'custom' spawn, and a 60% chance of 'random' spawn.
 *
 * **/
public class SpawnManager {

    Padi padi;
    GameScreen game;
    private Map<Tower, Integer> data;

    private boolean duckTime = true;//spawns one duck to signify start of bullrushing.

    private Array<String> allEnemies;//list of all enemies.
    private int chosenEnemyType;//the index of type of enemy chosen to spawn when bullrushing.

    public CustomPool<Enemy> enemyCustomPool;//TODO: REMEMBER TO DELETE THE FREE() IN ENEMYMANAGER

    Array<String> weak;
    Array<Tower> mostType;//Array of least and most frequent type towers.
    Array<Tower> leastType;



    public SpawnManager(GameScreen g, Padi p){
        game = g;
        padi = p;
        enemyCustomPool = padi.assets.enemyCustomPoolL;

        data = new HashMap<Tower, Integer>();
        allEnemies = new Array<String>();

        //ADD ENEMIES HERE.
        allEnemies.add("bipedaldragon");
        allEnemies.add("bluespider");
        allEnemies.add("cobra");
        allEnemies.add("golem");
        allEnemies.add("ironspider");
        allEnemies.add("mage");
        allEnemies.add("redspider");

        weak = new Array<String>();
        mostType = new Array<Tower>();
        leastType = new Array<Tower>();
    }


    private int first50 = 0;//the first 25 enemies should be random,
    //but after that, spawning will take into account the user's tower types.
    public void spawnEnemy(EnemyManager enemy){


        if(first50 < 50){

            Enemy e;
            int r = (int)(Math.random()*10);

            if(r == 0) e = enemyCustomPool.obtain("bipedaldragon");
            else if(r == 1) e = enemyCustomPool.obtain("bluespider");
            else if(r == 2) e = enemyCustomPool.obtain("cobra");
            else if(r == 3) e = enemyCustomPool.obtain("golem");
            else if(r == 4) e = enemyCustomPool.obtain("ironspider");
            else if(r == 5) e = enemyCustomPool.obtain("mage");
            else if(r == 6) e = enemyCustomPool.obtain("redspider");


            else e = enemyCustomPool.obtain("mage");


            e.init(-100f, 0);
            e.setTime(0f);
            e.setCurrentPath(0);
            e.setHealth(e.getOriginalHealth());

            e.alive = true;

            enemy.getActiveEnemy().add(e);

            first50++;

        }
        else{//initial 50 enemies have spawned.

            gatherTowerData();
            if(data.size() == 0){
                //System.out.println("no towers built.");
                Enemy e = spawnRandom();
                enemy.getActiveEnemy().add(e);
                //System.out.println("Spawned " + e.getName());
            }
            else{
                Enemy e = spawnResponse();
                enemy.getActiveEnemy().add(e);

                //System.out.println("Spawned " + e.getName());

            }

           /*for(Map.Entry<Tower, Integer> k: data.entrySet()){
                System.out.print(k.getKey().getID() + "  " + k.getValue() + " ___ " );
            }
            System.out.print("  Total" + "  " + data.size() + "\n");*/
        }

        data.clear();
        weak.clear();
        mostType.clear();
        leastType.clear();

    }


    // Counts how many of each  tower there are.
    public void gatherTowerData(){
        //TODO: check the case where user has no towers.
        for(int x = 0; x < game.tower.getTowerArray().size; x++){
            Tower temp = game.tower.getTowerArray().get(x);

            if(data.size() == 0){//if size is zero.
                data.put(temp, 1);

            }
            else{//It is in data. Searching for it in data to increment by one.
                boolean added = false;
                for(Map.Entry<Tower, Integer> k: data.entrySet()){
                    if(k.getKey().getID().equals(temp.getID())){
                        //k.setValue(k.getValue()+1);
                        int t = k.getValue();
                        data.put(k.getKey(), t+1);
                        added = true;
                    }
                }
                if(!added)//'temp' is not in 'data', so it has to be added in.
                    data.put(temp, 1);
            }//end else.
        }// end for-loop.
    }// end gatherTowerData();


    public Enemy spawnResponse(){
        Enemy newEnemy;

        Integer mostValue = Collections.max(data.values());//gets the max value.
        Integer leastValue = Collections.min(data.values());

        //If least value equals most value,
        //then that means user only built 1 type of tower.
        if(leastValue.equals(mostValue)){
            leastValue = 0;
        }


        //no need to assign these a value unless mostValue < 4
        //Array<Tower> mostType;//Array of least and most frequent type towers.
        //Array<Tower> leastType;
        //System.out.println("Most: " + mostValue);


        if(mostValue < 3){
            if(duckTime){//The duck should only spawn once to signify the start of bullrushing.
                newEnemy = new Duck();

                duckTime = false;
                chosenEnemyType = (int)(Math.random()*allEnemies.size);
                //System.out.println("DUCK TIME!" + " ... Chosen enemy: " + allEnemies.get(chosenEnemyType));
                return newEnemy;
            }
            else return spawnBullRush();

        }
        else{
            duckTime = true;
            //System.out.println("duck time over...");

            //mostType = new Array<Tower>();
            //leastType = new Array<Tower>();

            for(Map.Entry<Tower, Integer> k: data.entrySet()){
                if(k.getValue().equals(mostValue)) mostType.add(k.getKey());
                else if(k.getValue().equals(leastValue)) leastType.add(k.getKey());
            }

            double x = Math.random()*100;

            //Example: if DIFFICULTY was 55, then there is a
            //55% chance the enemy spawn will counter the user's towers.
            if(x >= padi.assets.getDifficulty()){
                newEnemy = spawnRandom();
                //System.out.println("spawn Random()");
            }
            else{
                newEnemy = spawnCustom(mostType);
                //System.out.println("spawn Custom()");
            }
        }

        return newEnemy;
    }





    public Enemy spawnBullRush(){
        return convertToEnemy(allEnemies.get(chosenEnemyType));
    }



    private Enemy spawnCustom(Array<Tower> mostType) {

        //Array<String> weak = new Array<String>();
        //gathers all the enemies that the most frequent towers are weak against.
        for(int x = 0; x < mostType.size; x++){
            int size = mostType.get(x).getWeakAgainst().size;
            for(int y = 0; y < size; y++){
                weak.add(mostType.get(x).getWeakAgainst().get(y));
            }
        }
        //System.out.println(selected);
        String random = weak.get((int)(Math.random()*weak.size));

        //System.out.println("Tower: " + mostType.get(0).getID() + "  weakness: " + random);
        return convertToEnemy(random);//returns chosen enemy object.
    }


    //spawns a random enemy.
    private Enemy spawnRandom() {

        int x = (int)(Math.random()*7);
        Enemy e;
        if(x == 0) e = new IronSpider();
        else if (x == 1) e =  new BlueSpider();
        else if (x == 2) e =  new RedSpider();
        else if (x == 3) e = new IronSpider();
        else if (x == 4) e = new Cobra();
        else if (x == 5) e = new BipedalDragon();
        else if (x == 6) e = new Mage();

        else e = new IronSpider();
        return e;
    }

    //TODO: add more enemies.
    private Enemy convertToEnemy(String type){
        if(type.equals("ironspider")) return new IronSpider();
        else if (type.equals("bluespider")) return new BlueSpider();
        else if (type.equals("redspider")) return new RedSpider();
        else if (type.equals("golem")) return new Golem();
        else if (type.equals("cobra")) return new Cobra();
        else if (type.equals("mage")) return new Mage();
        else if (type.equals("bipedaldragon")) return new BipedalDragon();

        else return new IronSpider();
    }

/************FOR BUILDABLE SPOTS***********************************************************/
    public void spawnBuildableSpots(TowerManager tower){
        //For testing purposes only.
        //BuildableSpots are manually spawned here.
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        tower.addBuildableSpots(new Vector2(w/12f, h/3));
        tower.addBuildableSpots(new Vector2(w/2f, h/6));
        tower.addBuildableSpots(new Vector2(w*2/3, h/2));
        tower.addBuildableSpots(new Vector2(w/2f, h*2/3));
        tower.addBuildableSpots(new Vector2(w*3/4f, h*2/3));
        tower.addBuildableSpots(new Vector2(w/2f, h*3/4));
        tower.addBuildableSpots(new Vector2(w*5/6f, h*5/6));

    }

    public void upgradeTower(BuildableSpot b){
        Tower t = b.getCurrentTower();

        if(t.getLevel() < 3){
            t.setAttack(t.getAttack()*1.1f);
            t.setRange(t.getRange()*1.1f);
            t.setSize(t.getWidth()*1.1f, t.getHeight()*1.1f);
            if(t.getLevel() == 1)
                t.setColor(Color.GREEN);
            else if (t.getLevel() == 2)
                t.setColor(Color.RED);
            t.setLevel(t.getLevel()+1);
        }

    }

    /**
     * tower manager implements an inputprocessor,
     * so when it is clicked the function touchedDown() executes,
     * and calls this function.
     * Currently it just builds a new tower, and upgrades that tower.
     * There are no options to sell or charge yet.
     *
     *
     * */
    public void buildATower(BuildableSpot t, String type){

        //System.out.println("Making: " + type);

        Tower newTower = null;
        Sprite s;
        Texture texture;
        Vector2 spawnPosition = new Vector2(t.getX() + (t.getWidth() / 8),
                t.getY() + (t.getHeight() / 8));


        //Create RogueTower
        if(type.equals("rogue")){
            s = padi.assets.towerAtlas.createSprite("rogue_level_one");
            texture = s.getTexture();
            newTower = new RogueTower(spawnPosition, texture);

        }

        //Create SniperTower
        else if(type.equals("sniper")) {
            s = padi.assets.towerAtlas.createSprite("sniper_level_one", -14);
            texture = s.getTexture();
            newTower = new SniperTower(spawnPosition, texture);
        }

        //Create StrengthTower
        else if(type.equals("strength")) {
            s = padi.assets.towerAtlas.createSprite("strength_level_one");

            texture = s.getTexture();
            newTower = new StrengthTower(spawnPosition, texture);
        }

        else if(type.equals("speed")){
            s = padi.assets.towerAtlas.createSprite("speed_level_one");
            texture = s.getTexture();
            newTower = new SpeedTower(spawnPosition, texture);
        }

        else if(type.equals("aoe")){
            s = padi.assets.towerAtlas.createSprite("aoe_level_one");
            texture = s.getTexture();
            newTower = new AoeTower(spawnPosition, texture);
        }

        else if(type.equals("laser")){
            s = padi.assets.towerAtlas.createSprite("laser_level_one");
            texture = s.getTexture();
            newTower = new LaserTower(spawnPosition, texture);
        }

        if(newTower != null){
            if(game.tower.getInGameMoney() >= newTower.getCost()){

                if(t.getCurrentTower()!= null)
                    game.tower.getTowerArray().removeValue(t.getCurrentTower(), false);//deletes RogueTower from towerArray.

                //System.out.println("Range Before: " + newTower.getRange());
                applyStatChanges(newTower);
               // System.out.println("Range After: " + newTower.getRange());
                game.tower.getTowerArray().add(newTower);
                t.setCurrentTower(newTower);//points to the tower.
                game.tower.updateInGameMoney(-(int)newTower.getCost());

            }
        }

        //System.out.println("towerArray size = " + towerArray.size);
    }


    public void applyStatChanges(Tower t){

        Player p = padi.player;//pointer to the Player object.

        for(int x = 0; x < p.getItemsUnlocked().size;x++){//getTargets() returns a String of towers targeted by item.
            for(int y = 0; y < p.getItemsUnlocked().get(x).getTargets().size; y++){
                String target = p.getItemsUnlocked().get(x).getTargets().get(y);//will point to every element in item's tower target list.

                //if the ID of the newly built tower matches the Name of the item,
                //item will change the stats of the newly built tower.
                //update() is declared in ItemStorage.
                if(t.getID().equals(target)){
                    p.getItemsUnlocked().get(x).update(t);
                }
            }
        }
    }


    //TODO; look up how to delete a map.
    public void dispose(){
        data.clear();
        allEnemies.clear();
    }

    public void reset(){
        data.clear();
    }
}



/***
 *
 //TODO: possible duplicate function. Keeping for now. If no errors pop up later, delete.
 public void dragBuildTower(BuildableSpot b, String type){

 Tower newTower = new Tower();
 Vector2 spawnPosition = new Vector2(b.getX() + (b.getWidth() / 8),
 b.getY() + (b.getHeight() / 8));

 //Create RogueTower
 if(type.equals("speed")){
 newTower = new RogueTower(spawnPosition);
 }

 //Create SniperTower
 else if(type.equals("strength")) {
 newTower = new SniperTower(spawnPosition); //Create SniperTower
 }

 //Create StrengthTower
 else if(type.equals("ice")) {
 newTower = new StrengthTower(spawnPosition);
 }

 else if(type.equals("rogue")){
 newTower = new SpeedTower(spawnPosition);
 }

 else if(type.equals("aoe")){
 newTower = new AoeTower(spawnPosition);
 }

 else if(type.equals("ghost")){
 newTower = new LaserTower(spawnPosition);
 }

 if(game.tower.getInGameMoney() >= newTower.getCost()){
 game.tower.getTowerArray().add(newTower);
 b.setCurrentTower(newTower);//points to the tower.
 game.tower.updateInGameMoney(-(int)newTower.getCost());

 }


 }//end dragBuildTower();
 *
 * */