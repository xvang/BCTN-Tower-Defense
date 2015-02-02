package com.padisDefense.game.Managers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Ball;
import com.padisDefense.game.Enemies.Duck;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Player;
import com.padisDefense.game.Towers.PurpleTower;
import com.padisDefense.game.Towers.Tower;
import com.padisDefense.game.Towers.BuildableSpot;

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

    private boolean duckTime = true, spawnedDuck = false;//spawns one duck to signify start of bullrushing.
    private float duckTimer = 0f;


    private Array<String> allEnemies;//list of all enemies.
    private Array<String> levelEnemies;//list of available enemies for current Level.
    private int chosenEnemyType;//the index of type of enemy chosen to spawn when bullrushing.



    Array<String> weak;
    Array<Tower> mostType;//Array of least and most frequent type towers.
    Array<Tower> leastType;
    String oldMosttype = "";


    public SpawnManager(GameScreen g, Padi p){
        game = g;
        padi = p;

        data = new HashMap<Tower, Integer>();
        allEnemies = new Array<String>();
        levelEnemies = new Array<String>();





        weak = new Array<String>();
        mostType = new Array<Tower>();
        leastType = new Array<Tower>();
    }

    public void initSpawn(){
        //ADD ENEMIES HERE.
        allEnemies.add("armyball");         allEnemies.add("blueball");
        allEnemies.add("greenball");        allEnemies.add("orangeball");
        allEnemies.add("pinkball");         allEnemies.add("purpleball");
        allEnemies.add("redball");          allEnemies.add("violetball");
        allEnemies.add("yellowball");


        levelEnemies.clear();

        for(int x = 0; x < game.limit; x++){
            levelEnemies.add(allEnemies.get(x));
        }
    }


    private int first50 = 0;//the first 50 enemies should be random,
    //but after that, spawning will take into account the user's tower types.
    public void spawnEnemy(EnemyManager enemy){


        if(first50 < 50){

            Enemy e;
            int r = (int)(Math.random()*(game.limit));

            if(r == 0) e = padi.assets.enemyCustomPoolL.obtain("armyball");
            else if(r == 1) e = padi.assets.enemyCustomPoolL.obtain("blueball");
            else if(r == 2) e = padi.assets.enemyCustomPoolL.obtain("greenball");
            else if(r == 3) e = padi.assets.enemyCustomPoolL.obtain("orangeball");
            else if(r == 4) e = padi.assets.enemyCustomPoolL.obtain("pinkball");
            else if(r == 5) e = padi.assets.enemyCustomPoolL.obtain("purpleball");
            else if(r == 6) e = padi.assets.enemyCustomPoolL.obtain("redball");
            else if(r == 7) e = padi.assets.enemyCustomPoolL.obtain("violetball");
            else if(r == 8) e = padi.assets.enemyCustomPoolL.obtain("yellowball");

            else e = padi.assets.enemyCustomPoolL.obtain("orangeball");

            e.reset();

            enemy.getActiveEnemy().add(e);

            first50++;

        }
        else{//initial 50 enemies have spawned.

            gatherTowerData();
            if(data.size() == 0){
                Enemy e = spawnRandom();
                e.reset();
                enemy.getActiveEnemy().add(e);
            }
            else{
                Enemy e = spawnResponse();
                e.reset();
                enemy.getActiveEnemy().add(e);
            }

        }

        data.clear();
        weak.clear();
        mostType.clear();
        leastType.clear();

    }


    // Counts how many of each  tower there are.
    public void gatherTowerData(){

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

        //if the difficulty level is less than 50, duck time should not run.
        if(padi.assets.getDifficulty() > 49){
            //spawns either the duck, or a bull rush spawn.
            if(mostValue < 3){
                duckTimer++;
                duckTime = true;

                //every 8 spawns, every active enemy has a chance to have increased armor,
                //up to a maximum of 500% of the intial armor value.
                //the chance is based on the difficulty level.
                if(duckTimer >= 8){
                    duckTimer = 0;
                    double x = Math.random()*100;
                    if(x <= padi.assets.getDifficulty()){

                        for(int s = 0; s < game.enemy.getActiveEnemy().size;s++){
                            Enemy e = game.enemy.getActiveEnemy().get(s);

                            if(e.getArmor() < e.getOriginalArmor()*5)
                                e.setArmor(e.getArmor()*1.25f);
                        }
                        //System.out.println("Armoring up: " + game.enemy.getActiveEnemy().get(1).getArmor());
                    }
                }
                if(!spawnedDuck){//The duck should only spawn once to signify the start of bullrushing.
                    newEnemy = new Duck();

                    spawnedDuck = true;
                    chosenEnemyType = (int)(Math.random()*levelEnemies.size);

                    // changing the giant ball at the end.
                    game.enemy.changeEndImage(levelEnemies.get(chosenEnemyType));
                    return newEnemy;
                }
                else{//Duck already spawned, it's time to spawn the rush.
                    //System.out.println("Spawning type: " + levelEnemies.get(chosenEnemyType));
                    return spawnBullRush();
                }

            }
            else{

                //change giant ball at the end to original image: rainbowball.png .
                //As long as the amount of dominant tower is >3, this else statement is always executed.
                //So the 'duckTime' boolean exists so changeEndImage() is not called
                //every time the else statement is entered. It is toggled to "false" after the if-statement.
                //Also, reset the all the enemies's defense ONCE.
                if(duckTime){

                    //rainbow is the default.
                    game.enemy.changeEndImage("rainbowball");

                    //duck time is over, reset all the increased armor.
                    for(int s = 0; s < game.enemy.getActiveEnemy().size;s++){
                        Enemy e = game.enemy.getActiveEnemy().get(s);
                        e.setArmor(e.getOriginalArmor());
                    }
                    //resetting the enemy that was in pool when duck time ended.
                    Array<Enemy> temp = padi.assets.enemyCustomPoolL.returnPool();
                    for(int w = 0; w < temp.size; w++){
                        temp.get(w).setArmor(temp.get(w).getOriginalArmor());
                    }
                }


                duckTime = false;
                spawnedDuck = false;
                //mostType = new Array<Tower>();
                //leastType = new Array<Tower>();


                for(Map.Entry<Tower, Integer> k: data.entrySet()){
                    if(k.getValue().equals(mostValue)) mostType.add(k.getKey());
                    else if(k.getValue().equals(leastValue)) leastType.add(k.getKey());
                }


                //We don't want changeEndImage() to be called every time,
                //So below prevents that. If  the old type is not equal, then
                //we need to change the giant ball. If not, then we dont need to call the function.
                if(!oldMosttype.equals(mostType.get(0).getWeakAgainst().first())){
                    game.enemy.changeEndImage(mostType.get(0).getWeakAgainst().get(0));
                }

                oldMosttype = mostType.get(0).getWeakAgainst().first();

                double x = Math.random()*100;

                //Example: if DIFFICULTY was 55, then there is a
                //55% chance the enemy spawn will counter the user's dominant tower.
                if(x >= padi.assets.getDifficulty()){
                    newEnemy = spawnRandom();
                    //System.out.println("spawn Random()");
                }
                else{

                    newEnemy = spawnCustom(mostType);

                    //System.out.println("spawn Custom()");
                }
            }
        }
        else{//If difficulty < 50, spawn random enemy.
            newEnemy = spawnRandom();
        }

        //System.out.println("Spawning response: " + newEnemy.getName());
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

        int x = (int)(Math.random()*(game.limit));

        if (x == 0) return new Ball("army", padi.assets.skin_balls.getSprite("armyball"));
        else if (x == 1) return new Ball("blue", padi.assets.skin_balls.getSprite("blueball"));
        else if (x == 2) return new Ball("green", padi.assets.skin_balls.getSprite("greenball"));
        else if (x == 3) return new Ball("orange", padi.assets.skin_balls.getSprite("orangeball"));
        else if (x == 4) return new Ball("pink", padi.assets.skin_balls.getSprite("pinkball"));
        else if (x == 5) return new Ball("purple", padi.assets.skin_balls.getSprite("purpleball"));
        else if (x == 6) return new Ball("red", padi.assets.skin_balls.getSprite("redball"));
        else if (x == 7) return new Ball("violet", padi.assets.skin_balls.getSprite("violetball"));
        else if (x == 8) return new Ball("yellow", padi.assets.skin_balls.getSprite("yellowball"));


        else return new Ball("yellow", padi.assets.skin_balls.getSprite("yellowball"));

    }

    //TODO: add more enemies.
    private Enemy convertToEnemy(String type){
       // System.out.println("Spawning type: " + type);

        if(type.equals("orangeball")) return new Ball("orange", padi.assets.skin_balls.getSprite("orangeball"));
        else if (type.equals("pinkball")) return new Ball("pink", padi.assets.skin_balls.getSprite("pinkball"));
        else if (type.equals("purpleball")) return new Ball("purple", padi.assets.skin_balls.getSprite("purpleball"));
        else if (type.equals("armyball")) return new Ball("army", padi.assets.skin_balls.getSprite("armyball"));
        else if (type.equals("greenball")) return new Ball("green", padi.assets.skin_balls.getSprite("greenball"));
        else if (type.equals("violetball")) return new Ball("violet", padi.assets.skin_balls.getSprite("violetball"));
        else if (type.equals("blueball")) return new Ball("blue", padi.assets.skin_balls.getSprite("blueball"));
        else if (type.equals("yellowball")) return new Ball("yellow", padi.assets.skin_balls.getSprite("yellowball"));


        else return new Ball("yellow", padi.assets.skin_balls.getSprite("yellowball"));


    }

/************FOR BUILDABLE SPOTS***********************************************************/




    //this function upgrades a tower.
    //it gathers the necessary information, then its suppose to delete the current tower
    //then it calls buildATower() on the buildablespot.
    public void upgradeTower(BuildableSpot b){
        //System.out.println("upgradeTower called");
        Tower t = b.getCurrentTower();


        if(t.getLevel() < 3 && game.tower.getInGameMoney() >= t.getCost()){


            game.tower.updateInGameMoney(-(int)t.getCost());


            Tower localTower = b.getCurrentTower();

            Sprite sprite = padi.assets.towerAtlas.createSprite(localTower.getID() , (localTower.getLevel()+1));

            Vector2 location = new Vector2(localTower.getX(), localTower.getY());
            sprite.setPosition(location.x, location.y);

            sprite.setRotation(localTower.getRotation());


            //TODO: also upgrade tower abilities. freeze, aoe, etc.
            //The stat upgrades should go here.
            localTower.setLevel(localTower.getLevel() + 1);
            localTower.setAttack(localTower.getAttack()*1.10f); // + 10% attack.
            localTower.setRange(localTower.getRange() * 1.05f); // + 5% range.
            localTower.setCost((int)((double)(localTower.getCost())*1.20)); // + 20% cost. might be overkill with the castings.
            localTower.setChargeRate(localTower.getChargeRate()*1.2f); // + 20% charge
            localTower.set(sprite);

            if(localTower.getID().equals("PURPLE") && localTower.getLevel() == 3){

                ((PurpleTower)(localTower)).flower = padi.assets.towerAtlas.createSprite("roguespin");
                ((PurpleTower)(localTower)).flower.setCenter(localTower.getCenterX(), localTower.getCenterY());
                System.out.println(localTower.getLocation());
            }

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
    public void buildATower(String action, BuildableSpot b, String type, int level){

        //System.out.println("action: " + action);

        Vector2 spawnPosition = new Vector2(b.getX() + (b.getWidth() / 8),
                b.getY() + (b.getHeight() / 8));

        Tower newTower = padi.assets.towerCustomPool.obtain(type, level, spawnPosition);
        newTower.setPosition(spawnPosition.x, spawnPosition.y);



        //updating level.
        newTower.setLevel(level);

        if(action.equals("build")){
            if(game.tower.getInGameMoney() >= newTower.getCost()){
                applyStatChanges(newTower);
                game.tower.getTowerArray().add(newTower);
                b.setCurrentTower(newTower);//points to the tower.
                game.tower.updateInGameMoney(-(int)newTower.getCost());
            }
        }

        else if(action.equals("upgrade")){

            float oldRotate = b.getCurrentTower().getRotation();
            newTower.setRotation(oldRotate);

            Tower pointer = b.getCurrentTower();
            game.tower.getTowerArray().removeValue(b.getCurrentTower(), false);
            padi.assets.towerCustomPool.free(pointer);
            b.setCurrentTower(null);


            game.tower.getTowerArray().add(newTower);
            b.setCurrentTower(newTower);
        }


    }


    public void applyStatChanges(Tower t){

        Player p = padi.player;//pointer to the Player object.

        for(int x = 0; x < p.getItemsUnlocked().size;x++){//getTargets() returns a String of towers targeted by item.

            for(int y = 0; y < p.getItemsUnlocked().get(x).getTargets().size; y++){
                String target = p.getItemsUnlocked().get(x).getTargets().get(y);//will point to every element in item's tower target list.
                System.out.print("Name: " + p.getItemsUnlocked().get(x).getName() + ",  target: " + p.getItemsUnlocked().get(x).getTargets().get(y));
                System.out.println();
                //if the ID of the newly built tower matches the Name of the item,
                //item will change the stats of the newly built tower.
                //update() is declared in ItemStorage.
                if(target.equals("ALL")){
                    System.out.println("t.originalAtk: " + t.getAttack());
                    p.getItemsUnlocked().get(x).update(t);
                    System.out.println("t.afterAtk: " + t.getAttack());
                }
                else if(target.equals(t.getID())){
                    System.out.println("changing" + t.getID());
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
        duckTime = true;
        spawnedDuck = false;
        duckTimer = 0f;

        allEnemies.clear();
        levelEnemies.clear();
        weak.clear();
        mostType.clear();
        leastType.clear();
        oldMosttype = "";

        //rainbow is the default.
        game.enemy.changeEndImage("rainbowball");


        data.clear();
        first50 = 0;




    }
}



