package com.padisDefense.game.Managers;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Assets;
import com.padisDefense.game.CustomPool;
import com.padisDefense.game.Enemies.BestGoblin;
import com.padisDefense.game.Enemies.BiggerGoblin;
import com.padisDefense.game.Enemies.Duck;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Goblin;
import com.padisDefense.game.Enemies.Lobbo;
import com.padisDefense.game.Enemies.Nocto;
import com.padisDefense.game.Enemies.Numbo;
import com.padisDefense.game.Enemies.Purpo;
import com.padisDefense.game.Enemies.Sluggo;
import com.padisDefense.game.Enemies.Snuggo;
import com.padisDefense.game.Enemies.Turto;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Player;
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
 * this should force the user to build one type of tower.
 * once a dominant tower type is established, the enemies will spawn accordingly.
 *
 *
 *
 * enemyManager calls spawnEnemy();
 * spawnEnemy() randomly spawns 25 initial enemy.
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

    GameScreen game;
    private Map<MainTower, Integer> data;

    private Assets assets;
    private boolean duckTime = true;//spawns one duck to signify start of bullrushing.

    private Array<String> allEnemies;//list of all enemies.
    private int chosenEnemyType;//the index of type of enemy chosen to spawn when bullrushing.

    Pool<Enemy> enemyPool;
    CustomPool<Enemy> enemyCustomPool;//TODO: REMEMBER TO DELETE THE FREE() IN ENEMYMANAGER

    TestSpawnDeleteLater tsdl;

    int dummyCounter = 0;
    public SpawnManager(GameScreen g){
        game = g;
        assets = game.padi.assets;
        enemyPool = new Pool<Enemy>() {
            @Override
            protected Enemy newObject() {

                dummyCounter++;
                return new BiggerGoblin();
            }
        };

        enemyCustomPool = new CustomPool<Enemy>() {
            @Override
            protected Enemy newObject(String type) {

                if(type.equals("biggergoblin")) return new BiggerGoblin();

                else if(type.equals("goblin")) return new Goblin();

                else if (type.equals("bestgoblin")) return new BestGoblin();

                else if(type.equals("sluggo")) return new Sluggo();

                else if (type.equals("lobbo")) return new Lobbo();

                else if (type.equals("nocto")) return new Nocto();

                else if (type.equals("numbo")) return new Numbo();

                else if(type.equals("purpo")) return new Purpo();

                else if (type.equals("snuggo")) return new Snuggo();

                else if (type.equals("turto")) return new Turto();


                System.out.println("RETURNING NULL");
                return null;
            }
        };

        initPool();
        data = new HashMap<MainTower, Integer>();
        allEnemies = new Array<String>();
        tsdl = new TestSpawnDeleteLater();

        //ADD ENEMIES HERE.
        allEnemies.add("goblin");
        allEnemies.add("biggergoblin");
        allEnemies.add("bestgoblin");
    }


    //Creating 25 of each enemy.
    //So they can be freed into the pool.
    public void initPool(){
        Array<Enemy> eArray = new Array<Enemy>();
        for(int x = 0; x < 25; x++){

            eArray.add(enemyCustomPool.obtain("goblin"));
            eArray.add(enemyCustomPool.obtain("biggergoblin"));
            eArray.add(enemyCustomPool.obtain("bestgoblin"));
            eArray.add(enemyCustomPool.obtain("sluggo"));
            eArray.add(enemyCustomPool.obtain("snuggo"));
            eArray.add(enemyCustomPool.obtain("nocto"));
            eArray.add(enemyCustomPool.obtain("purpo"));
            eArray.add(enemyCustomPool.obtain("lobbo"));
            eArray.add(enemyCustomPool.obtain("numbo"));
            eArray.add(enemyCustomPool.obtain("turto"));
            //eArray.add(enemyPool.obtain());
            //System.out.println("Pool init(): " + dummyCounter);
        }

        System.out.println("Size of eArray: " + eArray.size);
        //enemyPool.freeAll(eArray);
        enemyCustomPool.freeAll(eArray);


    }


    private int first50 = 0;//the first 25 enemies should be random,
    //but after that, spawning will take into account the user's tower types.
    public void spawnEnemy(EnemyManager enemy){



        if(first50 < 50){

            Enemy e;
            int r = (int)(Math.random()*10);

            if(r == 0) e = enemyCustomPool.obtain("bestgoblin");
            else if(r == 1) e = enemyCustomPool.obtain("biggergoblin");
            else if(r == 2) e = enemyCustomPool.obtain("goblin");
            else if(r == 3) e = enemyCustomPool.obtain("sluggo");
            else if(r == 4) e = enemyCustomPool.obtain("lobbo");
            else if(r == 5) e = enemyCustomPool.obtain("nocto");
            else if(r == 6) e = enemyCustomPool.obtain("numbo");
            else if(r == 7) e = enemyCustomPool.obtain("purpo");
            else if(r == 8) e = enemyCustomPool.obtain("snuggo");
            else if(r == 9) e = enemyCustomPool.obtain("turto");



            else e = enemyCustomPool.obtain("goblin");


            e.init(-50f, 0);
            e.setTime(0f);
            e.setCurrentPath(0);
            e.setHealth(e.getOriginalHealth());
            e.alive = true;

            enemy.getActiveEnemy().add(e);

            /*ee.init(-50f, 0);
            ee.setTime(0f);
            ee.setCurrentPath(0);

            enemy.getActiveEnemy().add(ee);*/

            /*int rand = (int)(Math.random()*3);
            Enemy newEnemy;
            if(rand == 0){
                newEnemy = new Goblin();
                enemy.getActiveEnemy().add(newEnemy);
            }

            else if (rand == 1){
                newEnemy = new BiggerGoblin();
                enemy.getActiveEnemy().add(newEnemy);
            }

            else if(rand == 2){
                newEnemy = new BestGoblin();
                enemy.getActiveEnemy().add(newEnemy);
            }


            first50++;*/

        }
        else{//initial 25 enemies have spawned.

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

            /*for(Map.Entry<MainTower, Integer> k: data.entrySet()){
                System.out.print(k.getKey().getID() + "  " + k.getValue() + " ___ " );
            }
            System.out.print("  Total" + "  " + data.size() + "\n");*/
        }

        data.clear();

    }


    // Counts how many of each  tower there are.
    public void gatherTowerData(){
        //TODO: check the case where user has no towers.
        for(int x = 0; x < game.tower.getTowerArray().size; x++){
            MainTower temp = game.tower.getTowerArray().get(x);

            if(data.size() == 0){//if size is zero.
                data.put(temp, 1);

            }
            else{//It is in data. Searching for it in data to increment by one.
                boolean added = false;
                for(Map.Entry<MainTower, Integer> k: data.entrySet()){
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
        Array<MainTower> mostType;//Array of least and most frequent type towers.
        Array<MainTower> leastType;
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

            mostType = new Array<MainTower>();
            leastType = new Array<MainTower>();

            for(Map.Entry<MainTower, Integer> k: data.entrySet()){
                if(k.getValue().equals(mostValue)) mostType.add(k.getKey());
                else if(k.getValue().equals(leastValue)) leastType.add(k.getKey());
            }

            double x = Math.random()*100;

            //Example: if DIFFICULTY was 55, then there is a
            //55% chance the enemy spawn will counter the user's towers.
            if(x >= assets.getDifficulty()){
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



    private Enemy spawnCustom(Array<MainTower> mostType) {

        Array<String> weak = new Array<String>();

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

        int x = (int)(Math.random()*3);
        Enemy e;
        if(x == 0) e = new Goblin();
        else if (x == 1) e =  new BiggerGoblin();
        else if (x == 2) e =  new BestGoblin();
        else e = new Goblin();
        return e;
    }

    //TODO: add more enemies.
    private Enemy convertToEnemy(String type){
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
        tower.addBuildableSpots(new Vector2(100f, 120f));
        tower.addBuildableSpots(new Vector2(500f, 240f));
        tower.addBuildableSpots(new Vector2(500f, 400f));
        tower.addBuildableSpots(new Vector2(400f, 500f));
        tower.addBuildableSpots(new Vector2(500f, 350f));
        tower.addBuildableSpots(new Vector2(550f, 500f));
        tower.addBuildableSpots(new Vector2(660f, 400f));
        tower.addBuildableSpots(new Vector2(777f, 500f));
        tower.addBuildableSpots(new Vector2(600f, 550f));

    }

    public void upgradeTower(BuildableSpot b){
        MainTower t = b.getCurrentTower();

        if(t.getLevel() < 3){
            t.setAttack(t.getAttack()*1.1f);
            t.setRange(t.getRange()*1.1f);
            t.setBulletLimit(t.getBulletLimit()+1);
            t.setSize(t.getWidth()*1.1f, t.getHeight()*1.1f);
            t.setColor(Color.GREEN);
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

        int d = 0;

        if(newTower != null){
            if(game.tower.getInGameMoney() >= newTower.getCost()){

                if(t.getCurrentTower()!= null)
                    game.tower.getTowerArray().removeValue(t.getCurrentTower(), false);//deletes SpeedTower from towerArray.

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


    public void applyStatChanges(MainTower t){

        Player p = game.padi.player;//pointer to the Player object.

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


    public void dispose(){
    }
}



/***
 *
 //TODO: possible duplicate function. Keeping for now. If no errors pop up later, delete.
 public void dragBuildTower(BuildableSpot b, String type){

 MainTower newTower = new MainTower();
 Vector2 spawnPosition = new Vector2(b.getX() + (b.getWidth() / 8),
 b.getY() + (b.getHeight() / 8));

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

 if(game.tower.getInGameMoney() >= newTower.getCost()){
 game.tower.getTowerArray().add(newTower);
 b.setCurrentTower(newTower);//points to the tower.
 game.tower.updateInGameMoney(-(int)newTower.getCost());

 }


 }//end dragBuildTower();
 *
 * */