

package com.padisDefense.game.TransitionScreens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.padisDefense.game.Padi;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Transition screen between the actual screens.
 *
 * @author Xeng Vang
 *
 * @param 'padi'
 * **/
public class Splash extends ScreenAdapter {


    private Sprite sprite;
    private Padi padi;
    private TweenManager tweenManager;
    Screen newScreen;

    //Constructor
    public Splash(Padi p, Screen s){
        padi = p;
        newScreen = s;
    }


    @Override
    public void show(){

        //Gets a random picture, put that in a new Texture,
        //and put the texture in a sprite.
        sprite = new Sprite(padi.assets.getRandomPic());

        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tweenManager = new TweenManager();

        Tween.registerAccessor(Sprite.class, new FadeSprite());

        //Fading splash screen.
        Tween.set(sprite, FadeSprite.ALPHA).target(0).start(tweenManager);
        Tween.to(sprite, FadeSprite.ALPHA, 0).target(1).repeatYoyo(1,1).setCallback(
                new TweenCallback(){
                    @Override
                public void onEvent(int type, BaseTween<?> source){
                        try{
                            Thread.sleep(1000);
                        }catch(Exception e){
                           System.out.println("Will never need try-catch because I'm using a single thread.");
                        }
                        padi.setScreen(newScreen);
                    }
                }).start(tweenManager);



       // Gdx.input.setInputProcessor(null);
    }


    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tweenManager.update(delta);

        //Begin rendering.
        padi.assets.batch.begin();
        sprite.draw( padi.assets.batch);
        padi.assets.batch.end();

       /* if (Gdx.input.justTouched()){
            padi.setScreen(padi.main_menu);
        }*/

    }




    @Override
    public void dispose(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void resize(int x, int y){
        sprite.setSize(x,y);
    }
}
