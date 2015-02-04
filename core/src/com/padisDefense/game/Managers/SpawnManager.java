package com.padisDefense.game.Managers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Ball;
import com.padisDefense.game.Enemies.BallStorage;
import com.padisDefense.game.Enemies.Duck;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Player;
import com.padisDefense.game.Towers.PurpleTower;
import com.padisDefense.game.Towers.Tower;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.TowerStorage;

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


    private BallStorage ballStorage;

    public SpawnManager(GameScreen g, Padi p){
        game = g;
        padi = p;

        data = new HashMap<Tower, Integer>();
        allEnemies = new Array<String>();
        levelEnemies = new Array<String>();


        ballStorage = new BallStorage(padi);


        weak = new Array<String>();
        mostType = new Array<Tower>();
        leastType = new Array<Tower>();
    }

    public void initSpawn(){
        //ADD ENEMIES HERE.
        allEnemies.clear();
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

            int r = (int)(Math.random()*(game.limit));


            //'levelEnemies' contains the allowed color balls in a level
            //'r' is the index of random ball in 'levelEnemies'.
            Enemy e = padi.assets.enemyPool.obtain();
            ballStorage.createBall(levelEnemies.get(r), (Ball)e);

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

                            if(e.getRate() < e.getOriginalRate()*2)
                                e.setRate(e.getRate()*1.1f);
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
                        e.setRate(e.getOriginalRate());
                    }

                    //Some enemies will have died while their stats were boosted,
                    //and are in the pool when it should be reset.
                    //To do so, we have to fetch all the objects in the pool.
                    Array<Enemy> temp = new Array<Enemy>();

                    while(padi.assets.enemyPool.getFree() > 0){
                        temp.add(padi.assets.enemyPool.obtain());
                    }

                    //resetting armor and rate
                    for(int w = 0; w < temp.size; w++){
                        temp.get(w).setArmor(temp.get(w).getOriginalArmor());
                        temp.get(w).setRate(temp.get(w).getOriginalRate());
                    }

                    //returning objects back into pool.
                    padi.assets.enemyPool.freeAll(temp);

                    System.out.println("enemyPool.size = " + padi.assets.enemyPool.getFree());
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
        Enemy e = padi.assets.enemyPool.obtain();

        ballStorage.createBall(levelEnemies.get(x), (Ball)e);
        return e;
    }

    //TODO: add more enemies.
    private Enemy convertToEnemy(String type){

        Enemy e = padi.assets.enemyPool.obtain();

        ballStorage.createBall(type, (Ball)e);
        return null;
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
            localTower.setAttack(localTower.getAttack()*2f); // + 50% attack.
            localTower.setRange(localTower.getRange() * 1.15f); // + 15% range.
            localTower.setCost((int)((double)(localTower.getCost())*1.50)); // + 50% cost. might be overkill with the castings.
            localTower.setChargeRate(localTower.getChargeRate()*1.1f); // + 10% charge
            localTower.set(sprite);

            if(localTower.getID().equals("PURPLE") && localTower.getLevel() == 3){

                //((PurpleTower)(localTower)).flower = padi.assets.towerAtlas.createSprite("roguespin");
                //((PurpleTower)(localTower)).flower.setCenter(localTower.getCenterX(), localTower.getCenterY());

                localTower.flower = padi.assets.towerAtlas.createSprite("roguespin");
                localTower.flower.setCenter(localTower.getCenterX(), localTower.getCenterY());
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

        Tower newTower = padi.assets.towerPool.obtain();
        Sprite picture = padi.assets.towerAtlas.createSprite(type);

        if(newTower == null)
            System.out.println("newTower is null!");

        if(type.equals("PURPLE")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("purple_bullet");
            newTower = padi.assets.towerStorage.PurpleTower(spawnPosition, picture, bullet, newTower);
        }
        else if(type.equals("BLUE")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("blue_bullet");
            newTower = padi.assets.towerStorage.BlueTower(spawnPosition, picture,  bullet, newTower);
        }
        else if(type.equals("YELLOW")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("yellow_bullet");
            newTower = padi.assets.towerStorage.YellowTower(spawnPosition, picture,  bullet, newTower);
        }
        else if(type.equals("PINK")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("pink_bullet");
            newTower = padi.assets.towerStorage.PinkTower(spawnPosition, picture, bullet, newTower);
        }
        else if(type.equals("GREEN")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("green_bullet");
            newTower = padi.assets.towerStorage.GreenTower(spawnPosition, picture,  bullet, newTower);
        }
        else if(type.equals("RED")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("red_bullet");
            newTower = padi.assets.towerStorage.RedTower(spawnPosition, picture, bullet, newTower);
        }
        else if(type.equals("ARMY")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("army_bullet");
            newTower = padi.assets.towerStorage.ArmyTower(spawnPosition, picture, bullet, newTower);
        }

        else if(type.equals("VIOLET")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("violet_bullet");
            newTower = padi.assets.towerStorage.VioletTower(spawnPosition, picture, bullet, newTower);
        }
        else if(type.equals("ORANGE")){
            Sprite bullet = padi.assets.bulletAtlas.createSprite("orange_bullet");
            newTower = padi.assets.towerStorage.OrangeTower(spawnPosition, picture,  bullet, newTower);
        }
        else{
            Sprite bullet = padi.assets.bulletAtlas.createSprite("purple_bullet");
            newTower = padi.assets.towerStorage.PurpleTower(spawnPosition, picture, bullet, newTower);
        }




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
            padi.assets.towerPool.free(pointer);
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

                //if the ID of the newly built tower matches the Name of the item,
                //item will change the stats of the newly built tower.

                if(target.equals("ALL")){
                    p.getItemsUnlocked().get(x).update(t);
                }
                else if(target.equals(t.getID())){
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

        initSpawn();
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



