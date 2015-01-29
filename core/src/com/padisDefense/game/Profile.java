package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.math.BigDecimal;
import java.math.RoundingMode;



public class Profile extends ScreenAdapter {

    private Padi padi;
    TextButton clearProfile, menu;

    Group group;

    Stage stage;
    public Profile(Padi p){
        padi = p;
    }

   @Override
   public void show(){
       Player p = padi.player;
       int w = Gdx.graphics.getWidth();
       int h = Gdx.graphics.getHeight();
       group = new Group();
       stage = new Stage();


       Label money = new Label("Money:   " + String.valueOf(p.money) + "\n", padi.assets.someUIskin, "default");
       Label win = new Label("Wins:   " + String.valueOf(p.wins) + "\n", padi.assets.someUIskin, "default");
       Label loss = new Label("Losses:   "  + String.valueOf(p.loss) + "\n", padi.assets.someUIskin, "default");

       double wl;
       if(padi.player.gamesPlayed > 0)
           wl = ((double)padi.player.wins/(double)padi.player.gamesPlayed)*100;
       else{
           wl = 0;
       }

       Label wlpercent = new Label("W/L percent:   " + String.valueOf(round(wl, 2)) + " % \n", padi.assets.someUIskin, "default");
       Label kills = new Label("Kills:   " + String.valueOf(p.kills) + "\n", padi.assets.someUIskin, "default");


       money.setPosition(w/2, h*4/5);
       win.setPosition(money.getX(), money.getY() - 50f);
       loss.setPosition(win.getX(), win.getY() - 50f);
       wlpercent.setPosition(loss.getX(), loss.getY() - 50f);
       kills.setPosition(wlpercent.getX(), wlpercent.getY() - 50f);

       clearProfile = new TextButton("delete profile", padi.assets.bubbleUI, "yellow");
       clearProfile.setSize(200f, 60f);
       clearProfile.setPosition(w - clearProfile.getWidth() - 20f, 20f);



       final Label message = new Label("Profile has been reset.", padi.assets.someUIskin, "default");
       message.setColor(Color.RED);
       message.setVisible(false);
       message.setPosition(clearProfile.getX(), clearProfile.getY() + clearProfile.getHeight() + 5f);

       clearProfile.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent e, float x, float y){
               padi.player.deleteAll();
               padi.loadsave.clearSavedProfile();
               message.setVisible(true);

           }
       });


       menu = new TextButton("Menu", padi.assets.bubbleUI, "yellow");
       menu.setSize(200f, 60f);
       menu.setPosition( 20f, 20f);

       menu.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent e, float x, float y){
               padi.setScreen(padi.main_menu);
               message.setVisible(true);
           }
       });

       group.addActor(money);
       group.addActor(win);
       group.addActor(loss);
       group.addActor(wlpercent);
       group.addActor(kills);
       group.addActor(clearProfile);
       group.addActor(menu);
       group.addActor(message);

       stage.addActor(group);

       Gdx.input.setInputProcessor(stage);
   }



    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    //Not mine. some answer on Stackoverflow.
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
