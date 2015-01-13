package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Duck;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Towers.RogueTower;

import com.padisDefense.game.Towers.Tower;

public class RotateTest extends ScreenAdapter {

    final float w = Gdx.graphics.getWidth();
    final float h = Gdx.graphics.getHeight();
    Tower tower;
    Bullet bullet;

    Duck duck;
    SpriteBatch batch;
    Bezier<Vector2> path;
    Vector2 arbitraryPoint;
    Vector2 out, outD;
    double hypotenuse, opposite, hypoppo, arcSin, radian;
    boolean lockedOnTarget = false;
    float rotateDestination;

    double  radius;

    ShapeRenderer shape;

    public RotateTest(){
        tower = new RogueTower(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()*3/4), new Sprite(new Texture("towers/SNIPER_3.png")));
        //tower = new Sprite(new Texture("towers/strength_level_three.png"));
        //bullet = new Sprite(new Texture("redbullet.png"));
        bullet = new Bullet(new Vector2(tower.getBulletSpawnLocation()), new Texture("redbullet.png"));
        duck = new Duck();
        batch = new SpriteBatch();
        duck.setRate(0.003f);
        path = new Bezier<Vector2>();
       // path.set(new Vector2(0, Gdx.graphics.getHeight()/2),
        //        new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2));

        path.set(new Vector2(0,0), new Vector2(w/2, h*2/3),
                new Vector2(w, 0));

        out = new Vector2();
        outD = new Vector2();


        arbitraryPoint = new Vector2();



        bullet.setCenterLocation(tower.getCenterX(), tower.getCenterY() + tower.getHeight()/2);


        radius = findDistance(new Vector2(bullet.getX() + bullet.getWidth()/2, bullet.getY() + bullet.getHeight()/2),
                new Vector2(tower.getX() + tower.getWidth()/2, tower.getY() + tower.getHeight()/2));

        //System.out.println("Original radius: " + radius);

        double radius2 = findDistance(bullet.getLocation(), tower.getCenterPosition());

        //System.out.println("Using Location(): " + radius2);

        //tower.setRotation(-360);

        //System.out.println(tower.getWidth()+ " , " +  tower.getHeight());


        shape = new ShapeRenderer();
        System.out.println("Tower's Range = " + tower.getRange());
    }


    float counter = 0;
    float spinny = 2;
    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        duck.setTime(duck.getTime() + Gdx.graphics.getDeltaTime() / 5);
        path.valueAt(out, duck.getTime());
        path.derivativeAt(outD, duck.getTime());
        counter += Gdx.graphics.getDeltaTime();

        if(duck.getTime() >= 1f){
            if(duck.getTime() >= 1f && counter >= 7){
                duck.setTime(0f);
                tower.setPosition((float)Math.random()*(w*2/3) + w/3,
                        (float)Math.random()*(h*2/3) + h/3);
                counter = 0;
                spinny = -spinny;
            }
            else
                duck.setTime(0f);

        }


        duck.goTo(out);

        calcRotate(tower, duck);

        //tower.rotate(spinny);
        rotate(tower);
        bulletLocate();
        //tower.rotate(2);

        batch.begin();
        tower.draw(batch, 1);
        duck.draw(batch, 1);
        if(inRange())
            bullet.draw(batch, 1);

        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0,1,0,0));
        shape.setColor(0, 1, 0, 0.1f);
        shape.circle(tower.getCenterX(), tower.getCenterY(), tower.getRange());
        shape.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);



    }

    public boolean inRange(){
        return (findDistance(duck.getLocation(), tower.getLocation()) <= tower.getRange());
    }

    //The bullet's location will be relative to the tower's center point.
    //it's location should move accordingly to the tower's rotation value.
    public void bulletLocate(){

        float x, y, deltaX = 0, deltaY = 0;

        //currentRotation is modulus'd by 90.
        //convertedRotation converts the rotation to positive if it is negative.
        double currentRotation, convertedRotation;

        if(tower.getRotation() < 0){

            currentRotation = tower.getRotation() % 90;
            convertedRotation = 360 - (Math.abs(tower.getRotation())) % 360;
        }



        else{
            currentRotation = tower.getRotation() % 90;
            convertedRotation = Math.abs(tower.getRotation()) % 360;

        }




        currentRotation = Math.toRadians(currentRotation);




        //Assuming origin of the coordinate system is located at center of tower,
        //the if-elseif-else below checks if bullet should be quadrant II, III, IV, or I (in that order)
        //the cases where the rotation equals 90, 180, and 270 are checked first.

        if(convertedRotation == 0){

            deltaX = 0;
            deltaY = (float)radius;
        }
        else if(convertedRotation == 90){

            if(currentRotation > 0){
                deltaX = - (float)radius;
                deltaY = 0;
            }
            else{
                deltaX = - (float)radius;
                deltaY = 0;
            }
        }
        else if(convertedRotation == 180){
            if(currentRotation > 0){
                deltaX = 0;
                deltaY = - (float)radius;
            }
            else{
                deltaX = 0;
                deltaY = -(float)radius;
            }

        }

        else if(convertedRotation == 270){
            if(currentRotation > 0){
                deltaX = (float)radius;
                deltaY = 0;
            }
            else{
                deltaX = (float)radius;
                deltaY = 0;
            }

        }

        else if(convertedRotation == 360){
            deltaX = 0;
            deltaY = (float) radius;
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

        x = tower.getCenterX() + deltaX;
        y = tower.getCenterY() + deltaY;

        bullet.setCenterLocation(x,y);

        //System.out.println(tower.getCenterY() + "  " + deltaY + " ...Y suppose to be at: " + bullet.getY());

        //System.out.println(convertedRotation + " , " + bullet.getLocation());

        double d = findDistance(bullet.getLocation(), tower.getCenterPosition());
        //System.out.println("newRadius: " + d);

    }

    //TODO: make towers have different rotation speed???
    public void rotate(Tower t){
        if(tower.getRotation() != rotateDestination){
            if( t.getRotation() + 2 <= rotateDestination){
                t.rotate(2);

            }
            else if(t.getRotation() - 2 >= rotateDestination) {
                t.rotate(-2);
            }
        }



    }
    //controls rotation.
    //the location of 'arbitraryPoint' will make a right triangle
    //when connected with the enemy's location and tower's location.
    //so the distance between tower and enemy will always be the hypotenuse.
    //that makes calculating the tower's rotation easier.
    //sin(x) = hypotenuse/opposite.
    //change 'x' to radians, and we get our rotation value.
    //at no rotation the tower points up, because in the picture it points up.
    public void calcRotate(Tower t, Enemy d){
        //finding the hypotenuse.
        Vector2 tt = new Vector2(t.getX(), t.getY());
        Vector2 dd = new Vector2(d.getX(), d.getY());
        hypotenuse = findDistance(tt, dd);

        arbitraryPoint.set(t.getX(), d.getY());
        opposite = Math.abs(d.getX() - arbitraryPoint.x);

        //calculating.
        hypoppo = opposite/hypotenuse;

        arcSin = Math.asin(hypoppo);

        radian = arcSin*(180/Math.PI);

        //System.out.println(hypotenuse + " / " + hypotenuses + "......." + radian + " / " + radian2);
        if((t.getY() < d.getY()) && t.getX() != d.getX()){//tower is lower than enemy.
            if(d.getX() > arbitraryPoint.x) rotateDestination = -(float)radian;
            else rotateDestination = (float)radian;
        }

        else if((t.getY() > d.getY()) && t.getX() != d.getX()){// tower is higher than enemy.

            if(d.getX() > arbitraryPoint.x) rotateDestination = 180 + (float)radian;
            else rotateDestination = 180 - (float)radian;
        }

        else if(t.getY() == d.getY()){//tower is directly across enemy
            if(t.getX() < d.getX())
                rotateDestination = 90;
            else
                rotateDestination = 270;
        }
        else if(t.getX() == d.getX()){//tower is directly below/above enemy
            if(t.getY() < d.getY())
                rotateDestination = 0;
            else
                rotateDestination = 180;
        }
    }



    public double findDistance(Vector2 a, Vector2 b){
        float x2x1 = a.x - b.x;
        float y2y1 = a.y - b.y;
        return (Math.sqrt((x2x1 * x2x1) + (y2y1 * y2y1)));
    }
}
