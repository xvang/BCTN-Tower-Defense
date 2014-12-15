package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.padisDefense.game.Towers.BuildableSpot;


import java.math.BigDecimal;
import java.math.RoundingMode;


public class UIManager implements InputProcessor{


    Image background;
    Stage stage;
    Table table;
    Skin skin;

    //prints money and enemy left.
    Label moneyMessage;
    Label enemyMessage;

    //prints time in float.
    private float TIMER = 0;
    private Label timeMessage;
    Table popup;

    //if user clicks on that image thing on the right.
    int fakeMoney;



    //charging meter.
    private Image loadingHidden;
    private Image loadingFrame;
    private Actor loadingBar;


    //optionMenu, when clicked.
    private Table optionTable;
    private TextButton charge, upgrade, sell;
    private BuildableSpot currentBS = null;//points to the clicked buildableSpot.
    private boolean b = false;
    private TowerManager UITower;

    public UIManager(){
        this.init();
    }

    public void init(){


        background = new Image(new Texture("test3.png"));
        stage = new Stage();
        table = new Table();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        //messages
        moneyMessage = new Label("$ ", skin);
        enemyMessage = new Label("Enemies left: ", skin);


        //charging meter
        loadingHidden = new Image(new Texture("progressbarempty.png"));
        loadingFrame = new Image(new Texture("progressbarbackground.png"));
        loadingBar = new Image(new Texture("progressbar.png"));

        //charging meter sizes and positions
        loadingFrame.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - (loadingFrame.getHeight() / 2 + 10f));
        loadingBar.setPosition(loadingFrame.getX() + 10f, loadingFrame.getY() + 10f);
        loadingBar.setSize(0, 0);
        loadingHidden.setPosition(loadingBar.getX(), loadingBar.getY());

        //option Table
        optionTable = new Table();
        charge = new TextButton("Charge", skin, "default");
        upgrade = new TextButton("Upgrade", skin, "default");
        sell = new TextButton("Sell", skin, "default");
        optionTable.add(charge).pad(5f);
        optionTable.add(upgrade).pad(5f);
        optionTable.add(sell).pad(5f);
        optionTable.setSize(50f, 50f);
        optionTable.setVisible(false);

        //adding clicklisteners for option table.
        charge.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent e, float x, float y){
                System.out.println("clickedCharge");
                b = !b;
                optionTable.setVisible(b);

                try{
                    if(currentBS.getCurrentTower().getState()) {
                        currentBS.getCurrentTower().setState(false);
                        charge.setText(currentBS.getCurrentTower().getMessage());
                    }

                    else if(!currentBS.getCurrentTower().getState()){
                        currentBS.getCurrentTower().setState(true);
                        charge.setText(currentBS.getCurrentTower().getMessage());
                    }
                }catch(Exception a){
                    //do nothing.
                }
            }
        });

        //upgrades the tower, and keeps the old state of the tower.
        //Exaple: tower was charging, got upgraded, should still be charging.
        upgrade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                b = !b;
                optionTable.setVisible(b);
                boolean oldState = true;
                try{
                    oldState = currentBS.getCurrentTower().getState();


                }catch(Exception t){
                    //do nothing.
                }

                UITower.clickedBuildable(currentBS);
                try{
                    currentBS.getCurrentTower().setState(oldState);
                    charge.setText(currentBS.getCurrentTower().getMessage());
                }catch(NullPointerException n){
                    System.out.println("NOT ENOUGH MONEY");
                }



            }
        });

        sell.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                b = !b;
                optionTable.setVisible(b);
                UITower.clearBuildable(currentBS);

            }
        });


        //UI thing on the right.
        timeMessage = new Label("Total time: " + String.valueOf(TIMER), skin);
        popup = new Table();
        popup.add(timeMessage);
        popup.setSize(400f, 150f);
        popup.setCenterPosition(Gdx.graphics.getWidth() - timeMessage.getWidth(), 20f);
        popup.setVisible(false);

        background.setSize(200f, Gdx.graphics.getHeight());
        background.setPosition(Gdx.graphics.getWidth() - background.getWidth(), 0);

        background.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                fakeMoney++;
            }
        });
        table.setSize(200f, Gdx.graphics.getHeight());
        table.setPosition(Gdx.graphics.getWidth() - 200f, 0);



        table.add(enemyMessage).row().row();
        table.add(moneyMessage).row().row();
        table.add(background).row();
        table.add(timeMessage).row();


        stage.addActor(table);
        stage.addActor(popup);
        stage.addActor(loadingFrame);
        stage.addActor(loadingHidden);
        stage.addActor(loadingBar);
        stage.addActor(optionTable);

        //stage.addActor(button);
    }




    public int getFakeMoney(){if(fakeMoney > 0)return fakeMoney--;return 0;}

    public Stage getStage(){return stage;}
    public Table getTable(){return table;}
    public float getTimer(){return TIMER;}
    public void setBackground(Image s){background = s;}
    public void updateTimer(float d){TIMER += d;}


    public void updateEnemyMessage(int e){
        enemyMessage.setText("Enemies Left: " + String.valueOf(e));
    }

    public void updateMoneyMessage(int m){
        moneyMessage.setText("$ " + String.valueOf(m));
    }

    public void updateTimerMessage(){
        timeMessage.setText("Timer: " + String.valueOf(round(TIMER, 1)));
    }

    float a = 0;
    boolean stopUpdating = false;
    public void updateChargeMeter(float d){


        if(!stopUpdating) {
            if (loadingBar.getWidth() < loadingHidden.getWidth()) {
                a += d;
                loadingBar.setSize(a, loadingHidden.getHeight());
                stage.act();
            }
        }

        if(loadingBar.getWidth() >= loadingHidden.getWidth()) {
            stopUpdating = true;
            loadingBar.setWidth(loadingHidden.getWidth());
        }

    }

    public void updateUIStuff(int enemyCounter, int inGameMoney){
        updateEnemyMessage(enemyCounter);
        updateMoneyMessage(inGameMoney);


    }
    public boolean fullChargeMeter(){
        return (loadingBar.getWidth() == loadingHidden.getWidth());
    }


    public static double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    /**Takes the coord of a click, and the coord of a tower
     * if their respective rectangles overlap, then user has
     * "clicked" a tower.  Towers actually take no direct user input.
     * The optionsTable will be located at where the clicked tower is.
     * 'b' boolean below toggles the option table.
     * 'currentBS' points to the clicked BuildableSpot
     * it will be passed into clickedBuildableSpot() in towerManager.
     * */
    public void clickedTower(int x, int y, TowerManager tower){
        UITower = tower;
        Rectangle rec1 = new Rectangle();
        rec1.setSize(2f, 2f);
        rec1.setPosition(x, Gdx.graphics.getHeight() - y);
        for(int s = 0; s < tower.getBuildableArray().size; s++){
            if(rec1.overlaps(tower.getBuildableArray().get(s).getBoundingRectangle())){
                b = !b;

                //updating the 'charge' button message.
                try{
                    charge.setText(tower.getBuildableArray().get(s).getCurrentTower().getMessage());
                }catch(Exception e){

                    charge.setText(tower.getBuildableArray().get(s).getMessage());
                }

                //setting the optiontable's location to where clicked tower is.
                optionTable.setPosition(tower.getBuildableArray().get(s).getX() - (optionTable.getWidth()/2),
                        tower.getBuildableArray().get(s).getY() - (optionTable.getHeight() - 5f));
                optionTable.setVisible(b);
                currentBS = tower.getBuildableArray().get(s);

            }
        }
    }


    public void dispose(){
        stage.dispose();
        skin.dispose();
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        System.out.println("touch UI");

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {


        System.out.println("UI   " + screenX + "  :  " + screenY);
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
 *
 * https://github.com/Matsemann/libgdx-loading-screen/blob/master/Main/src/com/matsemann/libgdxloadingscreen/screen/LoadingScreen.java
 * **/