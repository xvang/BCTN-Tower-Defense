package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.padisDefense.game.Enemies.BlueSpider;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Pathing.PathStorage;

/**
 * Created by Toog on 12/26/2014.
 */
public class TEST4 extends ScreenAdapter {

    public final float w = Gdx.graphics.getWidth();
    public final float h = Gdx.graphics.getHeight();


    Array<Enemy> activeEnemy;
    Pool<Enemy> enemyPool;
    PathStorage storage;

    Array<Path<Vector2>> path;
    ImmediateModeRenderer20 renderer20;
    SpriteBatch batch;

    @Override
    public void show(){

        activeEnemy = new Array<Enemy>();
        enemyPool = new Pool<Enemy>() {
            @Override
            protected Enemy newObject() {
                return new BlueSpider();
            }

            protected Enemy newCustomObject(int type){

                return new BlueSpider();
            }
        };


        addEnemies();
        storage = new PathStorage();

        path = new Array<Path<Vector2>>();
        addPaths();
        renderer20 = new ImmediateModeRenderer20(false, false, 0);
        batch = new SpriteBatch();

        //System.out.println("sin(0) = "+ Math.sin(0f) + "  sin(1f) = " + Math.sin(1f) + "     sin(2f) = " + Math.sin(2f) + "   sin(1.5f) = " + Math.sin(1.5f) + "     sin(10f) = " + Math.sin(10f));


    }

    public void update() {
        // if you want to free dead bullets, returning them to the pool:
        Enemy item;
        int len = activeEnemy.size;
        for (int i = len; --i >= 0;) {
            item = activeEnemy.get(i);
            if (item.alive == false) {
                activeEnemy.removeIndex(i);
                enemyPool.free(item);
            }
        }
    }


    float count = 0;
    Vector2 tmpV = new Vector2();
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        count += Gdx.graphics.getDeltaTime();

        if(count >= 15f && activeEnemy.size < 30){
            count = 0;
            addEnemies();
        }

        //check();
        batch.begin();
        run();
        batch.end();


        renderer20.begin(batch.getProjectionMatrix(), GL20.GL_LINE_STRIP);
        float val = 0f;
        for(int x = 0; x < path.size; x++){
            while (val <= 1f) {
                renderer20.color(0f, 0f, 0f, 1f);
                path.get(x).valueAt(tmpV, val);
                renderer20.vertex(tmpV.x, tmpV.y, 0);
                val += Gdx.graphics.getDeltaTime();
            }
            renderer20.end();

           val = 0f;
        }


    }


    public void run(){
        Vector2 position = new Vector2();
        Vector2 position2 = new Vector2();
        Enemy currentEnemy;
        float time;
        for(int x = 0; x < activeEnemy.size; x++){
            currentEnemy = activeEnemy.get(x);


            time = currentEnemy.getTime() + (Gdx.graphics.getDeltaTime() * currentEnemy.getRate());
            currentEnemy.setTime(time);

            path.get(currentEnemy.getCurrentPath()).derivativeAt(position2, currentEnemy.getTime());

            time = currentEnemy.getTime()+ (currentEnemy.getRate()*Gdx.graphics.getDeltaTime())/position2.len();
            currentEnemy.setTime(time);

            path.get(currentEnemy.getCurrentPath()).valueAt(position, currentEnemy.getTime());


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

            if (activeEnemy.get(x).getTime() >= 1f){
                if(activeEnemy.get(x).getCurrentPath()+1 < path.size){
                    activeEnemy.get(x).setCurrentPath(activeEnemy.get(x).getCurrentPath()+1);
                    //enemy.get(x).setStrayAmount(0f);
                }

                else
                    activeEnemy.get(x).setCurrentPath(0);
                activeEnemy.get(x).setTime(0f);

            }

            currentEnemy.goTo(position);
            currentEnemy.draw(batch, 1);

        }
    }

    public void addEnemies(){

        Enemy e = enemyPool.obtain();
        e.init(-50f,0);
        e.setTime(0f);
        e.setCurrentPath(0);
        activeEnemy.add(e);

        /*for(int x = 0; x < 5;x++){
            activeEnemy.add(new BlueSpider());
            //System.out.println("Wait: " + enemy.get(x).getWait());
        }*/
    }

    public void addPaths() {

        //Path 1
        path.add(new Bezier<Vector2>(new Vector2(-50f, h/10), new Vector2(w/5, h/10)));

        path.add(new Bezier<Vector2>(new Vector2(w/5, h/10), new Vector2(w/3, h/10),
                new Vector2(w/3, h/4)));

        path.add(new Bezier<Vector2>(new Vector2(w/3, h/4), new Vector2(w/3, h/2)));

        path.add(new Bezier<Vector2>(new Vector2(w/3, h/2), new Vector2(w/3, h*5/6)));

        path.add(new Bezier<Vector2>(new Vector2(w/3, h*5/6), new Vector2(w/3, h*12/13),
                new Vector2(w/2, h*12/13)));

        path.add(new Bezier<Vector2>(new Vector2(w/2, h*12/13), new Vector2(w*2/3, h*12/13),
                new Vector2(w*2/3, h*5/6)));

        path.add(new Bezier<Vector2>(new Vector2(w*2/3, h*5/6), new Vector2(w*2/3, h/2)));

        path.add(new Bezier<Vector2>(new Vector2(w*2/3, h/2), new Vector2(w*2/3, h/3),
                new Vector2(w*3/4, h/3)));

        path.add(new Bezier<Vector2>(new Vector2(w*3/4, h/3), new Vector2(w*11/12, h/3)));
        path.add(new Bezier<Vector2>(new Vector2(w*11/12, h/3), new Vector2(w+50f, h/3)));


    }
}


/*
 * path.add(new Bezier<Vector2>(new Vector2(), new Vector2()));
 * path.add(new Bezier<Vector2>(new Vector2(),
 new Vector2(), new Vector2()));
 path.add(new Bezier<Vector2>(new Vector2(),
 new Vector2(), new Vector2()));
 * */