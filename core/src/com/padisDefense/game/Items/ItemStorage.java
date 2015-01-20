package com.padisDefense.game.Items;


import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Towers.Tower;


/**
 * Every item overrides the update() method.
 * In that method will be calls to other methods that will change the towers' stats.(?)
 * I think that's what I was trying to do.
 * TODO: make sure stacking is for original stat, not the changed stat!
 * for example, original is 100. +10%, +10% should end up at 120, NOT 121.
 *
 *
 * TODO: put all the image for items into one spritesheet. We can totally do this. we've done it before....ish.
 * **/
public class ItemStorage {

    Padi padi;
    public int size = 0;
    public Array<MainItem> itemArray;
    MainItem seeker;

    public ItemStorage(Padi p) {

        padi = p;
        itemArray = new Array<MainItem>();

        MainItem item1 = new MainItem("items/coffee.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        item1.setCost(150);
        item1.addTargets("speed");
        item1.setDisplayStats("+ 10% Range\n - 5% Attack\n");
        item1.setName("coffee");
        size++;

        MainItem item2 = new MainItem("telescope.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        item2.setCost(50);
        item2.addTargets("speed");
        item2.setDisplayStats("+ 5% Range\n - 5% Attack\n");
        item2.setName("scope");
        size++;

        MainItem item3 = new MainItem("telescope.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        item3.setCost(50);
        item3.addTargets("speed");
        item3.setDisplayStats("+ 5% Range\n - 5% Attack\n");
        item3.setName("scope");
        size++;

        MainItem item4 = new MainItem("telescope.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        item4.setCost(50);
        item4.addTargets("speed");
        item4.setDisplayStats("+ 5% Range\n - 5% Attack\n");
        item4.setName("scope");
        size++;

        MainItem item5 = new MainItem("telescope.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        item5.setCost(50);
        item5.addTargets("speed");
        item5.setDisplayStats("+ 5% Range\n - 5% Attack\n");
        item5.setName("scope");
        size++;

        MainItem item6 = new MainItem("telescope.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        item6.setCost(50);
        item6.addTargets("speed");
        item6.setDisplayStats("+ 5% Range\n - 5% Attack\n");
        item6.setName("scope");
        size++;

        MainItem item7 = new MainItem("shield.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        item7.setCost(50);
        item7.addTargets("speed");
        item7.setDisplayStats("+ 5% Range\n - 5% Attack\n");
        item7.setName("Shield");
        size++;

        seeker = new MainItem("items/seeker.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };

        seeker.setCost(p.player.getMoney() + 1);
        seeker.addTargets("?");
        seeker.setDisplayStats("- ?\n- ?\n");
        seeker.setName("?");
        size++;


        itemArray.add(item1);
        itemArray.add(item2);
        itemArray.add(item3);
        itemArray.add(item4);
        itemArray.add(item5);
        itemArray.add(item6);
        itemArray.add(item7);
        itemArray.add(seeker);
    }


    public void updateSeeker(){
        seeker.setCost(padi.player.getMoney()+1);

    }
}