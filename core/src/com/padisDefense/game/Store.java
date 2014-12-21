package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

//TODO: update money. Have clickable items in store. etc.
public class Store extends ScreenAdapter{
    Padi padi;
    public Store(Padi p){padi = p;}
    private Stage stage;

    private final int size = 11;
    private Array<Image> imageList;

    private Label money;
    @Override public void show() {

        stage = new Stage();
        imageList = new Array<Image>();
        TextButton menu = new TextButton("Back to Menu", padi.skin);



        //array of images.
        for(int x = 0; x < size; x++)
            imageList.add(new Image(new Texture("badlogic.jpg")));

        //adding clicklisteners to images.
        //It is in another function to avoid cluttering.
        addListeners();

        final Table imageTable = new Table();
        for(int x = 0; x < size; x++){
            imageTable.add(imageList.get(x)).pad(20f);
            if(x % 3 == 0)
                imageTable.row();
        }


        //making the scroll thing, and adding it to table.
        final ScrollPane scrollPane = new ScrollPane(imageTable);
        final Table t = new Table();
        t.setFillParent(true);
        t.add(scrollPane).padRight(150f);

        //background image.
        final Image background = new Image(new Texture("test9.png"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //display user's money and menu button
        money = new Label(String.valueOf((int)padi.player.getMoney()) + " credits", padi.skin);
        money.setPosition(Gdx.graphics.getWidth() - 100f,Gdx.graphics.getHeight()*3 / 4);
        menu.setSize(80f, 50f);
        menu.setPosition(money.getX(), money.getY() - (menu.getHeight() + 20f));
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                padi.setScreen(padi.main_menu);
            }
        });


        stage.addActor(background);
        stage.addActor(money);
        stage.addActor(menu);
        stage.addActor(t);

        Gdx.input.setInputProcessor(stage);
    }

    @Override public void render(float delta) {
        this.stage.act();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
    }


    //adding the popup box to the images.
    //popup box shows a short description
    //and option to unlock.
    public void addListeners(){

        for(int x = 0; x < size; x ++){

            imageList.get(x).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent e, float x, float y){

                }
            });

        }
    }




    @Override
    public void dispose(){}
}

/**
 * http://stackoverflow.com/questions/15484077/libgdx-and-scrollpane-with-multiple-widgets
 * */