package com.padisDefense.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.padisDefense.game.Assets;
import com.padisDefense.game.Padi;
import com.padisDefense.game.Test1;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "PDefense";
        config.width = Assets.w;
        config.height = Assets.h;
        config.vSyncEnabled = true;

        new LwjglApplication(new Padi(), config);
    }
}
