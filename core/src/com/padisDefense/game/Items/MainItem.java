
package com.padisDefense.game.Items;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Towers.MainTower;

/**
 * this will be the main item class.
 *
 * @author Xeng
 *
 *
 * Item effects will be calculated in buildATower() in SpawnManager.
 * That's where towers are created.
 * SpawnManager has a pointer to the GameScreen,
 * which has a pointer to the Game Object(Padi).
 * Padi contains a Player object,
 * which contains an Array<MainItem> of items.
 *
 * */

/**TODO: Read this to understand what happened. I will not understand this later.
 * Tower is created in SpawnManager.
 * applyStatChange() function is called.
 * Tower is checked against all the towers the item(s) target.
 * If match, then the item's update() function is called, with Tower as one of the parameters.
 *
 *
 *
 * When declared, override the update() function.
 * In update() function, pick which change____() function(s) to call.
 *
 * For example, the item 'scope' changes only the range,
 * so in its update() function, it calls changeRange(), with appropriate parameters.
 *
 * To add more items, just add it in ItemStorage.
 * */

public class MainItem extends Sprite{

    private int cost;
    private String displayStats;
    private Array<String> targets;
    private String name;

    public MainItem(String pic){
        super(new Texture(pic));

        targets = new Array<String>();
    }


    public int getCost(){return cost;}
    public String getDisplayStats(){return displayStats;}
    public Array<String> getTargets(){return targets;}
    public String getName(){return name;}


    public void setCost(int c){cost = c;}
    public void setDisplayStats(String s){displayStats = s;}
    public void addTargets(String t){targets.add(t);}
    public void setName(String s){name = s;}


    public void changeBulletRate(MainTower t, float amount){
        t.setBulletRate(t.getBulletRate()*amount);
    }

    public void changeChargeRate(MainTower t, float amount){
        t.setChargeRate(t.getChargeRate()*amount);
    }

    public void changeRange(MainTower t, float amount){
        t.setRange(t.getRange() * amount);
    };

    public void changeAttack(MainTower t, float amount){
        t.setAttack(t.getAttack() * amount);
    }

    public void changeFireRate(MainTower t, float amount){
        t.setFireRate(t.getFireRate()*amount);
    }

    public void changeBulletAmount(MainTower t, int amount){
        t.setBulletLimit(t.getBulletLimit() + amount);
    }

    public void update(MainTower t){
    }

}
