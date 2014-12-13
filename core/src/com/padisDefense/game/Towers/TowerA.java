package com.padisDefense.game.Towers;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TowerA extends MainTower{

    //super(attack, range, charge rate, income rate, state, fire rate, ID).
    public TowerA(Vector2 position){
        super(2,100f,3,4,true,0.1f, "A");
        setPosition(position.x, position.y);
        setTexture(new Texture("test9.png"));
        setSize(30f, 30f);
    }



}

