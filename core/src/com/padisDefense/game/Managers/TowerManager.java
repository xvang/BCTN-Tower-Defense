package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.Tower;

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
    private Array<Tower> towerArray;
    private Array<BuildableSpot> buildableArray;
    private int inGameMoney = 3000;


    public TowerManager(GameScreen g){
        game = g;

        towerArray = new Array<Tower>();
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


        for(int x = 0; x < towerArray.size; x++){




            //towerArray.get(x).spinning();
            towerArray.get(x).draw(batch);

            checkRange(towerArray.get(x));
            checkForDead(towerArray.get(x));

            //System.out.println(towerArray.get(x).getTarget().isDead());
            //System.out.println("Target distance: " + findDistance(towerArray.get(x).getLocation(), towerArray.get(x).getTarget().getLocation()));
            //towerArray.get(x).getTarget().rotate(10);

            if(!towerArray.get(x).getHasTarget())
                assignTargets(enemy, towerArray.get(x));

            game.bullet.shooting(batch, towerArray.get(x),
                    towerArray.get(x).getTarget());

            checkRange(towerArray.get(x));
            checkForDead(towerArray.get(x));
            //System.out.println("Has target: #" + x + "  ...  " + towerArray.get(x).getHasTarget());

        }






    }

    //Checks to see if target enemy object is out of range.
    public void checkRange(Tower t){

        double distance = findDistance(t.getLocation(), t.getTarget().getLocation());

        //System.out.println((int)distance);
        if(distance > t.getRange()){
            t.setHasTarget(false);
        }

        else{
            t.setOldTargetPosition(t.getTarget().getLocation());
        }
    }

    public void checkForDead(Tower t){

        //setting oldTargetPosition, like in checkRange().
        if(t.getHasTarget()){

            if(!t.getTarget().alive){
                t.setHasTarget(false);
                Bullet b;

                for(int x = 0; x < t.getActiveBullets().size; x++){
                    b = t.getActiveBullets().get(x);

                    b.setTime(0f);
                    t.getPool().free(b);
                }
            }
            else{
                t.setOldTargetPosition(t.getTarget().getLocation());
            }
        }

    }// end checkforDead();


    //Assigns targets to towers. Has a small pause.
    public void assignTargets(EnemyManager enemy, Tower t){
        double currentMin, previousMin = 2000;
        Enemy temp = null;

        if(t.pause >= 0f || stillActiveBullets(t)){
            t.pause -= Gdx.graphics.getDeltaTime();
        }
        else{

            //checks all the  enemies, and finds the closest one.
            for(int x = 0; x < enemy.getActiveEnemy().size; x++){

                currentMin = findDistance(enemy.getActiveEnemy().get(x).getLocation(), t.getLocation());

                if(currentMin < previousMin){
                    temp = enemy.getActiveEnemy().get(x);
                    previousMin = currentMin;
                }
            }

            //If potential target is within range, then it becomes target.
            if(previousMin < t.getRange()){
                t.setTarget(temp);
                t.setHasTarget(true);
                t.setOldTargetPosition(temp.getLocation());
                t.pause = 0.8f;
            }
        }
    }



    public Array<Tower> getTowerArray(){return towerArray;}
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

    public boolean stillActiveBullets(Tower t){
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
