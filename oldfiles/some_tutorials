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



import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;

import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TEST2 extends ScreenAdapter {
    Object[] listEntries = {"This is a list entry1", "And another one1", "The meaning of life1", "Is hard to come by1",
            "This is a list entry2", "And another one2", "The meaning of life2", "Is hard to come by2", "This is a list entry3",
            "And another one3", "The meaning of life3", "Is hard to come by3", "This is a list entry4", "And another one4",
            "The meaning of life4", "Is hard to come by4", "This is a list entry5", "And another one5", "The meaning of life5",
            "Is hard to come by5"};

    Skin skin;
    Stage stage;
    Texture texture1;
    Texture texture2;
    Label fpsLabel;

    @Override
    public void show () {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        texture1 = new Texture(Gdx.files.internal("test1.png"));
        texture2 = new Texture(Gdx.files.internal("badlogic.jpg"));
        TextureRegion image = new TextureRegion(texture1);
        TextureRegion imageFlipped = new TextureRegion(image);
        imageFlipped.flip(true, true);
        TextureRegion image2 = new TextureRegion(texture2);
        // stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, new PolygonSpriteBatch());
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Group.debug = true;

        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(imageFlipped);
        ImageButton iconButton = new ImageButton(style);

        Button buttonMulti = new TextButton("Multi\nLine\nToggle", skin, "toggle");
        Button imgButton = new Button(new Image(image), skin);
        Button imgToggleButton = new Button(new Image(image), skin, "toggle");

        Label myLabel = new Label("this is some text.", skin);
        myLabel.setWrap(true);

        Table t = new Table();
        t.row();
        t.add(myLabel);

        t.layout();

        final CheckBox checkBox = new CheckBox(" Continuous rendering", skin);
        checkBox.setChecked(true);
        final Slider slider = new Slider(0, 10, 1, false, skin);
        slider.setAnimateDuration(0.3f);
        TextField textfield = new TextField("", skin);
        textfield.setMessageText("Click here!");
        final SelectBox dropdown = new SelectBox(skin);
        dropdown.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println(dropdown.getSelected());
            }
        });
        dropdown.setItems("Android1", "Windows1", "Linux1", "OSX1", "Android2", "Windows2", "Linux2", "OSX2", "Android3",
                "Windows3", "Linux3", "OSX3", "Android4", "Windows4", "Linux4", "OSX4", "Android5", "Windows5", "Linux5", "OSX5",
                "Android6", "Windows6", "Linux6", "OSX6", "Android7", "Windows7", "Linux7", "OSX7");
        dropdown.setSelected("Linux6");
        Image imageActor = new Image(image2);
        ScrollPane scrollPane = new ScrollPane(imageActor);
        List list = new List(skin);
        list.setItems(listEntries);
        list.getSelection().setMultiple(true);
        list.getSelection().setRequired(false);
        // list.getSelection().setToggle(true);
        ScrollPane scrollPane2 = new ScrollPane(list, skin);
        scrollPane2.setFlickScroll(false);
        SplitPane splitPane = new SplitPane(scrollPane, scrollPane2, false, skin, "default-horizontal");
        fpsLabel = new Label("fps:", skin);

        // configures an example of a TextField in password mode.
        final Label passwordLabel = new Label("Textfield in password mode: ", skin);
        final TextField passwordTextField = new TextField("", skin);
        passwordTextField.setMessageText("password");
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setPasswordMode(true);

        // window.debug();
        Window window = new Window("Dialog", skin);
        window.getButtonTable().add(new TextButton("X", skin)).height(window.getPadTop());
        window.setPosition(0, 0);
        window.defaults().spaceBottom(10);
        window.row().fill().expandX();
        window.add(iconButton);
        window.add(buttonMulti);
        window.add(imgButton);
        window.add(imgToggleButton);
        window.row();
        window.add(checkBox);
        window.add(slider).minWidth(100).fillX().colspan(3);
        window.row();
        window.add(dropdown);
        window.add(textfield).minWidth(100).expandX().fillX().colspan(3);
        window.row();
        window.add(splitPane).fill().expand().colspan(4).maxHeight(200);
        window.row();
        window.add(passwordLabel).colspan(2);
        window.add(passwordTextField).minWidth(100).expandX().fillX().colspan(2);
        window.row();
        window.add(fpsLabel).colspan(4);
        window.pack();

        // stage.addActor(new Button("Behind Window", skin));
        stage.addActor(window);

        textfield.setTextFieldListener(new TextFieldListener() {
            public void keyTyped (TextField textField, char key) {
                if (key == '\n') textField.getOnscreenKeyboard().show(false);
            }
        });

        slider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.log("UITest", "slider: " + slider.getValue());
            }
        });

        iconButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                new Dialog("Some Dialog", skin, "dialog") {
                    protected void result (Object object) {
                        System.out.println("Chosen: " + object);
                    }
                }.text("Are you enjoying this demo?").button("Yes", true).button("No", false).key(Keys.ENTER, true)
                        .key(Keys.ESCAPE, false).show(stage);
            }
        });

        checkBox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.graphics.setContinuousRendering(checkBox.isChecked());
            }
        });
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
        texture1.dispose();
        texture2.dispose();
    }
}
/*
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
*/