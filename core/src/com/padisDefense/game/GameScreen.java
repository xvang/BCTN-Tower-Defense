package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Managers.BulletManager;
import com.padisDefense.game.Managers.EnemyManager;
import com.padisDefense.game.Managers.LevelManager;
import com.padisDefense.game.Managers.TowerManager;
import com.padisDefense.game.Managers.UIManager;

import java.math.BigDecimal;
import java.math.RoundingMode;


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
    private boolean  END_GAME = false;

    public EnemyManager enemy;
    public TowerManager tower;
    public BulletManager bullet;
    public LevelManager level;

    //stuff for the UI
    public UIManager UI;
    private float oldEnemyCount = 0;
    private float newEnemyCount = 0;


    //maybe right now a multi is not needed,
    //but it never hurts to have,  right?
    InputMultiplexer multi;




    int whatLevel;
    public GameScreen(Padi p, int l){
        padi = p;
        whatLevel = l;
    }

    @Override
    public void show(){

        enemy = new EnemyManager();
        tower = new TowerManager();
        bullet = new BulletManager();
        level = new LevelManager();
        UI = new UIManager();

        level.setLevel(whatLevel);
        level.determineLevel();

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
        tower.addBuildableSpots(new Vector2(550f, 500f));
        tower.addBuildableSpots(new Vector2(660f, 400f));

        //Setting up the inputs.
        multi = new InputMultiplexer();


        multi.addProcessor(UI.getStage());
        multi.addProcessor(tower);


        Gdx.input.setInputProcessor(multi);

    }


    //TODO: mess with the GDX.clearcolor() to make NUKE animations.
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(2f,.5f,0.88f,6);
        //Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




        padi.batch.begin();
        oldEnemyCount = enemy.getEnemyCounter();
        enemy.startEnemy(padi.batch);
        tower.startTowers(padi.batch);


        if(!END_GAME) {

            for (int x = 0; x < tower.getTowerArray().size; x++) {
                updateTargets();
                bullet.shooting(padi.batch, tower.getTowerArray().get(x),
                        tower.getTowerArray().get(x).getTarget());
            }


            //if needed, assigns new targets.
            assignTargets();

            UI.updateTimer(Gdx.graphics.getDeltaTime());
            UI.updateTimerMessage();
        }

        newEnemyCount = enemy.getEnemyCounter();

        calcMoney();
        updateUIStuff();

        //}
        padi.batch.end();

        UI.getStage().draw();

        //checks if game ended.
        END_GAME = enemy.noMoreEnemy();
        if(END_GAME){
            System.out.println("You win!");
            //padi.setScreen(padi.worldmap);
        }
    }




    public void updateUIStuff(){
        UI.updateEnemyMessage(enemy.getEnemyCounter());
        UI.updateMoneyMessage(tower.getInGameMoney());


    }


    //TODO: make different types of enemies worth differently.
    public void calcMoney(){
        tower.updateInGameMoney((int)(Math.abs(oldEnemyCount - newEnemyCount)*10));
        oldEnemyCount = newEnemyCount;
    }


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


    public void assignTargets(){

        double currentMin, previousMin = 1000;
        Enemy temp = new Enemy(new Vector2(-1,-1));//dummy enemy.

        for(int x = 0; x < tower.getTowerArray().size; x++){

            if(!tower.getTowerArray().get(x).getHasTarget()){//hasTarget == false

                for(int y = 0; y < enemy.getActiveEnemy().size; y++){

                    //finding distance between tower and enemy.
                    currentMin = findDistance(new Vector2(tower.getTowerArray().get(x).getLocation()),
                                              new Vector2(enemy.getActiveEnemy().get(y).getLocation()));

                    //Within range, closest target so far.
                    if(currentMin < tower.getTowerArray().get(x).getRange() &&
                            currentMin < previousMin){
                        previousMin = currentMin;
                        temp = enemy.getActiveEnemy().get(y);
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