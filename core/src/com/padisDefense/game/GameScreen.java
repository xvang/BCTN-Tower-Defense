package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.padisDefense.game.Managers.BulletManager;
import com.padisDefense.game.Managers.CollisionManager;
import com.padisDefense.game.Managers.DamageManager;
import com.padisDefense.game.Managers.EnemyManager;
import com.padisDefense.game.Managers.LevelManager;
import com.padisDefense.game.Managers.SpawnManager;
import com.padisDefense.game.Managers.TowerManager;
import com.padisDefense.game.Managers.UIManager;


/**
 *
 *
 * */
public class GameScreen extends ScreenAdapter implements InputProcessor {

    public Padi padi;
    private Sprite background;
    private boolean  END_GAME = false;

    public EnemyManager enemy;
    public TowerManager tower;
    public BulletManager bullet;
    public LevelManager level;
    public SpawnManager spawn;
    public DamageManager damage;
    public CollisionManager collision;

    //stuff for the UI
    public UIManager UI;
    private float oldEnemyCount = 0;
    private float newEnemyCount = 0;


    //maybe right now a multi is not needed,
    //but it never hurts to have,  right?
    InputMultiplexer multi;


    public int whatLevel;
    public GameScreen(Padi p, int l){
        padi = p;
        whatLevel = l;
    }

    @Override
    public void show(){

        background = new Sprite(new Texture("custombackground1.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setOrigin(0,0);
        tower = new TowerManager(this);
        enemy = new EnemyManager(this);



        level = new LevelManager(this);
        spawn = new SpawnManager(this);
        UI = new UIManager(this);
        damage = new DamageManager(this);
        bullet = new BulletManager(this);
        collision = new CollisionManager(this);


        level.setLevel(whatLevel);
        level.determineLevel();


        //enemy amount and path is stored in enemy.
        //information about those things are stored in levelManager.
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
        background.draw(padi.batch);
        oldEnemyCount = enemy.getEnemyCounter();
        enemy.startEnemy(padi.batch, spawn);
        tower.startTowers(padi.batch, enemy);


        if(!END_GAME) {

            gatherCharge();
            if(enemy.getCountDownTimer() <= 0f){//game clock starts when countdown ends.
                UI.updateTimer(Gdx.graphics.getDeltaTime());
                UI.updateTimerMessage();
            }

        }

        newEnemyCount = enemy.getEnemyCounter();

        calcMoney();


        padi.batch.end();


        UI.getStage().draw();

        //checks if game ended.


        if((enemy.noMoreEnemy() || UI.fullChargeMeter())){

            END_GAME = true;
            enemy.destroyAllEnemy();
            UI.updateUIStuff(enemy.getEnemyCounter(), tower.getInGameMoney());

            UI.gameOver();
            multi.clear();
            multi.addProcessor(UI.endStage);
            UI.endGameTable.setVisible(true);
            UI.endStage.draw();


        }
        UI.updateUIStuff(enemy.getEnemyCounter(), tower.getInGameMoney());

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
        level.dispose();
        spawn.dispose();
    }

    @Override
    public void resize(int x, int y){
        UI.getStage().getViewport().update(x, y, true);
    }

    @Override
    public void hide(){}
    @Override
    public void pause(){}

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        UI.clickedTower(x, y);
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