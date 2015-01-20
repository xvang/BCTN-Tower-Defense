package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    ShapeRenderer shape;

    public TowerManager(GameScreen g, Padi p){
        game = g;
        padi = p;
        towerArray = new Array<Tower>();
        buildableArray = new Array<BuildableSpot>();
        batch = new SpriteBatch();
        arbitraryPoint = new Vector2();
        shape = new ShapeRenderer();

        Array<Tower> tempStorage = new Array<Tower>();


        //creating some instances of the towers to be released into tower pool.
        for(int x = 0; x < 2; x++){
            for(int y = 1; y <= 3; y++){
                Tower t = padi.assets.towerCustomPool.obtain("ROGUE", y, new Vector2(-50f, -50f));
                Tower t2 = padi.assets.towerCustomPool.obtain("SPEED", y, new Vector2(-50f, -50f));
                Tower t3 = padi.assets.towerCustomPool.obtain("SNIPER", y, new Vector2(-50f, -50f));
                Tower t4 = padi.assets.towerCustomPool.obtain("STRENGTH", y, new Vector2(-50f, -50f));
                Tower t5 = padi.assets.towerCustomPool.obtain("AOE", y, new Vector2(-50f, -50f));
                Tower t6 = padi.assets.towerCustomPool.obtain("LASER", y, new Vector2(-50f, -50f));

                tempStorage.add(t); tempStorage.add(t2);
                tempStorage.add(t3); tempStorage.add(t4);
                tempStorage.add(t5); tempStorage.add(t6);
            }
        }

        padi.assets.towerCustomPool.freeAll(tempStorage);
    }



    /**
     * renders all the towers and buildablespots
     *
     * */
    public void startTowers(){


        batch.begin();
        for(int x = 0; x < buildableArray.size; x++){
            buildableArray.get(x).draw(batch);
        }


        for(int x = 0; x < towerArray.size; x++){
            Tower currentTower = towerArray.get(x);
            //towerArray.get(x).spinning();






            checkRange(currentTower);
            checkForDead(currentTower);
            checkSpecialConditions(currentTower);

            //System.out.println(towerArray.get(x).getTarget().isDead());
            //System.out.println("Target distance: " + findDistance(towerArray.get(x).getLocation(), towerArray.get(x).getTarget().getLocation()));
            //towerArraycurrentTower.draw(batch);.get(x).getTarget().rotate(10);



            if(!currentTower.hasTarget)
                assignTargets(game.enemy, currentTower);

            game.bullet.shooting(batch, currentTower,
                    currentTower.getTarget());

            checkRange(currentTower);
            checkForDead(currentTower);
            checkSpecialConditions(currentTower);

            if(currentTower.hasTarget){
                calcRotate(currentTower, currentTower.getTarget());
                customRotate(currentTower);
            }


            if(!currentTower.state){
                currentTower.sparkle.animate(batch, currentTower.getSparkleLocation());
            }

            //TODO: implement explosions?
            //explosion animation for when the bullet hits enemy.
            //the nested if-statement checks if animation is finished.
            if(currentTower.explode){
                currentTower.explosion.animate(batch);
                if(currentTower.explosion.finished()){
                    currentTower.explode = false;
                    currentTower.explosion.stateTime = 0;
                }
            }

            currentTower.draw(batch);
            //System.out.println("Has target: #" + x + "  ...  " + towerArray.get(x).getHasTarget());

            /*if(currentTower.getID().equals("STRENGTH")){
                System.out.println("Strength: hastarget = " + currentTower.hasTarget + " ,  targetLocation: " + currentTower.getTarget().getLocation() +
                        ",  targetHealth = " + currentTower.getTarget().getHealth());
            }*/
        }


        batch.end();

    }


    //the general conditions that would cause a tower to lose its target are:
    //if target goes out of range
    //if target dies
    //here, we check the special conditions for each tower.
    public void checkSpecialConditions(Tower t){


        //strength tower's special ability is to freeze the enemy.
        //if enemy is frozen, tower should look for new enemy.
        if(t.getID().equals("STRENGTH")){
            if(t.getTarget().getRate() == 0){
                t.hasTarget = false;
                t.lockedOnTarget = false;
            }
        }


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


                for(int x = 0; x < t.getActiveBullets().size; x++){
                    Bullet b = t.getActiveBullets().get(x);

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


        if(t.pause >= 0f || stillActiveBullets(t)){
            t.pause -= Gdx.graphics.getDeltaTime();
        }
        else{

            Enemy temp = null;
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

                //if the tower is a strength tower, then one extra condition
                //needs to be checked.
                //else, tower's target is set and we are good to go.
                //the nullpointer warnings are not errors.
                if(t.getID().equals("STRENGTH")){
                    if(temp.getRate() >= 0f){
                        t.setTarget(temp);
                        t.hasTarget = true;
                        t.setOldTargetPosition(temp.getLocation());
                        t.pause = 0.2f;
                        t.lockedOnTarget = false;
                    }
                }

                else{
                    t.setTarget(temp);
                    t.hasTarget = true;
                    t.setOldTargetPosition(temp.getLocation());
                    t.pause = 0.2f;
                    t.lockedOnTarget = false;
                }

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


    //returns the tower to the tower pool.
    public void clearBuildable(BuildableSpot t){

        if(t.getCurrentTower() != null){

            Tower pointer = t.getCurrentTower();
            inGameMoney += (int)(t.getCurrentTower().getCost()*0.6);
            towerArray.removeValue(t.getCurrentTower(), false);

            padi.assets.towerCustomPool.free(pointer);
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

    public void customRotate(Tower t){
        if(t.hasTarget && t.state){
            if(t.getRotation() != t.rotateDestination){
                if( t.getRotation() + 2 <= t.rotateDestination){
                    t.rotate(t.getRotateRate());
                }
                else if(t.getRotation() - 2 >= t.rotateDestination){
                    t.rotate(-t.getRotateRate());
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

    public void drawCircles(){
        for(int x = 0; x < towerArray.size; x++){
            Tower t = towerArray.get(x);

            if(t.clicked){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shape.begin(ShapeRenderer.ShapeType.Filled);
                shape.setColor(new Color(0,1,0,0));
                shape.setColor(1,0, 0, 0.3f);
                shape.circle(t.getCenterX(), t.getCenterY(), t.getRange());
                shape.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }

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


        for(int s = 0; s < game.tower.getTowerArray().size; s++)
            game.tower.getTowerArray().get(s).clicked = false;


    }

}
