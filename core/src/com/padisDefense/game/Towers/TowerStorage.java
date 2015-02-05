package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Enemies.Enemy;
import com.padisDefense.game.Padi;


public class TowerStorage  {

    Padi padi;
    public TowerStorage(Padi p){
        padi = p;
    }
    //All these functions should still work if they were void functions.
    //any changes to the parameter 't' should be saved in the actual tower...?
    public void ArmyTower(Vector2 position, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        //t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setOriginalCost(60);
        t.setAttack(20f);
        t.setOriginalAttack(20f);
        t.setRange(200f);
        t.setOriginalRange(200f);
        t.setChargeRate(0.025f);
        t.setOriginalChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("ARMY");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("blueball");
        t.setStrongAgainst("armyball");

        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();

        TextureRegion b = padi.assets.bulletAtlas.findRegion("army_bullet");
        t.setBulletTextureRegion(b);



    }

    public void BlueTower(Vector2 position, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        //t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(100);
        t.setOriginalCost(100);
        t.setAttack(20f);
        t.setOriginalAttack(20f);
        t.setRange(200f);
        t.setOriginalRange(200f);
        t.setChargeRate(0.025f);
        t.setOriginalChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.2f);
        t.setID("BLUE");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.07f);
        t.setWeakAgainst("greenball");
        t.setStrongAgainst("blueball");

        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();

        TextureRegion b = padi.assets.bulletAtlas.findRegion("blue_bullet");
        t.setBulletTextureRegion(b);

    }

    public void GreenTower(Vector2 position, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        //t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setRotateRate(4);
        t.setBulletLimit(1);
        t.setCost(80);
        t.setAttack(20f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(80);
        t.setOriginalAttack(20f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.025f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.1f);
        t.setID("GREEN");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.08f);
        t.setWeakAgainst("armyball");
        t.setStrongAgainst("greenball");

        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();
        TextureRegion b = padi.assets.bulletAtlas.findRegion("green_bullet");
        t.setBulletTextureRegion(b);


    }

    public void OrangeTower(Vector2 position, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        //t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setAttack(20f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(60);
        t.setOriginalAttack(20f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.025f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("ORANGE");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("pinkball");
        t.setStrongAgainst("orangeball");

        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();
        TextureRegion b = padi.assets.bulletAtlas.findRegion("orange_bullet");
        t.setBulletTextureRegion(b);


    }

    public void PinkTower(Vector2 position, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        //t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(50);
        t.setRange(200f);
        t.setAttack(20f);
        t.setChargeRate(0.02f);
        t.setIncomeRate(4f);
        t.setOriginalCost(50);
        t.setOriginalRange(200f);
        t.setOriginalAttack(20f);
        t.setOriginalChargeRate(0.02f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.001f);
        t.setID("PINK");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.12f);
        t.setWeakAgainst("purpleball");
        t.setStrongAgainst("pinkball");


        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();

        TextureRegion b = padi.assets.bulletAtlas.findRegion("pink_bullet");
        t.setBulletTextureRegion(b);


    }

    public void PurpleTower(Vector2 position,Tower t){

        //t.set(sprite);
        //t.setBulletTextureRegion(bullet);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.state = true;
        t.setID("PURPLE");
        t.setCost(25);
        t.setAttack(20f);
        t.setRange(200f);
        t.setIncomeRate(4f);
        t.setChargeRate(0.025f);
        t.setOriginalCost(25);
        t.setOriginalAttack(20f);
        t.setOriginalRange(200f);
        t.setOriginalIncomeRate(4f);
        t.setOriginalChargeRate(0.03f);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setFireRate(0.3f);
        t.setBulletRate(0.08f);
        t.setCustomArc(120f);
        t.setWeakAgainst("orangeball");
        t.setStrongAgainst("purpleball");

        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();

        TextureRegion b = padi.assets.bulletAtlas.findRegion("purple_bullet");
        t.setBulletTextureRegion(b);

    }


    public void RedTower(Vector2 position, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        //t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setAttack(20f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(60);
        t.setOriginalAttack(20f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.025f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("RED");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("violetball");
        t.setStrongAgainst("redball");


        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();

        TextureRegion b = padi.assets.bulletAtlas.findRegion("red_bullet");
        t.setBulletTextureRegion(b);

    }


    public void VioletTower(Vector2 position,Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        //t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setAttack(20f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(60);
        t.setOriginalAttack(20f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.025f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("VIOLET");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("yellowball");
        t.setStrongAgainst("violetball");

        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();

        TextureRegion b = padi.assets.bulletAtlas.findRegion("violet_bullet");
        t.setBulletTextureRegion(b);
    }

    public void YellowTower(Vector2 position, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
       // t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(70);
        t.setAttack(20f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(70);
        t.setOriginalAttack(20f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.01f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.4f);
        t.setID("YELLOW");
        //t.setBulletTextureRegion(bullet);
        t.setBulletRate(0.08f);
        t.setCustomArc(50f);
        t.setWeakAgainst("redball");
        t.setStrongAgainst("yellowball");
        t.setRotateRate(5f);

        TextureRegion r = padi.assets.towerAtlas.findRegion(t.getID());
        t.setRegion(r);
        t.setBounds(t.getX(), t.getY(), r.getRegionWidth(), r.getRegionHeight());
        t.setOriginCenter();

        TextureRegion b = padi.assets.bulletAtlas.findRegion("yellow_bullet");
        t.setBulletTextureRegion(b);
    }









}
