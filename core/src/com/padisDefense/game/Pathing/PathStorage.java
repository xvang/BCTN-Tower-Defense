package com.padisDefense.game.Pathing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Stores all the generated path into one location.
 *
 * @author Xeng.
 *
 */
public class PathStorage {


    private Array<Array<Path<Vector2>>> allPaths;
    private Array<Path<Vector2>> path1, path2, path3, path4, path5;
    private final float w = Gdx.graphics.getWidth();
    private final float h = Gdx.graphics.getHeight();


    public PathStorage(){

        allPaths = new Array<Array<Path<Vector2>>>();
        path1 = new Array<Path<Vector2>>();
        path2 = new Array<Path<Vector2>>();
        path3 = new Array<Path<Vector2>>();
        path4 = new Array<Path<Vector2>>();
        path5 = new Array<Path<Vector2>>();

        /*path1.add(new Bezier<Vector2>(new Vector2(-60f, 5f), new Vector2(w - 10f, 30f), new Vector2(15f, h - 40f), new Vector2(w + 80f, h -  50f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 30f), new Vector2(w - 30f, 35f), new Vector2(30f, h - 50f), new Vector2(w + 80f, h -  60f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 40f), new Vector2(w - 40f, 50f), new Vector2(25f, h - 10f), new Vector2(w + 80f, h -  100f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 65f), new Vector2(w - 20f, 25f), new Vector2(30f, h - 25f), new Vector2(w + 80f, h -  70f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 85f), new Vector2(w - 25f, 45f), new Vector2(30f, h - 35f), new Vector2(w + 80f, h -  65f)));
*/
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 85f), new Vector2(w - 25f, 45f), new Vector2(30f, h - 35f), new Vector2(w + 80f, h -  65f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 85f), new Vector2(w - 25f, 45f), new Vector2(30f, h - 35f), new Vector2(w + 80f, h -  65f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 85f), new Vector2(w - 25f, 45f), new Vector2(30f, h - 35f), new Vector2(w + 80f, h -  65f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 85f), new Vector2(w - 25f, 45f), new Vector2(30f, h - 35f), new Vector2(w + 80f, h -  65f)));
        path1.add(new Bezier<Vector2>(new Vector2(-60f, 85f), new Vector2(w - 25f, 45f), new Vector2(30f, h - 35f), new Vector2(w + 80f, h -  65f)));

        path2.add(new Bezier<Vector2>(new Vector2(30f, h + 20f),new Vector2(w / 5, h/5), new Vector2(w + 40f, 40f)));
        path2.add(new Bezier<Vector2>(new Vector2(40f, h  + 30f),new Vector2(w / 8, h/8), new Vector2(w + 50f, 60f)));
        path2.add(new Bezier<Vector2>(new Vector2(-30f, h - 30f), new Vector2(w*4/5, h*4/5), new Vector2(w - 40f, - 40f)));
        path2.add(new Bezier<Vector2>(new Vector2(-30f, h - 45f), new Vector2(h*7/8, h*7/8), new Vector2(w - 50f,  - 50f)));
        path2.add(new Bezier<Vector2>(new Vector2(40f, h  + 30f),new Vector2(w / 8, h/8), new Vector2(w + 50f, 400f)));


        path3.add(new Bezier<Vector2>(new Vector2(w / 2, h + 50f), new Vector2(w - 50f, 3f)));
        path3.add(new Bezier<Vector2>(new Vector2(w / 2, h + 50f), new Vector2(w - 50f, 3f)));
        path3.add(new Bezier<Vector2>(new Vector2(w / 2, h + 50f), new Vector2(w - 50f, 3f)));
        path3.add(new Bezier<Vector2>(new Vector2(w / 2, h + 50f), new Vector2(w - 50f, 3f)));
        path3.add(new Bezier<Vector2>(new Vector2(w / 2, h + 50f), new Vector2(w - 50f, 3f)));


       /* path4.add(new Beziero<Vector2>(new Vector2(0f,0f),new Vector2(w, h),new Vector2(),new Vector2(),new Vector2()));
        path4.add(new Beziero<Vector2>(new Vector2(0f,0f),new Vector2(w, h),new Vector2(),new Vector2(),new Vector2()));
        path4.add(new Beziero<Vector2>(new Vector2(0f,0f),new Vector2(w, h),new Vector2(),new Vector2(),new Vector2()));
        path4.add(new Beziero<Vector2>(new Vector2(0f,0f),new Vector2(w, h),new Vector2(),new Vector2(),new Vector2()));
        path4.add(new Beziero<Vector2>(new Vector2(w,h),new Vector2(0,0),new Vector2(),new Vector2(),new Vector2()));*/

        path4.add(new Bezier<Vector2>(new Vector2(w/2, h/2),new Vector2(0, h)));
        path4.add(new Bezier<Vector2>(new Vector2(w/2, h/2),new Vector2(w, 0)));
        path4.add(new Bezier<Vector2>(new Vector2(w/2, h/2),new Vector2(w/2, w/2)));
        path4.add(new Bezier<Vector2>(new Vector2(w/2, h/2),new Vector2(w, h)));
        path4.add(new Bezier<Vector2>(new Vector2(w/2, h/2),new Vector2(0,0)));

        Random rand = new Random();
        path5.add(new Bezier<Vector2>(new Vector2(w/2, h / 2),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w+h,rand.nextFloat()*h+h)));
        path5.add(new Bezier<Vector2>(new Vector2(w/2, h / 2),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w-w,rand.nextFloat()*h-h)));
        path5.add(new Bezier<Vector2>(new Vector2(w/2, h / 2),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w+w,rand.nextFloat()*h+h)));
        path5.add(new Bezier<Vector2>(new Vector2(w/2, h / 2),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w+w,rand.nextFloat()*h-h)));
        path5.add(new Bezier<Vector2>(new Vector2(w/2, h / 2),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w,rand.nextFloat()*h),
                new Vector2(rand.nextFloat()*w-w,rand.nextFloat()*h+h)));


        allPaths.add(path1);
        allPaths.add(path2);
        allPaths.add(path3);
        allPaths.add(path4);
        allPaths.add(path5);
    }


    public Array<Path<Vector2>> getPath(int level){return allPaths.get(level);}


    public void dispose(){

    }
}
