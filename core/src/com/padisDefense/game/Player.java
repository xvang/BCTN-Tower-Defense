package com.padisDefense.game;


import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Items.MainItem;

import java.util.Vector;


/**
 * This is where a user's profile is stored.
 *
 * @author Xeng
 * @param 'padi'
 * */
public class Player {

    public static int numberOfLevels = 5;
    public static String name;
    public static float money;
    public static Array<MainItem> items;
    private String password;


    //Levels
    public static boolean[] levels = new boolean[numberOfLevels];


    //Constructor
    Player(){

        levels[0] = true;
        money = 0;
        name = "Guest" +  (int)(Math.random()*1000);
        items = new Array<MainItem>();


    }

    //SET functions.
    public void setName(String n){ name = n;}
    public void setScore(int s){money = s;}
    public void addItemsUnlocked(MainItem i){items.add(i);}
    public void setLevelsUnlocked(int l){levels[l - 1] = true;}
    public void setPassword(String p){password = p;}

    //GET functions.
    public String get_Name(){return name;}
    public float getMoney(){return money;}



    /**
     * Checks if a level is unlocked.
     * @param 'n'
     * */
    public boolean isLevelUnlocked(int n){
        try{
                return levels[n - 1];
        }catch(Exception e){
            System.out.println("getLevelsUnlocked(): bad parameter.");
            return false;
        }

    }

    public String getPassword(){return password;}




}
