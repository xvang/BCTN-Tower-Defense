package com.padisDefense.game.Managers;


import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Enemies.IronSpider;

public class TestSpawnDeleteLater {

    public TestSpawnDeleteLater(){

    }

    public Enemy getSpawn(){


        return new IronSpider();
    }

}
