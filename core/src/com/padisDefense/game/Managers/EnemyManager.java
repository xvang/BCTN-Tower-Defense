package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
    }


    public void setEnemyAmount(int newEA){spawnsLeft = newEA;enemyCounter = newEA;}

    public void setPath(int p){
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
    }

    public void startEnemy(){

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(countDownTimer >= 0f){//no enemy should spawn until countdown ends.
            countDownTimer -= Gdx.graphics.getDeltaTime();

        }

        else{
            batch.begin();
            run(batch);
            batch.end();
            checkForDead();

            spawnPause += Gdx.graphics.getDeltaTime();
            //Calculating if spawning is necessary.
            if(activeEnemy.size < 50 && spawnsLeft > 0 && spawnPause >= 1.0f){

                spawnPause = 0f;
                int amount = (int)(Math.random()* 3 + 1);
                if(spawnsLeft <= 4)
                    amount = spawnsLeft;

                for(int x = 0; x < amount; x++){
                    spawnsLeft--;
                    game.spawn.spawnEnemy(this);
                }
            }
        }

    }

    public Array<Enemy> getActiveEnemy(){return activeEnemy;}
    public Array<Path<Vector2>> getPath(){return path;}
    //public int getSpawnsLeft(){return spawnsLeft;}
    public Boolean noMoreEnemy(){return (spawnsLeft == 0 && activeEnemy.size == 0);}

    public void run(SpriteBatch batch){
        Vector2 position = new Vector2();
        Vector2 position2 = new Vector2();
        float time;
        Enemy currentEnemy;

        if(activeEnemy.size == 1){
            System.out.println("One left: " + activeEnemy.get(0).getLocation());
        }
        for(int x = 0; x < activeEnemy.size; x++){
            currentEnemy = activeEnemy.get(x);

            if(currentEnemy == null)
                System.out.println("Null enemy");
            time = currentEnemy.getTime() + (Gdx.graphics.getDeltaTime() * currentEnemy.getRate());
            currentEnemy.setTime(time);

            path.get(currentEnemy.getCurrentPath()).derivativeAt(position2, currentEnemy.getTime());

            time = currentEnemy.getTime()+ (currentEnemy.getRate()*Gdx.graphics.getDeltaTime())/position2.len();
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
                    currentEnemy.setCurrentPath(0);
                    currentEnemy.stateTime = 0f;
                    currentEnemy.oldPosition.set(0,0);
                    currentEnemy.newPosition.set(0,0);
                }
                currentEnemy.setTime(0f);
            }

            //moving enemy, and displaying animations and health bar.
            currentEnemy.goTo(position);
            currentEnemy.animate(batch);

            if(currentEnemy.getHealth() != currentEnemy.getOriginalHealth())
                currentEnemy.displayHealth(batch);
        }


    }
    /** Checks enemy array for dead enemies and removes them */
    public void  checkForDead(){

        Tower currentTower;
        Enemy e;
        for(int x = 0; x < activeEnemy.size; x++){

            e = activeEnemy.get(x);

            e.isDead();
            if(e.isDead()) {
                //if enemy is dead, the tower that targeted it will have 'hasTarget' set to false.
                for(int s = 0; s < game.tower.getTowerArray().size;s++){
                    currentTower = game.tower.getTowerArray().get(s);

                    if(currentTower.getTarget().equals(e)){
                        currentTower.setHasTarget(false);


                        Bullet b;
                        //Resetting the tower's bullets.
                        for(int w = 0; w < currentTower.getActiveBullets().size; w++){
                            //currentTower.getActiveBullets().get(w).setTime(0f);

                            b = currentTower.getActiveBullets().get(w);
                            //b.setTime(0f);
                            currentTower.getActiveBullets().removeIndex(w);
                            currentTower.getPool().free(b);
                        }

                    }

                }

                enemyCounter--;
                //activeEnemy.get(x).dispose();
                activeEnemy.removeIndex(x);
                //game.spawn.enemyPool.free(e);
                game.spawn.enemyCustomPool.free(e);

            }

            else{//else, update the tower's oldTargetPosition.
                for(int s = 0; s < game.tower.getTowerArray().size;s++){
                    currentTower = game.tower.getTowerArray().get(s);

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
        activeEnemy.clear();

    }

    public float getCountDownTimer(){return countDownTimer;}
    public void setCountDownTimer(float t){countDownTimer = t;}

    /**
     * private Array<Path<Vector2>> path;
     private PathStorage storage;
     private int spawnsLeft;
     private int enemyCounter = 0;
     ImmediateModeRenderer20 renderer;
     private float spawnPause = 0f;

     protected Array<Enemy>  activeEnemy;


     //TODO: find out why a high arc value makes the bullet disappear.
     public float time = 0;
     public float countDownTimer = 10f;
     SpriteBatch batch;
     * */
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

        activeEnemy.clear();

        countDownTimer  = 10f;

    }
}


/***************************************************************************/
/* public void removeReachedEnd(){

        for(int x = 0; x < activeEnemy.size; x++){

            if(activeEnemy.get(x).getTime() > 1f){
                activeEnemy.get(x).dispose();
                activeEnemy.removeIndex(x);
            }
        }
    }*/



/**
 * This sets every enemy's time to zero.
 * every enemy will respawn at the beginning at the same time.
 * */
/*public void toZero(){

    for(int x = 0; x < activeEnemy.size; x++){
        activeEnemy.get(x).setTime(0f);
    }
}*/


/**THE OLD PATHS**/

/*
 Vector2 position = new Vector2();
 //Makes enemy travel along path.
 for(int x = 0; x < activeEnemy.size; x++){

 if (!activeEnemy.get(x).isDead()) {
 time = activeEnemy.get(x).getRate() + activeEnemy.get(x).getTime();
 activeEnemy.get(x).setTime(time);

 path.getPath().get(activeEnemy.get(x).getChosenPath()).valueAt(position, time);
 activeEnemy.get(x).goTo(new Vector2(position.x, position.y));
 activeEnemy.get(x).move();
 activeEnemy.get(x).draw(batch);
 activeEnemy.get(x).updateMessage();
 activeEnemy.get(x).label.draw(batch, 1);

 //If enemy reached end, it starts path over.
 if(activeEnemy.get(x).getTime() >= 1f)
 activeEnemy.get(x).setTime(0f);
 }

}
 * */