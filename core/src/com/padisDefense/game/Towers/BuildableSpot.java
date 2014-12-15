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
    private MainTower currentTower = null;


    //Currently, Vector p is unused.
    public BuildableSpot(Vector2 p){
        setTexture(new Texture("test3.png"));
        setSize(30f, 30f);
        setID("BS");
        setState(false);
        //setMessage("Attack");
    }


    public MainTower getCurrentTower(){
        return currentTower;
    }
    public void setCurrentTower(MainTower newTower){
        currentTower = newTower;
    }


    //If is is a null pointer, then setAttack should throw an error, right?
    //side note: i don't think 'if(currentTower == null)' works.
    //further test required.
    public boolean emptyCurrentTower(){

        try{
            currentTower.setAttack(1);
            return false;//false, currentTower is NOT empty.
        }catch(Exception e){
            return true;//true, it IS empty.
        }

    }









}
