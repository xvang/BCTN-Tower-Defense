package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.padisDefense.game.Managers.BulletManager;
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
    private boolean  GAME_OVER = false;
    private EndGameAnimation endGameAnimation;


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
    public InputMultiplexer multi;


    public GameScreen(Padi p){

        padi = p;
        background = new Sprite(new Texture("test1.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setOrigin(0, 0);
        endGameAnimation = new EndGameAnimation();
        tower = new TowerManager(this, p);
        enemy = new EnemyManager(this, p);
        level = new LevelManager(this, p);
        spawn = new SpawnManager(this, p);
        UI = new UIManager(this, p);
        damage = new DamageManager(this, p);
        bullet = new BulletManager(this, p);
        multi = new InputMultiplexer();



    }

    @Override
    public void show(){

    }

    //enemy amount and path is stored in enemy.
    //information about those things are stored in levelManager.
    public void assignLevel(int L){
        level.setLevel(L);
        level.determineLevel();
        enemy.setEnemyAmount(level.getEnemyAmount());
        enemy.setPath(level.getPath());
        spawn.spawnBuildableSpots(tower);
    }

    boolean do_once = true;
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!UI.PAUSED){
            //background.draw(padi.batch);
            oldEnemyCount = enemy.getEnemyCounter();

            enemy.startEnemy();

            tower.startTowers();

            if(!GAME_OVER) {


                gatherCharge();
                if(enemy.getCountDownTimer() <= 0f){//game clock starts when countdown ends.
                    UI.updateTimer(Gdx.graphics.getDeltaTime());
                    UI.updateTimerMessage();
                }
            }

            newEnemyCount = enemy.getEnemyCounter();

            calcMoney();

            UI.getStage().draw();

            //checks if game ended.


            if((enemy.noMoreEnemy() || UI.fullChargeMeter()) && do_once){

                do_once = false;
                GAME_OVER = true;
                enemy.destroyAllEnemy();
                UI.updateUIStuff(enemy.getEnemyCounter(), tower.getInGameMoney());

                UI.gameOver();
                multi.clear();
                multi.addProcessor(UI.endStage);
                UI.endGameTable.setVisible(true);
                UI.endStage.draw();
                UI.masterTable.setVisible(false);
                UI.hideButton.setPosition(Gdx.graphics.getWidth()-UI.hideButton.getWidth() - 10f, 10f);

            /*if(UI.fullChargeMeter())
                endGameAnimation.run();*/



            }
            else if (GAME_OVER){

                if(UI.fullChargeMeter()){
                    endGameAnimation.run();

                }


                UI.endStage.draw();
            }
            UI.updateUIStuff(enemy.getEnemyCounter(), tower.getInGameMoney());

        }


        //PAUSED
        else{
            UI.pauseStage.draw();
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
        System.out.println("DISPOSING GAME SCREEN NOW!");

        endGameAnimation.dispose();
        enemy.dispose();
        tower.dispose();
        bullet.dispose();
        UI.dispose();
        level.dispose();
        spawn.dispose();
        damage.dispose();

        multi.getProcessors().clear();
    }

    public void reset(){
        endGameAnimation.reset();
        enemy.reset();
        tower.reset();
        bullet.reset();
        UI.reset();
        level.reset();
        spawn.reset();
        damage.reset();

        GAME_OVER = false;
        enemy.setEnemyAmount(level.getEnemyAmount());
        do_once = true;
        oldEnemyCount = 0;
        newEnemyCount = 0;

        multi.clear();

        multi.addProcessor(UI.getStage());
        multi.addProcessor(UI);
        multi.addProcessor(this);


        Gdx.input.setInputProcessor(multi);
    }
    @Override
    public void resize(int x, int y){
        UI.getStage().getViewport().update(x, y, true);
    }

    @Override
    public void hide(){this.reset();}
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