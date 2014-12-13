package com.padisDefense.game.Pathing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



/**
 *
 * Generic path. Other paths will extend this one.
 * For example, level 1 is path1. Level 2 is path2, etc.
 *
 * Every path consists of an array of Path vectors.
 * For example, the path for level 1 is made up of 5 individual paths.
 *
 * All path parameters will be a MainPath.
 * For example, the EnemyManager class takes in 1 path per level.
 * So it will take in a type MainPath as a parameter, and every level a Path1, or Path2, or
 * whatever path will be accepted in.
 *
 *
 * @author Xeng
* **/


 public class MainPath {

    public final static float w = Gdx.graphics.getWidth();
    public final static float h = Gdx.graphics.getHeight();
    public Array<Path<Vector2>> paths;

    public MainPath(){
        paths = new Array<Path<Vector2>>();

    }

    public MainPath(Array<Path<Vector2>> p){

        paths = p;
    }

    public Array<Path<Vector2>> getPath(){
        return paths;
    }






    public void dispose(){
        paths.clear();

    }




}
