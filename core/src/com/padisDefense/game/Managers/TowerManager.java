package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
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


    Padi padi;
    GameScreen game;
    private Array<Tower> towerArray;
    private Array<BuildableSpot> buildableArray;
    private int inGameMoney = 3000;
    SpriteBatch batch;
    Vector2 arbitraryPoint;

    public TowerManager(GameScreen g, Padi p){
        game = g;
        padi = p;
        towerArray = new Array<Tower>();
        buildableArray = new Array<BuildableSpot>();
        batch = new SpriteBatch();
        arbitraryPoint = new Vector2();
    }



    /**
     * renders all the towers and buildablespots
     *
     * */
    public void startTowers(){


        batch.begin();
        for(int x = 0; x < buildableArray.size; x++){
            buildableArray.get(x).draw(batch, 1);
        }


        for(int x = 0; x < towerArray.size; x++){
            Tower currentTower = towerArray.get(x);
            //towerArray.get(x).spinning();
            currentTower.draw(batch);


            checkRange(currentTower);
            checkForDead(currentTower);

            //System.out.println(towerArray.get(x).getTarget().isDead());
            //System.out.println("Target distance: " + findDistance(towerArray.get(x).getLocation(), towerArray.get(x).getTarget().getLocation()));
            //towerArray.get(x).getTarget().rotate(10);

            if(!currentTower.hasTarget)
                assignTargets(game.enemy, currentTower);

            game.bullet.shooting(batch, currentTower,
                    currentTower.getTarget());

            checkRange(currentTower);
            checkForDead(currentTower);

            if(currentTower.hasTarget){
                calcRotate(currentTower, currentTower.getTarget());
                customRotate(currentTower);
            }

            //System.out.println("Has target: #" + x + "  ...  " + towerArray.get(x).getHasTarget());

        }


        batch.end();

    }

    //Checks to see if target enemy object is out of range.
    public void checkRange(Tower t){

        double distance = findDistance(t.getLocation(), t.getTarget().getLocation());

        //System.out.println((int)distance);
        if(distance > t.getRange()){
            t.hasTarget = false;
            t.lockedOnTarget = false;
        }

        else{
            t.setOldTargetPosition(t.getTarget().getLocation());
        }
    }

    public void checkForDead(Tower t){

        //setting oldTargetPosition, like in checkRange().
        if(t.hasTarget){

            if(!t.getTarget().alive){
                t.hasTarget = false;
                t.lockedOnTarget = false;
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
                t.hasTarget = true;
                t.setOldTargetPosition(temp.getLocation());
                t.pause = 0.8f;
                t.lockedOnTarget = false;
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

    public void customRotate(Tower t){//TODO: different rotation speed for towers??
        if(t.hasTarget && t.state){
            if(t.getRotation() != t.rotateDestination){
                if( t.getRotation() + 2 <= t.rotateDestination){
                    t.rotate(2);
                }
                else if(t.getRotation() - 2 >= t.rotateDestination){
                    t.rotate(-2);
                }
            }
        }


        //System.out.println(Math.abs(t.getRotation() - t.rotateDestination) + " , " + t.lockedOnTarget);
        if(Math.abs(t.getRotation() - t.rotateDestination) <= 3)
            t.lockedOnTarget = true;
        else
            t.lockedOnTarget = false;
    }
    //an arbitrary point, the tower, and the enemy will make up a right triangle.
    //figures out the rotation by calculating the the sin of an angle in the triangle.
    public void calcRotate(Tower t, Enemy d){


        //finding the hypotenuse.
        Vector2 tt = new Vector2(t.getX(), t.getY());
        Vector2 dd = new Vector2(d.getX(), d.getY());
        double hypotenuse = findDistance(tt, dd);

        arbitraryPoint.set(t.getX(), d.getY());
        double opposite = Math.abs(d.getX() - arbitraryPoint.x);

        //calculating.
        double hypoppo = opposite/hypotenuse;

        double arcSin = Math.asin(hypoppo);

        double radian = arcSin*(180/Math.PI);

        //System.out.println(hypotenuse + " / " + hypotenuses + "......." + radian + " / " + radian2);
        if((t.getY() < d.getY()) && t.getX() != d.getX()){//tower is lower than enemy.
            if(d.getX() > arbitraryPoint.x) t.rotateDestination = -(float)radian;
            else t.rotateDestination = (float)radian;
        }

        else if((t.getY() > d.getY()) && t.getX() != d.getX()){// tower is higher than enemy.

            if(d.getX() > arbitraryPoint.x) t.rotateDestination = 180 + (float)radian;
            else t.rotateDestination = 180 - (float)radian;
        }

        else if(t.getY() == d.getY()){//tower is directly across enemy
            if(t.getX() < d.getX())
                t.rotateDestination = 90;
            else
                t.rotateDestination = 270;
        }
        else if(t.getX() == d.getX()){//tower is directly below/above enemy
            if(t.getY() < d.getY())
                t.rotateDestination = 0;
            else
                t.rotateDestination = 180;
        }
    }


    public double findDistance(Vector2 a, Vector2 b){

        double x2x1 = a.x - b.x;
        double y2y1 = a.y - b.y;
        return Math.sqrt((x2x1 * x2x1) + (y2y1 * y2y1));
    }


    /**
     * private Array<Tower> towerArray;
     private Array<BuildableSpot> buildableArray;
     private int inGameMoney = 3000;
     * */
    public void dispose() {

        for (int x = 0; x < towerArray.size; x++)
            towerArray.get(x).getTexture().dispose();

        for(int x = 0; x < buildableArray.size; x++)
            buildableArray.get(x).setCurrentTower(null);

        towerArray.clear();
        buildableArray.clear();
    }

    public void reset(){

        for(int x = 0; x < buildableArray.size; x++)
            buildableArray.get(x).setCurrentTower(null);

        //buildableArray.clear();
        towerArray.clear();



    }

}
