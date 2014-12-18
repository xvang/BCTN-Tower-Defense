package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.MainTower;
import com.padisDefense.game.Towers.RogueTower;

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
public class TowerManager{


    private Array<MainTower> towerArray;
    private Array<BuildableSpot> buildableArray;

    private int inGameMoney = 3000;

    ShapeRenderer renderer;

    public TowerManager(){
        towerArray = new Array<MainTower>();
        buildableArray = new Array<BuildableSpot>();
        renderer = new ShapeRenderer();


    }


    /**
     * renders all the towers and buildablespots
     *
     *
     * @param 'batch'
     * */
    public void startTowers(SpriteBatch batch, EnemyManager enemy){

        for(int x = 0; x < buildableArray.size; x++){
            buildableArray.get(x).draw(batch, 1);
        }
 //       double distance, y2y1, x2x1;
        for(int x = 0; x < towerArray.size; x++){

            //towerArray.get(x).spinning();
            towerArray.get(x).draw(batch);

            RogueTower t;
            if(towerArray.get(x).getID().equals("rogue")){
                t = (RogueTower)towerArray.get(x);
                t.spin(batch);
            }


            //TODO: test to see if these two function calls are needed!!!
            checkRange();
            checkForDead();
            assignTargets(enemy);

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
                resetBullets(towerArray.get(x));
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

    public void checkForDead(){

        for(int x = 0; x < towerArray.size; x++)
        if (towerArray.get(x).getTarget().isDead()){
            towerArray.get(x).setTarget(new Enemy());
        }

    }
    public void clearBuildable(BuildableSpot t){

        if(t.getCurrentTower() != null){
            inGameMoney += (int)(t.getCurrentTower().getCost()*0.6);
            towerArray.removeValue(t.getCurrentTower(), false);
            t.setCurrentTower(null);
            t.setHasTower(false);
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
                resetBullets(towerArray.get(x));
            }
        }
    }

    public void assignTargets(EnemyManager enemy){
        double currentMin, previousMin = 1000;
        Enemy temp = new Enemy(new Vector2(-1,-1));//dummy enemy.

        for(int x = 0; x < towerArray.size; x++){

            //the pause is there so tower doesn't assign target too quickly.
            if(towerArray.get(x).pause < 0f){
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
                towerArray.get(x).pause = 1f;
            }

            else{
               towerArray.get(x).pause -= Gdx.graphics.getDeltaTime();
            }


            //temp was initialized to be at (-1, -1). If it is no longer there,
            //then it had to have changed above.
            if(temp.getX() != -1){
                towerArray.get(x).setTarget(temp);
                towerArray.get(x).setHasTarget(true);
                resetBullets(towerArray.get(x));
            }
        }
    }

    public void resetBullets(MainTower t){
        for(int x = 0; x < t.getActiveBullets().size; x++){
            t.getActiveBullets().get(x).alive = false;
            t.getActiveBullets().get(x).setTime(0f);
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
