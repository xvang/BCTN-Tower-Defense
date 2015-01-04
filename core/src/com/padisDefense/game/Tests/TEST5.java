package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.BlueSpider;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.IronSpider;
import com.padisDefense.game.Enemies.RedSpider;
import com.padisDefense.game.Pathing.PathStorage;

/**
 * What I did:
 *
 * added the animation stuff to enemy class. made 2 functions: initMovement, animate()
 * pizza and soda are identical, but pizza is not working for some reason.
 * will find out why.
 *
 * */
public class TEST5 extends ScreenAdapter {


    Array<Path<Vector2>> path;
    PathStorage storage;

    SpriteBatch batch;
    Array<Enemy> enemyArray;
    Enemy currentEnemy;
    @Override
    public void show(){
        storage = new PathStorage();
        batch = new SpriteBatch();
        path = storage.getPath(0);
        enemyArray = new Array<Enemy>();
        enemyArray.add(new IronSpider());
        enemyArray.add(new RedSpider());
        enemyArray.add(new BlueSpider());


    }

    @Override
    public void render(float delta){

        Gdx.gl20.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        run();
        batch.end();
    }



    public void run(){
        Vector2 position = new Vector2();
        Vector2 position2 = new Vector2();
        float time;

        for(int x = 0; x < enemyArray.size; x++){
            currentEnemy = enemyArray.get(x);
            time = currentEnemy.getTime() + (Gdx.graphics.getDeltaTime() * currentEnemy.getRate());
            currentEnemy.setTime(time);

            path.get(currentEnemy.getCurrentPath()).derivativeAt(position2, currentEnemy.getTime());

            time = currentEnemy.getTime()+ (currentEnemy.getRate()*Gdx.graphics.getDeltaTime())/position2.len();
            currentEnemy.setTime(time);

            path.get(currentEnemy.getCurrentPath()).valueAt(position, currentEnemy.getTime());

            //the newPosition is set here because
            //we don't want to calculate in straying from the path.
            currentEnemy.newPosition.set(position);

            //should only run once to initialize the old position at
            //enemy's initial position, instead of at (0,0)
            //TODO: find a way around this. Anything that is "do once only" should not exist...?
            if(currentEnemy.oldPosition.x == 0 && currentEnemy.oldPosition.y == 0){
                currentEnemy.oldPosition.set(position);
            }


            //If waiting period is over, then enemy can start swerving up and down path.
            if(currentEnemy.getWait()<= 0f){
                currentEnemy.setStrayAmount(currentEnemy.getStrayAmount()+(Gdx.graphics.getDeltaTime()/2f));

                position2.nor();
                position2.set(-position2.y, position2.x);
                position2.scl((float)(Math.sin(currentEnemy.getStrayAmount())) * 20f);
                position.add(position2);
            }

            else if(currentEnemy.getWait() > 0f) {
                currentEnemy.setWait(currentEnemy.getWait() - Gdx.graphics.getDeltaTime());
            }

            if (currentEnemy.getTime() >= 1f){//Reached end of path or path segment.
                if(currentEnemy.getCurrentPath()+1 < path.size){
                    currentEnemy.setCurrentPath(currentEnemy.getCurrentPath() + 1);
                    //enemy.get(x).setStrayAmount(0f);
                }

                else{//Reached end of path.
                    currentEnemy.setCurrentPath(0);
                    currentEnemy.stateTime = 0f;
                    currentEnemy.oldPosition.set(0,0);
                    currentEnemy.newPosition.set(0,0);
                }

                currentEnemy.setTime(0f);


            }

            currentEnemy.goTo(position);
            currentEnemy.animate(batch);
            currentEnemy.displayHealth(batch);
        }


    }
}
