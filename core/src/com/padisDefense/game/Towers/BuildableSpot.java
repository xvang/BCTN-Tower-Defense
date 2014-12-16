package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;


/**
 * This signifies places on the map where towers can be built.
 *
 * @author Xeng
 *
 * */
public class BuildableSpot extends MainTower{




    //'currentTower' points to what is currently built on a buildableSpot.
    private MainTower currentTower = null;
    private boolean hasTower = false;



    //Currently, Vector p is unused.
    public BuildableSpot(Vector2 p){
        setTexture(new Texture("test3.png"));
        setSize(30f, 30f);
        setID("BS");
        setState(false);

    }


    public MainTower getCurrentTower(){
        return currentTower;
    }

    //called from clearBuildable() in towerManager. It passes in a null.
    // If newTower is null, then hasTower should be false.
    public void setCurrentTower(MainTower newTower){
        currentTower = newTower;
        hasTower = true;

        if(newTower == null) {
            hasTower = false;
        }
    }

    public void setHasTower(boolean b){hasTower = b;}

    public boolean emptyCurrentTower(){
        return !hasTower;
    }







}
