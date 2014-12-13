package com.padisDefense.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.padisDefense.game.Assets;
import com.padisDefense.game.MyGdxGame;
import com.padisDefense.game.Padi;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //new LwjglApplication(new MyGdxGame(), config);
        config.title = "Padi's Defense";
        config.width = Assets.screen_width;
        config.height = Assets.screen_height;
        config.vSyncEnabled = true;
        new LwjglApplication(new Padi(), config);
    }
}
