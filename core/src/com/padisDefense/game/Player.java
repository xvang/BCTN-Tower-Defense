package com.padisDefense.game;


import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Items.MainItem;


/**
 * This is where a user's profile is stored.
 *
 * @author Xeng
 * @param 'padi'
 * */
public class Player {

    public static int numberOfLevels = 8;
    public String name;
    public int money;
    public Array<MainItem> itemsUnlocked;
    private String password;


    //Levels
    //has to be this kind of array because Array<> and Vector() do not take primitive types.
    //boolean is apparently a primitive type. Probably along with int, float, char... etc.
    public boolean[] levels = new boolean[numberOfLevels];


    //Constructor
    public Player(){
        //TODO: lock the levels.
        //only the first level is unlocked.
        for(int x = 0; x < levels.length; x++){
            if(x == 0)
                levels[x] = true;
            else
                levels[x] = false;
            //levels[x] = true;
        }
        money = 1000;
        //name = "Guest" +  (int)(Math.random()*1000);
        name = "";
        itemsUnlocked = new Array<MainItem>();


    }

    //SET functions.
    public void setName(String n){ name = n;}
    public void setScore(int s){money = s;}
    public void addItemsUnlocked(MainItem i){itemsUnlocked.add(i);}
    public void setLevelsUnlocked(int l){levels[l] = true;}
    public void setPassword(String p){password = p;}
    public void setMoney(int m){money = m;}

    //GET functions.
    public String getName(){return name;}
    public int getMoney(){return money;}
    public Array<MainItem> getItemsUnlocked(){return itemsUnlocked;}



    public boolean isLevelUnlocked(int n){
        try{
                return levels[n - 1];
        }catch(Exception e){
            System.out.println("getLevelsUnlocked(): bad parameter. cheeck yoself");
            return false;
        }

    }

    public boolean isItemUnlocked(MainItem i){

        for(int x = 0; x < itemsUnlocked.size; x++) {
            if (itemsUnlocked.get(x).getName().equals(i.getName()))
                return true;
        }
        return false;
    }

    public String getPassword(){return password;}
    public int getNumberOfLevels(){return numberOfLevels;}



}
