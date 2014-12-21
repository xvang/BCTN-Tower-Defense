package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class TEST2 extends ScreenAdapter{


    Stage stage;
    Image image, image_false;
    Table table;

    @Override
    public void show(){
        stage = new Stage();
        image = new Image(new Texture("strengthtower.png"));
        image_false = new Image(new Texture("strengthtower.png"));

        final Vector2 oldLocation = new Vector2();
        table = new Table();

        //image.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        //image_false.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 5);

        table.add(image_false).row();
        table.add(image).row();

        table.setSize(100f, 200f);
        table.setPosition(800f, 100f);
        oldLocation.set(image.getX(), image.getY());
        image.addListener(new DragListener(){
            @Override
            public void drag(InputEvent e, float x, float y, int pointer){
                image.setCenterPosition(image.getX() + x, image.getY() + y);
                image_false.setColor(Color.RED);
            }

            @Override
            public void dragStop(InputEvent e, float x, float y, int pointer){
                image.setPosition(oldLocation.x, oldLocation.y);
                System.out.println("Image: " + image.getX() + "  " + image.getY());
            }
        });

        stage.addActor(table);
        //stage.addActor(image);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        stage.draw();
    }


}












/**public class TEST2 extends ScreenAdapter {
    Stage stage;
    final Image image = new Image(new Texture("icetower.png"));

    @Override
    public void show () {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        final Skin skin = new Skin();
        skin.add("default", new LabelStyle(new BitmapFont(), Color.GREEN));
        skin.add("badlogic", new Texture("badlogic.jpg"));

        Image sourceImage = new Image(skin, "badlogic");
        sourceImage.setBounds(50, 125, 100, 100);
        stage.addActor(sourceImage);

        Image validTargetImage = new Image(skin, "badlogic");
        validTargetImage.setBounds(200, 50, 100, 100);
        stage.addActor(validTargetImage);

        Image invalidTargetImage = new Image(skin, "badlogic");
        invalidTargetImage.setBounds(200, 200, 100, 100);
        stage.addActor(invalidTargetImage);

        DragAndDrop dragAndDrop = new DragAndDrop();

        dragAndDrop.addSource(new Source(sourceImage) {
            public Payload dragStart (InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
                payload.setObject("Some payload!");

                payload.setDragActor(image);

                Label validLabel = new Label("Some payload!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some payload!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);
                System.out.println("RUNRUNRUN");
                return payload;
            }
        });
        dragAndDrop.addTarget(new Target(validTargetImage) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.GREEN);
                return true;
            }

            public void reset (Source source, Payload payload) {
                //getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
                System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
            }
        });
        dragAndDrop.addTarget(new Target(invalidTargetImage) {
            public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.RED);
                return false;
            }

            public void reset (Source source, Payload payload) {
                //getActor().setColor(Color.WHITE);
            }

            public void drop (Source source, Payload payload, float x, float y, int pointer) {
                System.out.println("REJECTED");
            }
        });


    }


    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose () {
        stage.dispose();
    }
}*/