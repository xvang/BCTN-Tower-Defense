package com.padisDefense.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.GameScreen;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Towers.BuildableSpot;
import com.padisDefense.game.Towers.Tower;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class UIManager implements InputProcessor{


    Padi padi;
    GameScreen game;
    //Used to create/upgrade towers
    public boolean GAME_OVER = false;
    Stage stage;
    public Table masterTable;
    public TextButton hideButton;
    public int lifepoints = 20;


    //prints money and enemy left.
    Table messageTable;
    Label moneyMessage;
    Label enemyMessage;

    //prints time in float.
    private float TIMER = 0;
    private Label timeMessage;

    private Table dragTowers;//table containing towers that can be dragged to build.

    //charging meter.
    public Image loadingHidden;
    private Image loadingFrame;
    public Actor loadingBar;

    //optionMenu, when clicked.
    private Table clickedOptionTable;
    private TextButton charge, upgrade;//, sell;
    private BuildableSpot currentBS = null;//points to the clicked buildableSpot.
    private boolean b = false;// to hide the option popup after a change has been made.


    //displays the towers when buildable spot is clicked.
    private Table clickedTowerTable;
    private Array<TextButton> towerOptions;
    private ShapeRenderer shapeRenderer;

    //TODO: make the circles around the range visible.
    //table for countdown.
    private Table countDownTable;
    private Label countDownMessage2;

    //end-GameScreen
    public Stage endStage;//This is to make the endgame popup the only thing that takes input.
    public Table endGameTable;
    private Label endGameTimeMessage;
    public Label winMessage, loseMessage;


    //pause Screen
    public boolean PAUSED = false;
    public Table pauseTable;
    public Stage pauseStage;
    private ImageButton pauseButton;
    private Table pauseButtonTable; // to resize button


    //displays tower stats in mastertable.
    public Table statsTable;
    private Label attackLabel, rangeLabel, costLabel, levelLabel,
            nameLabel, lifeLabel, weakLabel, strongLabel;


    public UIManager(GameScreen g, Padi p){
        game = g;
        padi = p;
        stage = new Stage();

        //pause table.
        pauseButtonTable = new Table();
        pauseTable = new Table();
        pauseStage = new Stage();

        shapeRenderer = new ShapeRenderer();

        countDownTable = new Table();
        endGameTable = new Table();

        clickedTowerTable = new Table();
        towerOptions = new Array<TextButton>();

        endStage = new Stage();
        endGameTable = new Table();
        winMessage = new Label("You Won!", padi.assets.someUIskin, "default");
        loseMessage = new Label("You Lost!", padi.assets.someUIskin, "default");
        endGameTimeMessage = new Label("Time: ", padi.assets.someUIskin, "default");
        clickedOptionTable = new Table();

        masterTable = new Table();

        //message table.
        messageTable = new Table();
        moneyMessage = new Label("Bank: $ ", padi.assets.someUIskin);
        enemyMessage = new Label("Enemies left: ", padi.assets.someUIskin);
        timeMessage = new Label("Total time: " + String.valueOf(TIMER), padi.assets.someUIskin);
        lifeLabel = new Label("\n", padi.assets.someUIskin);


        //stats table
        statsTable = new Table();
        nameLabel = new Label("\n", padi.assets.someUIskin, "black");
        attackLabel = new Label("\n", padi.assets.someUIskin, "black");
        rangeLabel = new Label("\n", padi.assets.someUIskin, "black");
        costLabel = new Label("\n", padi.assets.someUIskin, "black");
        levelLabel = new Label("\n", padi.assets.someUIskin, "black");
        weakLabel = new Label("\n", padi.assets.someUIskin, "black");
        strongLabel = new Label("\n", padi.assets.someUIskin, "black");


        pauseTable = new Table();
        dragTowers = new Table();
        endGameTable = new Table();

        //charge meter.
        loadingHidden = new Image(new Texture("progressbarempty.png"));
        loadingBar = new Image(new Texture("progressbar.png"));
        loadingFrame = new Image(new Texture("progressbarbackground.png"));


        //the table on the right side.
        //displays drag options, tower stats.
        masterTable = new Table();

        //hide button
        hideButton = new TextButton("Hide", padi.assets.bubbleUI, "yellow");

        TIMER = 0f;
        GAME_OVER = false;
        stopUpdatingChargeMeter = false;
        PAUSED = false;
        updateTimerMessage();
        hideButton.setText("Hide");
        lifepoints = 20;

        loadingBar.setSize(0, loadingBar.getHeight());
        currentCharge = 0;

        hideButton.setSize(80f, 50f);
        hideButton.setPosition(Gdx.graphics.getWidth()-hideButton.getWidth() - 10f, 10f);
        hideButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                if(masterTable.isVisible()){
                    hideButton.setText("Show");
                    masterTable.setVisible(false);
                    //hideButton.setPosition(Gdx.graphics.getWidth()-hideButton.getWidth() - 10f, 10f);
                }
                else{
                    hideButton.setText("Hide");
                    //hideButton.setPosition(Gdx.graphics.getWidth()-masterTable.getWidth(), 10f);
                    masterTable.setVisible(true);
                }

            }
        });

        stage.addActor(loadingFrame);
        stage.addActor(loadingHidden);
        stage.addActor(loadingBar);
        stage.addActor(clickedOptionTable);
        stage.addActor(masterTable);
        stage.addActor(hideButton);
        stage.addActor(clickedTowerTable);
        stage.addActor(pauseButtonTable);
        stage.addActor(messageTable);
        stage.addActor(countDownTable);

    }

    public void initUI(){

        createPauseTable();
        createDragTowers();
        createCountDownTable();
        createTowerTable();
        createEndGameTable();
        createChargeMeter();
        createOptionTable();
        createMessageTable();

        createStatsTable();
        createMasterTable();
    }

    public void clearTables(){
        clickedOptionTable.reset();
        masterTable.reset();
        clickedTowerTable.reset(); towerOptions.clear();
        pauseButtonTable.reset();
        pauseTable.reset();
        messageTable.reset();
        countDownTable.reset();
        endGameTable.reset();
        statsTable.reset();
        dragTowers.reset();
    }

    public void setGame(GameScreen g){
        game = g;
    }

    public Stage getStage(){return stage;}

    public void updateTimer(float d){TIMER += d;}


    public void updateEnemyMessage(int e){
        enemyMessage.setText("Enemies Left: " + String.valueOf(e));
    }

    public void updateMoneyMessage(int m){
        moneyMessage.setText("Bank: $ " + String.valueOf(m));
    }

    public void updateTimerMessage(){
        timeMessage.setText("Timer: " + String.valueOf(round(TIMER, 1)));
        endGameTimeMessage.setText("Time: " + String.valueOf(round(TIMER, 3)) + " secs.");
    }


    //this function also hides the countdown table.
    public void updateCountDownMessage(){
        countDownMessage2.setText("Time left: " + String.valueOf((int) game.enemy.getCountDownTimer()));

        if(game.enemy.getCountDownTimer() <= 0f){
            countDownTable.setVisible(false);
        }
    }

    float currentCharge = 0;
    boolean stopUpdatingChargeMeter = false;
    public void updateChargeMeter(float d){
        if(!stopUpdatingChargeMeter) {
            if (loadingBar.getWidth() < loadingHidden.getWidth()) {
                currentCharge += d;
                loadingBar.setSize(currentCharge, loadingHidden.getHeight());
                stage.act();
            }


            if (loadingBar.getWidth() >= loadingHidden.getWidth()) {
                stopUpdatingChargeMeter = true;
                loadingBar.setWidth(loadingHidden.getWidth());
            }
        }
    }


    public void updateStatsTable(Tower t){

        attackLabel.setText("Attack: " + String.valueOf((int)t.getAttack()));
        rangeLabel.setText("Range: " + String.valueOf((int)t.getRange()));
        costLabel.setText("Upgrade Cost: $" + String.valueOf((int)t.getCost()));
        levelLabel.setText("Level: " + String.valueOf(t.getLevel()));
        nameLabel.setText("Name: " + t.getID());
        String w = t.getWeakAgainst().get(0).substring(0, t.getWeakAgainst().get(0).length() - 4);
        String s = t.getStrongAgainst().get(0).substring(0, t.getStrongAgainst().get(0).length() - 4);
        weakLabel.setText("Weak against: " + w );
        strongLabel.setText("Strong against: " + s );


    }
    public void updateUIStuff(int enemyCounter, int inGameMoney){
        if(!GAME_OVER){
            updateEnemyMessage(enemyCounter);
            updateMoneyMessage(inGameMoney);
            updateCountDownMessage();
            lifeLabel.setText("LifePoints: " + String.valueOf(lifepoints));

            //lost the game.
            if(lifepoints <= 0)
                game.gameStatus = "lose";
        }
    }

    public boolean fullChargeMeter(){

        if(loadingBar.getWidth() == loadingHidden.getWidth())
            game.gameStatus = "win";

        return (loadingBar.getWidth() == loadingHidden.getWidth());
    }


    //Not mine. some answer on Stackoverflow.
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
     * it will be passed into buildATower() in towerManager.
     * */
    public void clickedTower(int x, int y){


        Rectangle rec1 = new Rectangle();
        rec1.setSize(2f, 2f);
        rec1.setPosition(x, Gdx.graphics.getHeight() - y);
        BuildableSpot currentBuildable;
        for(int s = 0; s < game.tower.getBuildableArray().size; s++){
            currentBuildable = game.tower.getBuildableArray().get(s);

            if(rec1.overlaps(currentBuildable.getBoundingRectangle())){
                b = !b;

                //updating the 'charge' button message.
                /*try{
                    charge.setText(currentBuildable.getCurrentTower().getMessage());
                }catch(Exception e){

                    //charge.setText(tower.getBuildableArray().get(s).getMessage());
                }*/



                //'currentBuildable' is local to this function.
                //'currentBS' is global in this class.
                currentBS = game.tower.getBuildableArray().get(s);//pointer to clicked buildablespot.

                //if buildable is empty, choices of towers to build should pop up.
                if(currentBuildable.emptyCurrentTower()){
                    //checkBorders(currentBuildable, clickedTowerTable);

                    clickedTowerTable.setPosition(currentBuildable.getCenterX(), currentBuildable.getCenterY() - 40f);
                    clickedTowerTable.setVisible(true);
                }
                //else, the option table containing 'shoot', 'upgrade', 'sell' should pop up.
                else{

                    //checkBorders(currentBuildable, clickedOptionTable);
                    clickedOptionTable.setPosition(currentBuildable.getCenterX(), currentBuildable.getCenterY() - 40f);
                    clickedOptionTable.setVisible(true);
                    currentBuildable.getCurrentTower().clicked = true;
                }
                break;//breaks the forloop.
                // if clicked buildablespot is found, no need to keep checking
            }
        }
    }


    public void checkBorders(BuildableSpot b, Table t){

        if(t == null)
            System.out.println("t = null!");

        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();

        Rectangle bsRec, towerRec, tRec, top, bottom, left, right;
        bsRec = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());

        //http://stackoverflow.com/questions/18075414/getting-stage-coordinates-of-actor-in-table-in-libgdx
        //starting point was the answer found above. below is where I ended up. I don't know what happened.
        //I think 'realLoc' is the coord of the first element of the table,
        //and 'realLoc2' is the coord of the last element of the table.
        Vector2 loc = t.localToStageCoordinates(new Vector2(t.getCells().get(0).getActorX(),t.getCells().get(0).getActorY()));
        Vector2 realLoc = t.getCells().get(0).getActor().getStage().stageToScreenCoordinates(loc);

        Vector2 loc2 = t.localToStageCoordinates(new Vector2(t.getCells().get(t.getCells().size - 1).getActorX() +
                t.getCells().get(t.getCells().size - 1).getPrefWidth(),
                t.getCells().get(t.getCells().size - 1).getActorY() +
        t.getCells().get(t.getCells().size - 1).getPrefHeight()));

        Vector2 realLoc2 = t.getCells().get(t.getCells().size - 1).getActor().getStage().stageToScreenCoordinates(loc2);
        tRec = new Rectangle( realLoc.x - 10f,  h - realLoc.y - 10f, Math.abs(realLoc.x - realLoc2.x), Math.abs(realLoc.y - realLoc2.y));


        top = new Rectangle(0, h, w, 250f);
        bottom = new Rectangle(0, -250f, w, 250f);
        left = new Rectangle(-250f, 0, 250f, h);
        right = new Rectangle(w, 0, 250f, h);

        t.setPosition(b.getCenterX(), b.getCenterY() - 40f);


        while (tRec.overlaps(left)){
            t.setPosition(t.getX() + 1, t.getY());

            loc = t.localToStageCoordinates(new Vector2(t.getCells().get(0).getActorX(),t.getCells().get(0).getActorY()));
            realLoc = t.getCells().get(0).getActor().getStage().stageToScreenCoordinates(loc);

            tRec = new Rectangle( realLoc.x,  h - realLoc.y, t.getPrefWidth(), t.getPrefHeight());

        }

        while (tRec.overlaps(right)){
            t.setPosition(t.getX() - 1, t.getY());
            loc = t.localToStageCoordinates(new Vector2(t.getCells().get(0).getActorX(),t.getCells().get(0).getActorY()));
            realLoc = t.getCells().get(0).getActor().getStage().stageToScreenCoordinates(loc);

            tRec = new Rectangle( realLoc.x,  h - realLoc.y, t.getPrefWidth(), t.getPrefHeight());

        }

        while (tRec.overlaps(top)){
            t.setPosition(t.getX(), t.getY() - 1);
            loc = t.localToStageCoordinates(new Vector2(t.getCells().get(0).getActorX(),t.getCells().get(0).getActorY()));
            realLoc = t.getCells().get(0).getActor().getStage().stageToScreenCoordinates(loc);

            tRec = new Rectangle( realLoc.x,  h - realLoc.y, t.getPrefWidth(), t.getPrefHeight());
        }

        while (tRec.overlaps(bottom)) {
            t.setPosition(t.getX(), t.getY() + 1);
            loc = t.localToStageCoordinates(new Vector2(t.getCells().get(0).getActorX(),t.getCells().get(0).getActorY()));
            realLoc = t.getCells().get(0).getActor().getStage().stageToScreenCoordinates(loc);

            tRec = new Rectangle( realLoc.x,  h - realLoc.y, t.getPrefWidth(), t.getPrefHeight());
        }
    }

    public void gameOver(){
        GAME_OVER = true;
        endGameTable.setVisible(true);
    }


    //check if user dropped the image on a buildableSpot.
    public void checkTheDrop(Rectangle r, String type){
        //access the buildable array via the spawnManager.
        Array<BuildableSpot> BS = game.tower.getBuildableArray();

        //create Rectangle around BuildableSpot.
        for(int x = 0; x < BS.size; x++){
            Rectangle rec = new Rectangle(BS.get(x).getX(), BS.get(x).getY(),
                    BS.get(x).getWidth()*2, BS.get(x).getHeight()*2); //100% bigger?

            if(rec.overlaps(r) && BS.get(x).emptyCurrentTower()){
                game.spawn.buildATower("build", BS.get(x), type.toUpperCase(), 1);//passes in the buildablespot, name of tower, and level.
                clickedOptionTable.setVisible(false);
                clickedTowerTable.setVisible(false);
                BS.get(x).getCurrentTower().clicked = false;
                statsTable.setVisible(false);
            }




        }

    }


    public void createStatsTable(){

        statsTable.add(nameLabel).row();
        statsTable.add(weakLabel).row();
        statsTable.add(strongLabel).row();
        statsTable.add(attackLabel).row();
        statsTable.add(rangeLabel).row();
        statsTable.add(costLabel).row();
        statsTable.add(levelLabel).row();


        statsTable.setVisible(false);

    }
    public void createMessageTable(){
        // messageTable.setSize(200f, Gdx.graphics.getHeight());
        // messageTable.setPosition(Gdx.graphics.getWidth() - 250f, 0);

        messageTable.add(enemyMessage).padRight(30f);
        messageTable.add(moneyMessage).padRight(30f);
        messageTable.add(timeMessage).padRight(30f);
        messageTable.add(lifeLabel).padRight(30f);
        //messageTable.setSize(300f, 30f);
        messageTable.setPosition(400f, Gdx.graphics.getHeight() - 20f);
       // messageTable.setPosition(0,0);

    }



    public void createMasterTable(){

        masterTable.setSize(Gdx.graphics.getWidth()*5/16, Gdx.graphics.getHeight());
        masterTable.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()*5/16,0);



        //creating the background for  the table.
        TextureRegionDrawable background = new TextureRegionDrawable(
                new TextureRegion(new Texture("limegreen.png")));

        //masterTable.add(countDownTable).row();
        masterTable.setBackground(background);
        masterTable.add(statsTable).row();
        masterTable.add(dragTowers).row();


    }

    public void createPauseTable(){
        ImageButton pauseButton = new ImageButton(padi.assets.bubbleUI, "pause");
        pauseButton.setSize(60f, 60f);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){

                PAUSED = true;
                game.multi.clear();
                game.multi.addProcessor(pauseStage);
            }
        });
        //pauseButton.scaleBy(0.2f);





        final TextButton resume = new TextButton("Resume", padi.assets.bubbleUI, "green");
        final TextButton quit = new TextButton("Quit", padi.assets.bubbleUI, "green");
        final TextButton restart = new TextButton("Restart", padi.assets.bubbleUI, "green");
        final TextButton mute = new TextButton("Mute", padi.assets.bubbleUI, "green");


        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                PAUSED = false;
                game.multi.clear();
                game.multi.addProcessor(game.UI.getStage());
                game.multi.addProcessor(game.UI);
                game.multi.addProcessor(game);
            }
        });

        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){

                padi.setScreen(padi.worldmap);
            }
        });

        mute.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){

                if(game.gameMusic.isPlaying()){

                    mute.setText("Un-Mute");
                   //game.gameMusic.pause();
                }



                else{
                    mute.setText("Mute");
                    //game.gameMusic.play();
                }



            }
        });

        restart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                game.reset();
            }
        });

        pauseButtonTable.add(pauseButton).width(30f).height(30f);
        pauseButtonTable.setPosition(20f, Gdx.graphics.getHeight() - 20f );

        pauseTable.add(quit).width(150f).height(60f).pad(30f);
        pauseTable.add(resume).width(150f).height(60f).pad(30f).row();
        pauseTable.add(restart).width(150f).height(60f).pad(30f);
        pauseTable.add(mute).width(200f).height(60f).pad(30f);
        pauseTable.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        pauseStage.addActor(pauseTable);
    }
    public void createChargeMeter(){
        loadingFrame.setSize(padi.assets.getScreenWidth()/3, padi.assets.getScreenHeight()/40);
        loadingHidden.setSize(loadingFrame.getWidth()-10f, loadingFrame.getHeight()-10f);

        //charging meter sizes and positions
        loadingFrame.setCenterPosition(Gdx.graphics.getWidth() / 2, loadingFrame.getHeight()+10f);
        loadingHidden.setCenterPosition(loadingFrame.getCenterX(), loadingFrame.getCenterY());
        loadingBar.setPosition(loadingHidden.getX(), loadingHidden.getY());
        loadingBar.setSize(0, 0);
    }
    public void createCountDownTable(){

        final TextButton startButton = new TextButton("start", padi.assets.bubbleUI, "red");
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                game.enemy.setCountDownTimer(0f);
                countDownTable.setVisible(false);
            }
        });

        final Label countDownMessage1 = new Label("Click the Start Button to start!", padi.assets.someUIskin);

        float temp = game.enemy.getCountDownTimer();
        countDownMessage2 = new Label("Time Left: " + String.valueOf((int)temp),
                padi.assets.someUIskin, "default");


        countDownTable.add(startButton).width(120f).height(50f).row().pad(5f);
        countDownTable.add(countDownMessage1).row().pad(5f);
        countDownTable.add(countDownMessage2).row().pad(5f);
        countDownTable.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);


    }

    public void createOptionTable(){

        clickedOptionTable.setName("clickedOptionTable");

        ImageButton charge = new ImageButton(padi.assets.bubbleUI, "charge");
        ImageButton upgrade = new ImageButton(padi.assets.bubbleUI, "upgrade");
        ImageButton sell = new ImageButton(padi.assets.bubbleUI, "trash");
        clickedOptionTable.add(charge).width(55f).height(55f).pad(35f);
        clickedOptionTable.add(upgrade).width(55f).height(55f).pad(35f);
        clickedOptionTable.add(sell).width(55f).height(55f).pad(35f);
        //clickedOptionTable.setSize(50f, 50f);
        clickedOptionTable.setVisible(false);



        //adding clicklisteners for option table.

        //'currentBS' points to the BuildableSpot that was clicked.
        charge.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent e, float x, float y){

                b = !b;
                clickedOptionTable.setVisible(b);
                clickedTowerTable.setVisible(b);
                //emtyCurrentTower() returns true if
                //nothing is built on the buildablespot.
                if(!currentBS.emptyCurrentTower()) {

                    //getState() returns true if in shooting mode.
                    //changes the state, and the button message.
                    if (currentBS.getCurrentTower().state) {
                        currentBS.getCurrentTower().state = false;
                        //charge.setText(currentBS.getCurrentTower().getMessage());
                    } else {
                        currentBS.getCurrentTower().state = true;
                        //charge.setText(currentBS.getCurrentTower().getMessage());
                    }

                    clickedOptionTable.setVisible(false);
                    clickedTowerTable.setVisible(false);
                    currentBS.getCurrentTower().clicked = true;
                }
            }
        });

        //upgrades the tower, and keeps the old state of the tower.
        //Example: tower was charging, got upgraded, should still be charging.
        upgrade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                b = !b;
                clickedOptionTable.setVisible(b);

                //stores old state of the tower: charge or shoot.
                //default is shoot. maybe 'oldState' is already true when it is declared?
                boolean oldState = true;

                //if buildablespot has a tower built on it
                //that tower's state is saved.
                if(!currentBS.emptyCurrentTower()){
                    oldState = currentBS.getCurrentTower().state;
                }
                game.spawn.upgradeTower(currentBS);
                currentBS.getCurrentTower().state = oldState;

                clickedOptionTable.setVisible(false);



            }//end click()
        });//end clicklistener

        sell.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                b = !b;
                clickedOptionTable.setVisible(false);
                clickedTowerTable.setVisible(false);
                game.tower.clearBuildable(currentBS);
            }
        });
    }

    public void createTowerTable(){
        //tower option table. it shows up with all the towers the user can make.
        clickedTowerTable.setName("clickedTowerTable");


        String[] names = {"army", "blue", "green","orange",
                          "pink", "purple", "red","violet", "yellow"};


        for(int x = 0; x < game.limit; x++){
            TextButton t = new TextButton(names[x], padi.assets.towerButtons, names[x]);
            t.setSize(60f, 35f);
            t.setName(names[x]);
            towerOptions.add(t);
        }
        /*for(String s: names){
            TextButton t = new TextButton(s, padi.assets.towerButtons, s);
            t.setSize(60f, 35f);
            t.setName(s);
            towerOptions.add(t);
        }*/

        //adding listeners to the towers.
        for(int x = 0; x < towerOptions.size; x++){
            final int xx = x;
            towerOptions.get(x).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent e, float x, float y){
                    game.spawn.buildATower("build",currentBS, towerOptions.get(xx).getName().toUpperCase(), 1);

                    clickedTowerTable.setVisible(false);
                    clickedOptionTable.setVisible(false);
                }
            });
        }

        //creating the images and adding them to the table.
        //table should be 3 to a row.
        for(int x = 0; x < game.limit; x++) {

            if(x % 3 == 0 && x != 0) clickedTowerTable.row();

            clickedTowerTable.add(towerOptions.get(x)).width(100f).height(45f).pad(4f);
            clickedTowerTable.pad(5f);
        }




        clickedTowerTable.setVisible(false);
    }

    public void createEndGameTable(){

        final TextButton returnButton = new TextButton(" World Map ", padi.assets.bubbleUI, "red");
        final TextButton retryButton = new TextButton("Try Level Again", padi.assets.bubbleUI, "red");

        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                padi.setScreen(padi.worldmap);
                //game.dispose();
            }
        });

        retryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                //game.padi.setScreen(new GameScreen(game.padi, game.whatLevel));
                game.reset();
            }
        });


        endGameTable.add(winMessage).row().pad(15f);
        endGameTable.add(loseMessage).row().pad(15f);
        endGameTable.add(endGameTimeMessage).row().pad(15f);
        endGameTable.add(returnButton).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/9).padRight(30f);
        endGameTable.add(retryButton).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/9).row().pad(15f);

        endGameTable.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        //endGameTable.setVisible(true);
        endStage.addActor(endGameTable);
    }

    public void createDragTowers(){

        final Array<Image> image = new Array<Image>();

        //Creating the images for the towers.

        final Image purple = new Image(padi.assets.towerAtlas.findRegion("PURPLE", 1));
        final Image pink = new Image(padi.assets.towerAtlas.findRegion("PINK", 1));
        final Image red = new Image(padi.assets.towerAtlas.findRegion("RED", 1));
        final Image blue = new Image(padi.assets.towerAtlas.findRegion("BLUE", 1));
        final Image yellow = new Image(padi.assets.towerAtlas.findRegion("YELLOW", 1));
        final Image green = new Image(padi.assets.towerAtlas.findRegion("GREEN", 1));
        final Image orange = new Image(padi.assets.towerAtlas.findRegion("ORANGE", 1));
        final Image violet = new Image(padi.assets.towerAtlas.findRegion("VIOLET", 1));
        final Image army = new Image(padi.assets.towerAtlas.findRegion("ARMY", 1));

        //giving each image the appropriate names.
        purple.setName("PURPLE");
        pink.setName("PINK");
        red.setName("RED");
        blue.setName("BLUE");
        yellow.setName("YELLOW");
        green.setName("GREEN");
        orange.setName("ORANGE");
        violet.setName("VIOLET");
        army.setName("ARMY");

        //adding the images to the images array.
        image.add(army);
        image.add(blue);
        image.add(green);
        image.add(orange);
        image.add(purple);
        image.add(pink);
        image.add(red);
        image.add(violet);
        image.add(yellow);

        dragTowers.clear();
        for(int w = 0; w < game.limit; w++){
            if(w % 2 == 0 && w != 0)
                dragTowers.row();
            dragTowers.add(image.get(w)).width(50f).height(50f).pad(10f);
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
                    for(int w = 0; w < game.limit; w++) {
                        if (w % 2 == 0 && w != 0) dragTowers.row();
                        dragTowers.add(image.get(w)).width(50f).height(50f).pad(10f);
                    }
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
        clickedOptionTable.setVisible(false);
        clickedTowerTable.setVisible(false);
        for(int s = 0; s < game.tower.getTowerArray().size; s++){
            game.tower.getTowerArray().get(s).clicked = false;
        }
        statsTable.setVisible(false);
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
        endStage.dispose();
        masterTable.clearChildren();
        masterTable.reset();
        hideButton.clear();
        messageTable.clearChildren();
        messageTable.reset();
        moneyMessage.clear();
        enemyMessage.clear();
        timeMessage.clear();
        dragTowers.clearChildren();
        dragTowers.reset();



        loadingHidden.clear();
        loadingBar.clear();
        clickedOptionTable.reset();
        charge.clear();
        upgrade.clear();
        clickedTowerTable.reset();

        shapeRenderer.dispose();
        countDownTable.reset();
        countDownMessage2.clear();
        endGameTable.reset();
        endGameTimeMessage.clear();
        pauseButton.getImage().clear();
        pauseButton.clear();
        pauseTable.reset();
        pauseStage.dispose();

        for(int x = 0; x < towerOptions.size; x++)
            towerOptions.get(x).clear();

        //for(int x = 0; x < image.size; x++)
        //    image.get(x).clear();
    }

    public void reset(){

        countDownTable.setVisible(true);


        endGameTable.setVisible(false);


        clickedTowerTable.setVisible(false);


        clickedOptionTable.setVisible(false);

        statsTable.setVisible(false);

        masterTable.setVisible(true);
        TIMER = 0f;
        GAME_OVER = false;
        stopUpdatingChargeMeter = false;
        PAUSED = false;
        //updateTimerMessage();
        hideButton.setText("Hide");
        lifepoints = 20;

        loadingBar.setSize(0, loadingBar.getHeight());
        currentCharge = 0;
    }
}

/**http://stackoverflow.com/questions/18075414/getting-stage-coordinates-of-actor-in-table-in-libgdx
 *
 *
 *
 * */
