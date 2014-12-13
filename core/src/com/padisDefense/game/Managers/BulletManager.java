package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
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


    private float spawnTimer = 0;


    //stores path of current level.
    private Path<Vector2> path;

    public BulletManager(){
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


            if(t.getActiveBullets().size < t.getBulletLimit() && spawnTimer > 1f){
                item = t.getPool().obtain();
                item.init(out.x + (t.getWidth() / 2), out.y + (t.getHeight() / 2));
                t.getActiveBullets().add(item);
                spawnTimer = 0;
            }


            for(int x = 0;x < t.getActiveBullets().size; x++){

                //time is from the bullet
                float time = (t.getActiveBullets().get(x).getTime() + 0.01f);

                //calculates the bullet's location on the path
                //using the bullet's time.
                //stores the bullet's location in a Vector2 'out'
                if(time < 1f)
                    path.valueAt(out, time);

                //update new time, go to new position, draw
                t.getActiveBullets().get(x).setTime(time);
                t.getActiveBullets().get(x).goTo(new Vector2(out.x, out.y));
                t.getActiveBullets().get(x).draw(batch);



                /**2 different ways to determine if bullet hit enemy.
                 * not sure which one is better yet.
                 * One uses Rectangles, the other uses Vector2.*/
            //Bullet hits the enemy.
                if (reachedEnemy(new Vector2(t.getActiveBullets().get(x).getLocation()), enemy)){
                    e.updateHealth(t.getAttack());
                }
                /*if(hitEnemy(t.getActiveBullets().get(x), e)){
                    e.updateHealth(t.getAttack());
                }*/

                //not sure what this is for
                if (time < 1f){
                    item = t.getActiveBullets().get(x);
                    t.getPool().free(item);

                }

                //this is to return bullet back to the beginning.
                if (time >= 1f)
                    t.getActiveBullets().get(x).setTime(0);


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


    //Create rectangles around tower and enemy to see if they overlap.
    public Boolean hitEnemy(Bullet b, Enemy e){

        Rectangle t_rec = new Rectangle();
        t_rec.setSize(b.getWidth(), b.getHeight());
        t_rec.setPosition(b.getLocation());

        Rectangle e_rec = new Rectangle();
        e_rec.setSize(e.getWidth(), e.getHeight());
        e_rec.setPosition(e.getLocation());

        return t_rec.overlaps(e_rec);
    }


    public void dispose(){

    }

}
