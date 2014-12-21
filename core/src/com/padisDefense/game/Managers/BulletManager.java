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

    GameScreen game;
    private float spawnTimer = 0;


    //Constructor
    public BulletManager(GameScreen g){game = g;}


    /**
     * This function takes in the location of a tower, and that tower's target's location.
     * Creates a line (bezier) between the points where bullet will travel along.
     * Every iteration, a new line is calculated.
     *
     * */
    public void shooting(SpriteBatch batch, MainTower t, Enemy e){

        spawnTimer += Gdx.graphics.getDeltaTime();

        //If tower has target AND tower is in shoot mode.
        if(t.getState()){

            Vector2 tower = new Vector2(t.getLocation());
            Vector2 enemy;

            if(t.getHasTarget())
                enemy = new Vector2(e.getLocation());
            else
                enemy = new Vector2(t.getOldTargetPosition());




            Vector2 midpoint = almostMidPoint(tower, enemy, t.getCustomArc());



            //Creating path between the two points.
            final Path<Vector2> path = new Bezier<Vector2>(tower,midpoint,  enemy);
            Vector2 out = new Vector2();
            Bullet item;


            if(t.getActiveBullets().size < t.getBulletLimit() && spawnTimer > t.getFireRate()
                    &&t.getHasTarget()){

                item = t.getPool().obtain();
                item.init(t.getX()+ (t.getWidth() / 2), t.getY()+ (t.getHeight() / 2));
                item.setTexture(t.getBulletTexture());
                t.getActiveBullets().add(item);
                spawnTimer = 0;
            }


            for(int x = 0;x < t.getActiveBullets().size; x++){

                //time is from the bullet
                float time = (t.getActiveBullets().get(x).getTime() + t.getBulletRate());

                //calculates the bullet's location on the path
                //using the bullet's time.
                //stores the bullet's location in a Vector2 'out'
                //if(time <= 1f)
                path.valueAt(out, time);



                //This little bugger here gave me the most difficult time.
                //There is a moment where path.valueAt() returns (0,0) for 'out'.
                //That made the bullets briefly spawn at (0,0).
                if(out.x != 0f && out.y != 0f){
                    //update new time, go to new position, draw
                    t.getActiveBullets().get(x).setTime(time);
                    t.getActiveBullets().get(x).goTo(new Vector2(out.x, out.y));
                    t.getActiveBullets().get(x).draw(batch);
                }

                if(hitEnemy(t.getActiveBullets().get(x), e)){
                    game.damage.hit(t, e);
                }


                //not sure what this is for
                if (time >= 1f){
                    if(t.getHasTarget())//if no target, no need for bullet to restart.
                        t.getActiveBullets().get(x).setTime(0f);

                    item = t.getActiveBullets().get(x);
                    if(!item.alive || item.getTime() > 1f){
                        item.setTime(0f);
                        t.getActiveBullets().removeIndex(x);
                        t.getPool().free(item);
                    }

                }
            }

        }

    }// end shooting();


    //TODO: work 'arc' into the midpoint formula.
    //midpoint formula.
    //something about private access prevents me from just putting
    //Vector2 mid = new Vector2((t.x + 10f + e.x)/2, (t.y + 10f + e.y)/2);
    //It can be worked around, but...this is already here.
    public Vector2 almostMidPoint(Vector2 t, Vector2 e, float arc){
        return new Vector2((t.x+ 50f + e.x)/2, (t.y + 50f + e.y)/2);
    }

    /**Takes the location of the bullet and location of the enemy.
     * If the distance between the two is ~1f, then bullet has "hit" the enemy.
     *
     * */


    public Boolean hitEnemy(Bullet b, Enemy e){

        Rectangle b_rec = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
        Rectangle e_rec = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());

        return b_rec.overlaps(e_rec);
    }



    public void dispose(){

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