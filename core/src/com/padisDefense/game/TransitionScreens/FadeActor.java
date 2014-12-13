

package com.padisDefense.game.TransitionScreens;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Fades an Actor object.
 *
 * */
public class FadeActor implements TweenAccessor<Actor> {

    public static final int ALPHA = 0, Y = 2;
    @Override
    public void setValues(Actor target, int tweenType, float[] newValues){

        switch(tweenType){
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            case Y:
                target.setY(newValues[0]);
                break;
            default:
                assert false;
        }

    }

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues){

        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            case Y:
                returnValues[0] = target.getY();
                return 1;

            default:
                assert false;
                return -1;

        }
    }
}
