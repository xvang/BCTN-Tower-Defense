package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;


/**
 * This is the Settings class.
 *
 *
 * @author Xeng
 *
 * */
public class Setting extends ScreenAdapter {


    final int AMOUNT = 2;
    final Padi padi;
    private Stage stage;

    public Array<Slider> slider;

    public Array<Label> names;
    public Array<Label> values;


    //These rectangles form a boundary around screen.
    public Rectangle top, bottom, left, right;


    public Setting(Padi p){
        padi = p;
    }


    @Override
    public void show(){

        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();


        //Rectangles are used for the wandering() function.
        //It's more for cosmetic than functionality.
        top = new Rectangle();
        bottom = new Rectangle();
        left = new Rectangle();
        right = new Rectangle();

        top.setSize(w, 10f);
        bottom.setSize(w, 10f);
        left.setSize(10f, h);
        right.setSize(10f, h);

        top.setPosition(0, h);
        bottom.setPosition(0, 0-bottom.getHeight());
        left.setPosition(0-left.getWidth(), 0);
        //right.setPosition(w + right.getWidth(),0);
        right.setPosition(w, 0);


        stage = new Stage();


        Group background, foreground;
        background = new Group();
        foreground = new Group();

        slider = new Array<Slider>();

        names = new Array<Label>();
        values = new Array<Label>();

        //slider[0] corresponds with texture[0] and images[0], etc.
        for(int x = 0; x < AMOUNT; x++){
            slider.add(new Slider(1,100,1,false,padi.assets.skin));

            names.add(new Label("", padi.assets.someUIskin));
            values.add(new Label("", padi.assets.someUIskin));

            slider.get(x).setSize(250f,60f);

            //Getting the proper values from assets
            if(x == 0) slider.get(x).setValue(padi.assets.getDifficulty());
            else if (x == 1) slider.get(x).setValue(padi.assets.getSoundLevel());

            names.get(x).setSize(150f,60f);
            values.get(x).setSize(50f, 30f);
        }

        names.get(0).setText("Difficulty");
        names.get(1).setText("Sound");


        //'return to menu' button
        final TextButton back_button = new TextButton("Back to Menu", padi.assets.bubbleUI, "gray");


        //on click, return to menu.
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                padi.setScreen(padi.main_menu);
            }
        });



        //First slider is at set position. Proceeding sliders are located based on previous one.
        for(int x = 0; x < AMOUNT; x++){
            if (x == 0) {//first one. image is to the left. value output is to the right.
                slider.get(x).setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight()*2 / 3);
                names.get(x).setPosition(slider.get(x).getX() - 160f, slider.get(x).getY());
                values.get(x).setPosition(slider.get(x).getRight() + 20f, slider.get(x).getY()+(names.get(x).getHeight()/2-values.get(x).getHeight()/2));
            }
            else {//the other two.
                slider.get(x).setPosition(slider.get(x - 1).getX(),
                        slider.get(x - 1).getY() - slider.get(x - 1).getHeight() - 40f);

                names.get(x).setPosition(slider.get(x).getX() - 160f, slider.get(x).getY());
                values.get(x).setPosition(slider.get(x).getRight() + 20f, slider.get(x).getY()+(names.get(x).getHeight()/2-values.get(x).getHeight()/2));

            }
        }

        back_button.setPosition(Gdx.graphics.getWidth() / 2 - 75f, 30f);
        back_button.setSize(250f, 60f);

        stage.addActor(background);
        stage.addActor(foreground);

        for(int x = 0; x < AMOUNT; x++){
            foreground.addActor(slider.get(x));
            foreground.addActor(names.get(x));
            foreground.addActor(values.get(x));
        }

        foreground.addActor(back_button);


        //setting the input to this stage on this screen.
        Gdx.input.setInputProcessor(stage);

    }//end show();


    //Save settings into assets.
    //The '0' and '2' values are just arbitrary right now.
    public void saveSettings(){
        int sound = (int)slider.get(0).getVisualValue();
        padi.assets.setDifficulty(sound);
        padi.assets.setSoundLevel((int)slider.get(1).getVisualValue());
        padi.assets.setOriginalSoundLevel((int)slider.get(1).getVisualValue());

        padi.assets.star.setVolume(padi.assets.SOUND_LEVEL/100f);
        padi.assets.rain.setVolume(padi.assets.SOUND_LEVEL/100f);
        padi.assets.east.setVolume(padi.assets.SOUND_LEVEL/100f);

    }

    public void updateValues(){
        for(int x = 0; x < values.size; x++){
            values.get(x).setText(String.valueOf((int)slider.get(x).getVisualValue()));
        }
    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);


        saveSettings();
        updateValues();

        stage.draw();
        //wander();

    }




     /**This function makes the sliders and images slightly move around.*/
    public void wander() {

        Array<Rectangle> covers = new Array<Rectangle>();

        for(int x = 0; x < AMOUNT; x++){

            //new Rectangle(x-coord, y-coord, width, height).
            covers.add(new Rectangle(names.get(x).getX(), names.get(x).getY(),
                    names.get(x).getWidth() + 50f + slider.get(x).getWidth(), names.get(x).getHeight()));

            //Testing if covers overlaps any border rectangles.
            int temp = touched(covers.get(x));

            //touched nothing.
            if(temp == 0){slider.get(x).moveBy(((float)Math.random()*1) - 0.5f, ((float)Math.random()*1) - 0.5f);}

            //touched top
            if (temp == 1){slider.get(x).moveBy(((float)Math.random()*1) - 0.5f, -(((float)Math.random()*1) + 0.5f));}

            //touched bottom
            if (temp == 2){slider.get(x).moveBy(((float)Math.random()*1) - 0.5f, ((float)(Math.random()*1)) + 0.5f);}

            //touched left
            if (temp == 3){slider.get(x).moveBy((float)(Math.random()*1) + 0.5f, (float)(Math.random()*1) - 0.5f);}

            //touched right
            if (temp == 4){slider.get(x).moveBy(-((float)(Math.random()*1 + 0.5f)), ((float)(Math.random()*1)) - 0.5f);}

            //Wherever sliders are, corresponding images should be 50f to the left.
            names.get(x).setPosition(slider.get(x).getX() - 50f - names.get(x).getWidth(),
                    slider.get(x).getY());

            values.get(x).setPosition(slider.get(x).getRight() + 20f, slider.get(x).getY()+(names.get(x).getHeight()/2-values.get(x).getHeight()/2));

        }//End for-loop.


    }//End function.

    /**Used with wander(). Checks Rectangle for collision with Bounding boxes.
     *
     *
     * */
    public int touched(Rectangle r){

        if (r.overlaps(top)) return 1;
        else if(r.overlaps(bottom)) return 2;
        else if(r.overlaps(left)) return 3;
        else if (r.overlaps(right)) return 4;

        return 0;

    }

    @Override
    public void dispose(){
    }

    @Override
    public void hide(){
    }


    @Override
    public void resize(int width, int height){
        //System.out.println(width + "   " + height);
    }

    @Override
    public void pause(){
        super.pause();
    }


}
