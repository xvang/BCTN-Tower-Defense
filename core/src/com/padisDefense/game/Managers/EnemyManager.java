package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Pathing.PathStorage;
import com.padisDefense.game.Towers.Tower;


/**
 *
 * This class manages all the enemies on a level.
 * Movements, damage, etc.
 * May need to dispose paths.?
 *
 *
 * @author Xeng
* **/
public class EnemyManager {

    Padi padi;
    GameScreen game;
    private Array<Path<Vector2>> path;
    private PathStorage storage;
    private int spawnsLeft;
    private int enemyCounter = 0;
    ImmediateModeRenderer20 renderer;
    private float spawnPause = 0f;

    protected Array<Enemy>  activeEnemy;
    public Enemy boss;
    private int enhanceBoss =  0;//counts the number of enemies that reached the end.
    public Sprite end;

    private Vector2 endPosition;


    //TODO: find out why a high arc value makes the bullet disappear.
    public float time = 0;
    public float countDownTimer = 10f;
    SpriteBatch batch;

    /**CONSTRUCTOR**/
    public EnemyManager(GameScreen g, Padi p){
        padi = p;
        game = g;
        renderer = new ImmediateModeRenderer20(false, false, 0);
        activeEnemy = new Array<Enemy>();

        batch = new SpriteBatch();
        storage = new PathStorage();

        end = new Sprite(padi.assets.skin_balls.getSprite("rainbowball"));
        end.setSize(150f, 150f);
        endPosition = new Vector2();


    }



    public void setEnemyAmount(int newEA){spawnsLeft = newEA;enemyCounter = newEA;}

    public void setPath(int p){

        if(storage == null)
            System.out.println("null storage");


        switch(p){

            case(1):
                path = storage.getPath(0);
                break;
            case(2):
                path = storage.getPath(1);
                break;
            case(3):
                path = storage.getPath(2);
                break;
            case(4):
                path = storage.getPath(3);
                break;
            default:
                path = storage.getPath(4);
                break;
        }

        //getting the location for the end of the path.
        path.get(path.size - 1).valueAt(endPosition, 0.9f);

        end.setSize(150f, 150f);
        end.setCenterX(endPosition.x);
        end.setCenterY(endPosition.y);
        //end.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    public void startEnemy(){

        //Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(countDownTimer >= 0f){//no enemy should spawn until countdown ends.
            countDownTimer -= Gdx.graphics.getDeltaTime();

        }

        else{
            batch.begin();
            end.draw(batch);
            //end.rotate(1);
            run();
            batch.end();

            if(activeEnemy.size > 0)
                checkForDead();

            spawnPause += Gdx.graphics.getDeltaTime();
            //Calculating if spawning is necessary.


            if(activeEnemy.size < 50 && spawnsLeft > 0 && spawnPause >= 1.0f){
                //Every second, 1 to 4 enemies will spawn.
                //if the number of enemies left is less, then the # of enemies minus one will spawn instead.
                //And then in the next iteration, the boss will spawn.
                //TODO:once game.reset() is called, boss will be thrown into enemy pool.
                // It should never be created, but it's still in there. Remove it somehow?


                spawnPause = 0f;

                if(spawnsLeft == 1){
                    if(activeEnemy.size == 0){
                        try{
                            boss = game.level.getBoss();
                            boss.init(-100f, 0);
                            boss.setTime(0f);
                            boss.setCurrentPath(0);
                            boss.setHealth(boss.getOriginalHealth());
                            boss.setArmor(boss.getOriginalArmor());

                            for(int sa = 0; sa < enhanceBoss; sa++){
                                boss.setArmor(boss.getArmor()*1.05f);
                            }

                            boss.alive = true;
                            activeEnemy.add(boss);
                            spawnsLeft--;
                        }catch(Exception e){
                            System.out.println("BOSS IS A NO GO. FIX IT YOU.");
                        }

                    }

                }
                else{
                    int amount = (int)((Math.random()* 4) + 1);

                    if(spawnsLeft <= 4 && spawnsLeft > 0)
                        amount = spawnsLeft - 1;


                    for(int x = 0; x < amount; x++){
                        spawnsLeft--;
                        game.spawn.spawnEnemy(this);
                    }
                }
            }
        }

    }

    public Array<Enemy> getActiveEnemy(){return activeEnemy;}
    public Array<Path<Vector2>> getPath(){return path;}
    //public int getSpawnsLeft(){return spawnsLeft;}
    public Boolean noMoreEnemy(){
        if(spawnsLeft == 0 && activeEnemy.size == 0)
            game.gameStatus = "win";

        return (spawnsLeft == 0 && activeEnemy.size == 0);
    }

    public void run(){
        Vector2 position = new Vector2();
        Vector2 position2 = new Vector2();
        float time;
        Enemy currentEnemy;

       /* if(activeEnemy.size == 1){
            System.out.println("One left: " + activeEnemy.get(0).getLocation() + "      enemyCounter = " +
                    enemyCounter + "    spawnsLeft = "  + spawnsLeft +
                    " , Time = " + activeEnemy.get(0).getTime() +
            ",  HP = " + activeEnemy.get(0).getHealth());
        }*/
        for(int x = 0; x < activeEnemy.size; x++){
            currentEnemy = activeEnemy.get(x);

            if(currentEnemy == null)
                System.out.println("Null enemy");
            time = currentEnemy.getTime() + (Gdx.graphics.getDeltaTime() * currentEnemy.getRate());
            currentEnemy.setTime(time);

            path.get(currentEnemy.getCurrentPath()).derivativeAt(position2, currentEnemy.getTime());

            time = currentEnemy.getTime()+ (currentEnemy.getRate()*Gdx.graphics.getDeltaTime())/*/position2.len()*/;
            currentEnemy.setTime(time);

            path.get(currentEnemy.getCurrentPath()).valueAt(position, currentEnemy.getTime());

            //the newPosition is set here because
            //we don't want to calculate in straying from the path.
            currentEnemy.newPosition.set(position);

            //should only run once to initialize the old position at
            //enemy's initial position, instead of at (0,0)
            //TODO: find a way around this. Anything that is "do once only" should not exist...?
            if(currentEnemy.oldPosition.x == 0 && currentEnemy.oldPosition.y == 0){
                currentEnemy.oldPosition.set(position);
            }


            //If waiting period is over, then enemy can start swerving up and down path.
            if(currentEnemy.getWait()<= 0f){
                currentEnemy.setStrayAmount(currentEnemy.getStrayAmount()+(Gdx.graphics.getDeltaTime()/2f));

                position2.nor();
                position2.set(-position2.y, position2.x);
                position2.scl((float)(Math.sin(currentEnemy.getStrayAmount())) * 20f);
                position.add(position2);
            }

            else if(currentEnemy.getWait() > 0f) {
                currentEnemy.setWait(currentEnemy.getWait() - Gdx.graphics.getDeltaTime());
            }

            //Reached end of path or path segment.
            if (currentEnemy.getTime() >= 1f){//end of segment
                if(currentEnemy.getCurrentPath()+1 < path.size){
                    currentEnemy.setCurrentPath(currentEnemy.getCurrentPath() + 1);
                    //enemy.get(x).setStrayAmount(0f);
                }

                else{//end of path.

                    //if active enemy size is 1, and no spawns are left, then enemy must be the boss
                    //enemy should not travel path again.
                    // which triggers end game in gamescreen.java
                    if(activeEnemy.size == 1 && spawnsLeft == 0){

                        game.gameStatus = "lose";
                        break;//breaks the for-loop. activeEnemy array is now empty.
                    }
                    else{
                        if(enhanceBoss < 10)
                            enhanceBoss++;
                        //game.UI.lifepoints--;//once this gets to zero, game is over.
                        currentEnemy.setCurrentPath(0);
                        currentEnemy.stateTime = 0f;
                        currentEnemy.oldPosition.set(0,0);
                        currentEnemy.newPosition.set(0,0);
                    }

                }
                currentEnemy.setTime(0f);
            }

            //moving enemy, and displaying animations and health bar.
            currentEnemy.goTo(position);
            currentEnemy.animate(batch);

            if(currentEnemy.getHealth() != currentEnemy.getOriginalHealth())
                currentEnemy.displayHealth(batch);

            currentEnemy.updateAlteredStats();
        }


    }
    /** Checks enemy array for dead enemies and removes them */
    public void  checkForDead(){

        Tower currentTower;
        for(int x = 0; x < activeEnemy.size; x++){

            Enemy e = activeEnemy.get(x);

            e.isDead();
            if(e.isDead()) {
                padi.player.kills++;

                //if enemy is dead, the tower that targeted it will have 'hasTarget' set to false.
                for(int s = 0; s < game.tower.getTowerArray().size;s++){
                    currentTower = game.tower.getTowerArray().get(s);

                    if(currentTower.getTarget().equals(e)){
                        currentTower.hasTarget = false;
                        currentTower.lockedOnTarget = false;



                        //Resetting the tower's bullets.
                        for(int w = 0; w < currentTower.getActiveBullets().size; w++){
                            //currentTower.getActiveBullets().get(w).setTime(0f);

                            Bullet b = currentTower.getActiveBullets().get(w);
                            //b.setTime(0f);
                            currentTower.getActiveBullets().removeValue(b, false);
                            currentTower.getPool().free(b);
                        }

                    }

                }

                if(e.isBoss){//If boss, don't return to pool.
                    activeEnemy.removeValue(e, true);//'true', because why not? removes all occurences in array.
                }
                else{
                    activeEnemy.removeValue(e, false);
                    //game.spawn.enemyPool.free(e);
                    padi.assets.enemyPool.free(e);
                }
                enemyCounter--;


            }

            else{//else, update the tower's oldTargetPosition.
                for(int s = 0; s < game.tower.getTowerArray().size;s++){
                    currentTower = game.tower.getTowerArray().get(s);

                    //when game.reset() is called the tower's target is set to null.
                    //this seems to be a bandaid solution.
                    if(currentTower.getTarget() != null)
                        if(currentTower.getTarget().equals(activeEnemy.get(x))){
                            currentTower.setOldTargetPosition(activeEnemy.get(x).getLocation());
                        }
                }
            }
        }
    }


    /**
     * If enemy reaches end of path, it should be destroyed.
     * End of path should be off the screen.
     * Currently, I don't want the enemy to die at the end of the path.
     * */
    public int getEnemyCounter(){return enemyCounter;}


    //if winning condition is met,
    //destroys remaining enemy.
    public void destroyAllEnemy(){
        spawnsLeft = 0;
        enemyCounter = 0;
        for(int x = 0; x < activeEnemy.size; x++){
            Enemy e = activeEnemy.get(x);

            activeEnemy.removeIndex(x);
            padi.assets.enemyPool.free(e);
        }

    }


    //Change the giant ball at the end to correspond with the current spawn.
    //It is called in UIManager.
    public void changeEndImage(String newBall){

        //System.out.println("change to: " + newBall);

        end = new Sprite(padi.assets.skin_balls.getSprite(newBall));

        end.setSize(150f, 150f);
        end.setCenterX(endPosition.x);
        end.setCenterY(endPosition.y);
    }


    public float getCountDownTimer(){return countDownTimer;}
    public void setCountDownTimer(float t){countDownTimer = t;}


    public void dispose(){
        renderer.dispose();
        batch.dispose();
        for(int x = 0; x < activeEnemy.size; x++)
            activeEnemy.get(x).getTexture().dispose();
        activeEnemy.clear();
        storage.dispose();
        path.clear();
    }

    public void reset(){


        for(int x = 0; x < activeEnemy.size; x++){
            Enemy e = activeEnemy.get(x);
            e.reset();
        }
        System.out.println("enemyPool.size = " + padi.assets.enemyPool.getFree());
        padi.assets.enemyPool.freeAll(activeEnemy);
        activeEnemy.clear();

        padi.assets.enemyPool.clear();

        countDownTimer = 15f;
        enhanceBoss = 0;
    }
}
