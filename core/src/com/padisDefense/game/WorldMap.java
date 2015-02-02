package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.TransitionScreens.Splash;


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
    Array<Image> lockedStatus;

    SpriteBatch batch;
    Stage stage;

    public WorldMap(Padi p){
        padi = p;
    }

    @Override
    public void show(){

        /*if(!padi.assets.rain.isPlaying())
            padi.assets.rain.play();*/
        batch = padi.assets.batch;
        background_texture = new Texture("worldmap.png");
        buttons = new Array<TextButton>();
        lockedStatus = new Array<Image>();

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
        for(int x = 0; x < padi.player.getNumberOfLevels() + 2; x++){
            final int g = x+1;
            //Adds a button. The last two buttons are 'menu' and 'store'.
            if(x < padi.player.getNumberOfLevels()) {
                buttons.add(new TextButton(String.valueOf(x + 1), padi.assets.bubbleUI, "green"));



                buttons.get(x).addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent e, float a, float b) {

                        if(padi.player.isLevelUnlocked(g)){
                            padi.assets.rain.stop();

                            padi.gameScreen.initLevel(g);

                            padi.setScreen(new Splash(padi, padi.gameScreen));
                            //padi.setScreen(padi.gameScreen);
                            Gdx.input.setInputProcessor(null);
                        }
                        else{
                            System.out.println("No No No! No Bueno!");
                        }

                    }
                });
            }
            else if(x == padi.player.getNumberOfLevels()) {
                buttons.add(new TextButton(" menu ", padi.assets.bubbleUI, "yellow"));
                buttons.get(x).addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent e, float a, float b){
                        padi.setScreen(padi.main_menu);
                    }
                });
            }
            else if(x == padi.player.getNumberOfLevels() + 1) {
                buttons.add(new TextButton(" store ", padi.assets.bubbleUI, "yellow"));
                buttons.get(x).addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent e, float a, float b){
                        padi.setScreen(padi.store);
                    }
                });
            }

            //This sets the dimensions and color of the buttons.
            buttons.get(x).setSize(100f, 50f);

        }//end for-loop

        //TODO: Get rid of hard code
        //This is to position the buttons. Each button is 50f lower than the previous one.
        buttons.get(0).setPosition(10f, 10f);
        buttons.get(1).setPosition(110f, 80f);
        buttons.get(2).setPosition(210f, 150f);
        buttons.get(3).setPosition(310f, 220f);
        buttons.get(4).setPosition(410f, 290f);
        buttons.get(5).setPosition(510f, 360f);
        buttons.get(6).setPosition(610f, 430f);
        buttons.get(7).setPosition(710f, 540f);
        buttons.get(8).setPosition(500f, 10f);//menu button
        buttons.get(9).setPosition(650f, 10f);//store button



        //displays the locked/unlocked symbol next to level button.
        //'buttons' contains all the buttons, so
        //the -2 is for the menu button and store button. Those don't need locked/unlocked status.
        for(int x = 0; x < buttons.size - 2; x++){

            Image s;
            if(padi.player.levels[x]){
                s = new Image(padi.assets.bubbleUI.getRegion("unlocked"));
            }
            else{
                s = new Image(padi.assets.bubbleUI.getRegion("locked"));
            }
            s.setSize(buttons.get(0).getHeight(), buttons.get(0).getHeight());

            lockedStatus.add(s);

            //setting the status's position to be to the right of buttons.
            lockedStatus.get(x).setPosition(buttons.get(x).getX() + buttons.get(x).getWidth(),
                    buttons.get(x).getY());
        }



        for(int x = 0; x < buttons.size; x++)
            stage.addActor(buttons.get(x));

        for(int x = 0; x < lockedStatus.size; x++)
            stage.addActor(lockedStatus.get(x));

        Gdx.input.setInputProcessor(stage);
    }//End of Worldmap() Constructor.

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for(int x = 0; x < buttons.size; x++)
            buttons.get(x).draw(padi.assets.batch, 1);//Draw buttons.

        for(int x = 0; x < lockedStatus.size; x++)
            lockedStatus.get(x).draw(padi.assets.batch, 1);

        batch.end();
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
