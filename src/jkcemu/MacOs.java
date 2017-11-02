package jkcemu;

import java.awt.Image;
import java.awt.Window;
import java.net.URL;
import com.apple.eawt.Application;

class MacOs {

    public static boolean exists(String className) {
        try {
            Class.forName(className, false, null);
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    public static void setIconImages(Window window, String iconImage) {
        URL url = window.getClass().getResource(iconImage);
        if (url == null) {
            return;
        }
        Image img = window.getToolkit().createImage(url);
        if (img == null) {
            return;
        }
        if (exists("com.apple.eawt.Application")) {
            System.out.println("existiert,ja");
        }
        if (exists("com.apple.eawt.Application")) {
            com.apple.eawt.Application.getApplication().setDockIconImage(img);
        }
    }
}
