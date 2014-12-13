package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.padisDefense.game.Pathing.MainPath;
import com.padisDefense.game.Pathing.PathStorage;
import com.badlogic.gdx.graphics.Color;


/**
 *
 * This is where I will mess around and try to create paths.
 *
 *
 * **/
public class TEST2 extends ScreenAdapter{


    Padi padi;
    public TEST2(Padi p){
        padi = p;
    }
    ImmediateModeRenderer20 renderer;
    private MainPath path;
    PathStorage storage;
    SpriteBatch batch;

    Slider slider;
    TextureRegionDrawable textureBar;
    ProgressBar.ProgressBarStyle bar_style;
    private float stepsize = 0f;


    @Override
    public void show(){
        renderer = new ImmediateModeRenderer20(false, false, 0);
        storage = new PathStorage();
        batch = new SpriteBatch();
        textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("limegreen.png"))));
        bar_style = new ProgressBar.ProgressBarStyle(padi.skin.newDrawable("white", Color.CYAN), textureBar);
        slider = new Slider(1, 100, 1, false, padi.skin);

        slider.setWidth(250f);
        slider.setHeight(60f);
        slider.setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight() / 2);
        //slider.setValue(50);
        slider.setAnimateDuration(5f);

        //change path here.
        path = new MainPath(storage.getPath(0));


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int x = 0; x < path.getPath().size; x++) {
            renderer.begin(batch.getProjectionMatrix(), GL20.GL_LINE_STRIP);
            Vector2 out = new Vector2();
            float val = 0f;
            while (val <= 1f) {
                renderer.color(0.5f, 0.1f, 0.4f, 0.7f);
                path.getPath().get(x).valueAt(out, val);
                renderer.vertex(out.x, out.y, 0);
                val += 0.001f;
            }
            renderer.end();
        }


        stepsize += Gdx.graphics.getDeltaTime();
        System.out.println(slider.getValue());

        if (stepsize < slider.getMaxValue())
            slider.setStepSize(1f);

        padi.batch.begin();
        slider.draw(padi.batch, 1);
        padi.batch.end();


    }

    @Override
    public void dispose(){
        batch.dispose();
        renderer.dispose();
        path.dispose();
    }
}
