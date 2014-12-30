package com.padisDefense.game.Items;


import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Towers.Tower;

public class ItemStorage {

    public int size = 0;
    public Array<MainItem> itemArray;

    public ItemStorage(){
        itemArray = new Array<MainItem>();
        itemArray.add(new MainItem("telescope.png"){
            @Override
            public void update(Tower t){
                changeRange(t, 1.1f);
            }
        });//scope.
        itemArray.add(new MainItem("telescope.png"){
            @Override
            public void update(Tower t){
                changeRange(t, 1.1f);
            }
        });//heat-vision scope

        itemArray.add(new MainItem("telescope.png"){
            @Override
            public void update(Tower t){
                changeRange(t, 1.2f);
            }
        });//hubble



        init();
    }

    public void init(){
        itemArray.get(0).setCost(10);
        itemArray.get(0).addTargets("speed");
        itemArray.get(0).setDisplayStats("+ 5% Range\n -5% Attack\n");
        itemArray.get(0).setName("scope");
        size++;

        itemArray.get(1).setCost(50);
        itemArray.get(1).addTargets("speed");
        itemArray.get(1).setDisplayStats(("+ 10% Range\n No negative effects."));
        itemArray.get(1).setName("heat-vision scope");
        size++;

        itemArray.get(2).setCost(50);
        itemArray.get(2).addTargets("speed");
        itemArray.get(2).setDisplayStats(("+ 20% Range\n No negative effects."));
        itemArray.get(2).setName("hubble");
        size++;
    }
}
