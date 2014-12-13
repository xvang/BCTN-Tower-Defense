package com.padisDefense.game.Towers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TowerB extends MainTower {


    //super(attack, range, charge rate, income rate, state, fire rate, ID).
    public TowerB(Vector2 position){
        super(20,150f,3,4,true,0.05f, "B");
        setPosition(position.x, position.y);
        setTexture(new Texture("test8.png"));
        setSize(30f, 30f);
        setBulletLimit(10);
    }
}
