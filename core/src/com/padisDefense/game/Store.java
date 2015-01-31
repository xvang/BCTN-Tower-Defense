package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.padisDefense.game.Items.ItemStorage;
import com.padisDefense.game.Items.MainItem;


/**
 * This is where user can unlock items that boosts a tower's stats.
 * The list of items is in ItemStorage.java
 * The stats and info about the items are in itemstorage.java
 * The array here is an array of just pictures of the items
 *
 * **/
public class Store extends ScreenAdapter{

    Padi padi;
    private Stage stage;
    private ItemStorage itemStorage;

    private Array<Image> imageList;

    //making table on the right.

    private Label name;//item's name.
    private Label info;//item's stat boosts
    private Label cost;//cost of an item
    private int clickedItemCost;//pointer to the cost.
    private MainItem clickedItem = null;//pointer to the item.
    private Label affects;//displays all the affected towers.
    //private Label money;//displays the user's money
    private TextButton money, dollarSign;
    private Label message;//Prints out a message: item unlocked, not enough money, no item selected.


    public Store(Padi p){
        padi = p;
    }

    @Override
    public void show(){


        stage = new Stage();
        imageList = new Array<Image>();
        itemStorage = new ItemStorage(padi);

        TextButton menu = new TextButton("Back to Menu", padi.assets.bubbleUI, "red");
        TextButton worldMap = new TextButton("Back to Map", padi.assets.bubbleUI, "green");
        worldMap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                padi.setScreen(padi.worldmap);
            }
        });

        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                padi.setScreen(padi.main_menu);
            }
        });

        //array of images.
        for(int x = 0; x < itemStorage.itemArray.size; x++)
            imageList.add(new Image(itemStorage.itemArray.get(x)));

        //adding clicklisteners to images.
        //It is in another function to avoid cluttering.
        addListeners();

        final Table imageTable = new Table();
        for(int x = 0; x < itemStorage.itemArray.size; x++){
            imageTable.add(imageList.get(x)).pad(20f);
            if((x+1) % 3 == 0 && x != 0)
                imageTable.row();
        }


        //making the scroll thing, and adding it to table.
        final ScrollPane scrollPane = new ScrollPane(imageTable);
        final Table t = new Table();
        t.setFillParent(true);
        t.add(scrollPane).padRight(250f);

        //background image.
        //final Image background = new Image(new Texture("badlogic.jpg"));
        //background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //making the stuff on the right.


        String amount = String.valueOf(padi.player.getMoney());
        dollarSign = new TextButton("", padi.assets.someUIskin, "dollarSign");
        money = new TextButton(amount, padi.assets.someUIskin, "default");
        name = new Label("\n", padi.assets.someUIskin, "default");
        info = new Label("\n", padi.assets.someUIskin, "default");
        cost = new Label("\n", padi.assets.someUIskin, "default");
        affects = new Label("\n", padi.assets.someUIskin, "default");

        final TextButton unlockButton = new TextButton("Unlock Now", padi.assets.bubbleUI, "yellow");
        message = new Label("\n", padi.assets.skin, "default");
        unlockButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){

                if(clickedItem != null){
                    //if item is unlocked.
                    if(!padi.player.isItemUnlocked(clickedItem)){

                        //subtracts cost from Player' money, and adds item to item array in Player.
                        if(padi.player.getMoney() >= clickedItemCost){

                            padi.player.setMoney(padi.player.getMoney() - clickedItemCost);

                            padi.player.addItemsUnlocked(clickedItem);

                            padi.loadsave.savePlayer(padi.player);//save the purchase.

                            money.setText(String.valueOf(padi.player.getMoney()));
                            message.setColor(Color.GREEN);
                            message.setText("Item unlocked.");
                        }

                        else{//item is not unlocked, but not enough credits.
                            message.setColor(Color.ORANGE);
                            message.setText("Not enough credits.");
                        }
                    }
                    else{
                        message.setColor(Color.BLUE);
                        message.setText("Item is already unlocked.");
                    }
                }
                else{
                    message.setColor(Color.RED);
                    message.setText("No item selected.");
                }
            }
        });

        Table moneyTable = new Table();

        moneyTable.add(money).width(150f).height(40f).padLeft(40f);
        moneyTable.add(dollarSign).width(50f).height(40f).padLeft(-money.getWidth()+40f);


        final Table allTables = new Table();


        allTables.add(moneyTable).padBottom(20f).row();
        allTables.add(name).padBottom(0).row();
        allTables.add(info).padBottom(0).row();
        allTables.add(affects).padBottom(0).row();
        allTables.add(cost).padBottom(0).row();
        allTables.add(unlockButton).width(200f).height(60f).row().padTop(0).row();
        allTables.add(message).padBottom(20f).row();
        allTables.add(menu).width(200f).height(60f).row().padBottom(10f).padTop(10f).row();
        allTables.add(worldMap).width(200f).height(60f).padBottom(10f).padTop(20f).row();
        allTables.setPosition(Gdx.graphics.getWidth()*7/8, Gdx.graphics.getHeight()/2);


       // stage.addActor(background);


        stage.addActor(t);
        stage.addActor(allTables);

        Gdx.input.setInputProcessor(stage);
    }


    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();


        this.stage.draw();
    }


    //adding the popup box to the images.
    //popup box shows a short description
    //and option to unlock.
    public void addListeners(){

        //When clicked, all the relevant labels should be updated.
        //'clickedItemCost' and 'clickedItem' store exactly what they are named after.
        for(int w = 0; w < itemStorage.itemArray.size; w ++){

            final int ww = w;
            imageList.get(w).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent e, float x, float y){

                    for(int t = 0; t < itemStorage.itemArray.size; t++){
                        imageList.get(t).setColor(Color.WHITE);
                    }


                    clickedItemCost = itemStorage.itemArray.get(ww).getCost();
                    clickedItem = itemStorage.itemArray.get(ww);

                    name.setText(itemStorage.itemArray.get(ww).getName());
                    info.setText(itemStorage.itemArray.get(ww).getDisplayStats());
                    cost.setText("Cost: " + String.valueOf(clickedItemCost) + " credits.");

                    String temp = "";

                    //gets an item's list of towers that it affects,
                    //stores them in a single string.
                    //The list of towers needs to be separated to calculate its effects to certain towers.
                    for (int a = 0; a < itemStorage.itemArray.get(ww).getTargets().size; a++){
                        temp += itemStorage.itemArray.get(ww).getTargets().get(a);

                        if((a+1) < itemStorage.itemArray.get(ww).getTargets().size)
                            temp += ", \n";


                    }
                    affects.setText("Affects: " + temp);
                    //message.setText("\n");
                    if(padi.player.isItemUnlocked(clickedItem)){
                        message.setColor(Color.GREEN);
                        message.setText("Unlocked");
                    }
                    else{
                        message.setColor(Color.RED);
                        message.setText("Locked");
                    }

                    imageList.get(ww).setColor(Color.RED);
                }

                /*@Override
                public void touchUp(InputEvent e, float x, float y, int pointer, int button){
                    imageList.get(ww).setColor(Color.WHITE);
                }*/

            });
        }
    }

    @Override
    public void dispose(){}
}

/**
 * http://stackoverflow.com/questions/15484077/libgdx-and-scrollpane-with-multiple-widgets
 * */