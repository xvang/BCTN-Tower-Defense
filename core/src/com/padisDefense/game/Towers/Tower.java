package com.padisDefense.game.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.MiscellaniousCharacters.IceSparkle;


/**
 * The main tower class. All other towers will extend from this one.
 *
 * @author Xeng
 *
 * */
public class Tower extends Sprite{


    private String ID;
    private float cost = 1;
    private float attack = 1;
    private float range = 1;
    private float chargeRate = 1;//Used in gameScreen. gatherCharge().
    private float incomeRate = 1;//unused for now.
    public boolean state = true;//TRUE is shooting. FALSE is charging.
    private float fireRate = 1;//Used in bulletManager. shooting(). How often a new Bullet is created.
    public Boolean hasTarget = false;
    private Enemy target;
    private int upgradeCost = 1;
    private Array<String> weakAgainst;
    private Array<String> strongAgainst;

    private Texture bulletTexture;
    private float bulletRate;//How fast a bullet travels.
    public float pause = 0.2f;
    private int level = 1;


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


    public float rotateDestination;
    public boolean lockedOnTarget = false;
    public float radius = this.getHeight()/2;


    public IceSparkle sparkle;

    public boolean clicked = false;//used to display the range circle.
    //Constructor #1
    public Tower(Texture picture){
        super(picture);
        hasTarget = false;
        ID = "";
        customArc = 25f;
        oldTargetPosition = new Vector2();
        activeBullets = new Array<Bullet>();

        weakAgainst = new Array<String>();
        strongAgainst = new Array<String>();

        pool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet(new Vector2(getLocation()), bulletTexture);
            }
        };

        sparkle = new IceSparkle();
    }

    //Constructor #2
    public Tower(){

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


        sparkle = new IceSparkle();
    }

    public Tower( Sprite sprite){
        super(sprite);
        this.setSize(sprite.getWidth(), sprite.getHeight());
        hasTarget = false;
        ID = "";
        customArc = 25f;
        oldTargetPosition = new Vector2();
        activeBullets = new Array<Bullet>();

        weakAgainst = new Array<String>();
        strongAgainst = new Array<String>();

        pool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return new Bullet(new Vector2(getLocation()), bulletTexture);
            }
        };


        sparkle = new IceSparkle();
    }

    public void setCost(int newCost){cost = newCost;}
    public void setAttack(float newAttack){attack = newAttack; }
    public void setRange(float newRange){range = newRange;}
    public void setChargeRate(float newCharge){chargeRate = newCharge;}
    public void setIncomeRate(float newIncome) {incomeRate = newIncome;}

    public void setFireRate(float newFire){fireRate = newFire;}
    public void setTarget(Enemy newE){target = newE;}

    public void setID(String id){
        ID = id;
    }
    public void setBulletLimit(int b){bulletLimit = b;}
    public void setBulletTexture(Texture t){bulletTexture = t;}
    public void setBulletRate(float r){bulletRate = r;}
    public void setCustomArc(float c){customArc = c;}
    public void setOldTargetPosition(Vector2 d){oldTargetPosition = d;}
    public void setUpgradeCost(int n){upgradeCost = n;}
    public void setWeakAgainst(String... s){
        for(int x = 0; x <s.length; x++)
            weakAgainst.add(s[x]);
    }
    public void setStrongAgainst(String... s){
        for(int x = 0; x <s.length; x++)
            strongAgainst.add(s[x]);
    }
    public void setLevel(int i){level = i;}






    public float getCost(){return cost;}
    public float getAttack(){return attack;}
    public float getRange(){return range;}
    public float getChargeRate(){return chargeRate;}
    public float getIncomeRate() {return incomeRate;}

    public float getFireRate(){return fireRate;}
    public Enemy getTarget(){return target;}

    public Pool<Bullet> getPool(){return pool;}
    public int getBulletLimit(){return bulletLimit;}
    public Array<Bullet> getActiveBullets(){return activeBullets;}
    public String getID(){return ID;}
    public Texture getBulletTexture(){return bulletTexture;}
    public float getBulletRate(){return bulletRate;}
    public float getCustomArc(){return customArc;}
    public Vector2 getOldTargetPosition(){return oldTargetPosition;}
    public Array<String> getWeakAgainst(){return weakAgainst;}
    public Array<String> getStrongAgainst(){return strongAgainst;}
    public int getUpgradeCost(){return upgradeCost;}
    public int getLevel(){return level;}

    public String getMessage(){
        if(state)
            return "Charge";

        return "Attack";
    }



    public Vector2 getLocation(){
        return new Vector2(getX() + (this.getWidth()/2), getY()+ (this.getHeight()*2 / 3));
    }

    public Vector2 getSparkleLocation(){
        return new Vector2(getX() - getWidth(), getY() - getHeight());
    }

    //This function calculates the bullet's spawn location relative to the tower.
    //bullet should spawn where the "gun" on the tower is pointed.
    //new bullet location is calculated using SOH-CAH-TOA. i didn't use TOA, so maybe just SOH-CAH.
    public Vector2 getBulletSpawnLocation(){

        float x, y, deltaX = 0, deltaY = 0;

        double currentRotation, convertedRotation;

        if(getRotation() < 0){
            currentRotation = getRotation() % 90;
            convertedRotation = 360 - (Math.abs(getRotation())) % 360;
        }

        else{
            currentRotation = getRotation() % 90;
            convertedRotation = Math.abs(getRotation()) % 360;
        }

        currentRotation = Math.toRadians(currentRotation);

        //Assuming origin of the coordinate system is located at center of tower,
        //the if-elseif-else below checks if bullet should be quadrant II, III, IV, or I (in that order)
        //the cases where the rotation equals 90, 180, 270, and 360 are checked first.
        if(convertedRotation == 0){

            deltaX = 0;
            deltaY = radius;
        }
        else if(convertedRotation == 90){

            if(currentRotation > 0){
                deltaX = - radius;
                deltaY = 0;
            }
            else {
                deltaX = -radius;
                deltaY = 0;
            }
        }
        else if(convertedRotation == 180){
            if(currentRotation > 0){
                deltaX = 0;
                deltaY = - radius;
            }
            else{
                deltaX = 0;
                deltaY = -radius;
            }
        }

        else if(convertedRotation == 270){
            if(currentRotation > 0){
                deltaX = radius;
                deltaY = 0;
            }
            else{
                deltaX = radius;
                deltaY = 0;
            }
        }

        else if(convertedRotation == 360){
            deltaX = 0;
            deltaY = radius;
        }
        else if(convertedRotation < 90){

            if(currentRotation > 0){
                deltaX = -(float)(Math.sin(currentRotation)*radius);
                deltaY = (float)(Math.cos(currentRotation)*radius);
            }
            else{
                deltaX = - (float)(Math.cos(currentRotation)*radius);
                deltaY = -(float)(Math.sin(currentRotation)*radius);
            }
        }

        else if( convertedRotation < 180){
            if(currentRotation > 0){
                deltaX = - (float)(Math.cos(currentRotation)*radius);
                deltaY = - (float)(Math.sin(currentRotation)*radius);
            }
            else{
                deltaX = (float)(Math.sin(currentRotation)*radius);
                deltaY =  - (float)(Math.cos(currentRotation)*radius);
            }
        }

        else if ( convertedRotation < 270){
            if(currentRotation > 0){
                deltaX =  (float)(Math.sin(currentRotation)*radius);
                deltaY = - (float)(Math.cos(currentRotation)*radius);
            }
            else{
                deltaX = (float)(Math.cos(currentRotation)*radius);
                deltaY =   (float)(Math.sin(currentRotation)*radius);
            }
        }

        else if( convertedRotation < 360){
            if(currentRotation > 0){
                deltaX = (float)(Math.cos(currentRotation)*radius);
                deltaY = (float)(Math.sin(currentRotation)*radius);
            }
            else{
                deltaX = -(float)(Math.sin(currentRotation)*radius);
                deltaY = (float)(Math.cos(currentRotation)*radius);
            }
        }

        x = getCenterX() + deltaX;
        y = getCenterY() + deltaY;

        return new Vector2(x,y);
    }

    public void customRotate(){
        if(getRotation() != rotateDestination){
            if( getRotation() + 2 <= rotateDestination){
                rotate(2);

            }
            else if(getRotation() - 2 >= rotateDestination) {
                rotate(-2);
            }
        }
    }

    public void update() {
        // if you want to free dead bullets, returning them to the pool:
        Bullet item;
        int len = activeBullets.size;
        for (int i = len; --i >= 0;) {
            item = activeBullets.get(i);
            if (!item.alive) {
                activeBullets.removeIndex(i);
                pool.free(item);
            }
        }
    }

    public Vector2 getCenterPosition(){return new Vector2(this.getX() + this.getWidth()/2,
            this.getY() + this.getHeight()/2);}

    public float getCenterX(){return this.getX() + this.getWidth()/2;}
    public float getCenterY(){return this.getY() + this.getHeight()/2;}

    public void print(){
        System.out.print(ID);
    }



    public void dispose(){
        //getTexture().dispose();
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
