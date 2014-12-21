package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Towers.BuildableSpot;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class UIManager implements InputProcessor{


    GameScreen game;
    //Used to create/upgrade towers
    public boolean  GAME_OVER = false;
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

    private Table dragTowers;//table containing towers that can be dragged to build.
    private Array<Image> image;



    //charging meter.
    private Image loadingHidden;
    //private Image loadingFrame;
    private Actor loadingBar;


    //optionMenu, when clicked.
    private Table optionTable;
    private TextButton charge, upgrade;//, sell;
    private BuildableSpot currentBS = null;//points to the clicked buildableSpot.
    private boolean b = false;// to hide the option popup after a change has been made.

    //displays the towers for user to drag.
    private Table towerTable;
    private Array<TextButton> towerOptions;


    //table for countdown.
    private Table countDownTable;
    //private TextButton startButton;
    //private Label countDownMessage1;
    private Label countDownMessage2;

    //end-GameScreen
    public Stage endStage;//This is to make the endgame popup the only thing that takes input.
    public Table endGameTable;
    private Label winMessage;
    private Label loseMessage;
    /*private TextButton returnButton;
    private TextButton retryButton;*/

    public UIManager(GameScreen g){
        game = g;
        this.init();
    }

    public void init(){

        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        createDragTowers();
        createCountDownTable();
        createTowerTable();
        createEndGameTable();


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
        loadingBar = new Image(new Texture("progressbar.png"));
        Image loadingFrame = new Image(new Texture("progressbarbackground.png"));
        //android studio suggested to make 'loadingFrame' local.

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
        //messageTable.add(dragTowers);



        masterTable = new Table();
        masterTable.setSize(200f, Gdx.graphics.getHeight());
        masterTable.setPosition(Gdx.graphics.getWidth() - 250f,0);

        masterTable.add(countDownTable).row().pad(10f);
        masterTable.add(messageTable).padBottom(20f).row();
        masterTable.add(dragTowers);



        stage.addActor(loadingFrame);
        stage.addActor(loadingHidden);
        stage.addActor(loadingBar);
        stage.addActor(optionTable);
        stage.addActor(masterTable);
        stage.addActor(towerTable);



        //stage.addActor(button);
    }

    public Stage getStage(){return stage;}

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


    //this function also hides the countdown table.
    public void updateCountDownMessage(){
        countDownMessage2.setText("Time left: " + String.valueOf((int) game.enemy.getCountDownTimer()));

        if(game.enemy.getCountDownTimer() <= 0f){
            countDownTable.setVisible(false);
        }
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
        if(!GAME_OVER){
            updateEnemyMessage(enemyCounter);
            updateMoneyMessage(inGameMoney);
            updateCountDownMessage();
        }

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
    public void clickedTower(int x, int y){


        Rectangle rec1 = new Rectangle();
        rec1.setSize(2f, 2f);
        rec1.setPosition(x, Gdx.graphics.getHeight() - y);
        for(int s = 0; s < game.tower.getBuildableArray().size; s++){
            if(rec1.overlaps(game.tower.getBuildableArray().get(s).getBoundingRectangle())){
                b = !b;

                //updating the 'charge' button message.
                try{
                    charge.setText(game.tower.getBuildableArray().get(s).getCurrentTower().getMessage());
                }catch(Exception e){

                    //charge.setText(tower.getBuildableArray().get(s).getMessage());
                }

                //setting the optiontable's location to where clicked tower is.
                optionTable.setPosition(game.tower.getBuildableArray().get(s).getX() - (optionTable.getWidth()/2),
                        game.tower.getBuildableArray().get(s).getY() - (optionTable.getHeight() - 5f));

                //setting the towerTable's location.
                towerTable.setPosition(game.tower.getBuildableArray().get(s).getX() - (towerTable.getWidth()/2),
                        game.tower.getBuildableArray().get(s).getY() +
                                game.tower.getBuildableArray().get(s).getHeight() + 40f);


                currentBS = game.tower.getBuildableArray().get(s);//pointer to clicked buildablespot.

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


    public void createCountDownTable(){
        countDownTable = new Table();
        final TextButton startButton = new TextButton("START", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                game.enemy.setCountDownTimer(0f);
                countDownTable.setVisible(false);
            }
        });
        final Label countDownMessage1 = new Label("Click the Start Button to start!", skin);

        float temp = game.enemy.getCountDownTimer();
        countDownMessage2 = new Label("Time Left: " + String.valueOf((int)temp),
                skin, "default");

        startButton.setSize(40f, 30f);
        countDownTable.add(startButton).row().pad(15f);
        countDownTable.add(countDownMessage1).row().pad(15f);
        countDownTable.add(countDownMessage2).row().pad(15f);


    }

    public void gameOver(){
        GAME_OVER = true;
        endGameTable.setVisible(true);
    }
    public void createEndGameTable(){

        endStage = new Stage();
        endGameTable = new Table();
        winMessage = new Label("You Won!", skin, "default");
        loseMessage = new Label("You Lost!", skin, "default");
        final TextButton returnButton = new TextButton(" World Map ", skin);
        final TextButton retryButton = new TextButton("Try Level Again", skin);
        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                game.padi.setScreen(game.padi.worldmap);
            }
        });

        retryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                game.padi.setScreen(new GameScreen(game.padi, game.whatLevel ));
            }
        });

        returnButton.setSize(40, 20f);
        retryButton.setSize(40, 20f);

        //TODO: figure out an end-game condition(s).

        endGameTable.add(winMessage).row().pad(15f);
        endGameTable.add(loseMessage).row().pad(15f);
        endGameTable.add(new Label("Time taken: " + String.valueOf(round(TIMER, 2)), skin)).row().pad(15f);
        endGameTable.add(returnButton).padRight(30f);
        endGameTable.add(retryButton).row().pad(15f);

        endGameTable.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //endGameTable.setVisible(true);
        endStage.addActor(endGameTable);
    }
    public void createDragTowers(){

        dragTowers = new Table();
        image = new Array<Image>();

        //Creating the images for the towers.
        final Image ice = new Image(new Texture("icetower_small.png"));
        final Image strength=new Image(new Texture("strengthtower_small.png"));
        final Image rogue=new Image(new Texture("roguetower_small.png"));
        final Image ghost=new Image(new Texture("ghosttower_small.png"));
        final Image speed=new Image(new Texture("speedtower_small.png"));
        final Image aoe=new Image(new Texture("aoetower_small.png"));

        //giving each image the appropriate names.
        ice.setName("ice");
        strength.setName("strength");
        rogue.setName("rogue");
        ghost.setName("ghost");
        speed.setName("speed");
        aoe.setName("aoe");

        //adding the images to the images array.
        image.add(ice);
        image.add(strength);
        image.add(rogue);
        image.add(ghost);
        image.add(speed);
        image.add(aoe);

        dragTowers.clear();
        for(int w = 0; w < image.size; w++){
            if(w % 2 == 0 && w != 0)
                dragTowers.row();
            dragTowers.add(image.get(w)).pad(25f);


        }
        dragTowers.setOrigin(0,0);

        for(int s = 0; s < image.size; s++){
            final int ss = s;
            image.get(s).addListener(new DragListener(){
                @Override
                public void drag(InputEvent e, float x, float y, int pointer){
                    image.get(ss).setCenterPosition(image.get(ss).getX() + x, image.get(ss).getY() + y);
                }

                @Override
                public void dragStop(InputEvent e, float x, float z, int pointer){
                    Vector2 a = new Vector2(e.getStageX(), e.getStageY());//gets the coord with center at (0,0)

                    //if tower was dragged onto an empty BuildableSpot.
                    //passes in a rectangle of the image, and the image's name.
                    checkTheDrop(new Rectangle(a.x, a.y, image.get(ss).getWidth(), image.get(ss).getHeight()), image.get(ss).getName());

                    dragTowers.clear();
                    for(int w = 0; w < image.size; w++) {

                        if (w % 2 == 0 && w != 0) dragTowers.row();
                        dragTowers.add(image.get(w)).pad(25f);
                    }
                }
            });
        }
    }

    //check if user dropped the image on a buildableSpot.
    public void checkTheDrop(Rectangle r, String type){
        //access the buildable array via the spawnManager.
        Array<BuildableSpot> BS = game.tower.getBuildableArray();

        //create Rectangle around BuildableSpot.
        for(int x = 0; x < BS.size; x++){
            Rectangle rec = new Rectangle(BS.get(x).getX(), BS.get(x).getY(),
                    BS.get(x).getWidth(), BS.get(x).getHeight());

            if(rec.overlaps(r) && BS.get(x).emptyCurrentTower())
                game.spawn.dragBuildTower(BS.get(x), type);//passes in the buildablespot, and name of tower.



        }

    }

    public void createOptionTable(){
        optionTable = new Table();
        charge = new TextButton("Charge", skin, "default");
        upgrade = new TextButton("Upgrade", skin, "default");
        final TextButton sell = new TextButton("Sell", skin, "default");
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
                    } else {
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
                    game.spawn.clickedBuildable(currentBS, "upgrade");
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
                game.tower.clearBuildable(currentBS);


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


        for(String s: names){
            final TextButton t = new TextButton(s, skin, "default");
            t.setSize(25f, 15f);
            t.setName(s);
            towerOptions.add(t);
        }

        //TODO: delete later if no errors pop up.
        //Just changed the for-loop into a foreach loop.
        /*for(int x = 0; x < names.length; x++){
            final TextButton t = new TextButton(names[x], skin, "default");
            t.setSize(25f, 15f);
            t.setName(names[x]);
            towerOptions.add(t);
        }*/



        //adding listeners to the towers.
        for(int x = 0; x < towerOptions.size; x++){
            final int xx = x;
            towerOptions.get(x).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent e, float x, float y){
                    game.spawn.clickedBuildable(currentBS, towerOptions.get(xx).getName());
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

        endStage.dispose();
        skin.dispose();


    }
}

/**http://stackoverflow.com/questions/18075414/getting-stage-coordinates-of-actor-in-table-in-libgdx
 *
 *
 *
 * */
