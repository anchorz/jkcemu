package jkcemu;

import java.awt.Image;
import java.awt.Window;
import java.net.URL;

class MacOs {

    public static void setIconImages(Window window, String iconImage) {
        URL url = window.getClass().getResource(iconImage);
        if (url == null) {
            return;
        }
        Image img = window.getToolkit().createImage(url);
        if (img == null) {
            return;
        }
        com.apple.eawt.Application.getApplication().setDockIconImage(img);
    }
}
