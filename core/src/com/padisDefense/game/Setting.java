package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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



    final Padi padi;
    private Stage stage;

    public Array<Slider> slider;
    public Array<TextureRegion> textures;//SOUND, DIFFICULTY, SPEED.
    public Array<Image> images;//SOUND, DIFFICULTY, SPEED.
    public Array<Label> values;


    //These rectangles form a boundary around screen.
    public Rectangle top, bottom, left, right;



    //TODO: get rid of random pictures here.
    //It will be stored in this array.
    Array<String> names;
    //Name of pictures for textures. Currently, random pictures are assigned.


    public Setting(Padi p){
        padi = p;
    }
    @Override
    public void show(){
        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();
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

        TextureRegionDrawable textureBar;
        ProgressBar.ProgressBarStyle bar_style;
        textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("limegreen.png"))));
        bar_style = new ProgressBar.ProgressBarStyle(padi.assets.skin.newDrawable("white", Color.CYAN), textureBar);
        slider = new Array<Slider>();
        textures = new Array<TextureRegion>();
        images = new Array<Image>();
        values = new Array<Label>();


        textures.add(padi.assets.skin3.getRegion("SYMB_VOLUME"));
        textures.add(padi.assets.skin3.getRegion("SYMB_VOLUME"));
        textures.add(padi.assets.skin3.getRegion("SYMB_VOLUME"));
        //slider[0] corresponds with texture[0] and images[0], etc.
        for(int x = 0; x < 3; x++){
            slider.add(new Slider(1,100,1,false,padi.assets.skin));

            images.add(new Image(textures.get(x)));
            values.add(new Label("stuff", padi.assets.skin));

            slider.get(x).setSize(250f,60f);

            //Getting the proper values from assets
            if(x == 0) slider.get(x).setValue(padi.assets.getDifficulty());
            else if (x == 1) slider.get(x).setValue(padi.assets.getSoundLevel());

            images.get(x).setSize(150f,60f);
            values.get(x).setSize(50f, 30f);
        }


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
        for(int x = 0; x < 3; x++){
            if (x == 0) {//first one. image is to the left. value output is to the right.
                slider.get(x).setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight()*2 / 3);
                images.get(x).setPosition(slider.get(x).getX() - 160f, slider.get(x).getY());
                values.get(x).setPosition(slider.get(x).getRight() + 20f, slider.get(x).getY()+(images.get(x).getHeight()/2-values.get(x).getHeight()/2));
            }
            else {//the other two.
                slider.get(x).setPosition(slider.get(x - 1).getX(),
                        slider.get(x - 1).getY() - slider.get(x - 1).getHeight() - 40f);

                images.get(x).setPosition(slider.get(x).getX() - 160f, slider.get(x).getY());
                values.get(x).setPosition(slider.get(x).getRight() + 20f, slider.get(x).getY()+(images.get(x).getHeight()/2-values.get(x).getHeight()/2));

            }
        }

        back_button.setPosition(Gdx.graphics.getWidth() / 2 - 75f, 30f);
        back_button.setSize(250f, 60f);





        stage.addActor(background);
        stage.addActor(foreground);

        for(int x = 0; x < 3; x++){
            foreground.addActor(slider.get(x));
            foreground.addActor(images.get(x));
            foreground.addActor(values.get(x));
        }

        foreground.addActor(back_button);


        //setting the input to this stage on this screen.
        Gdx.input.setInputProcessor(stage);

    }//end show();


    /*@Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }*/


    //Save settings into assets.
    //The '0' and '2' values are just arbitrary right now.
    public void saveSettings(){
        padi.assets.setDifficulty((int)slider.get(2).getVisualValue());
        padi.assets.setSoundLevel((int)slider.get(0).getVisualValue());
        padi.assets.setOriginalSoundLevel((int)slider.get(0).getVisualValue());
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

        for(int x = 0; x < 3; x++){

            //new Rectangle(x-coord, y-coord, width, height).
            covers.add(new Rectangle(images.get(x).getX(), images.get(x).getY(),
            images.get(x).getWidth() + 50f + slider.get(x).getWidth(), images.get(x).getHeight()));

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
            images.get(x).setPosition(slider.get(x).getX() - 50f - images.get(x).getWidth(),
                    slider.get(x).getY());

            values.get(x).setPosition(slider.get(x).getRight() + 20f, slider.get(x).getY()+(images.get(x).getHeight()/2-values.get(x).getHeight()/2));

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

    public void bounce(){
        Array<Rectangle> covers = new Array<Rectangle>();

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
