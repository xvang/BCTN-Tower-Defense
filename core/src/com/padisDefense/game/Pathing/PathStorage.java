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
        init();
    }


    public Array<Path<Vector2>> getPath(int level){

        return allPaths.get(level);


    }



    public void init(){


        Array<Path<Vector2>> path1 = new Array<Path<Vector2>>();
        path1.add(new Bezier<Vector2>(new Vector2(w+100f, h*5/6), new Vector2(w*9/10, h*5/6)));
        path1.add(new Bezier<Vector2>(new Vector2(w*9/10, h*5/6), new Vector2(w*3/5, h*5/6)));
        path1.add(new Bezier<Vector2>(new Vector2(w*3/5, h*5/6), new Vector2(w/2, h*5/6),
                new Vector2(w/3, h*29/32), new Vector2(w*7/30, h*25/32)));
        path1.add(new Bezier<Vector2>(new Vector2(w*7/30, h*25/32), new Vector2(w*4/30, h*19/32),
                new Vector2(w/3, h*17/32)));
        path1.add(new Bezier<Vector2>(new Vector2(w/3, h*17/32), new Vector2(w*7/10, h*17/32)));
        path1.add(new Bezier<Vector2>(new Vector2(w*7/10, h*17/32), new Vector2(w*5/6, h/2),
                new Vector2(w*5/6, h/4)));
        path1.add(new Bezier<Vector2>(new Vector2(w*5/6, h/4), new Vector2(w*5/6, h/7),
                new Vector2(w*8/12, h/7)));
        path1.add(new Bezier<Vector2>(new Vector2(w*8/12, h/7), new Vector2(w/3, h/7)));
        path1.add(new Bezier<Vector2>(new Vector2(w/3, h/7), new Vector2(w/9, h/7)));
        path1.add(new Bezier<Vector2>(new Vector2(w/9, h/7), new Vector2(0-50f, h/7)));




        Array<Path<Vector2>> path2 = new Array<Path<Vector2>>();
        path2.add(new Bezier<Vector2>(new Vector2(w/8, -50f), new Vector2(w/7, h/15)));
        path2.add(new Bezier<Vector2>(new Vector2(w/7, h/15), new Vector2(w/6, h/5)));
        path2.add(new Bezier<Vector2>(new Vector2(w / 6, h / 5), new Vector2(w / 5, h * 4 / 10)));
        path2.add(new Bezier<Vector2>(new Vector2(w/5, h*4/10), new Vector2(w/4, h*7/10)));
        path2.add(new Bezier<Vector2>(new Vector2(w/4, h*7/10), new Vector2(w*7/24, h*11/12),
                new Vector2(w*10/24, h*11/12)));
        path2.add(new Bezier<Vector2>(new Vector2(w*10/24, h*11/12), new Vector2(w*12/24, h*11/12),
                new Vector2(w*13/24, h*8/12)));
        path2.add(new Bezier<Vector2>(new Vector2(w*13/24, h*8/12), new Vector2(w*18/30, h/3)));
        path2.add(new Bezier<Vector2>(new Vector2(w*18/30, h/3), new Vector2(w*19/30, h/7)));
        path2.add(new Bezier<Vector2>(new Vector2(w*19/30, h/7), new Vector2(w*2/3, 0-50f)));
        path2.add(new Bezier<Vector2>(new Vector2(-50f, h/2), new Vector2(w/8, h/2)));
        path2.add(new Bezier<Vector2>(new Vector2(w/8, h/2), new Vector2(w/4, h/2)));
        path2.add(new Bezier<Vector2>(new Vector2(w*10/40, h/2), new Vector2(w*18/40, h/2)));
        path2.add(new Bezier<Vector2>(new Vector2(w*18/40, h/2), new Vector2(w*26/40, h/2)));
        path2.add(new Bezier<Vector2>(new Vector2(w*26/40, h/2), new Vector2(w*34/40, h/2)));
        path2.add(new Bezier<Vector2>(new Vector2(w*34/40, h/2), new Vector2(w + 50f, h/2)));




        Array<Path<Vector2>> path3 = new Array<Path<Vector2>>();
        path3.add(new Bezier<Vector2>(new Vector2(w/9, h + 50f), new Vector2(w/8, h*10/12)));
        path3.add(new Bezier<Vector2>(new Vector2(w/8, h*10/12), new Vector2(w/7, h*7/12)));
        path3.add(new Bezier<Vector2>(new Vector2(w/7, h*7/12), new Vector2(w/6, h/3)));
        path3.add(new Bezier<Vector2>(new Vector2(w/6, h/3), new Vector2(w/5, h/12),
                new Vector2(w/4, h/12)));
        path3.add(new Bezier<Vector2>(new Vector2(w/4, h/12), new Vector2(w*7/24, h/14),
                new Vector2(w*15/48, h/6)));
        path3.add(new Bezier<Vector2>(new Vector2(w*15/48, h/6), new Vector2(w*19/48, h/2)));
        path3.add(new Bezier<Vector2>(new Vector2(w*19/48,  h/2), new Vector2(w*22/48, h*2/3),
                new Vector2(w*25/48, h/2 )));
        path3.add(new Bezier<Vector2>(new Vector2(w*25/48,h/2), new Vector2(w*30/48, h/6)));
        path3.add(new Bezier<Vector2>(new Vector2(w*30/48, h/6), new Vector2(w*33/48, h/20),
                new Vector2(w*37/48, h/6)));
        path3.add(new Bezier<Vector2>(new Vector2(w*37/48, h/6), new Vector2(w*41/48, h/3),
                new Vector2(w*41/48, h/2)));
        path3.add(new Bezier<Vector2>(new Vector2(w*41/48, h/2), new Vector2(w*40/48, h*2/3)));
        path3.add(new Bezier<Vector2>(new Vector2(w*40/48, h*2/3), new Vector2(w*39/48, h*5/6)));
        path3.add(new Bezier<Vector2>(new Vector2(w*39/48, h*5/6), new Vector2(w*38/48, h+40f)));




        Array<Path<Vector2>> path4 = new Array<Path<Vector2>>();
        path4.add(new Bezier<Vector2>(new Vector2(-100f, h*4/5), new Vector2(w/12, h*4/5)));
        path4.add(new Bezier<Vector2>(new Vector2(w/12, h*4/5), new Vector2(w/6, h*4/5),
                new Vector2(w/6, h*5/8)));
        path4.add(new Bezier<Vector2>(new Vector2(w/6, h*5/8), new Vector2(w/6, h/2)));
        path4.add(new Bezier<Vector2>(new Vector2(w/6, h/2), new Vector2(w/6, h/4)));
        path4.add(new Bezier<Vector2>(new Vector2(w/6, h/4), new Vector2(w/6, h/8),
                new Vector2(w/4, h/8)));
        path4.add(new Bezier<Vector2>(new Vector2(w/4, h/8), new Vector2(w/3, h/8)));
        path4.add(new Bezier<Vector2>(new Vector2(w/3, h/8), new Vector2(w*5/12, h/8),
                new Vector2(w*5/12, h/4)));
        path4.add(new Bezier<Vector2>(new Vector2(w*5/12, h/4), new Vector2(w*5/12, h/2)));
        path4.add(new Bezier<Vector2>(new Vector2(w*5/12, h/2), new Vector2(w*5/12, h*2/3)));
        path4.add(new Bezier<Vector2>(new Vector2(w*5/12, h*2/3), new Vector2(w*5/12, h*4/5),
                new Vector2(w/2, h*4/5)));
        path4.add(new Bezier<Vector2>(new Vector2(w/2, h*4/5), new Vector2(w*3/5, h*4/5)));
        path4.add(new Bezier<Vector2>(new Vector2(w*3/5, h*4/5), new Vector2(w*2/3, h*4/5),
                new Vector2(w*2/3, h*2/3)));
        path4.add(new Bezier<Vector2>(new Vector2(w*2/3,h*2/3), new Vector2(w*2/3, h/2)));
        path4.add(new Bezier<Vector2>(new Vector2(w*2/3,h/2), new Vector2(w*2/3, h/3)));
        path4.add(new Bezier<Vector2>(new Vector2(w*2/3,h/3), new Vector2(w*2/3, h/6)));
        path4.add(new Bezier<Vector2>(new Vector2(w*2/3,h/6), new Vector2(w*2/3, -50f)));



        Array<Path<Vector2>> path5 = new Array<Path<Vector2>>();
        path5.add(new Bezier<Vector2>(new Vector2(-100f, h/10), new Vector2(w/5, h/10)));
        path5.add(new Bezier<Vector2>(new Vector2(w/5, h/10), new Vector2(w/3, h/10),
                new Vector2(w/3, h/4)));
        path5.add(new Bezier<Vector2>(new Vector2(w/3, h/4), new Vector2(w/3, h/2),
                new Vector2(w*6/12, h/2)));
        path5.add(new Bezier<Vector2>(new Vector2(w*6/12, h/2), new Vector2(w*3/4, h/2)));
        path5.add(new Bezier<Vector2>(new Vector2(w*3/4, h/2), new Vector2(w*7/8, h/2),
                new Vector2(w*7/8, h*3/4)));
        path5.add(new Bezier<Vector2>(new Vector2(w*7/8, h*3/4), new Vector2(w*7/8, h*8/9),
                new Vector2(w*2/3, h*8/9)));
        path5.add(new Bezier<Vector2>(new Vector2(w*2/3, h*8/9), new Vector2(w*5/12, h*8/9)));
        path5.add(new Bezier<Vector2>(new Vector2(w*5/12, h*8/9), new Vector2(w/3, h*8/9),
                new Vector2(w/3, h*5/8)));
        path5.add(new Bezier<Vector2>(new Vector2(w/3, h*5/8), new Vector2(w/3, h/3),
                new Vector2(w/5, h/3)));
        path5.add(new Bezier<Vector2>(new Vector2(w/5, h/3), new Vector2(w/10, h/3),
                new Vector2(w/10, h*2/3)));
        path5.add(new Bezier<Vector2>( new Vector2(w/10, h*2/3), new Vector2(w/10, h + 50f)));




        Array<Path<Vector2>> path6 = new Array<Path<Vector2>>();
        path6.add(new Bezier<Vector2>(new Vector2(-100f, h*9/10), new Vector2(w/5, h*9/10)));
        path6.add(new Bezier<Vector2>(new Vector2(w/5, h*9/10), new Vector2(w*5/12, h*9/10)));
        path6.add(new Bezier<Vector2>(new Vector2(w*5/12, h*9/10), new Vector2(w*2/3, h*9/10)));
        path6.add(new Bezier<Vector2>(new Vector2(w*2/3, h*9/10), new Vector2(w*5/6, h*9/10),
                new Vector2(w*5/6, h*3/4)));
        path6.add(new Bezier<Vector2>(new Vector2(w*5/6, h*3/4), new Vector2(w*5/6, h*2/5)));
        path6.add(new Bezier<Vector2>(new Vector2(w*5/6, h*2/5), new Vector2(w*5/6, h/5)));
        path6.add(new Bezier<Vector2>(new Vector2(w*5/6, h/5), new Vector2(w*5/6, h/12),
                new Vector2(w*3/4, h/12)));
        path6.add(new Bezier<Vector2>(new Vector2(w*3/4, h/12), new Vector2(w/2, h/12)));
        path6.add(new Bezier<Vector2>(new Vector2(w/2, h/12), new Vector2(w*2/5, h/12),
                new Vector2(w*2/5, h/4)));
        path6.add(new Bezier<Vector2>(new Vector2(w*2/5, h/4), new Vector2(w*2/5, h*2/5),
                new Vector2(w/2, h*2/5)));
        path6.add(new Bezier<Vector2>(new Vector2(w/2, h*2/5), new Vector2(w*3/5, h*2/5),
                new Vector2(w*3/5, h/2)));
        path6.add(new Bezier<Vector2>(new Vector2(w*3/5, h/2), new Vector2(w*3/5, h*7/10),
                new Vector2(w*7/20, h*7/10)));
        path6.add(new Bezier<Vector2>(new Vector2(w*7/20, h*7/10), new Vector2(w/8, h*7/10),
                new Vector2(w/8, h/2)));
        path6.add(new Bezier<Vector2>(new Vector2(w/8, h/2), new Vector2(w/8, h*2/5),
                new Vector2(w/4, h*2/5)));
        path6.add(new Bezier<Vector2>(new Vector2(w/4, h*2/5), new Vector2(w/3, h*2/5),
                new Vector2(w/3, h*1/5)));
        path6.add(new Bezier<Vector2>(new Vector2(w/3, h*1/5), new Vector2(w/3, h/12),
                new Vector2(w/5, h/12)));
        path6.add(new Bezier<Vector2>(new Vector2(w/5, h/12), new Vector2(-50f, h/12)));




        Array<Path<Vector2>> path7 = new Array<Path<Vector2>>();
        path7.add(new Bezier<Vector2>(new Vector2(-100f, h/10), new Vector2(w/5, h/10)));
        path7.add(new Bezier<Vector2>(new Vector2(w/5, h/10), new Vector2(w/3, h/10),
                new Vector2(w/3, h/4)));
        path7.add(new Bezier<Vector2>(new Vector2(w/3, h/4), new Vector2(w/3, h/2)));
        path7.add(new Bezier<Vector2>(new Vector2(w/3, h/2), new Vector2(w/3, h*5/6)));
        path7.add(new Bezier<Vector2>(new Vector2(w/3, h*5/6), new Vector2(w/3, h*12/13),
                new Vector2(w/2, h*12/13)));
        path7.add(new Bezier<Vector2>(new Vector2(w/2, h*12/13), new Vector2(w*2/3, h*12/13),
                new Vector2(w*2/3, h*5/6)));
        path7.add(new Bezier<Vector2>(new Vector2(w*2/3, h*5/6), new Vector2(w*2/3, h/2)));
        path7.add(new Bezier<Vector2>(new Vector2(w*2/3, h/2), new Vector2(w*2/3, h/3),
                new Vector2(w*3/4, h/3)));
        path7.add(new Bezier<Vector2>(new Vector2(w*3/4, h/3), new Vector2(w*11/12, h/3)));
        path7.add(new Bezier<Vector2>(new Vector2(w*11/12, h/3), new Vector2(w+50f, h/3)));




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


    public void dispose(){

        for(int x = 0; x < allPaths.size; x++){
            for(int y= 0; y < allPaths.get(x).size; x++){
                allPaths.get(x).clear();
            }
        }

        allPaths.clear();
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



/*
*
*
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
 new Vector2(rand.nextFloat()*w-w,rand.nextFloat()*h+h)));*/

