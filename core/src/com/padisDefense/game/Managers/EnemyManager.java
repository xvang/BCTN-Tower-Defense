package com.padisDefense.game.Managers;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.BestGoblin;
import com.padisDefense.game.Enemies.BiggerGoblin;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.Goblin;
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



    private int enemyAmount;
    private int deadEnemyCounter = 0;
    ImmediateModeRenderer20 renderer;

    private Array<Enemy>  enemyArray;


    public float time = 0;

    /**CONSTRUCTOR**/
    public EnemyManager(){
        renderer = new ImmediateModeRenderer20(false, false, 0);
        enemyArray = new Array<Enemy>();
        storage = new PathStorage();
    }


    public void setEnemyAmount(int newEA){enemyAmount = newEA;}

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
     * @param 'batch'
     * */
    public void startEnemy(SpriteBatch batch){



        position = new Vector2();


        //Makes enemy travel along path.
        for(int x = 0; x < enemyArray.size; x++){

            if (!enemyArray.get(x).isDead()) {
                time = enemyArray.get(x).getRate() + enemyArray.get(x).getTime();
                enemyArray.get(x).setTime(time);

                //System.out.println(enemyArray.get(x).getChosenPath());
                path.getPath().get(enemyArray.get(x).getChosenPath()).valueAt(position, time);
                enemyArray.get(x).goTo(new Vector2(position.x, position.y));
                enemyArray.get(x).draw(batch);

                //If enemy reached end, it starts path over.
                if(enemyArray.get(x).getTime() >= 1f)
                    enemyArray.get(x).setTime(0f);
            }
            /**NOTE: To see the enemy objects loop, set each object's time variable to zero.
             * I think the best way to reset is in GameScreen.**/
        }

        checkForDead();
        //removeReachedEnd();//If looping, we don't want this.

        //int old = enemyAmount;

        //Calculating if spawning is necessary.
        if(enemyArray.size < 25 && enemyAmount > 0){
            enemyAmount --;
            spawnEnemy();
        }


        //if (old != enemyAmount)
         //   System.out.println("Enemies left: " + enemyAmount);

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
    public Array<Enemy> getEnemyArray(){return enemyArray;}




    /**
     * TODO: Allow to different types of enemy to be generated.
     *
     * This function spawns enemies.
     *
     * **/
    public void spawnEnemy(){

        //TODO THIS IS WHERE THE ENEMY CHOOSES WHAT PATH TO TAKE. IT'S HARDCODED. CHANGE ITTTTT.
        /**Creates new enemy, and assigns it a path.**/

        Enemy newEnemy;

        //TODO don't make spawning enemy random. Have a set amount for each kind?
        //This is more for testing purposes.
        //0 for goblin ,1 for bigger goblin, 2 for bestgoblin
        int rand = (int)(Math.random()*3);

        if(rand == 0){

            //create object, set path, set texture, add to enemy array.
            newEnemy = new Goblin();
            newEnemy.setChosenPath((int)(Math.random()*100) % path.getPath().size);
            newEnemy.setTexture(new Texture("test3.png"));
            enemyArray.add(newEnemy);
        }

        else if (rand == 1){

            newEnemy = new BiggerGoblin();
            newEnemy.setChosenPath((int)(Math.random()*100) % path.getPath().size);
            newEnemy.setTexture(new Texture("test1.png"));
            enemyArray.add(newEnemy);
        }

        else if(rand == 2){

            newEnemy = new BestGoblin();
            newEnemy.setChosenPath((int)(Math.random()*100) % path.getPath().size);
            newEnemy.setTexture(new Texture("test8.png"));
            enemyArray.add(newEnemy);
        }




    }

    public int getEnemyAmount(){return enemyAmount;}
    public Boolean noMoreEnemy(){return (enemyAmount == 0 && enemyArray.size == 0);}



    /** Checks enemy array for dead enemies and removes them */
    public void  checkForDead(){

        for(int x = 0; x < enemyArray.size; x++){

            if(enemyArray.get(x).isDead()){
                deadEnemyCounter++;
                enemyArray.get(x).dispose();
                enemyArray.removeIndex(x);
            }
        }
    }


    /**
     * If enemy reaches end of path, it should be destroyed.
     * End of path should be off the screen.
     * Currently, I don't want the enemy to die at the end of the path.
     * */
    public void removeReachedEnd(){

        for(int x = 0; x < enemyArray.size; x++){

            if(enemyArray.get(x).getTime() > 1f){
                enemyArray.get(x).dispose();
                enemyArray.removeIndex(x);
            }
        }
    }

    public int getDeadEnemyCounter(){return deadEnemyCounter;}


    /**
     * This sets every enemy's time to zero.
     * every enemy will respawn at the beginning at the same time.
     * */
    public void toZero(){

        for(int x = 0; x < enemyArray.size; x++){
            enemyArray.get(x).setTime(0f);
        }
    }

    public void dispose(){
        renderer.dispose();
        path.dispose();
    }
}
