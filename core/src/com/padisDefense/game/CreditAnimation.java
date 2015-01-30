package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class CreditAnimation extends Image  {




    public Animation animation;          // #3
    Texture walkSheet;              // #4
    TextureRegion[] walkFrames;             // #5
    TextureRegion currentFrame;           // #7
    float stateTime;                                        // #8
    Vector2 loc, locInTable;

    public CreditAnimation(String file, int row, int col) {
        super(new Texture("end.png"));
        walkSheet = new Texture(Gdx.files.internal(file));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / col, walkSheet.getHeight() / row);
        walkFrames = new TextureRegion[col * row];
        int index = 0;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                walkFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation((1f/((float)(row*col))), walkFrames);

        /*System.out.println("before: " + this.getWidth() + ", " + this.getHeight() + "....."+ walkFrames[0].getRegionHeight());
        this.setHeight(walkFrames[0].getRegionHeight());
        this.setWidth(walkFrames[0].getRegionWidth());
        System.out.println("after: " + this.getWidth() + ", " + this.getHeight());*/

        stateTime = 0f;

        //(x,y) relative to the table it is in.
        locInTable = new Vector2(this.getX(), this.getY());
        //(x,y) relative to the screen.
        loc = localToStageCoordinates(locInTable);

    }




    public void draw(Batch batch, float x, float y, float width, float height) {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);


        //batch.draw(currentFrame, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch.draw(currentFrame, loc.x, loc.y);

    }


}