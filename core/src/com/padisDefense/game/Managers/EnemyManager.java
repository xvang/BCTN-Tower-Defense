package com.padisDefense.game.Managers;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Pathing.MainPath;
import com.padisDefense.game.Pathing.PathStorage;


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

    private MainPath path;
    private PathStorage storage;
    private Vector2 position;
    private TowerManager tower;
    private int spawnsLeft;
    private int enemyCounter = 0;
    ImmediateModeRenderer20 renderer;

    protected Array<Enemy>  activeEnemy;


    public float time = 0;

    /**CONSTRUCTOR**/
    public EnemyManager(){
        renderer = new ImmediateModeRenderer20(false, false, 0);
        activeEnemy = new Array<Enemy>();


        storage = new PathStorage();
    }

    //setting pointer to towerManager.
    public void setTowerManager(TowerManager t){
        tower = t;
    }


    public void setEnemyAmount(int newEA){spawnsLeft = newEA;enemyCounter = newEA;}

    public void setPath(int p){
        switch(p){

            case(1):
                path = new MainPath(storage.getPath(0));
                break;
            case(2):
                path = new MainPath(storage.getPath(1));;
                break;
            case(3):
                path = new MainPath(storage.getPath(2));
                break;
            case(4):
                path = new MainPath(storage.getPath(3));
                break;
            default:
                path = new MainPath(storage.getPath(4));
                break;
        }
    }

    /**
     * Loops through every enemy and moves them along the path.
     *
     *
     * param 'batch'
     * */
    public void startEnemy(SpriteBatch batch, SpawnManager spawn){

        position = new Vector2();
        //Makes enemy travel along path.
        for(int x = 0; x < activeEnemy.size; x++){

            if (!activeEnemy.get(x).isDead()) {
                time = activeEnemy.get(x).getRate() + activeEnemy.get(x).getTime();
                activeEnemy.get(x).setTime(time);

                path.getPath().get(activeEnemy.get(x).getChosenPath()).valueAt(position, time);
                activeEnemy.get(x).goTo(new Vector2(position.x, position.y));
                activeEnemy.get(x).draw(batch);

                //If enemy reached end, it starts path over.
                if(activeEnemy.get(x).getTime() >= 1f)
                    activeEnemy.get(x).setTime(0f);
            }
            /**NOTE: To see the enemy objects loop, set each object's time variable to zero.
             * I think the best way to reset is in GameScreen.**/
        }

        checkForDead();

        //Calculating if spawning is necessary.
        if(activeEnemy.size < 25 && spawnsLeft > 0){

            int amount = (int)(Math.random()* 5 + 1);
            if(spawnsLeft <= 5)
                amount = spawnsLeft;

            for(int x = 0; x < amount; x++){
                spawnsLeft--;
                spawn.spawnEnemy(this);
            }


            //System.out.println("Size: " + activeEnemy.size);
        }


    }


    /**Displays the paths.
     Mostly for testing purposes. User is not suppose to see path.**/
    public void drawPath(SpriteBatch batch){
        for(int x = 0; x < path.getPath().size; x++){
            renderer.begin(batch.getProjectionMatrix(), GL20.GL_LINE_STRIP);
            Vector2 out = new Vector2();
            float val = 0f;
            while (val <= 1f) {
                renderer.color(0f, 0f, 0f, 1f);
                path.getPath().get(x).valueAt(out, val);
                renderer.vertex(out.x, out.y, 0);
                val += 0.001f;
            }
            renderer.end();
        }

    }
    public Array<Enemy> getActiveEnemy(){return activeEnemy;}
    public MainPath getPath(){return path;}
    public int getSpawnsLeft(){return spawnsLeft;}
    public Boolean noMoreEnemy(){return (spawnsLeft == 0 && activeEnemy.size == 0);}



    /** Checks enemy array for dead enemies and removes them */
    public void  checkForDead(){

        for(int x = 0; x < activeEnemy.size; x++){

            if(activeEnemy.get(x).isDead()) {

                //if enemy is dead, the tower that targeted it will have 'hasTarget' set to false.
                for(int s = 0; s < tower.getTowerArray().size;s++){
                    if(tower.getTowerArray().get(s).getTarget().equals(activeEnemy.get(x))){
                        tower.getTowerArray().get(s).setHasTarget(false);
                    }
                }
                enemyCounter--;
                activeEnemy.get(x).dispose();
                activeEnemy.removeIndex(x);
            }

            else{//else, update the tower's oldTargetPosition.
                for(int s = 0; s < tower.getTowerArray().size;s++){
                    if(tower.getTowerArray().get(s).getTarget().equals(activeEnemy.get(x))){
                        tower.getTowerArray().get(s).setOldTargetPosition(activeEnemy.get(x).getLocation());
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

    public void dispose(){
        renderer.dispose();
        path.dispose();
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
