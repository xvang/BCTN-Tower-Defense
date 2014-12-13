package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Managers.BulletManager;
import com.padisDefense.game.Managers.EnemyManager;
import com.padisDefense.game.Managers.LevelManager;
import com.padisDefense.game.Managers.TowerManager;
import com.padisDefense.game.Padi;


/**
 * This class runs the game play.
 * Note: the game ends directly after all the enemies are dead.
 * There are no transitions nor messages yet.
 *
 * @author Xeng.
 *
 * @param 'level'
 * @param 'padi'
 *
 * */
public class GameScreen extends ScreenAdapter {

    Padi padi;


    public EnemyManager enemy;
    public TowerManager tower;
    public BulletManager bullet;
    public LevelManager level;

    //I believe an InputMultiplexer is needed to allow input
    // from all the towers. Not sure if that's what it really does,
    // but it seems to work.
    InputMultiplexer multi;

    //Used to calculate money.
    int oldEnemyLeft = 0;
    int money = 0;


    int whatLevel;
    public GameScreen(Padi p, int l){
        
        padi = p;
        whatLevel = l;

    }

    @Override
    public void show(){


        //Creating managers.
        enemy = new EnemyManager();
        tower = new TowerManager();
        bullet = new BulletManager();
        level = new LevelManager();
        level.setLevel(whatLevel);
        level.determineLevel();

        money = 0;

        //Setting the enemy amount and getting the path
        // for the level.
        enemy.setEnemyAmount(level.getEnemyAmount());
        enemy.setPath(level.getPath());


        //For testing purposes only.
        //BuildableSpots are manually spawned here.
        tower.addBuildableSpots(new Vector2(320f, 180f));
        tower.addBuildableSpots(new Vector2(500f, 240f));
        tower.addBuildableSpots(new Vector2(500f, 400f));
        tower.addBuildableSpots(new Vector2(400f, 500f));
        tower.addBuildableSpots(new Vector2(500f, 350f));

        //Setting up the inputs.
        multi = new InputMultiplexer();
        multi.addProcessor(tower);

        Gdx.input.setInputProcessor(multi);

    }


    @Override
    public void render(float delta){
        //Gdx.gl.glClearColor(2f,.5f,0.88f,6);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //getting the enemy count before a render
        oldEnemyLeft = enemy.getDeadEnemyCounter();


        padi.batch.begin();
        enemy.startEnemy(padi.batch);
        tower.startTowers(padi.batch);


        //For every tower, update every tower's target.
        //It doesn't sound quite right, but I think it's close.
        //shooting() takes in a tower and a tower's target.
        for(int x = 0; x < tower.getTowerArray().size; x++) {
            updateTargets();
            bullet.shooting(padi.batch, tower.getTowerArray().get(x),
                    tower.getTowerArray().get(x).getTarget());

        }

        //if needed, assigns new targets.
        assignTargets();
        //enemy.drawPath(padi.batch);
        //to see the path, uncomment.
        //But the bullets become invisible.
        //I guess SpriteBatches can only draw 1 thing at a time?
        padi.batch.end();



        addMoney();

        //TODO make a transition screen after game ends.
        //If game ended
        if(enemy.noMoreEnemy()){

            System.out.println("You win!");
            padi.setScreen(padi.worldmap);
        }
    }




    public void addMoney(){

        //'oldEnemyLeft' stores the original/previous amount of enemy.
        //If enemy died, then oldEnemyLeft != enemyArray.size
        int newMoney = Math.abs(oldEnemyLeft - enemy.getDeadEnemyCounter());

        if(newMoney > 0){

            oldEnemyLeft = enemy.getDeadEnemyCounter();
            money += newMoney*10;
        }


    }


    /**
     * Uses the distance formula to calculate the distance between tower
     * and tower's target. If it is out of tower's range,
     * hasTarget = false.
     * */
    public void updateTargets(){
        double currentDistance;
        for(int x = 0; x < tower.getTowerArray().size; x++) {
            currentDistance = findDistance(tower.getTowerArray().get(x).getLocation(),
                    tower.getTowerArray().get(x).getTarget().getLocation());

            if(currentDistance >= tower.getTowerArray().get(x).getRange()){
                tower.getTowerArray().get(x).setHasTarget(false);
            }
        }
    }


    /**
     * Assigns new targets to towers without a target.
     * */
    public void assignTargets(){

        double currentMin;
        Enemy temp = new Enemy(new Vector2(-1,-1));//dummy enemy.

        for(int x = 0; x < tower.getTowerArray().size; x++){

            if(!tower.getTowerArray().get(x).getHasTarget()){//hasTarget == false

                for(int y = 0; y < enemy.getEnemyArray().size; y++){

                    //finding distance between tower and enemy.
                    currentMin = findDistance(new Vector2(tower.getTowerArray().get(x).getLocation()),
                                              new Vector2(enemy.getEnemyArray().get(y).getLocation()));

                    //Within range, possible target.
                    if(currentMin < tower.getTowerArray().get(x).getRange()){
                        temp = enemy.getEnemyArray().get(y);
                    }

                }
            }

            //temp was initialized to be at (-1, -1). If it is no longer there,
            //then it had to have changed above.
            if(temp.getX() != -1){
                tower.getTowerArray().get(x).setTarget(temp);
                tower.getTowerArray().get(x).setHasTarget(true);
            }
        }
    }

    /**
     * Uses distance formula to find distance between the parameters.
     *
     * @param 'a'
     * @param 'b'
     * */
    public double findDistance(Vector2 a, Vector2 b){

        double x2x1 = a.x - b.x;
        double y2y1 = a.y - b.y;
        return Math.sqrt((x2x1*x2x1) + (y2y1*y2y1));
    }

    @Override
    public void dispose(){
        enemy.dispose();
        tower.dispose();
        bullet.dispose();



    }



    @Override
    public void hide(){

    }
    @Override
    public void pause(){

    }
}



/**
 * TODO too many calls like "foo.bar.fool.bark.fubar.get().fb()"?
 * Maybe that's why the game slows a bit at the beginning.
 * Return from this screen back to World Map.
 *
 * **/