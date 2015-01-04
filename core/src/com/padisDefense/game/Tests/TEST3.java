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

/** @author Xoppa */
public class TEST3 extends ScreenAdapter{
    int SAMPLE_POINTS = 100;
    float SAMPLE_POINT_DISTANCE = 1f / SAMPLE_POINTS;
    float ZIGZAG_SCALE;

    final float w = Gdx.graphics.getWidth();
    final float h = Gdx.graphics.getHeight();

    SpriteBatch spriteBatch;
    ImmediateModeRenderer20 renderer;
    Sprite obj;
    Sprite obj2;
    Array<Path<Vector2>> paths = new Array<Path<Vector2>>();
    int currentPath = 0;
    float t;
    float t2, t3;
    float zt;
    float speed = 0.2f;
    float speed2 = 0.1f;
    float zspeed = 1.0f;
    float wait = 0f;
    boolean zigzag = false;

    @Override
    public void show () {
        renderer = new ImmediateModeRenderer20(false, false, 0);
        spriteBatch = new SpriteBatch();
        obj = new Sprite(new Texture(Gdx.files.internal("sluggo.png")));
        obj2 = new Sprite(new Texture(Gdx.files.internal("lobbo.png")));

        obj.setSize(40f, 40f);
        obj2.setSize(40f, 40f);

        obj.setOriginCenter();
        obj.setOriginCenter();


        ZIGZAG_SCALE = Gdx.graphics.getDensity() * 48; // 48DP

        //paths.add(new Bezier<Vector2>(new Vector2(0,0), new Vector2(w/2, 2*h), new Vector2(w, 0)));
        paths.add(new Bezier<Vector2>(new Vector2(-50f, h/6), new Vector2(w/2, h/8), new Vector2(w*3/5, h*2/3)));
        paths.add(new Bezier<Vector2>(new Vector2(w*3/5, h*2/3), new Vector2(w*7/10, h), new Vector2(w + 50f, h*5/6)));
        //paths.add(new Bezier<Vector2>( new Vector2(w*2/3, h*8/16),new Vector2(w*2/3, h*14/16),new Vector2(w+50f, h*12/16)));
        //paths.add(new Bezier<Vector2>(new Vector2(w/3, h/2), new Vector2(w/2, h/10), new Vector2(w*0.8f, h/5)));
        //paths.add(new Bezier<Vector2>(new Vector2(w*0.8f, h/5), new Vector2(w+50f, h/2)));
        /*paths.add(new Bezier<Vector2>(new Vector2(-50f, 50), new Vector2(100f, 50f)));
        paths.add(new Bezier<Vector2>(new Vector2(100f, 100f), new Vector2(150f,10f), new Vector2(200f, 222f)));
        paths.add(new Bezier<Vector2>(new Vector2(200f, 222f), new Vector2(350f, 500f), new Vector2(400f, 50f), new Vector2(550f, h-50f)));


        paths.add(new Bezier<Vector2>(new Vector2(550f, h-50f), new Vector2(600f, 300f), new Vector2(650f, 100f)));
        Vector2 cp[] = new Vector2[] {new Vector2(0, 0), new Vector2(w * 0.25f, h * 0.5f), new Vector2(0, h),
                new Vector2(w * 0.5f, h * 0.75f), new Vector2(w, h), new Vector2(w * 0.75f, h * 0.5f), new Vector2(w, 0),
                new Vector2(w * 0.5f, h * 0.25f)};
        paths.add(new BSpline<Vector2>(cp, 3, true));

        paths.add(new CatmullRomSpline<Vector2>(cp, true));*/

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

            System.out.println(Gdx.graphics.getDeltaTime());
            t += speed * Gdx.graphics.getDeltaTime();
            t3 += speed2*Gdx.graphics.getDeltaTime();
            zt += zspeed * Gdx.graphics.getDeltaTime();
            while (t >= 1f) {
                currentPath = (currentPath + 1) % paths.size;
                pathLength = paths.get(currentPath).approxLength(500);
                avg_speed = speed * pathLength;

                t = 0f;
                t2 = 0f;


            }

            if(t3 >= 1f){
                t3 = 0f;
            }

            paths.get(currentPath).valueAt(tmpV, t);
            paths.get(currentPath).derivativeAt(tmpV2, t);

            paths.get(currentPath).derivativeAt(tmpV3, t2);

            t2 += speed * Gdx.graphics.getDeltaTime();


            paths.get(currentPath).valueAt(tmpV4, t3);
            obj.setRotation(tmpV2.angle());


             tmpV2.nor();
             tmpV2.set(-tmpV2.y, tmpV2.x);
             tmpV2.scl((float)Math.sin(zt) * ZIGZAG_SCALE);
             tmpV.add(tmpV2);


            obj.setPosition(tmpV.x, tmpV.y);
            obj2.setPosition(tmpV4.x, tmpV4.y);


        }

        renderer.begin(spriteBatch.getProjectionMatrix(), GL20.GL_LINE_STRIP);
        float val = 0f;
        while (val <= 1f) {
            renderer.color(0f, 0f, 0f, 1f);
            paths.get(currentPath).valueAt(tmpV, val);
            renderer.vertex(tmpV.x, tmpV.y, 0);
            val += SAMPLE_POINT_DISTANCE;
        }
        renderer.end();

        spriteBatch.begin();
        obj.draw(spriteBatch);
        obj2.draw(spriteBatch);
        spriteBatch.end();
    }


}