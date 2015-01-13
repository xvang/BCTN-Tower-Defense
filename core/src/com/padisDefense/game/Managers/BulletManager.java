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
    Vector2 towerLocation, enemyLocation;
    Bullet currentBullet;

    //Constructor
    public BulletManager(GameScreen g, Padi p){game = g;padi = p;}


    /**
     * This function takes in the location of a tower, and that tower's target's location.
     * Creates a line (bezier) between the points where bullet will travel along.
     * Every iteration, a new line is calculated.
     *
     * */
    public void shooting(SpriteBatch batch, Tower t, Enemy e){

        spawnTimer += Gdx.graphics.getDeltaTime();

        //if-statement wraps around every relevant code in shooting().
        //if tower is NOT in shooting mode, no code involving moving bullets should execute.
        if(t.state){

            towerLocation = new Vector2(t.getBulletSpawnLocation());

            if(t.hasTarget)
                enemyLocation = new Vector2(e.getLocation());
            else
                enemyLocation = new Vector2(t.getOldTargetPosition());




            //Vector2 midpoint = almostMidPoint(towerLocation, enemyLocation, t.getCustomArc());



            //Creating path between the two points.
            //final Path<Vector2> path = new Bezier<Vector2>(towerLocation,midpoint,  enemyLocation);
            final Path<Vector2> path = new Bezier<Vector2>(towerLocation, enemyLocation);
            Vector2 out = new Vector2();
            Vector2 angle = new Vector2();//used to get the angle rotation for path...
                                            //example: make an arrow curve along a path.
            Bullet item;



            if(t.getActiveBullets().size < t.getBulletLimit() && spawnTimer > t.getFireRate()
                    && t.hasTarget && t.lockedOnTarget /*&& t.explosion.stateTime == 0*/){

                item = t.getPool().obtain();
                //item.init(t.getX()+ (t.getWidth() / 2), t.getY()+ (t.getHeight() / 2));
                item.init(t.getBulletSpawnLocation());
                item.setTexture(t.getBulletTexture());
                item.setTime(0);

                t.getActiveBullets().add(item);

                spawnTimer = 0;

            }


            for(int x = 0;x < t.getActiveBullets().size; x++){

                currentBullet = t.getActiveBullets().get(x);

                //time is from the bullet
                float time = (currentBullet.getTime() + t.getBulletRate());

                //calculates the bullet's location on the path
                //using the bullet's time.
                //stores the bullet's location in a Vector2 'out'
                if(time <= 1f){
                    path.valueAt(out, time);
                    path.derivativeAt(angle, time);
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

                if(hitEnemy(currentBullet, e) /*|| arrivedAtOldLocation(currentBullet, e)*/){
                    game.damage.hit(t, e);

                    //telling game to show tower's animation for when bullet hits enemy.
                    //setting location of explosion to where enemy is currently located.
                    //if(t.getID().equals("AOE")){
                   //     t.explosion.setExplosionPosition(e.getLocation());
                   //     t.explode = true;
                   // }
                    currentBullet.setTime(0);//to activate below.
                    //item = t.getActiveBullets().get(x);
                    t.getActiveBullets().removeIndex(x);
                    t.getPool().free(currentBullet);
                }


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
            }

        }

    }// end shooting();


    public boolean notAlmostDead(Tower t, Enemy e){
        return e.getHealth() - t.getAttack() > 0;
    }
    //TODO: work 'arc' into the midpoint formula.
    //midpoint formula.
    //something about private access prevents me from just putting
    //Vector2 mid = new Vector2((t.x + 10f + e.x)/2, (t.y + 10f + e.y)/2);
    //It can be worked around, but...this is already here.
    public Vector2 almostMidPoint(Vector2 t, Vector2 e, float arc){
        return new Vector2((t.x+ arc + e.x)/2, (t.y + arc + e.y)/2);
    }

    /**Takes the location of the bullet and location of the enemy.
     * If the distance between the two is ~1f, then bullet has "hit" the enemy.
     *
     * */


    //The bullet's destination is the bottom left coordinate of the enemy.
    //But the bullet will overlap the enemy for many iterations before
    //reaching the bottom left. So damage is calculated many times before
    //bullet reaches its destination.
    //This feature might be useful later on.
    /*public boolean hitEnemy(Bullet b, Enemy e){
        Rectangle b_rec = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
        Rectangle e_rec = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
        return b_rec.overlaps(e_rec);
    }*/


    public boolean hitEnemy(Bullet b, Enemy e){
        double x  =findDistance(new Vector2(b.getX(), b.getY()),
                new Vector2(e.getX() + e.getWidth()/2, e.getY()+e.getHeight()/2));
        return x < 8f;
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