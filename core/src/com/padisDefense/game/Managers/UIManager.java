package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
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

    int fakeMoney;
    private TextButton button;


    //charging meter.
    private Image loadingHidden;
    private Image loadingFrame;
    private Actor loadingBar;


    public UIManager(){
        this.init();
    }

    public void init(){
        background = new Image(new Texture("test3.png"));
        stage = new Stage();
        table = new Table();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        moneyMessage = new Label("$ ", skin);
        enemyMessage = new Label("Enemies left: ", skin);
        button = new TextButton("WTF", skin, "default");

        loadingHidden = new Image(new Texture("progressbarempty.png"));
        loadingFrame = new Image(new Texture("progressbarbackground.png"));
        loadingBar = new Image(new Texture("progressbar.png"));

        loadingFrame.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - (loadingFrame.getHeight() / 2 + 10f));
        loadingBar.setPosition(loadingFrame.getX() + 10f, loadingFrame.getY() + 10f);
        loadingBar.setSize(0, 0);
        loadingHidden.setPosition(loadingBar.getX(), loadingBar.getY());


        button.setSize(100f, 20f);
        button.setPosition(Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2);

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
        //table.add(button);

        stage.addActor(table);
        stage.addActor(popup);
        stage.addActor(loadingFrame);
        stage.addActor(loadingHidden);
        stage.addActor(loadingBar);

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

    public boolean fullChargeMeter(){
        return (loadingBar.getWidth() == loadingHidden.getWidth());
    }


    public static double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void dispose(){
        stage.dispose();
        skin.dispose();
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {


        System.out.println(screenX + "  :  " + screenY);
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