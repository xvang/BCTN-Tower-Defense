package com.padisDefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


public class Credit extends ScreenAdapter {


    Stage stage;
    Padi padi;


    public Credit(Padi p){
        padi = p;
    }



    @Override
    public void show(){

        stage = new Stage();
       /* Array<CreditAnimation> animations = new Array<CreditAnimation>();

        animations.add(new CreditAnimation("enemies/bipedalDragon_all.png",6,4));/////
        animations.add(new CreditAnimation("enemies/blue_walk_updated.png",4,4));
        animations.add(new CreditAnimation("enemies/spider02.png",10,5));
        animations.add(new CreditAnimation("enemies/spider03.png",10,5));
        animations.add(new CreditAnimation("enemies/spider05.png",10,5));
        animations.add(new CreditAnimation("enemies/king_cobra.png",3 ,4));
        animations.add(new CreditAnimation("enemies/golem-walk.png",7,4));
        animations.add(new CreditAnimation("enemies/mage.png", 8,2));
        animations.add(new CreditAnimation("animation/fire_02.png", 8,8));
        animations.add(new CreditAnimation("animation/particlefx_06.png", 8,8));
        animations.add(new CreditAnimation("animation/particlefx_07.png", 8,8));

        Table table = new Table();
        for(int x = 0; x < animations.size; x++){
            table.add(new CreditAnimation("animation/particlefx_07.png", 8,8));
            table.add(new Image(new Texture("duck.png"))).pad(20f).row();

        }

        final ScrollPane scrollPane = new ScrollPane(table);

        final Table scrollWrap = new Table();
        scrollWrap.setFillParent(true);
        scrollWrap.add(scrollPane);

        stage = new Stage();
        stage.addActor(scrollWrap);
*/

        Label label1 = new Label("Title:\n" +
                "    Animated particle effects #2\n" +
                "\n" +
                "Author:\n" +
                "    para\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/animated-particle-effects-2\n" +
                "\n" +
                "License(s):\n" +
                "    * CC0 ( http://creativecommons.org/publicdomain/zero/1.0/legalcode )\n" +
                "\n" +
                "File(s):\n" +
                "    * para_CC0_particlefx-2.zip\n" +
                "    * air_bubbles_01.png\n" +
                "    * air_bubbles_02.png\n" +
                "    * blood_hit_01.png\n" +
                "    * blood_hit_02.png\n" +
                "    * blood_hit_03.png\n" +
                "    * blood_hit_04.png\n" +
                "    * blood_hit_05.png\n" +
                "    * blood_hit_06.png\n" +
                "    * blood_hit_07.png\n" +
                "    * blood_hit_08.png\n" +
                "    * fire_01.png\n" +
                "    * fire_01b.png\n" +
                "    * fire_01c.png\n" +
                "    * fire_02.png\n" +
                "    * lighter_flame_01.png\n" +
                "    * teleporter_01.png\n" +
                "    * teleporter_hit.png\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);
        Label label2 = new Label("\n" +
                "Title:\n" +
                "    Animated particle effects #1\n" +
                "\n" +
                "Author:\n" +
                "    para\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/animated-particle-effects-1\n" +
                "\n" +
                "License(s):\n" +
                "    * CC0 ( http://creativecommons.org/publicdomain/zero/1.0/legalcode )\n" +
                "\n" +
                "File(s):\n" +
                "    * para_CC0_particlefx-1.zip\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);
        Label label3 = new Label("\n" +
                "Title:\n" +
                "    [LPC] Spider\n" +
                "\n" +
                "Author:\n" +
                "    William.Thompsonj\n" +
                "\n" +
                "Collaborators:\n" +
                "    Redshrike\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/lpc-spider\n" +
                "\n" +
                "License(s):\n" +
                "    * CC-BY 3.0 ( http://creativecommons.org/licenses/by/3.0/legalcode )\n" +
                "    * GPL 3.0 ( http://www.gnu.org/licenses/gpl-3.0.html )\n" +
                "    * GPL 2.0 ( http://www.gnu.org/licenses/old-licenses/gpl-2.0.html )\n" +
                "    * OGA-BY 3.0 ( http://static.opengameart.org/OGA-BY-3.0.txt )\n" +
                "\n" +
                "SPECIAL ATTRIBUTION INSTRUCTIONS:\n" +
                "    Attribute Stephen \"Redshrike\" Challener as graphic artist and William.Thompsonj as contributor. If reasonable link to this page or the OGA homepage.\n" +
                "\n" +
                "File(s):\n" +
                "    * LPC_Spiders.zip\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);
        Label label4 = new Label("\n" +
                "Title:\n" +
                "    [LPC] Imp\n" +
                "\n" +
                "Author:\n" +
                "    William.Thompsonj\n" +
                "\n" +
                "Collaborators:\n" +
                "    Redshrike\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/lpc-imp\n" +
                "\n" +
                "License(s):\n" +
                "    * CC-BY 3.0 ( http://creativecommons.org/licenses/by/3.0/legalcode )\n" +
                "    * GPL 3.0 ( http://www.gnu.org/licenses/gpl-3.0.html )\n" +
                "    * GPL 2.0 ( http://www.gnu.org/licenses/old-licenses/gpl-2.0.html )\n" +
                "    * OGA-BY 3.0 ( http://static.opengameart.org/OGA-BY-3.0.txt )\n" +
                "\n" +
                "SPECIAL ATTRIBUTION INSTRUCTIONS:\n" +
                "    Link my profile (if reasonable), credit Redshrike as graphic artist, and credit me as contributor (I did pay to have it made after all...)\n" +
                "\n" +
                "File(s):\n" +
                "    * LPC_imp.zip\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);
        Label label5 = new Label("Title:\n" +
                "    Biped Dragon Sprite Sheet\n" +
                "\n" +
                "Author:\n" +
                "    Marco Giorgini\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/biped-dragon-sprite-sheet\n" +
                "\n" +
                "License(s):\n" +
                "    * CC-BY 3.0 ( http://creativecommons.org/licenses/by/3.0/legalcode )\n" +
                "\n" +
                "File(s):\n" +
                "    * Biped.Dragon.zip\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);
        Label label6 = new Label("Title:\n" +
                "    [LPC] Golem\n" +
                "\n" +
                "Author:\n" +
                "    William.Thompsonj\n" +
                "\n" +
                "Collaborators:\n" +
                "    Redshrike\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/lpc-golem\n" +
                "\n" +
                "License(s):\n" +
                "    * CC-BY 3.0 ( http://creativecommons.org/licenses/by/3.0/legalcode )\n" +
                "    * GPL 3.0 ( http://www.gnu.org/licenses/gpl-3.0.html )\n" +
                "    * GPL 2.0 ( http://www.gnu.org/licenses/old-licenses/gpl-2.0.html )\n" +
                "    * OGA-BY 3.0 ( http://static.opengameart.org/OGA-BY-3.0.txt )\n" +
                "\n" +
                "Copyright/Attribution Notice:\n" +
                "    Attribute Stephen \"Redshrike\" Challener as graphic artist and William.Thompsonj as contributor. If reasonable link to this page or the OGA homepage.\n" +
                "\n" +
                "File(s):\n" +
                "    * golem-walk.png\n" +
                "    * golem-atk.png\n" +
                "    * golem-die.png\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);
        Label label7 = new Label("Title:\n" +
                "    King Cobra\n" +
                "\n" +
                "Author:\n" +
                "    AntumDeluge\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/king-cobra\n" +
                "\n" +
                "License(s):\n" +
                "    * CC-BY 3.0 ( http://creativecommons.org/licenses/by/3.0/legalcode )\n" +
                "\n" +
                "SPECIAL ATTRIBUTION INSTRUCTIONS:\n" +
                "    Should be attributed to Jordan Irwin.\n" +
                "\n" +
                "File(s):\n" +
                "    * king_cobra_0.1.zip\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);
        Label label8 = new Label("Title:\n" +
                "    Fire Spell Explosion\n" +
                "\n" +
                "Author:\n" +
                "    Clint Bellanger\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/fire-spell-explosion\n" +
                "\n" +
                "License(s):\n" +
                "    * CC-BY 3.0 ( http://creativecommons.org/licenses/by/3.0/legalcode )\n" +
                "\n" +
                "File(s):\n" +
                "    * explosion.png\n" +
                "    * explosion.blend_.zip\n" +
                "\n" +
                "----------------------------------------\n", padi.assets.someUIskin);

        Label label9 = new Label("Title:\n" +
                "    16x16 Mage\n" +
                "\n" +
                "Author:\n" +
                "    saint11\n" +
                "\n" +
                "URL:\n" +
                "    http://opengameart.org/content/16x16-mage\n" +
                "\n" +
                "License(s):\n" +
                "    * CC0 ( http://creativecommons.org/publicdomain/zero/1.0/legalcode )\n" +
                "\n" +
                "File(s):\n" +
                "    * mage.png\n" +
                "\n" +
                "----------------------------------------", padi.assets.someUIskin);

        Label label10 = new Label("Title:\n" +
                "\tItem Collection - Fantasy Themed\n" +
                "\t\n" +
                "Author:\n" +
                "\tJana Ochse, 2D-Retroperspectives, www.2d-retroperspectives.org\n" +
                "\t\n" +
                "URL:\n" +
                "\thttp://opengameart.org/content/item-collection-fantasy-themed\n" +
                "\t\n" +
                "License(s):\n" +
                "\t* CC-BY 3.0 ( http://creativecommons.org/licenses/by/3.0/legalcode )\n" +
                "\t\n" +
                "File(s):\n" +
                "\t2DRP_CCArt_FantasyItems.tar.gz\n" +
                "\t\n" +
                "----------------------------------------", padi.assets.someUIskin);


        Label label11 = new Label("\n" +
                "Title:\n" +
                "\tFREE UI ASSET PACK 1\n" +
                "\t\n" +
                "Author:\n" +
                "\tCTatz\n" +
                "\n" +
                "URL:\n" +
                "\thttp://opengameart.org/content/free-ui-asset-pack-1\n" +
                "\n" +
                "License(s):\n" +
                "\t* CC0 ( http://creativecommons.org/publicdomain/zero/1.0/legalcode )\n" +
                "\t\n" +
                "File(s):\n" +
                "\tFREEUIASSETPACK_BY@CAMTATZ.zip\n" +
                "\t\n" +
                "Twitter:\n" +
                "\t@CamTatz\n" +
                "\t\n" +
                "----------------------------------------", padi.assets.someUIskin);
        Table s = new Table();
        s.add(label1).row();         s.add(label2).row();
        s.add(label3).row();         s.add(label4).row();
        s.add(label5).row();         s.add(label6).row();
        s.add(label7).row();         s.add(label8).row();
        s.add(label9).row();         s.add(label10).row();
        s.add(label11).row();

        final ScrollPane scroll = new ScrollPane(s);

        final Table scrollWrapper = new Table();
        scrollWrapper.setFillParent(true);
        scrollWrapper.add(scroll);



        TextButton menu = new TextButton("Menu", padi.assets.bubbleUI, "yellow");
        menu.setSize(150f, 60f);
        menu.setPosition(Gdx.graphics.getWidth() - menu.getWidth() - 20f, 20f);
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                padi.setScreen(padi.main_menu);
            }
        });



        stage.addActor(scrollWrapper);
        stage.addActor(menu);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act();
        stage.draw();

       /* batch.begin();
        for(int x = 0 ;x < animations.size; x++){
            animations.get(x).animate(batch);

        }

        batch.end();*/
    }
}
