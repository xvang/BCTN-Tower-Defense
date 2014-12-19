package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Towers.BuildableSpot;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class UIManager implements InputProcessor{


    //Used to create/upgrade towers.
    SpawnManager spawn;

    Stage stage;
    Table masterTable;
    Skin skin;


    //prints money and enemy left.
    Table messageTable;
    Label moneyMessage;
    Label enemyMessage;

    //prints time in float.
    private float TIMER = 0;
    private Label timeMessage;






    //charging meter.
    private Image loadingHidden;
    private Image loadingFrame;
    private Actor loadingBar;


    //optionMenu, when clicked.
    private Table optionTable;
    private TextButton charge, upgrade, sell;
    private BuildableSpot currentBS = null;//points to the clicked buildableSpot.
    private boolean b = false;// to hide the option popup after a change has been made.
    private TowerManager UITowerManager;

    //
    private Table towerTable;
    private Array<TextButton> towerOptions;


    public UIManager(SpawnManager s){
        spawn = s;
        this.init();
    }

    public void init(){

        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));



        createTowerTable();


        //option Table
        createOptionTable();//creates option table.
        //It's moved to a function because of the clutter

        //messages
        moneyMessage = new Label("$ ", skin);
        enemyMessage = new Label("Enemies left: ", skin);

        //making table for messages.
        messageTable = new Table();
        timeMessage = new Label("Total time: " + String.valueOf(TIMER), skin);






        //charging meter
        loadingHidden = new Image(new Texture("progressbarempty.png"));
        loadingFrame = new Image(new Texture("progressbarbackground.png"));
        loadingBar = new Image(new Texture("progressbar.png"));

        //charging meter sizes and positions
        loadingFrame.setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - (loadingFrame.getHeight() / 2 + 10f));
        loadingBar.setPosition(loadingFrame.getX() + 10f, loadingFrame.getY() + 10f);
        loadingBar.setSize(0, 0);
        loadingHidden.setPosition(loadingBar.getX(), loadingBar.getY());

        messageTable.setSize(200f, Gdx.graphics.getHeight());
        messageTable.setPosition(Gdx.graphics.getWidth() - 250f, 0);
        messageTable.add(enemyMessage).pad(20f).row();
        messageTable.add(moneyMessage).pad(20f).row();
        messageTable.add(timeMessage).row();



        masterTable = new Table();
        masterTable.setSize(200f, Gdx.graphics.getHeight());
        masterTable.setPosition(Gdx.graphics.getWidth() - 250f,0);

        masterTable.add(messageTable).padBottom(20f).row();




        stage.addActor(loadingFrame);
        stage.addActor(loadingHidden);
        stage.addActor(loadingBar);
        stage.addActor(optionTable);
        stage.addActor(masterTable);
        stage.addActor(towerTable);



        //stage.addActor(button);
    }

    public Stage getStage(){return stage;}
    public float getTimer(){return TIMER;}
    public void updateTimer(float d){TIMER += d;}


    public void updateEnemyMessage(int e){
        enemyMessage.setText("Enemies Left: " + String.valueOf(e));
    }

    public void updateMoneyMessage(int m){
        moneyMessage.setText("$ " + String.valueOf(m));
    }

    public void updateTimerMessage(){
        timeMessage.setText("Timer: " + String.valueOf(round(TIMER, 1)));
    }

    float a = 0;
    boolean stopUpdating = false;
    public void updateChargeMeter(float d){
        if(!stopUpdating) {
            if (loadingBar.getWidth() < loadingHidden.getWidth()) {
                a += d;
                loadingBar.setSize(a, loadingHidden.getHeight());
                stage.act();
            }
        }

        if(loadingBar.getWidth() >= loadingHidden.getWidth()) {
            stopUpdating = true;
            loadingBar.setWidth(loadingHidden.getWidth());
        }

    }

    public void updateUIStuff(int enemyCounter, int inGameMoney){
        updateEnemyMessage(enemyCounter);
        updateMoneyMessage(inGameMoney);


    }

    public boolean fullChargeMeter(){
        return (loadingBar.getWidth() == loadingHidden.getWidth());
    }


    public static double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    /**CALLED BY touchDown() in GAMESCREEN.
     * Takes the coord of a click, and the coord of a tower
     * if their respective rectangles overlap, then user has
     * "clicked" a tower.  Towers actually take no direct user input.
     * The optionsTable will be located at where the clicked tower is.
     * 'b' boolean below toggles the option table.
     * 'currentBS' points to the clicked BuildableSpot
     * it will be passed into clickedBuildableSpot() in towerManager.
     * */
    public void clickedTower(int x, int y, TowerManager tower){

        UITowerManager = tower;
        Rectangle rec1 = new Rectangle();
        rec1.setSize(2f, 2f);
        rec1.setPosition(x, Gdx.graphics.getHeight() - y);
        for(int s = 0; s < tower.getBuildableArray().size; s++){
            if(rec1.overlaps(tower.getBuildableArray().get(s).getBoundingRectangle())){
                b = !b;

                //updating the 'charge' button message.
                try{
                    charge.setText(tower.getBuildableArray().get(s).getCurrentTower().getMessage());
                }catch(Exception e){

                    //charge.setText(tower.getBuildableArray().get(s).getMessage());
                }

                //setting the optiontable's location to where clicked tower is.
                optionTable.setPosition(tower.getBuildableArray().get(s).getX() - (optionTable.getWidth()/2),
                        tower.getBuildableArray().get(s).getY() - (optionTable.getHeight() - 5f));

                //setting the towerTable's location.
                towerTable.setPosition(tower.getBuildableArray().get(s).getX() - (towerTable.getWidth()/2),
                        tower.getBuildableArray().get(s).getY() +
                                tower.getBuildableArray().get(s).getHeight() + 40f);


                currentBS = tower.getBuildableArray().get(s);//pointer to clicked buildablespot.

                //if buildable is empty, choices of towers to build should pop up.
                if(currentBS.emptyCurrentTower()){
                    towerTable.setVisible(true);
                }
                //else, the option table containing 'shoot', 'upgrade', 'sell' should pop up.
                else
                    optionTable.setVisible(true);

                break;//breaks the forloop.
                // if clicked buildablespot is found, no need to keep checking
            }
        }
    }



    public void createOptionTable(){
        optionTable = new Table();
        charge = new TextButton("Charge", skin, "default");
        upgrade = new TextButton("Upgrade", skin, "default");
        sell = new TextButton("Sell", skin, "default");
        optionTable.add(charge).pad(5f);
        optionTable.add(upgrade).pad(5f);
        optionTable.add(sell).pad(5f);
        optionTable.setSize(50f, 50f);
        optionTable.setVisible(false);
        //adding clicklisteners for option table.

        //'currentBS' points to the BuildableSpot that was clicked.
        charge.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent e, float x, float y){

                b = !b;
                optionTable.setVisible(b);
                towerTable.setVisible(b);

                //emtyCurrentTower() returns true if
                //nothing is built on the buildablespot.
                if(!currentBS.emptyCurrentTower()) {

                    //getState() returns true if in shooting mode.
                    //changes the state, and the button message.
                    if (currentBS.getCurrentTower().getState()) {
                        currentBS.getCurrentTower().setState(false);
                        charge.setText(currentBS.getCurrentTower().getMessage());
                    } else if (!currentBS.getCurrentTower().getState()) {
                        currentBS.getCurrentTower().setState(true);
                        charge.setText(currentBS.getCurrentTower().getMessage());
                    }

                    optionTable.setVisible(false);
                    towerTable.setVisible(false);
                }
            }
        });

        //upgrades the tower, and keeps the old state of the tower.
        //Example: tower was charging, got upgraded, should still be charging.
        upgrade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                b = !b;
                optionTable.setVisible(b);

                //If the button says "build" then we want it to
                //open up the option of choosing towers.
                if(String.valueOf(upgrade.getText()).equals("Build")){

                    towerTable.setVisible(true);
                    optionTable.setVisible(true);
                }

                else{
                    //stores old state of the tower: charge or shoot.
                    //default is shoot.
                    boolean oldState = true;

                    //if buildablespot has a tower built on it
                    //that tower's state is saved.
                    if(!currentBS.emptyCurrentTower()){
                        oldState = currentBS.getCurrentTower().getState();
                    }
                    spawn.clickedBuildable(currentBS, "upgrade");
                    currentBS.getCurrentTower().setState(oldState);

                    optionTable.setVisible(false);
                }



            }//end click()
        });//end clicklistener

        sell.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                b = !b;
                optionTable.setVisible(false);
                towerTable.setVisible(false);
                UITowerManager.clearBuildable(currentBS);


            }
        });
    }


    /**
     * Creates a tower option that user sees when they click a buildablespot.
     * Calls the UIManager screen to create the appropriate tower.
     * **/
    public void createTowerTable(){
        //tower option table. it shows up with all the towers the user can make.
        towerTable = new Table();
        towerOptions = new Array<TextButton>();

        String[] names = {"speed", "strength", "ice", "rogue", "aoe", "ghost"};

        for(int x = 0; x < names.length; x++){
            final TextButton t = new TextButton(names[x], skin, "default");
            t.setSize(25f, 15f);
            t.setName(names[x]);
            towerOptions.add(t);
        }



        //adding listeners to the towers.
        for(int x = 0; x < towerOptions.size; x++){
            final int xx = x;
            towerOptions.get(x).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent e, float x, float y){
                    spawn.clickedBuildable(currentBS, towerOptions.get(xx).getName());
                }
            });
        }

        //creating the images and adding them to the table.
        //table should be 3 to a row.
        for(int x = 0; x < 6; x++) {

            if(x % 3 == 0 && x != 0) towerTable.row();

            towerTable.add(towerOptions.get(x)).pad(8f);
            towerTable.pad(5f);
        }



        //towerTable.setSize(100f, 100f);
        towerTable.setVisible(false);


        //adding listeners. hiding the tables.
        for(int x = 0; x < towerOptions.size; x++){
            towerOptions.get(x).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent e, float x, float y){


                    towerTable.setVisible(false);
                    optionTable.setVisible(false);
                }
            });
        }
    }

    @Override
    public boolean keyDown(int keycode) {return false;}

    @Override
    public boolean keyUp(int keycode) {return false;}

    @Override
    public boolean keyTyped(char character) {return false;}

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        optionTable.setVisible(false);
        towerTable.setVisible(false);
        return false;}

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;}



    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {return false;}

    @Override
    public boolean scrolled(int amount) {return false;}











    public void dispose(){
        stage.dispose();
        skin.dispose();
    }
}


