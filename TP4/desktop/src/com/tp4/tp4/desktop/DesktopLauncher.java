package com.tp4.tp4.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tp4.tp4.Application;

public class DesktopLauncher {

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new Application(), config);
        config.setTitle("Drop");
        config.setWindowedMode(800,480);
        //new Lwjgl3Application(new Application(), config);
    }

}
