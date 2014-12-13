package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Vector2;


/**
 * This signifies places on the map where towers can be built.
 *
 * @author Xeng
 *
 * */
public class BuildableSpot extends MainTower{


    //'currentTower' points to what is currently built on a buildableSpot.
    private MainTower currentTower;


    //Currently, Vector p is unused.
    public BuildableSpot(Vector2 p){

        setTexture(new Texture("test3.png"));
        setSize(30f, 30f);

    }


    public MainTower getCurrentTower(){
        return currentTower;
    }
    public void setCurrentTower(MainTower newTower){
        currentTower = newTower;
    }


    public boolean emptyCurrentTower(){

        if(currentTower == null){
            return true;
        }

        return false;
    }









}
