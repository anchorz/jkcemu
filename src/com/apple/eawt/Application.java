package com.apple.eawt;

import java.awt.Image;

public class Application {

    private static Application instance;

    private Application() {
    }

    public static synchronized Application getApplication() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    public void setDockIconImage(Image img) {
    }

}
