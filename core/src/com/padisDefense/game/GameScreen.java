package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.padisDefense.game.Managers.BulletManager;
import com.padisDefense.game.Managers.DamageManager;
import com.padisDefense.game.Managers.EnemyManager;
import com.padisDefense.game.Managers.LevelManager;
import com.padisDefense.game.Managers.SpawnManager;
import com.padisDefense.game.Managers.TowerManager;
import com.padisDefense.game.Managers.UIManager;


/**
 *
 * There are three levels to the GUI control here.
 * UI, UI's stage, and this.
 *
 * */
public class GameScreen extends ScreenAdapter implements InputProcessor {

    Padi padi;
    private boolean  END_GAME = false;

    public EnemyManager enemy;
    public TowerManager tower;
    public BulletManager bullet;
    public LevelManager level;
    public SpawnManager spawn;
    public DamageManager damage;

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

        level = new LevelManager();
        spawn = new SpawnManager(tower);
        UI = new UIManager(spawn);
        damage = new DamageManager(enemy);
        bullet = new BulletManager(damage);



        level.setLevel(whatLevel);
        level.determineLevel();


        //Setting the enemy amount and getting the path
        // for the level.
        enemy.setEnemyAmount(level.getEnemyAmount());
        enemy.setPath(level.getPath());


        spawn.spawnBuildableSpots(tower);



        //Setting up the inputs.
        multi = new InputMultiplexer();



        multi.addProcessor(UI.getStage());
        multi.addProcessor(UI);
        multi.addProcessor(this);


        Gdx.input.setInputProcessor(multi);

    }


    //TODO: mess with the GDX.clearcolor() to make NUKE animations.
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);





        padi.batch.begin();
        oldEnemyCount = enemy.getEnemyCounter();
        enemy.startEnemy(padi.batch, spawn);
        tower.startTowers(padi.batch, enemy);


        if(!END_GAME) {

            for (int x = 0; x < tower.getTowerArray().size; x++) {
                tower.updateTargets();
                bullet.shooting(padi.batch, tower.getTowerArray().get(x),
                        tower.getTowerArray().get(x).getTarget());
            }

            //tower.checkRange();
            //if needed, assigns new targets.
            //tower.assignTargets(enemy);
            gatherCharge();

            UI.updateTimer(Gdx.graphics.getDeltaTime());
            UI.updateTimerMessage();
        }

        newEnemyCount = enemy.getEnemyCounter();

        calcMoney();
        UI.updateUIStuff(enemy.getEnemyCounter(), tower.getInGameMoney());

        padi.batch.end();


        UI.getStage().draw();

        //checks if game ended.
        if(enemy.noMoreEnemy() || UI.fullChargeMeter()){
            END_GAME = true;
            System.out.println("You win!");
            enemy.destroyAllEnemy();

        }
    }




    //TODO: make different types of enemies worth differently.
    public void calcMoney(){
        tower.updateInGameMoney((int) (Math.abs(oldEnemyCount - newEnemyCount) * 10));
        oldEnemyCount = newEnemyCount;
    }


    //if getState() returns 'false', then it must be in charging mode.
    //its chargRate is retrieved and added to temp.
    //temp is passed to UIManager to update charging meter.
    public void gatherCharge(){
        float temp = 0;
        for(int x = 0; x < tower.getTowerArray().size; x++){
            if(!tower.getTowerArray().get(x).getState())
                temp += tower.getTowerArray().get(x).getChargeRate();

        }
        UI.updateChargeMeter(temp);
    }
    @Override
    public void dispose(){
        enemy.dispose();
        tower.dispose();
        bullet.dispose();
        UI.dispose();
    }



    @Override
    public void hide(){}
    @Override
    public void pause(){}

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        UI.clickedTower(x, y, tower);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        //System.out.println(screenX + "  :  " + screenY);
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {return false;}

    @Override
    public boolean scrolled(int amount) {return false;}

    @Override
    public boolean keyDown(int keycode) {return false;}

    @Override
    public boolean keyUp(int keycode) {return false;}

    @Override
    public boolean keyTyped(char character) {return false;}

}



/**
 * TODO too many calls like "foo.bar.fool.bark.fubar.get().fb()"?
 * Maybe that's why the game slows a bit at the beginning.
 * Return from this screen back to World Map.
 *
 * **/