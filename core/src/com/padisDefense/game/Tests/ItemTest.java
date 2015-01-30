package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Items.MainItem;

import javax.swing.ScrollPaneConstants;


public class ItemTest extends ScreenAdapter {


    Sprite sprite, sprite1, sprite2;
    MainItem item;
    SpriteBatch batch;
    Stage stage;
    public ItemTest(){

    }

    @Override
    public void show(){
        batch = new SpriteBatch();
        stage  = new Stage();


        Texture sheet = new Texture("items/Fantasy_Item_Collection.png");
        TextureRegion[][] splitter = TextureRegion.split(sheet, sheet.getWidth()/3, sheet.getHeight()/4);

        TextureRegion[] sp = new TextureRegion[3*4];

        int index = 0;
        for(int x = 0; x < 4; x++)
            for(int y = 0; y < 3; y++)
                sp[index++] = splitter[x][y];


        sprite = new Sprite(sp[9]);
        sprite1 = new Sprite(sp[3]);
        sprite2 = new Sprite(sp[5]);

        sprite.setPosition(Gdx.graphics.getWidth()*2/3, Gdx.graphics.getHeight()/2);
        sprite1.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
        sprite2.setPosition(Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/8);

        item = new MainItem(new Sprite(sp[6]));
        item.setPosition(0, 300f);

        Array<MainItem> items = new Array<MainItem>();

        for(int x = 0; x < 10; x++){
            items.add(new MainItem(new Sprite(sp[4])));
        }

        Table innerTable = new Table();

        for(int x = 0; x < items.size; x++){
            innerTable.add(new Image(items.get(x)));
            if(x % 3 == 0)
                innerTable.row();
        }


        final ScrollPane scrollPane = new ScrollPane(innerTable);

        final Table t = new Table();
        t.setFillParent(true);
        t.add(scrollPane);

        stage.addActor(t);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
