package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
 * @param 'padi'
 * */
public class Setting extends ScreenAdapter {


    final Padi padi;
    private Stage stage;
    Group background, foreground;



    TextureRegionDrawable textureBar;
    ProgressBar.ProgressBarStyle bar_style;



    public Array<Slider> slider;
    public Array<Texture> textures;//SOUND, DIFFICULTY, SPEED.
    public Array<Image> images;//SOUND, DIFFICULTY, SPEED.


    //These rectangles form a boundary around screen.
    public Rectangle top, bottom, left, right;



    //TODO: get rid of random pictures here.
    //It will be stored in this array.
    Array<String> names;
    //Name of pictures for textures. Currently, random pictures are assigned.



    public Setting(Padi p){ padi = p;}

    @Override
    public void show(){

        top = new Rectangle();
        bottom = new Rectangle();
        left = new Rectangle();
        right = new Rectangle();

        top.setSize(Gdx.graphics.getWidth(), 10f);
        bottom.setSize(Gdx.graphics.getWidth(), 10f);
        left.setSize(10f, Gdx.graphics.getHeight());
        right.setSize(10f, Gdx.graphics.getHeight());

        top.setPosition(0, Gdx.graphics.getHeight());
        bottom.setPosition(0, 0-bottom.getHeight());
        left.setPosition(0-left.getWidth(), 0);
        right.setPosition(Gdx.graphics.getWidth()+right.getWidth(),0);







        stage = new Stage();

        background = new Group();
        foreground = new Group();
        textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("limegreen.png"))));
        bar_style = new ProgressBar.ProgressBarStyle(padi.skin.newDrawable("white", Color.CYAN), textureBar);
        slider = new Array<Slider>();
        textures = new Array<Texture>();
        images = new Array<Image>();


        //slider[0] corresponds with texture[0] and images[0], etc.
        for(int x = 0; x < 3; x++){
            slider.add(new Slider(1,100,1,false,padi.skin));
            textures.add(new Texture((String)padi.assets.getRandomPic()));
            images.add(new Image(textures.get(x)));

            slider.get(x).setSize(250f,60f);
            slider.get(x).setValue(10);
            images.get(x).setSize(150f,60f);
        }


        //'return to menu' button
        final TextButton back_button = new TextButton("Back", padi.skin, "default");


        //on click, return to menu.
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                padi.setScreen(padi.main_menu);
            }
        });



        //First slider is at set position. Proceeding sliders are located based on previous one.
        for(int x = 0; x < 3; x++){
            if (x == 0) {//first one
                slider.get(x).setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight()*2 / 3);
                images.get(x).setPosition(slider.get(x).getX() - 160f, slider.get(x).getY());
            }
            else {//the other two.
                slider.get(x).setPosition(slider.get(x - 1).getX(),
                        slider.get(x - 1).getY() - slider.get(x - 1).getHeight() - 40f);

                images.get(x).setPosition(slider.get(x).getX() - 160f, slider.get(x).getY());

            }
        }

        back_button.setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight() / 2 - 240f);






        stage.addActor(background);
        stage.addActor(foreground);

        for(int x = 0; x < 3; x++){
            foreground.addActor(slider.get(x));
            foreground.addActor(images.get(x));
        }

        foreground.addActor(back_button);


        //setting the input to this stage on this screen.
        Gdx.input.setInputProcessor(stage);

    }//End show().


    //Save settings into assets.
    //The '0' and '2' values are just arbitrary right now.
    public void saveSettings(){
        padi.assets.setDifficulty((int)slider.get(2).getVisualValue());
        padi.assets.setSoundLevel((int)slider.get(0).getVisualValue());

    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);


        saveSettings();

        wander();
        padi.batch.begin();
        //padi.background.draw(padi.batch);
        stage.draw();
        padi.batch.end();
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
            if(temp == 0){slider.get(x).moveBy(((float)Math.random()*2) - 1f, ((float)Math.random()*2) - 1f);}

            //touched top
            if (temp == 1){slider.get(x).moveBy(((float)Math.random()*2) - 1f, -(((float)Math.random()*2 + 1f)));}

            //touched bottom
            if (temp == 2){slider.get(x).moveBy(((float)Math.random()*2) - 1f, ((float)(Math.random()*2)) + 1f);}

            //touched left
            if (temp == 3){slider.get(x).moveBy((float)(Math.random()*2) + 1f, (float)(Math.random()*2) - 1f);}

            //touched right
            if (temp == 4){slider.get(x).moveBy(-((float)(Math.random()*2 + 1f)), ((float)(Math.random()*2)) - 1f);}

            //Wherever sliders are, corresponding images should be 50f to the left.
            images.get(x).setPosition(slider.get(x).getX() - 50f - images.get(x).getWidth(),
                    slider.get(x).getY());

        }//End for-loop.


    }//End function.

    /**Used with wander(). Checks Rectangle for collision with Bounding boxes.
     *
     * @param 'r'
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
    }

    @Override
    public void pause(){
        super.pause();
    }


}
