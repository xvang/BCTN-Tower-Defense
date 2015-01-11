package com.padisDefense.game.Tests;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.BSpline;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;


public class PathRunTest extends ScreenAdapter{
    int SAMPLE_POINTS = 100;
    float SAMPLE_POINT_DISTANCE = 1f / SAMPLE_POINTS;
    float ZIGZAG_SCALE;

    final float w = Gdx.graphics.getWidth();
    final float h = Gdx.graphics.getHeight();

    Sprite background;
    SpriteBatch spriteBatch;
    ImmediateModeRenderer20 renderer;
    Sprite obj;
    //Sprite obj2;
    Array<Path<Vector2>> paths = new Array<Path<Vector2>>();
    int currentPath = 0;
    float t;
    float t2, t3;
    float zt;
    float speed = 0.6f;
    float speed2 = 0.1f;
    float zspeed = 1.0f;
    float wait = 0f;
    boolean zigzag = false;

    @Override
    public void show () {
        background = new Sprite(new Texture("tiles/path1.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new ImmediateModeRenderer20(false, false, 0);
        spriteBatch = new SpriteBatch();
        obj = new Sprite(new Texture(Gdx.files.internal("turto.png")));
        //obj2 = new Sprite(new Texture(Gdx.files.internal("goblin.png")));

        obj.setSize(40f, 40f);
        //obj2.setSize(40f, 40f);

        obj.setOriginCenter();
        obj.setOriginCenter();


        ZIGZAG_SCALE = Gdx.graphics.getDensity() * 24; // 48DP


        addPath();

        pathLength = paths.get(currentPath).approxLength(500);
        avg_speed = speed * pathLength;
    }

    final Vector2 tmpV = new Vector2();
    final Vector2 tmpV2 = new Vector2();
    final Vector2 tmpV3 = new Vector2();
    final Vector2 tmpV4 = new Vector2();

    float pathLength;
    float avg_speed;

    @Override
    public void render (float delta) {
        GL20 gl = Gdx.gl20;
        gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (wait > 0)
            wait -= Gdx.graphics.getDeltaTime();
        else {

            //System.out.println(Gdx.graphics.getDeltaTime());
            t += speed*2 * Gdx.graphics.getDeltaTime();
            t3 += speed2*Gdx.graphics.getDeltaTime();
            zt += zspeed * Gdx.graphics.getDeltaTime();
            while (t >= 1f) {
                currentPath = (currentPath + 1) % paths.size;
                pathLength = paths.get(currentPath).approxLength(500);
                avg_speed = speed * pathLength;

                t = 0f;
                t2 = 0f;
                t3 = 0f;


            }

            if(t3 >= 1f){
                t3 = 0f;
            }

            paths.get(currentPath).valueAt(tmpV, t);
            paths.get(currentPath).derivativeAt(tmpV2, t);

            paths.get(currentPath).derivativeAt(tmpV3, t2);

            t2 += speed * Gdx.graphics.getDeltaTime();


            paths.get(currentPath).valueAt(tmpV4, t3);
            //obj.setRotation(tmpV2.angle());


             tmpV2.nor();
             tmpV2.set(-tmpV2.y, tmpV2.x);
             tmpV2.scl((float)Math.sin(zt) * ZIGZAG_SCALE);
             tmpV.add(tmpV2);


            obj.setPosition(tmpV.x, tmpV.y);
            //obj2.setPosition(tmpV4.x, tmpV4.y);


        }

        for(int x=  0; x < paths.size; x++){
            renderer.begin(spriteBatch.getProjectionMatrix(), GL20.GL_LINE_STRIP);
            float val = 0f;
            while (val <= 1f) {
                renderer.color(0f, 0f, 0f, 1f);
                paths.get(x).valueAt(tmpV, val);
                renderer.vertex(tmpV.x, tmpV.y, 0);
                val += SAMPLE_POINT_DISTANCE;
            }
            renderer.end();
        }


        spriteBatch.begin();
        background.draw(spriteBatch);
        obj.draw(spriteBatch);
        //obj2.draw(spriteBatch);

        spriteBatch.end();
    }



    //paths
    public void addPath(){

    }
}

/*






 Array<Path<Vector2>> path4 = new Array<Path<Vector2>>();
 //straight









 Array<Path<Vector2>> path6 = new Array<Path<Vector2>>();




 Array<Path<Vector2>> path7 = new Array<Path<Vector2>>();



 Array<Path<Vector2>> path8 = new Array<Path<Vector2>>();



 Array<Path<Vector2>> path9 = new Array<Path<Vector2>>();




 */