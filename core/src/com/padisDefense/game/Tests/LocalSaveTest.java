package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.padisDefense.game.Items.MainItem;
import com.padisDefense.game.LoadSaveGame;
import com.padisDefense.game.Player;
import com.padisDefense.game.Towers.Tower;


public class LocalSaveTest extends ScreenAdapter {

    SpriteBatch batch;

    Sprite sprite;


    Json json;
    LoadSaveGame loadsave;
    Player player;
    public LocalSaveTest(){

    }

    @Override
    public void show(){

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("duck.png"));
        sprite.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        json = new Json();



        /*player = new Player();
        loadsave = new LoadSaveGame();
        MainItem item1 = new MainItem("items/coffee.png") {
            @Override
            public void update(Tower t) {
                changeRange(t, 1.1f);
            }
        };
        item1.setCost(150);
        item1.addTargets("speed");
        item1.setDisplayStats("+ 10% Range\n - 5% Attack\n");
        item1.setName("coffee");

        player.addItemsUnlocked(item1);
        player.setName("FROM SAVEAOAIJEF");
        loadsave.savePlayer(player);
        //System.out.println(json.prettyPrint(player));
        System.out.println("bought: " + player.getItemsUnlocked().get(0).getName());
*/

        player = loadsave.loadPlayer();

        System.out.println(player.getName());



    }

    @Override
    public void render(float delta){


        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
}
