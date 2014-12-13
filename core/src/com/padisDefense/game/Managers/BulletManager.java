package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Towers.MainTower;

/**
 *This class manages the the 'shooting' aspect of the towers.
 * It manages the path that the bullets will traverse to the enemy.
 * It calculates damage.
 *
 *
 * @author Xeng.
 * **/
public class BulletManager {

    private final Array<Bullet> activeBullets;
    private final Pool<Bullet> bulletPool;
    private float spawnTimer = 0;


    //TODO: make different towers have different maxBullets.
    //Currently, towerManager and bulletManager do not communicate. Probably.
    private final int maxBullets = 10;


    //stores path of current level.
    private Path<Vector2> path;

    public BulletManager(){

        activeBullets = new Array<Bullet>();
        bulletPool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                //TODO make bullet spawn where tower is. Must find out why this is working.
                return new Bullet(new Vector2());
            }
        };

    }


    /**
     * This function takes in the location of a tower, and that tower's target's location.
     * Creates a line (bezier) between the points where bullet will travel along.
     * Every iteration, a new line is calculated.
     *
     * @param 'batch'
     * @param 't'
     * @param 'e'
     * */
    public void shooting(SpriteBatch batch, MainTower t, Enemy e){

        spawnTimer += Gdx.graphics.getDeltaTime();

        //If tower has target AND tower is in shoot mode.
        if(t.getHasTarget() && t.getState()){

            Vector2 tower = new Vector2(t.getLocation());
            Vector2 enemy = new Vector2(e.getLocation());

            //Creating path between the two points.
            path = new Bezier<Vector2>(new Vector2(tower), new Vector2(enemy));
            Vector2 out = new Vector2();
            Bullet item;

            //if max amount of bullet has not been produced,
            //AND spawn timer has gone on long enough,
            //get more bullets.
            if(activeBullets.size < maxBullets && spawnTimer > 1f) {
                item = bulletPool.obtain();
                item.init(out.x + (t.getWidth() / 2), out.y + (t.getHeight() / 2));
                activeBullets.add(item);
                spawnTimer = 0;

            }


            for(int x = 0;x < activeBullets.size; x++){

                //time is from the bullet
                float time = (activeBullets.get(x).getTime() + 0.02f);

                //calculates the bullet's location on the path
                //using the bullet's time.
                //stores the bullet's location in a Vector2 'out'
                if(time < 1f)
                    path.valueAt(out, time);

                //update new time
                //goTo new position.
                //draw
                activeBullets.get(x).setTime(time);
                activeBullets.get(x).goTo(new Vector2(out.x, out.y));
                activeBullets.get(x).draw(batch);

                //Bullet hits the enemy.
                if (reachedEnemy(new Vector2(activeBullets.get(x).getLocation()), enemy)){
                    e.updateHealth(t.getAttack());
                }

                //not sure what this is for
                if (time < 1f){
                    item = activeBullets.get(x);
                    bulletPool.free(item);

                }

                //this is to return bullet back to the beginning.
                if (time >= 1f)
                    activeBullets.get(x).setTime(0);


            }



        }





    }

    /**Takes the location of the bullet and location of the enemy.
     * If the distance between the two is ~1f, then bullet has "hit" the enemy.
     *
     * @param 'location'
     * @param 'enemy'
     * */
    public Boolean reachedEnemy(Vector2 location, Vector2 enemy){
        double x1x2, y1y2, distance;
        x1x2 = location.x - enemy.x;
        y1y2 = location.y - enemy.y;

        distance = Math.sqrt((x1x2 * x1x2) + (y1y2 * y1y2));

        return (distance < 1f);
    }


    public void dispose(){

    }

}













































/*
*
    public BulletManager() {
    }

    public void run(Array<MainTower> t,  SpriteBatch batch) {


        fire(t,  batch);
    }


    private float fireCounter = 0;
    public void fire(Array<MainTower> t, SpriteBatch batch) {



        //System.out.println(t.getX() + "  " + t.getY());
        Enemy closest;

        for(int x = 0; x < t.size; x++){
            fireCounter += Gdx.graphics.getDeltaTime();
            if (t.get(x).getActiveBullet().size < 20 && fireCounter >= t.get(x).getFireRate()){

                Bullet newBullet;
                newBullet = t.get(x).getBulletPool().obtain();
                newBullet.init(t.get(x).getX(), t.get(x).getY());
                t.get(x).getActiveBullet().add(newBullet);

                fireCounter = 0;
            }

            //deleting
            Bullet item;
            int len = t.get(x).getActiveBullet().size;
            for (int s = len; --s >= 0; ) {
                item = t.get(x).getActiveBullet().get(s);
                if (!item.alive) {
                    System.out.println("Bounding box lapped.");
                    t.get(x).getActiveBullet().removeIndex(s);
                    //t.get(x).getBulletPool().free(item);
                }
            }


            System.out.println("size: " + t.get(x).getActiveBullet().size);
        }


        for(int x = 0; x < t.size; x++){
            //drawing.
            Bullet item;
            int len;
            len = t.get(x).getActiveBullet().size;
            for (int s = len; --s >= 0; ) {
                item = t.get(x).getActiveBullet().get(s);
                if (item.alive && inRange(t.get(x),t.get(x).getTarget(), item)) {
                    item.fire(t.get(x).getTarget().getBoundingRectangle());
                    item.draw(batch);
                }
            }
        }










    }

    public Boolean inRange(MainTower tower, Enemy e, Bullet item){


        if(Math.abs(tower.getX() - e.getX()) <= tower.getRange() &&
           Math.abs(tower.getY() - e.getY()) <= tower.getRange()){

            return true;
        }


        return false;
    }



* */