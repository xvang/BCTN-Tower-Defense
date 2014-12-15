package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.MainTower;
import com.padisDefense.game.Towers.TowerA;
import com.padisDefense.game.Towers.TowerB;
import com.padisDefense.game.Towers.TowerC;

/**
 *
 * This class manages all the towers.
 * Upgrades, Charges, Create, Destroy, etc.
 *
 *
 *
 *
 * @author  Xeng
* **/
public class TowerManager {


    private Array<MainTower> towerArray;
    private Array<BuildableSpot> buildableArray;

    private int inGameMoney = 300;

    public TowerManager(){
        towerArray = new Array<MainTower>();
        buildableArray = new Array<BuildableSpot>();
    }



    /**
     * renders all the towers and buildablespots
     *
     *
     * @param 'batch'
     * */
    public void startTowers(SpriteBatch batch){


        for(int x = 0; x < buildableArray.size; x++){
            buildableArray.get(x).draw(batch);

        }
 //       double distance, y2y1, x2x1;
        for(int x = 0; x < towerArray.size; x++){

            //towerArray.get(x).spinning();
            towerArray.get(x).draw(batch);

            checkRange();
            checkForDead(towerArray.get(x));

        }



    }



    //Checks to see if target enemy object is out of range.
    public void checkRange(){
        double distance, y2y1, x2x1;

        for(int x = 0; x < towerArray.size; x++){

            y2y1 = towerArray.get(x).getY() - towerArray.get(x).getTarget().getY();
            x2x1 = towerArray.get(x).getX() - towerArray.get(x).getTarget().getX();
            distance = Math.sqrt((y2y1*y2y1) + (x2x1*x2x1));

            if (distance > towerArray.get(x).getRange()){
                towerArray.get(x).setHasTarget(false);
            }
        }
    }

    public void addTower(Vector2 position){

        //I know it's kind of funky how I am disposing
        //and setting a texture here. Will look into it
        //in the future if I have time. But it works.
        TowerA tower = new TowerA(position);
        tower.getTexture().dispose();

        tower.setTexture(new Texture("test2.png"));
        towerArray.add(tower);
    }

    public Array<MainTower> getTowerArray(){return towerArray;}
    public Array<BuildableSpot> getBuildableArray(){return buildableArray;}


    public void addBuildableSpots(Vector2 position){

        BuildableSpot build = new BuildableSpot(position);
        build.setPosition(position.x, position.y);
        buildableArray.add(build);
    }
    /**
     * checks for dead targets.
     * dead enemy object is deleted in EnemyManager
     * Here we are removing the pointer to the deleted object. I think.
     *
     * @param 't'
     */
    public void checkForDead(MainTower t){

        if (t.getTarget().isDead()){
            t.setTarget(new Enemy());
        }

    }




    //TODO change HOW tower upgrading works. Currently, the code is just stuff to see if everything work.


    /**
     * tower manager implements an inputprocessor,
     * so when it is clicked the function touchedDown() executes,
     * and calls this function.
     * Currently it just builds a new tower, and upgrades that tower.
     * There are no options to sell or charge yet.
     *
     * @param 't'
     * */
    public void clickedBuildable(BuildableSpot t){
        //if 'true' then nothing is built there. yet.
        if(t.emptyCurrentTower()){
            TowerA newTower = new TowerA(new Vector2(t.getX(),
                                                     t.getY()));
            if(inGameMoney >= newTower.getCost()){
                towerArray.add(newTower);
                t.setCurrentTower(newTower);//points the buildablspot to the tower.
                inGameMoney -= newTower.getCost();
            }

        }

        //TowerA is currently there. Will delete and build Tower B.
        else if(t.getCurrentTower().getID().equals("A")) {

            TowerB newTower = new TowerB(new Vector2(t.getX(),
                                                     t.getY())); //Create TowerB
            if(inGameMoney >= newTower.getCost()){

                towerArray.removeValue(t.getCurrentTower(), false);//deletes TowerA from towerArray.
                towerArray.add(newTower);
                t.setCurrentTower(newTower);//points to the tower.
                inGameMoney -= newTower.getCost();
            }
        }

        else if(t.getCurrentTower().getID().equals("B")) {

            TowerC newTower = new TowerC(new Vector2(t.getX(),
                    t.getY())); //Create TowerB
            if(inGameMoney >= newTower.getCost()){

                towerArray.removeValue(t.getCurrentTower(), false);//deletes TowerA from towerArray.
                towerArray.add(newTower);
                t.setCurrentTower(newTower);//points to the tower.
                inGameMoney -= newTower.getCost();
            }
        }



        //System.out.println("towerArray size = " + towerArray.size);
    }

    public void clearBuildable(BuildableSpot t){

        if(t.getCurrentTower() != null){
            inGameMoney += (int)(t.getCurrentTower().getCost()*0.6);
            towerArray.removeValue(t.getCurrentTower(), false);
            t.setCurrentTower(null);
        }


        //System.out.println("towerArray size = " + towerArray.size);
    }
    public void updateInGameMoney(int m){inGameMoney += m;}
    public int getInGameMoney(){return inGameMoney;}

    public void updateTargets(){
        double currentDistance;
        for(int x = 0; x < towerArray.size; x++) {
            currentDistance = findDistance(towerArray.get(x).getLocation(),
                    towerArray.get(x).getTarget().getLocation());

            if(currentDistance >= towerArray.get(x).getRange()){
                towerArray.get(x).setHasTarget(false);
            }
        }
    }

    public void assignTargets(EnemyManager enemy){
        double currentMin, previousMin = 1000;
        Enemy temp = new Enemy(new Vector2(-1,-1));//dummy enemy.

        for(int x = 0; x < towerArray.size; x++){

            if(!towerArray.get(x).getHasTarget()){//hasTarget == false

                for(int y = 0; y < enemy.getActiveEnemy().size; y++){

                    //finding distance between tower and enemy.
                    currentMin = findDistance(new Vector2(towerArray.get(x).getLocation()),
                            new Vector2(enemy.getActiveEnemy().get(y).getLocation()));

                    //Within range, closest target so far.
                    if(currentMin < towerArray.get(x).getRange() &&
                            currentMin < previousMin){
                        previousMin = currentMin;
                        temp = enemy.getActiveEnemy().get(y);
                    }

                }
            }

            //temp was initialized to be at (-1, -1). If it is no longer there,
            //then it had to have changed above.
            if(temp.getX() != -1){
                towerArray.get(x).setTarget(temp);
                towerArray.get(x).setHasTarget(true);
            }
        }
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
