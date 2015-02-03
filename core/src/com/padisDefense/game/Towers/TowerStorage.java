package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.padisDefense.game.Bullets.Bullet;
import com.padisDefense.game.Enemies.Enemy;

//TODO: this is happening, Xeng. Accept it. We screwed up. You can do this.
public class TowerStorage  {

    public Tower ArmyTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setOriginalCost(60);
        t.setAttack(80f);
        t.setOriginalAttack(80f);
        t.setRange(200f);
        t.setOriginalRange(200f);
        t.setChargeRate(0.025f);
        t.setOriginalChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("ARMY");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("blueball");
        t.setStrongAgainst("armyball");

        return t;
    }

    public Tower BlueTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(100);
        t.setOriginalCost(100);
        t.setAttack(80f);
        t.setOriginalAttack(80f);
        t.setRange(200f);
        t.setOriginalRange(200f);
        t.setChargeRate(0.04f);
        t.setOriginalChargeRate(0.04f);
        t.setIncomeRate(4f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.2f);
        t.setID("BLUE");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.07f);
        t.setWeakAgainst("greenball");
        t.setStrongAgainst("blueball");

        return t;
    }

    public Tower GreenTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setRotateRate(4);
        t.setBulletLimit(1);
        t.setCost(80);
        t.setAttack(80f);
        t.setRange(200f);
        t.setChargeRate(0.53f);
        t.setIncomeRate(4f);
        t.setOriginalCost(80);
        t.setOriginalAttack(200f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.53f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.1f);
        t.setID("GREEN");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.08f);
        t.setWeakAgainst("armyball");
        t.setStrongAgainst("greenball");

        return t;
    }

    public Tower OrangeTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setAttack(80f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(60);
        t.setOriginalAttack(50f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.025f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("ORANGE");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("pinkball");
        t.setStrongAgainst("orangeball");
        return t;
    }

    public Tower PinkTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(50);
        t.setRange(200f);
        t.setAttack(80f);
        t.setChargeRate(0.02f);
        t.setIncomeRate(4f);
        t.setOriginalCost(50);
        t.setOriginalRange(200f);
        t.setOriginalAttack(50f);
        t.setOriginalChargeRate(0.02f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.001f);
        t.setID("PINK");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.12f);
        t.setWeakAgainst("purpleball");
        t.setStrongAgainst("pinkball");


        return t;
    }

    public Tower PurpleTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        /*final Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f){
            public void spin(){
                this.flower.rotate(1);
            }
        };*/
        t.set(sprite);
        t.setBulletSprite(bullet);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.state = true;
        t.setID("PURPLE");
        t.setCost(25);
        t.setAttack(80f);
        t.setRange(200f);
        t.setIncomeRate(4f);
        t.setChargeRate(0.021f);
        t.setOriginalCost(25);
        t.setOriginalAttack(80f);
        t.setOriginalRange(200f);
        t.setOriginalIncomeRate(4f);
        t.setOriginalChargeRate(0.021f);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setFireRate(0.3f);
        t.setBulletRate(0.08f);
        t.setCustomArc(120f);
        t.setWeakAgainst("orangeball");
        t.setStrongAgainst("purpleball");

        return t;
    }


    public Tower RedTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setAttack(80f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(60);
        t.setOriginalAttack(80f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.025f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("RED");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("violetball");
        t.setStrongAgainst("redball");

        return t;
    }


    public Tower VioletTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);

        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(60);
        t.setAttack(80f);
        t.setRange(200f);
        t.setChargeRate(0.025f);
        t.setIncomeRate(4f);
        t.setOriginalCost(60);
        t.setOriginalAttack(80f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.025f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.08f);
        t.setID("VIOLET");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.10f);
        t.setCustomArc(40f);
        t.setWeakAgainst("yellowball");
        t.setStrongAgainst("violetball");

        return t;
    }

    public Tower YellowTower(Vector2 position, Sprite sprite, Sprite bullet, Tower t){

        //Tower t = new Tower(sprite, 80f, 0.025f, 150f, 60, 1f);
        t.set(sprite);
        t.setTarget(new Enemy());
        t.setPosition(position.x, position.y);
        t.setLevel(1);
        t.setBulletLimit(1);
        t.setCost(70);
        t.setAttack(80f);
        t.setRange(200f);
        t.setChargeRate(0.01f);
        t.setIncomeRate(4f);
        t.setOriginalCost(70);
        t.setOriginalAttack( 80f);
        t.setOriginalRange(200f);
        t.setOriginalChargeRate(0.01f);
        t.setOriginalIncomeRate(4f);
        t.state = true;
        t.setFireRate(0.4f);
        t.setID("YELLOW");
        t.setBulletSprite(bullet);
        t.setBulletRate(0.08f);
        t.setCustomArc(50f);
        t.setWeakAgainst("redball");
        t.setStrongAgainst("yellowball");
        t.setRotateRate(5f);

        return t;
    }









}
