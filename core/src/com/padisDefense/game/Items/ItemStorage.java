package com.padisDefense.game.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Towers.Tower;


/**
 * Every item overrides the update() method.
 * In that method will be calls to other methods that will change the towers' stats.(?)
 * I think that's what I was trying to do.
 *
 *
 *
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
                changeAttack(t, 0.95f);
            }
        };
        item1.setCost(800);
        item1.addTargets("ALL");
        item1.setDisplayStats("+ 10% Range\n - 5% Attack\n");
        item1.setName("Coffee");
        size++;

        //shield
        MainItem item2 = new MainItem("items/shield.png") {
            @Override
            public void update(Tower t) {
                //System.out.println("update() called");
                changeAttack(t, 1.3f);
                changeRange(t, 1.1f);
            }
        };
        item2.setCost(1000);
        item2.addTargets("ALL");
        item2.setDisplayStats("+ 10% Range\n + 30% Attack\n");
        item2.setName("Badge");
        size++;

        //seeker.
        seeker = new MainItem("items/seeker.png") {
            @Override
            public void update(Tower t) {
                changeAttack(t, 10f);
            }
        };
        seeker.setCost(p.player.getMoney() + 1);
        seeker.addTargets("?");
        seeker.setDisplayStats("- ?\n- ?\n");
        seeker.setName("?");
        size++;

        //adding in items from spritesheet.
        Texture sheet = new Texture("items/Fantasy_Item_Collection.png");
        TextureRegion[][] splitter = TextureRegion.split(sheet, sheet.getWidth()/3, sheet.getHeight()/4);
        TextureRegion[] sp = new TextureRegion[3*4];
        int index = 0;
        for(int x = 0; x < 4; x++)
            for(int y = 0; y < 3; y++)
                sp[index++] = splitter[x][y];

        //body armor
        MainItem item3 = new MainItem(new Sprite(sp[0])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item3.setCost(100);
        item3.addTargets("ARMY");
        item3.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item3.setName("Body Armor");
        size++;

        //bow arrow
        MainItem item4 = new MainItem(new Sprite(sp[1])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item4.setCost(100);
        item4.addTargets("BLUE");
        item4.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item4.setName("Bow/Arrow");
        size++;

        //shoe
        MainItem item5 = new MainItem(new Sprite(sp[2])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item5.setCost(100);
        item5.addTargets("GREEN");
        item5.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item5.setName("Shoe");
        size++;

        //wooden shield
        MainItem item6 = new MainItem(new Sprite(sp[3])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item6.setCost(100);
        item6.addTargets("ORANGE");
        item6.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item6.setName("Shield");
        size++;

        //ring
        MainItem item7 = new MainItem(new Sprite(sp[4])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item7.setCost(100);
        item7.addTargets("PINK");
        item7.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item7.setName("Ring");
        size++;

        //herbs
        MainItem item8 = new MainItem(new Sprite(sp[5])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item8.setCost(100);
        item8.addTargets("PURPLE");
        item8.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item8.setName("Herbs");
        size++;

        //chest
        MainItem item9 = new MainItem(new Sprite(sp[6])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item9.setCost(100);
        item9.addTargets("RED");
        item9.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item9.setName("Chest");
        size++;

        //sword
        MainItem item10 = new MainItem(new Sprite(sp[7])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item10.setCost(100);
        item10.addTargets("VIOLET");
        item10.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item10.setName("Sword");
        size++;

        //helmet
        MainItem item11 = new MainItem(new Sprite(sp[8])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item11.setCost(100);
        item11.addTargets("YELLOW");
        item11.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item11.setName("Helmet");
        size++;


        //staff
        MainItem item12 = new MainItem(new Sprite(sp[9])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item12.setCost(100);
        item12.addTargets("ARMY");
        item12.addTargets("BLUE");
        item12.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item12.setName("Staff");
        size++;

        //book
        MainItem item13 = new MainItem(new Sprite(sp[10])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item13.setCost(100);
        item13.addTargets("RED");
        item13.addTargets("PURPLE");
        item13.addTargets("VIOLET");
        item13.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item13.setName("Book");
        size++;

        //potion
        MainItem item14 = new MainItem(new Sprite(sp[11])){
            @Override
            public void update(Tower t){
                changeRange(t, 1.05f);
                changeAttack(t, 1.05f);
            }
        };
        item14.setCost(100);
        item14.addTargets("BLUE");
        item14.addTargets("ARMY");
        item14.addTargets("GREEN");
        item14.setDisplayStats("+ 5% Range\n + 5% Attack\n");
        item14.setName("Potion");
        size++;




        itemArray.add(item1);
        itemArray.add(item2);
        itemArray.add(item3);
        itemArray.add(item4);
        itemArray.add(item5);
        itemArray.add(item6);
        itemArray.add(item7);
        itemArray.add(item8);
        itemArray.add(item9);
        itemArray.add(item10);
        itemArray.add(item11);
        itemArray.add(item12);
        itemArray.add(item13);
        itemArray.add(item14);
        itemArray.add(seeker);
    }


    public void updateSeeker(){
        seeker.setCost(padi.player.getMoney()+1);
    }
}