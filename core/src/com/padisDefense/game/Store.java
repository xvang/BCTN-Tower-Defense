package com.padisDefense.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
import com.padisDefense.game.Items.ItemStorage;
import com.padisDefense.game.Items.MainItem;

//TODO: update money. Have clickable items in store. etc.
public class Store extends ScreenAdapter{

    Padi padi;
    public Store(Padi p){padi = p;}
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
    private Label money;//displays the user's money
    private Label message;//Prints out a message: item unlocked, not enough money, no item selected.



    @Override public void show() {

        stage = new Stage();
        imageList = new Array<Image>();
        itemStorage = new ItemStorage();

        TextButton menu = new TextButton("Back to Menu", padi.skin);
        TextButton worldMap = new TextButton("Back to Map", padi.skin);
        worldMap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                padi.setScreen(padi.worldmap);
            }
        });
        menu.setSize(80f, 20f);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                padi.setScreen(padi.main_menu);
            }
        });



        //array of images.
        for(int x = 0; x < itemStorage.itemArray.size; x++)
            imageList.add(new Image(itemStorage.itemArray.get(x).getTexture()));

        //adding clicklisteners to images.
        //It is in another function to avoid cluttering.
        addListeners();

        final Table imageTable = new Table();
        for(int x = 0; x < itemStorage.itemArray.size; x++){
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


        //making the stuff on the right.
        name = new Label(".", padi.skin, "default");
        info = new Label(".", padi.skin, "default");
        cost = new Label(".", padi.skin, "default");
        affects = new Label(".", padi.skin, "default");
        money = new Label(String.valueOf((int)padi.player.getMoney()) + " credits", padi.skin);


        final TextButton unlockButton = new TextButton("Unlock Now", padi.skin);
        message = new Label(".", padi.skin, "default");
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
                            money.setText(String.valueOf((int)padi.player.getMoney()) + " credits");
                            message.setColor(Color.GREEN);
                            message.setText("Item unlocked.");
                        }

                        else{//item is not unlocked, but not enough credits.
                            message.setColor(Color.DARK_GRAY);
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




        final Table table1 = new Table();//contains the navigation buttons.
        final Table table2 = new Table();//contains information about item.


        table1.add(menu).row().pad(40f, 40f, 40f, 40f);
        table1.add(worldMap).row().padBottom(20f);
        table1.setPosition(Gdx.graphics.getWidth() - 100f, Gdx.graphics.getHeight()*5 / 6);



        table2.add(name).row().pad(30f, 20f, 20f, 20f);
        table2.add(info).row().pad(20f, 20f, 20f, 20f);
        table2.add(affects).row().pad(10f, 10f, 10f, 10f);
        table2.add(cost).row().pad(20f, 20f, 20f, 20f);
        table2.add(unlockButton).row().pad(10f, 10f, 10f, 10f);
        table2.add(money).row().pad(10f, 10f, 10f, 10f);
        table2.add(message).row().pad(10f, 10f, 10f, 10f);
        table2.setPosition(Gdx.graphics.getWidth() - 100f, Gdx.graphics.getHeight() / 2);


        stage.addActor(background);
        stage.addActor(t);
        stage.addActor(table1);
        stage.addActor(table2);

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

        //When clicked, all the relevant labels should be updated.
        //'clickedItemCost' and 'clickedItem' store exactly what they are named after.
        for(int w = 0; w < itemStorage.itemArray.size; w ++){

            final int ww = w;
            imageList.get(w).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent e, float x, float y){

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
                            temp += ", ";


                    }
                    affects.setText("Affects: " + temp);
                    message.setText("\n");

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