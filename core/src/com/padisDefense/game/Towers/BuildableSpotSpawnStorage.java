package com.padisDefense.game.Towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;




//this object will take in the current level
//and should return the appropriate spawn locations
//for the buildable spots.
public class BuildableSpotSpawnStorage {


    private int level;

    private Array<Array<Vector2>> locations;
    public BuildableSpotSpawnStorage(){

        locations = new Array<Array<Vector2>>();
        init();
    }




    //TODO: add level  7 and level 8.
    public void init(){

        final float w = Gdx.graphics.getWidth()/100;
        final float h = Gdx.graphics.getHeight()/100;

        //path1.png
        Array<Vector2> one = new Array<Vector2>();

        one.add(new Vector2(w*25, h*30));
        one.add(new Vector2(w*37, h*66));
        one.add(new Vector2(w*45, h*30));
        one.add(new Vector2(w*55, h*66));
        one.add(new Vector2(w*62, h*30));
        one.add(new Vector2(w*72, h*66));


        //path2.png
        Array<Vector2> two = new Array<Vector2>();

        two.add(new Vector2(w*14, h*90));
        two.add(new Vector2(w*30, h*16));
        two.add(new Vector2(w*30, h*68));
        two.add(new Vector2(w*40, h*32));
        two.add(new Vector2(w*48, h*60));
        two.add(new Vector2(w*49, h*16));
        two.add(new Vector2(w*61, h*89));
        two.add(new Vector2(w*74, h*65));


        //path3.png
        Array<Vector2> three = new Array<Vector2>();
        three.add(new Vector2(w*45, h*18));
        three.add(new Vector2(w*29, h*52));
        three.add(new Vector2(w*65, h*52));

        three.add(new Vector2(w*63, h*81));
        three.add(new Vector2(w*27, h*82));



        //path4.png
        Array<Vector2> four = new Array<Vector2>();

        four.add(new Vector2(w*29, h*29));
        four.add(new Vector2(w*29, h*68));
        four.add(new Vector2(w*54, h*60));
        four.add(new Vector2(w*54, h*15));
        four.add(new Vector2(w*80, h*38));


        //path5.png
        Array<Vector2> five = new Array<Vector2>();
        five.add(new Vector2(w*17, h*82));
        five.add(new Vector2(w*21, h*54));
        five.add(new Vector2(w*43, h*31));
        five.add(new Vector2(w*81, h*32));
        five.add(new Vector2(w*72, h*60));
        five.add(new Vector2(w*60, h*73));
        five.add(new Vector2(w*49, h*60));
        five.add(new Vector2(w*72, h*60));
        five.add(new Vector2(w*15, h*22));


        //path6.png
        Array<Vector2> six = new Array<Vector2>();

        six.add(new Vector2(w*10, h*75));
        six.add(new Vector2(w*20, h*25));
        six.add(new Vector2(w*35, h*55));
        six.add(new Vector2(w*55, h*75));
        six.add(new Vector2(w*70, h*40));
        six.add(new Vector2(w*70, h*65));


        Array<Vector2> seven = new Array<Vector2>();
        seven.add(new Vector2(w*15, h*30));
        seven.add(new Vector2(w*15, h*65));
        seven.add(new Vector2(w*50, h*70));
        seven.add(new Vector2(w*50, h*50));
        seven.add(new Vector2(w*50, h*30));
        seven.add(new Vector2(w*80, h*55));



        Array<Vector2> eight = new Array<Vector2>();







        locations.add(one);
        locations.add(two);
        locations.add(three);
        locations.add(four);
        locations.add(five);
        locations.add(six);
        locations.add(seven);
        locations.add(eight);
    }


    public Array<Vector2> getBuildableLocations(int level){
        return locations.get(level);
    }
}
