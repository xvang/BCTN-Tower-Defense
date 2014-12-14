package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class UIManager {


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
        button.setSize(40f, 20f);
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

    public static double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
