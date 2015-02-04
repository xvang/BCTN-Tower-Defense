package com.padisDefense.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Enemies.Ball;
import com.padisDefense.game.Enemies.BallStorage;
import com.padisDefense.game.Pathing.PathStorage;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.BuildableSpotSpawnStorage;


public class BuildableSpotSpawnTest extends ScreenAdapter {



    SpriteBatch batch;


    Array<BuildableSpot> billy;
    BuildableSpotSpawnStorage storage;
    BallStorage ballStorage;


    private PathStorage pathStorage;
    private Array<Path<Vector2>> currentPath;

    Ball ball;
    Skin skin_balls;

    int level;
    public BuildableSpotSpawnTest(){
        batch = new SpriteBatch();
        pathStorage = new PathStorage();

        level = 6;
        //retrieves the path for level __
        currentPath = pathStorage.getPath(level);
        //to get the corresponding bs spots, go to addBS() and change parameter accordingly.

        ballStorage = new BallStorage();
        skin_balls = new Skin(new TextureAtlas("enemies/balls/ball.pack"));

        ball = new Ball();
        ballStorage.createBall("redball", ball);
        ball.setSize(40f, 40f);


        ball.setCurrentPath(0);


        billy = new Array<BuildableSpot>();
        storage = new BuildableSpotSpawnStorage();


        addBuildableSpots();
        //loadBuildableSpots();
    }


    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();


        for(int x = 0; x < billy.size; x++){
            billy.get(x).draw(batch);
        }

        run();
        batch.end();
    }


    public void run(){



        Vector2 out = new Vector2();

        currentPath.get(ball.getCurrentPath()).valueAt(out, ball.getTime());

        ball.setPosition(out.x, out.y);
        ball.draw(batch);

        ball.setTime(ball.getTime() + Gdx.graphics.getDeltaTime()*2);

        if(ball.getTime() >= 1f){

            //If not end of path, move on to next segment of path.
            if(ball.getCurrentPath() + 1 < currentPath.size){
                ball.setCurrentPath(ball.getCurrentPath() + 1);
            }

            //reached end of path.
            else{
                ball.setCurrentPath(0);
            }

            ball.setTime(0f);
        }


    }


    final float w = Gdx.graphics.getWidth()/100;
    final float h = Gdx.graphics.getHeight()/100;
    public void addBuildableSpots(){

        //Add the buildable spots in this format:
        //billy.add(new BuildableSpot(new Vector2()));

        billy.add(new BuildableSpot(new Vector2()));


    }

    public void loadBuildableSpots(){

        Array<Vector2> positions = storage.getBuildableLocations(level);

        for(int x = 0; x < positions.size; x++){
            billy.add(new BuildableSpot(positions.get(x)));
        }

    }
}
