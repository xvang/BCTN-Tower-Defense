package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.MainTower;
import com.padisDefense.game.Towers.RogueTower;

/**
 *
 * This class manages all the towers.
 * Upgrades, Charges, Create, Destroy, etc.
 *
 *
 * @author  Xeng
* **/
public class TowerManager{


    GameScreen game;
    private Array<MainTower> towerArray;
    private Array<BuildableSpot> buildableArray;
    private int inGameMoney = 3000;


    public TowerManager(GameScreen g){
        game = g;

        towerArray = new Array<MainTower>();
        buildableArray = new Array<BuildableSpot>();
    }



    /**
     * renders all the towers and buildablespots
     *
     * */
    public void startTowers(SpriteBatch batch, EnemyManager enemy){

        for(int x = 0; x < buildableArray.size; x++){
            buildableArray.get(x).draw(batch, 1);
        }
 //       double distance, y2y1, x2x1;
        for(int x = 0; x < towerArray.size; x++){

            //towerArray.get(x).spinning();
            towerArray.get(x).draw(batch);

            //for the spinning thing for rogue towers.
            RogueTower t;
            if(towerArray.get(x).getID().equals("rogue")){
                t = (RogueTower)towerArray.get(x);
                t.spin(batch);
            }


            checkRange(towerArray.get(x));
            checkForDead(towerArray.get(x));
            if(!towerArray.get(x).getHasTarget())
                assignTargets(enemy, towerArray.get(x));


            game.bullet.shooting(batch, towerArray.get(x),
                    towerArray.get(x).getTarget());
        }



    }

    //Checks to see if target enemy object is out of range.
    public void checkRange(MainTower t){
        double distance = findDistance(t.getLocation(), t.getTarget().getLocation());

        if(distance > t.getRange()){
            t.setHasTarget(false);
        }

        else{
            t.setOldTargetPosition(t.getTarget().getLocation());
        }
    }

    public void checkForDead(MainTower t){

        //setting oldTargetPosition, like in checkRange().
        if(t.getHasTarget()){
            if(t.getTarget().isDead()){
                t.setHasTarget(false);
            }
            else{
                t.setOldTargetPosition(t.getTarget().getLocation());
            }
        }
    }// end checkforDead();


    //Assigns targets to towers. Has a small pause.
    public void assignTargets(EnemyManager enemy, MainTower t){
        double currentMin, previousMin = 1000;
        Enemy temp = null;

        if(t.pause >= 0f || stillActiveBullets(t)){
            t.pause -= Gdx.graphics.getDeltaTime();
        }
        else{
            for(int x = 0; x < enemy.getActiveEnemy().size; x++){

                currentMin = findDistance(enemy.getActiveEnemy().get(x).getLocation(), t.getLocation());

                if(currentMin < previousMin){
                    temp = enemy.getActiveEnemy().get(x);
                    previousMin = currentMin;
                }
            }

            if(previousMin < t.getRange()){
                t.setTarget(temp);
                t.setHasTarget(true);
                t.pause = 0.2f;
            }
        }

    }

    public Array<MainTower> getTowerArray(){return towerArray;}
    public Array<BuildableSpot> getBuildableArray(){return buildableArray;}
    public void addBuildableSpots(Vector2 position){

        BuildableSpot build = new BuildableSpot(position);
        build.setPosition(position.x, position.y);
        buildableArray.add(build);
    }


    public void clearBuildable(BuildableSpot t){

        if(t.getCurrentTower() != null){
            inGameMoney += (int)(t.getCurrentTower().getCost()*0.6);
            towerArray.removeValue(t.getCurrentTower(), false);
            t.setCurrentTower(null);
            t.setHasTower(false);
        }
    }
    public void updateInGameMoney(int m){inGameMoney += m;}
    public int getInGameMoney(){return inGameMoney;}

    public boolean stillActiveBullets(MainTower t){
        for(int x = 0; x < t.getActiveBullets().size; x++){
            if(t.getActiveBullets().get(x).alive){
                return true;
            }
        }
        return false;
    }



    public double findDistance(Vector2 a, Vector2 b){

        double x2x1 = a.x - b.x;
        double y2y1 = a.y - b.y;
        return Math.sqrt((x2x1 * x2x1) + (y2y1 * y2y1));
    }


    public void dispose() {
        for (int x = 0; x < towerArray.size; x++) {
            towerArray.get(x).getTexture().dispose();
        }
    }
}
