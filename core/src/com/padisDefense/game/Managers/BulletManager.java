package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Towers.Tower;

/**
 *This class manages the the 'shooting' aspect of the towers.
 * It manages the path that the bullets will traverse to the enemy.
 * It calculates damage.
 *
 *
 * @author Xeng.
 * **/
public class BulletManager {

    Padi padi;
    GameScreen game;
    private float spawnTimer = 0;

    //Constructor
    public BulletManager(GameScreen g, Padi p){game = g;padi = p;}


    /**
     * This function takes in the location of a tower, and that tower's target's location.
     * Creates a line (bezier) between the points where bullet will travel along.
     * Every iteration, a new line is calculated.
     *
     *
     * This function should only be called when tower has a target that is not dead and within range.
     * */
    public void shooting(SpriteBatch batch, Tower t, Enemy e){

        //TODO: spawnTimer, getRate() thing. make the tower.rate useful.
        //if-statement wraps around every relevant code in shooting().
        //if tower is NOT in shooting mode, no code involving moving bullets should execute.
        if(t.state && t.hasTarget){
            Vector2 enemyLocation;
            Vector2 towerLocation = new Vector2(t.getBulletSpawnLocation());
            enemyLocation = new Vector2(e.getLocation());

            //Here is where the different firing towers are implemented...?
            if(t.getID().equals(/*"RED"*/"")){

                //System.out.println("FIRIN MA LASER");
                //laserShot(batch,enemyLocation, towerLocation);
            }
            else{


                //Creating path between the two points.
                //final Path<Vector2> path = new Bezier<Vector2>(towerLocation,midpoint,  enemyLocation);
                final Path<Vector2> path = new Bezier<Vector2>(towerLocation, enemyLocation);
                Vector2 out = new Vector2();
                Vector2 angle = new Vector2();//used to get the angle rotation for path...
                //example: make an arrow curve along a path.

                //if needed, spawn a new bullet.
                if(t.getActiveBullets().size < t.getBulletLimit() && t.hasTarget && t.lockedOnTarget /*&& t.explosion.stateTime == 0*/){

                    Bullet item = t.getPool().obtain();
                    item.init(t.getBulletSpawnLocation());

                    item.setTime(0);

                    t.getActiveBullets().add(item);
                }


                for(int x = 0;x < t.getActiveBullets().size; x++){

                    Bullet currentBullet = t.getActiveBullets().get(x);
                    //time is from the bullet
                    float time = (currentBullet.getTime() + t.getBulletRate());

                    //calculates the bullet's location on the path
                    //using the bullet's time.
                    //stores the bullet's location in a Vector2 'out'
                    if(time <= 1f){
                        path.valueAt(out, time);
                        path.derivativeAt(angle, time);
                    }
                    else{
                        game.damage.hit(t, e);
                        currentBullet.alive = false;
                    }


                    //This little bugger here gave me the most difficult time.
                    //There is a moment where path.valueAt() returns (0,0) for 'out'.
                    //That made the bullets briefly spawn at (0,0).
                    if(out.x != 0f && out.y != 0f){
                        //update new time, go to new position, draw
                        currentBullet.setTime(time);
                        if(findDistance(out, t.getLocation())< t.getRange()){
                            currentBullet.goTo(new Vector2(out.x, out.y));
                        }
                        else{
                            currentBullet.goTo(t.getOldTargetPosition());
                        }

                        currentBullet.draw(batch);
                    }

                    if(!currentBullet.alive){

                        currentBullet.setTime(0f);
                        t.getActiveBullets().removeIndex(x);
                        t.getPool().free(currentBullet);
                    }
                }
            }
        }

        //System.out.println("after shooting bullet size : " + t.getActiveBullets().size);
    }// end shooting();

    //todo: laser shots?
    public void laserShot(SpriteBatch batch, Vector2 enemy, Vector2 tower){


    }
    //TODO: work 'arc' into the midpoint formula.
    //midpoint formula.
    //something about private access prevents me from just putting
    //Vector2 mid = new Vector2((t.x + 10f + e.x)/2, (t.y + 10f + e.y)/2);
    //It can be worked around, but...this is already here.
    public Vector2 almostMidPoint(Vector2 t, Vector2 e, float arc){
        return new Vector2((t.x+ arc + e.x)/2, (t.y + arc + e.y)/2);
    }


    public double findDistance(Vector2 a, Vector2 b){

        double x2x1 = a.x - b.x;
        double y2y1 = a.y - b.y;
        return Math.sqrt((x2x1 * x2x1) + (y2y1 * y2y1));
    }

    public void dispose(){

    }

    public void reset(){

    }
}



//MAY NOT NEED THIS. JUST KEEPING FOR BECAUSE.
/**
 *  //Create rectangles around tower and enemy to see if they overlap.
 public Boolean hitEnemy(Bullet b, Enemy e){

 Rectangle t_rec = new Rectangle();
 t_rec.setSize(b.getWidth(), b.getHeight());
 t_rec.setPosition(b.getLocation());

 Rectangle e_rec = new Rectangle();
 e_rec.setSize(e.getWidth(), e.getHeight());
 e_rec.setPosition(e.getLocation());

 return t_rec.overlaps(e_rec);
 }
 * */






/*//not sure what this is for
                if (time >= 1f){
                    if(t.getHasTarget())//if no target, no need for bullet to restart.
                        currentBullet.setTime(0f);

                    item = t.getActiveBullets().get(x);
                    if(!item.alive || item.getTime() > 1f){
                        item.setTime(0f);
                        t.getActiveBullets().removeIndex(x);
                        t.getPool().free(item);
                    }

                }*/