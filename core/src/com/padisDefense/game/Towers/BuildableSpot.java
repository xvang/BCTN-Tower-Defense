package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


/**
 * This signifies places on the map where towers can be built.
 *
 * @author Xeng
 *
 * */
public class BuildableSpot extends Tower {


    //'currentTower' points to what is currently built on a buildableSpot.
    private Tower currentTower = null;
    private boolean hasTower = false;



    //Currently, Vector p is unused.
    public BuildableSpot(Vector2 p){
        super(new Texture("buildablespot.png"));
        setSize(40f, 40f);
        this.setPosition(p.x, p.y);
    }


    public Tower getCurrentTower(){
        return currentTower;
    }

    //called from clearBuildable() in towerManager. It passes in a null.
    // If newTower is null, then hasTower should be false.
    public void setCurrentTower(Tower newTower){
        currentTower = newTower;
        hasTower = true;

        if(newTower == null) {
            hasTower = false;
        }
    }

    public void setHasTower(boolean b){hasTower = b;}

    //only one condition is needed, but checking for thoroughness...?
    public boolean emptyCurrentTower(){
        return (!hasTower && currentTower == null);
    }





}
