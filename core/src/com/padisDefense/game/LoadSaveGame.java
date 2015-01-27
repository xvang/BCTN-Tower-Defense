package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.padisDefense.game.Items.MainItem;

/**
 * http://www.steventrigg.com/libgdx-save-game-state-and-local-data-persistence/
 */
public class LoadSaveGame {


    public LoadSaveGame(){

    }

    public static class Playa{

        public static int numberOfLevels = 8;
        public String name;
        public int money;
        public Array<Itemer> itemsUnlocked = new Array<Itemer>();
        public boolean[] levels = new boolean[numberOfLevels];

    }

    public static class Itemer{

        public int cost;
        public String displayStats;
        public Array<String> targets = new Array<String>();
        public String name;
        public String pic;
    }



    public static void writeFile(String fileName, String s){
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }

    public static String readFile(String fileName){
        FileHandle file = Gdx.files.local((fileName));

        if (file != null && file.exists()){
            String s = file.readString();

            if(!s.isEmpty()){
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }


    public static void savePlayer(Player player){

        Playa playa = new Playa();
        playa.money = player.getMoney();
        playa.name = player.name;
        playa.numberOfLevels = player.numberOfLevels;

        //copying all the unlocked item array.
        for(int x = 0;x < player.getItemsUnlocked().size; x++){
            MainItem item = player.getItemsUnlocked().get(x);
            Itemer i = new Itemer();


            i.cost = item.getCost();
            i.name = item.getName();
            i.pic = item.getPic();
            i.displayStats = item.getDisplayStats();


            //for the target array.
            for(int y = 0; y < item.getTargets().size; y++){
                i.targets.add(item.getTargets().get(y));
            }

            playa.itemsUnlocked.add(i);
        }


        //copying the levels.
        for(int x = 0; x < playa.levels.length; x++){
            playa.levels[x] = player.levels[x];
        }



        Json json = new Json();
        writeFile("game.sav", json.toJson(playa));

    }

    //loads the information from the .sav file.
    public static Player loadPlayer(){

        String save = readFile("game.sav");
        Player player = new Player();


        if(!save.isEmpty()){
            Json json = new Json();
            Playa playa = json.fromJson(Playa.class, save);

            player.money = playa.money;
            player.name = playa.name;


            for(int x = 0; x < playa.levels.length; x++){
                player.levels[x] = playa.levels[x];
            }

            if(playa.itemsUnlocked != null){

                for(int x = 0; x < playa.itemsUnlocked.size; x++){
                    Itemer i = playa.itemsUnlocked.get(x);
                    MainItem item = new MainItem(i.pic);

                    item.setCost(i.cost);
                    item.setName(i.name);
                    item.setDisplayStats(i.displayStats);

                    for(int y = 0; y < i.targets.size; y++){
                        item.getTargets().add(i.targets.get(y));
                    }

                    player.addItemsUnlocked(item);
                }
            }
        }




        //if file is empty, then player just defaults to a new player.
        return player;
    }
}
