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
import com.padisDefense.game.Enemies.BiggerGoblin;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Pathing.PathStorage;

/**
 * Created by Toog on 12/26/2014.
 */
public class TEST4 extends ScreenAdapter {

    public final float w = Gdx.graphics.getWidth();
    public final float h = Gdx.graphics.getHeight();


    Array<Enemy> activeEnemy;

    PathStorage storage;

    Array<Path<Vector2>> path;
    ImmediateModeRenderer20 renderer20;
    SpriteBatch batch;

    @Override
    public void show(){

        activeEnemy = new Array<Enemy>();
        addEnemies();
        storage = new PathStorage();

        path = new Array<Path<Vector2>>();
        addPaths();
        renderer20 = new ImmediateModeRenderer20(false, false, 0);
        batch = new SpriteBatch();

        //System.out.println("sin(0) = "+ Math.sin(0f) + "  sin(1f) = " + Math.sin(1f) + "     sin(2f) = " + Math.sin(2f) + "   sin(1.5f) = " + Math.sin(1.5f) + "     sin(10f) = " + Math.sin(10f));


    }


    float count = 0;
    Vector2 tmpV = new Vector2();
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        count += Gdx.graphics.getDeltaTime();

        if(count >= 15f && activeEnemy.size < 50){
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
        for(int x = 0; x < 5;x++){
            activeEnemy.add(new BiggerGoblin());
            //System.out.println("Wait: " + enemy.get(x).getWait());
        }
    }

    public void addPaths(){


        //straight
        path.add(new Bezier<Vector2>(new Vector2(-50f, h*9/10), new Vector2(w/5, h*9/10)));
        path.add(new Bezier<Vector2>(new Vector2(w/5, h*9/10), new Vector2(w*5/12, h*9/10)));
        path.add(new Bezier<Vector2>(new Vector2(w*5/12, h*9/10), new Vector2(w*2/3, h*9/10)));

        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w*2/3, h*9/10), new Vector2(w*5/6, h*9/10),
                new Vector2(w*5/6, h*3/4)));

        //Straight
        path.add(new Bezier<Vector2>(new Vector2(w*5/6, h*3/4), new Vector2(w*5/6, h*2/5)));
        path.add(new Bezier<Vector2>(new Vector2(w*5/6, h*2/5), new Vector2(w*5/6, h/5)));


        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w*5/6, h/5), new Vector2(w*5/6, h/12),
                new Vector2(w*3/4, h/12)));

        //Straight
        path.add(new Bezier<Vector2>(new Vector2(w*3/4, h/12), new Vector2(w/2, h/12)));

        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w/2, h/12), new Vector2(w*2/5, h/12),
                new Vector2(w*2/5, h/4)));


        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w*2/5, h/4), new Vector2(w*2/5, h*2/5),
                new Vector2(w/2, h*2/5)));

        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w/2, h*2/5), new Vector2(w*3/5, h*2/5),
                new Vector2(w*3/5, h/2)));

        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w*3/5, h/2), new Vector2(w*3/5, h*7/10),
                new Vector2(w*7/20, h*7/10)));

        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w*7/20, h*7/10), new Vector2(w/8, h*7/10),
                new Vector2(w/8, h/2)));

        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w/8, h/2), new Vector2(w/8, h*2/5),
                new Vector2(w/4, h*2/5)));

        //Curve
        path.add(new Bezier<Vector2>(new Vector2(w/4, h*2/5), new Vector2(w/3, h*2/5),
                new Vector2(w/3, h*1/5)));

        //Curve

        path.add(new Bezier<Vector2>(new Vector2(w/3, h*1/5), new Vector2(w/3, h/12),
                new Vector2(w/5, h/12)));

        path.add(new Bezier<Vector2>(new Vector2(w/5, h/12), new Vector2(-50f, h/12)));
    }
}


/*
 * path.add(new Bezier<Vector2>(new Vector2(), new Vector2()));
 * path.add(new Bezier<Vector2>(new Vector2(),
 new Vector2(), new Vector2()));
 path.add(new Bezier<Vector2>(new Vector2(),
 new Vector2(), new Vector2()));
 * */