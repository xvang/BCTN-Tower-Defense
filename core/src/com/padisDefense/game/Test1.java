package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

public class Test1 extends ScreenAdapter implements InputProcessor {


    SpriteBatch batch;

    //Sprite is the object that I will move.
    //You can use pretty much anything else.
    //I used a picture of a shield.
    Sprite shield;

    //destination is where we want the sprite to move to.
    Vector2 destination;

    //h is explained later down.
    int h = Gdx.graphics.getHeight();

    //also explained later down.
    float time = 0f;

    public Test1(){

        batch = new SpriteBatch();

        shield = new Sprite(new Texture("items/shield.png"));

        //random starting position. I chose the center of screen.
        shield.setPosition(Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/2);


        //destination is where you want the object to go.
        //For example, when you touch the screen, the coordinates of where you touch
        //is stored in 'destination'.
        destination = new Vector2();




        //Don't forget to set the input to this(Test1) object.
        Gdx.input.setInputProcessor(this);
    }

    //used to draw a line.
    float val = 0f;


    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(shield, 20f, 20f);
        //shield.draw(batch, 20f, 20f);

        batch.end();

        if(!arrivedAtDestination()){
            moveTheSprite();
        }

        //You can adjust how fast the shield travels by adjusting how much you
        //increase time by.
        time+= Gdx.graphics.getDeltaTime()/5;


    }


    //slowly moves the sprite.
    public void moveTheSprite(){

        //'newLocation' will store the new location of the shield.
        Vector2 newLocation = new Vector2();

        //stores current location of shield.
        Vector2 currentLocation = new Vector2(shield.getX(), shield.getY());

        Bezier<Vector2> path = new Bezier<Vector2>(currentLocation, destination);
        //Bezier is just a fancy way to say "path".
        //Here we create a path that starts at the current location,
        //and ends at the destination(where you touched the screen).
        //You should read up on libgdx splices.
        //You can add another vector in there to make things curve. For example, (currentLocation, [3rd vector], destination).
        //You can connect the paths. One path connects A to B, another connects B to C, and so on.




        //At time 1f, the valueAt() function stores the coordinates of the end of the path in the 'newLocation' that we passed in.
        //At time 0f, it stores the coords at the beginning of the path into 'newLocation'.
        //So the domain of a path is [0, 1f]. This is why we reset the 'time' variable each time 'destination' has a new value.
        path.valueAt(newLocation, time);


        //making the shield go to the new location.
        shield.setPosition(newLocation.x, newLocation.y);

        //Or you could set the center of the shield to be at that location.
        //shield.setCenter(newLocation.x, newLocation.y);
    }


    //if the coordinates of 'shield' and 'destination' is equal, then it returns true.
    public boolean arrivedAtDestination(){
        return shield.getX() == destination.x && shield.getY() == destination.y;
    }


    //The following function have to be overridden when you are implementing the InputProcessor interface.
    //I rarely use them all. The only one I use mostly is touchedDown().
    //There's probably a better way to implement user input. This is just the way I like it.
    //To try out dragging, you can uncomment the stuff in the touchDragged() function.
    @Override
    public void resize(int x, int y){

    }

    @Override
    public void hide(){

    }
    @Override
    public void pause(){}

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        // System.out.println("clicked at: " + x + ", " + y);


        //very important to rest the time variable.
        //When you touch somewhere, you're telling the shield to go somewhere new,
        //so a new path is created, and you should reset the time to zero.
        time = 0f;

        //Now we have our coordinates. We set it as our destination.
        //I don't know why, but the y-coordinate is funky.
        //(0,0) is apparently at the top left, instead of bottom left.
        destination.set(x,h-y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //System.out.println("Current drag location: " + screenX + ", " + screenY);
        //destination.set(screenX, h - screenY);
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {return false;}

    @Override
    public boolean scrolled(int amount) {return false;}

    @Override
    public boolean keyDown(int keycode) {return false;}

    @Override
    public boolean keyUp(int keycode) {return false;}

    @Override
    public boolean keyTyped(char character) {return false;}

}