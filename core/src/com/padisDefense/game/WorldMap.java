package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


/**
 * @author Xeng
 *
 *
 *
 * */
public class WorldMap implements Screen {

    private Padi padi;



    Texture background_texture;

    Array<TextButton> buttons;

    Sprite background;
    Stage stage;

    public WorldMap(Padi p){
        padi = p;
    }

    @Override
    public void show(){

        background_texture = new Texture("worldmap.png");
        background = new Sprite(background_texture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setOriginCenter();
        buttons = new Array<TextButton>();
        stage = new Stage();


        /**
         * For-loop makes the buttons.
         * Buttons 0-8 open up a gamescreen. button 9 and 10 opens up store/menu.
         *
         * new TextButtons take parameters of (string value, skin, stylename).
         * Each button gets its own ClickListener.
         * When the buttons are clicked, the game level will open up, or the menu/store screen
         * The new GameScreen() takes in a game object(padi) and a int level.
         * 'x' is not final, so it couldn't be passed into GameScreen.
         * That's why [final int g] was created.
         * **/
        for(int x = 0; x < 11; x++){
            final int g = x+1;
            //Adds a button. The last two buttons are 'menu' and 'store'.
            if(x < 9) {
                buttons.add(new TextButton(String.valueOf(x + 1), padi.assets.skin2, "default"));


                buttons.get(x).addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent e, float a, float b) {

                        if(padi.player.isLevelUnlocked(g)){
                            /*padi.assets.gameScreen.assignLevel(g);
                            padi.assets.gameScreen.reset();
                            padi.setScreen(padi.assets.gameScreen);*/
                            padi.gameScreen.reset();
                            padi.gameScreen.setLevel(g);
                            padi.setScreen(padi.gameScreen);
                        }
                        else{
                            System.out.println("No No No! No Bueno!");
                        }

                    }
                });

            }
            else if(x == 9) {
                buttons.add(new TextButton(" menu ", padi.assets.skin2, "default"));
                buttons.get(x).addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent e, float a, float b){
                        padi.setScreen(padi.main_menu);
                    }
                });
            }
            else if(x == 10) {
                buttons.add(new TextButton(" store ", padi.assets.skin2, "default"));
                buttons.get(x).addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent e, float a, float b){
                        padi.setScreen(padi.store);
                    }
                });
            }


            //This sets the dimensions and color of the buttons.
            buttons.get(x).setSize(100f, 50f);
            //buttons.get(x).setColor(0.2f,0.3f, 0.8f, 0.8f);


        }

        //TODO: Get rid of hard code
        //This is to position the buttons. Each button is 50f lower than the previous one.
        buttons.get(0).setPosition(10f, 10f);
        buttons.get(1).setPosition(110f, 80f);
        buttons.get(2).setPosition(210f, 150f);
        buttons.get(3).setPosition(310f, 220f);
        buttons.get(4).setPosition(410f, 290f);
        buttons.get(5).setPosition(510f, 360f);
        buttons.get(6).setPosition(610f, 430f);
        buttons.get(7).setPosition(710f, 50f);
        buttons.get(8).setPosition(610f, 150f);
        buttons.get(9).setPosition(300f, 20f);
        buttons.get(10).setPosition(500f, 50f);

        for(int x = 0; x < buttons.size; x++)
            stage.addActor(buttons.get(x));

        Gdx.input.setInputProcessor(stage);
    }//End of Worldmap() Constructor.

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        padi.assets.batch.begin();

        background.draw(padi.assets.batch);//Draw background.

        for(int x = 0; x < buttons.size; x++)
            buttons.get(x).draw(padi.assets.batch, 3);//Draw buttons.

        padi.assets.batch.end();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }



    @Override
    public void hide() {

    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }




    @Override
    public void dispose(){

        stage.dispose();
        background_texture.dispose();
    }
}
