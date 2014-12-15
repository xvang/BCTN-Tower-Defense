package com.padisDefense.game;
import com.badlogic.gdx.graphics.Texture;
import java.util.Vector;

/**
 * All the assets should be declared here. Probably.
 *
 *
 * @author Xeng
 * @param 'padi'
 * **/
public class Assets {

    Padi padi;
    public static Texture background;

    public  static final int w = 1200;
    public static final int h = 800;

    public static int SOUND_LEVEL = 20;
    public static int DIFFICULTY = 20;

    public Vector splash_Pages;


    public Assets(Padi p){

        padi = p;
        background = new Texture("test3.png");

        //test_comment
        splash_Pages = new Vector();

        //ADD MORE SPLASH SCREENS HERE


        splash_Pages.add("limegreen.png");

        splash_Pages.add("test10.png");
        splash_Pages.add("test9.png");
        splash_Pages.add("test8.png");
        splash_Pages.add("test7.png");
        splash_Pages.add("test6.png");
        splash_Pages.add("test5.png");
        splash_Pages.add("test4.png");
        splash_Pages.add("test3.png");
        splash_Pages.add("test2.png");
        splash_Pages.add("test1.png");
        splash_Pages.add("badlogic.jpg");


    }

    public Assets(){
        background = new Texture("test3.png");

        //test_comment
        splash_Pages = new Vector();

        //ADD MORE SPLASH SCREENS HERE


        splash_Pages.add("limegreen.png");

        splash_Pages.add("test10.png");
        splash_Pages.add("test9.png");
        splash_Pages.add("test8.png");
        splash_Pages.add("test7.png");
        splash_Pages.add("test6.png");
        splash_Pages.add("test5.png");
        splash_Pages.add("test4.png");
        splash_Pages.add("test3.png");
        splash_Pages.add("test2.png");
        splash_Pages.add("test1.png");
        splash_Pages.add("badlogic.jpg");


    }


    //Returns a random picture to be used as Splash screen, or background.
    public Object getRandomPic(){

        int position = (int)(Math.random()*1000 % ((splash_Pages.size())));

        return splash_Pages.get(position);
    }

    public void setSoundLevel(int level){
        SOUND_LEVEL = level;
    }

    public void setDifficulty(int diff){
        DIFFICULTY = diff;
    }



    public float getScreenWidth(){return w;}
    public float getScreenHeight(){return h;}
    public int getSound(){return SOUND_LEVEL;}
    public int getDifficulty(){return DIFFICULTY;}




    public void dispose(){

        background.dispose();;
    }

}
