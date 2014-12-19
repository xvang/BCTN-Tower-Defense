package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;


/**
 * The main tower class. All other towers will extend from this one.
 *
 * @author Xeng
 *
 * */
public class MainTower extends Sprite{



    private String ID;
    private float cost = 1;
    private float attack = 1;
    private float range = 1;
    private float chargeRate = 1;//Used in gameScreen. gatherCharge().
    private float incomeRate = 1;
    private Boolean state = true;//TRUE is shooting. FALSE is charging.
    private float fireRate = 1;//Used in bulletManager. shooting().
    private Boolean hasTarget = false;
    private Enemy target;

    private Texture bulletTexture;
    private float bulletRate;
    public float pause = 0.5f;


    //Creating a pool method thing.
    //Used for shooting. Used in enemyManager.shooting().
    private final Pool<Bullet> pool;
    private int bulletLimit = 1;
    private Array<Bullet> activeBullets;


    private float customArc;// used in bulletManager. It makes the bullet trajectory arc.
                           //Each tower should have a different arc.


    private Vector2 oldTargetPosition;//If enemy dies or goes out of range while bullet is traveling
    //remaining bullets will target this before resetting.
    //It is updated in TowerManager [checkRange(), checkForDead()] and EnemyManager [isDead()]


    public MainTower(String name){
        super(new Texture(name));
        hasTarget = false;
        ID = "";
        customArc = 25f;
        oldTargetPosition = new Vector2();
        activeBullets = new Array<Bullet>();
        pool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet(new Vector2(getLocation()), bulletTexture);
            }
        };
    }

    //Empty Constructor
    public MainTower(){

        hasTarget = false;
        ID = "";
        activeBullets = new Array<Bullet>();
        customArc = 25f;
        oldTargetPosition = new Vector2();
        pool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet(new Vector2(getLocation()), bulletTexture);
            }
        };


    }

    public void setCost(int newCost){cost = newCost;}
    public void setAttack(float newAttack){attack = newAttack; }
    public void setRange(float newRange){range = newRange;}
    public void setChargeRate(float newCharge){chargeRate = newCharge;}
    public void setIncomeRate(float newIncome) {incomeRate = newIncome;}
    public void setState(Boolean newState){state = newState;}
    public void setFireRate(float newFire){fireRate = newFire;}
    public void setTarget(Enemy newE){target = newE;}
    public void setHasTarget(Boolean t){hasTarget = t;}
    public void setID(String id){
        ID = id;
    }
    public void setBulletLimit(int b){bulletLimit = b;}
    public void setBulletTexture(Texture t){bulletTexture = t;}
    public void setBulletRate(float r){bulletRate = r;}
    public void setCustomArc(float c){customArc = c;}
    public void setOldTargetPosition(Vector2 d){oldTargetPosition = d;}


    public float getCost(){return cost;}
    public float getAttack(){return attack;}
    public float getRange(){return range;}
    public float getChargeRate(){return chargeRate;}
    public float getIncomeRate() {return incomeRate;}
    public Boolean getState(){return state;}
    public float getFireRate(){return fireRate;}
    public Enemy getTarget(){return target;}
    public Boolean getHasTarget(){return hasTarget;}
    public Pool<Bullet> getPool(){return pool;}
    public int getBulletLimit(){return bulletLimit;}
    public Array<Bullet> getActiveBullets(){return activeBullets;}
    public String getID(){return ID;}
    public Texture getBulletTexture(){return bulletTexture;}
    public float getBulletRate(){return bulletRate;}
    public float getCustomArc(){return customArc;}
    public Vector2 getOldTargetPosition(){return oldTargetPosition;}

    public String getMessage(){
        if(state)
            return "Charge";

        return "Attack";
    }


    //The bullet will spawn where this function returns..?
    public Vector2 getLocation(){
        return new Vector2(getX() + (this.getWidth()/2), getY()+ (this.getHeight()*2 / 3));
    }


    public void update() {
        // if you want to free dead bullets, returning them to the pool:
        Bullet item;
        int len = activeBullets.size;
        for (int i = len; --i >= 0;) {
            item = activeBullets.get(i);
            if (item.alive == false) {
                activeBullets.removeIndex(i);
                pool.free(item);
            }
        }
    }

    public void dispose(){
        getTexture().dispose();
        activeBullets.clear();
        pool.clear();
    }


}



//TODO: make spinning useful, somehow.  It's too cool to not have.
    /*private int degrees = 1;
    public void spinning(){
        degrees += 1;
        if (degrees % 360 == 0)
            degrees = 1;

        this.rotate(degrees);
    }*/
