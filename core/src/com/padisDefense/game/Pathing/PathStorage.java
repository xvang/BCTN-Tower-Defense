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
    private final float w = Gdx.graphics.getWidth();
    private final float h = Gdx.graphics.getHeight();


    public PathStorage(){

        allPaths = new Array<Array<Path<Vector2>>>();


        //Level 0
        Array<Path<Vector2>> path1 = new Array<Path<Vector2>>();
        path1.add(new Bezier<Vector2>(new Vector2(-50f, h/6),
                new Vector2(w/2, h/8), new Vector2(w*3/5, h*2/3)));
        path1.add(new Bezier<Vector2>(new Vector2(w*3/5, h*2/3),
                new Vector2(w*7/10, h), new Vector2(w + 50f, h*5/6)));


        //Level 1
        Array<Path<Vector2>> path2 = new Array<Path<Vector2>>();
        path2.add(new Bezier<Vector2>(new Vector2(-50f, h/6),
                new Vector2(w/2, h/8), new Vector2(w*3/5, h*2/3)));

        path2.add(new Bezier<Vector2>(new Vector2(w*3/5, h*2/3),
                new Vector2(w*7/10, h), new Vector2(w + 50f, h*5/6)));

        path2.add(new Bezier<Vector2>(new Vector2(-50f, h*5/6),
                new Vector2(w/2, h*7/8), new Vector2(w*3/5, h*1/3)));

        path2.add(new Bezier<Vector2>(new Vector2(w*3/5, h*1/3),
                new Vector2(w*7/10, 0), new Vector2(w + 50f, h*1/6)));


        //Level 2
        Array<Path<Vector2>> path3 = new Array<Path<Vector2>>();
        path3.add(new Bezier<Vector2>(new Vector2(-50f, h*4/5), new Vector2(w/12, h*4/5)));

        path3.add(new Bezier<Vector2>(new Vector2(w/12, h*4/5), new Vector2(w/6, h*4/5),
                new Vector2(w/6, h*5/8)));

        path3.add(new Bezier<Vector2>(new Vector2(w/6, h*5/8), new Vector2(w/6, h/2)));

        path3.add(new Bezier<Vector2>(new Vector2(w/6, h/2), new Vector2(w/6, h/4)));

        path3.add(new Bezier<Vector2>(new Vector2(w/6, h/4), new Vector2(w/6, h/8),
                new Vector2(w/4, h/8)));

        path3.add(new Bezier<Vector2>(new Vector2(w/4, h/8), new Vector2(w/3, h/8)));

        path3.add(new Bezier<Vector2>(new Vector2(w/3, h/8), new Vector2(w*5/12, h/8),
                new Vector2(w*5/12, h/4)));

        path3.add(new Bezier<Vector2>(new Vector2(w*5/12, h/4), new Vector2(w*5/12, h/2)));
        path3.add(new Bezier<Vector2>(new Vector2(w*5/12, h/2), new Vector2(w*5/12, h*2/3)));

        path3.add(new Bezier<Vector2>(new Vector2(w*5/12, h*2/3), new Vector2(w*5/12, h*4/5),
                new Vector2(w/2, h*4/5)));

        path3.add(new Bezier<Vector2>(new Vector2(w/2, h*4/5), new Vector2(w*3/5, h*4/5)));

        path3.add(new Bezier<Vector2>(new Vector2(w*3/5, h*4/5), new Vector2(w*2/3, h*4/5),
                new Vector2(w*2/3, h*2/3)));

        path3.add(new Bezier<Vector2>(new Vector2(w*2/3,h*2/3), new Vector2(w*2/3, h/2)));
        path3.add(new Bezier<Vector2>(new Vector2(w*2/3,h/2), new Vector2(w*2/3, h/3)));
        path3.add(new Bezier<Vector2>(new Vector2(w*2/3,h/3), new Vector2(w*2/3, h/6)));
        path3.add(new Bezier<Vector2>(new Vector2(w*2/3,h/6), new Vector2(w*2/3, -50f)));




        Array<Path<Vector2>> path4 = new Array<Path<Vector2>>();





        Random rand = new Random();
        Array<Path<Vector2>> path5 = new Array<Path<Vector2>>();
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




        Array<Path<Vector2>> path6 = new Array<Path<Vector2>>();
        Array<Path<Vector2>> path7 = new Array<Path<Vector2>>();
        Array<Path<Vector2>> path8 = new Array<Path<Vector2>>();
        Array<Path<Vector2>> path9 = new Array<Path<Vector2>>();







        allPaths.add(path1);
        allPaths.add(path2);
        allPaths.add(path3);
        allPaths.add(path4);
        allPaths.add(path5);
        allPaths.add(path6);
        allPaths.add(path7);
        allPaths.add(path8);
        allPaths.add(path9);

    }


    public Array<Path<Vector2>> getPath(int level){return allPaths.get(level);}




    public void dispose(){

    }
}


/**
 *
 //big loop
 Array<Path<Vector2>> path50 = new Array<Path<Vector2>>();
 path50.add(new Bezier<Vector2>(new Vector2(-50f, h/8),
 new Vector2(w*6/10, h/8), new Vector2(w*4/5, h*1/3)));

 path50.add(new Bezier<Vector2>(new Vector2(w*4/5, h*1/3),
 new Vector2(w, h/2), new Vector2(w*4/5, h*2/3)));

 path50.add(new Bezier<Vector2>(new Vector2(w*4/5, h*2/3),
 new Vector2(w/2, h), new Vector2(w*1/5, h*2/3)));

 path50.add(new Bezier<Vector2>(new Vector2(w*1/5, h*2/3),
 new Vector2(0, h/2), new Vector2(w*1/5, h*1/3)));

 path50.add(new Bezier<Vector2>(new Vector2(w*1/5, h*1/3),
 new Vector2(w*6/10, h/8), new Vector2(w+50f, h/8)));
 * */