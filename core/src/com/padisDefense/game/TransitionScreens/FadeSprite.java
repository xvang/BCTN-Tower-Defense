/**
 * Author: Xeng Vang
 * */

package com.padisDefense.game.TransitionScreens;


import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Fades a Sprite object.
 * */
public class FadeSprite implements TweenAccessor<Sprite> {

    public static final int ALPHA = 0;
    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues){

        switch(tweenType){
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            default:
                assert false;
        }

    }

    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues){

        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false;
                return -1;

        }
    }
}
