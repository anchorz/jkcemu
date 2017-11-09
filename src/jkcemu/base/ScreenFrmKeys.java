package jkcemu.base;

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.KeyStroke;

//https://developer.apple.com/library/content/documentation/Java/Conceptual/Java14Development/07-NativePlatformIntegration/NativePlatformIntegration.html
public class ScreenFrmKeys {
    // ALT
    public static final int[] ACTION_SCALE_1 = { KeyEvent.VK_1, 0 };
    public static final int[] ACTION_SCALE_2 = { KeyEvent.VK_2, 0 };
    public static final int[] ACTION_SCALE_3 = { KeyEvent.VK_3, 0 };
    public static final int[] ACTION_SCALE_4 = { KeyEvent.VK_4, 0 };
    public static final int[] ACTION_AUDIO = { KeyEvent.VK_A, 0 };
    public static final int[] ACTION_COPY = { KeyEvent.VK_C, 0 };
    public static final int DATEI = KeyEvent.VK_D;
    public static final int EXTRA = KeyEvent.VK_E;
    public static final int[] ACTION_FIND_FILES = { KeyEvent.VK_F, 0 };
    public static final int[] ACTION_SPEED = { KeyEvent.VK_G, 0 };
    public static final int[] ACTION_POWER_ON = { KeyEvent.VK_I, 0 };
    public static final int[] ACTION_JOYSTICK = { KeyEvent.VK_J, 0 };
    public static final int[] ACTION_KEYBOARD = { KeyEvent.VK_K, 0 };
    public static final int[] ACTION_FILE_LOAD = { KeyEvent.VK_L, 0 };
    public static final int[] ACTION_NMI = { KeyEvent.VK_N, 0 };
    public static final int[] ACTION_PAUSE = { KeyEvent.VK_P, 0 };
    public static final int[] ACTION_RESET = { KeyEvent.VK_R, 0 };
    public static final int[] ACTION_FILE_SAVE = { KeyEvent.VK_S, 0 };
    public static final int[] ACTION_TEXTEDITOR = { KeyEvent.VK_T, 0 };
    public static final int[] ACTION_USB = { KeyEvent.VK_U, 0 };
    public static final int[] ACTION_PASTE = { KeyEvent.VK_V, 0 };
    // ALT SHIFT
    public static final int[] ACTION_DEBUGGER = { KeyEvent.VK_D, InputEvent.SHIFT_MASK };
    public static final int[] ACTION_HEXEDITOR = { KeyEvent.VK_H, InputEvent.SHIFT_MASK };
    public static final int[] ACTION_MEMEDITOR = { KeyEvent.VK_M, InputEvent.SHIFT_MASK };
    public static final int[] ACTION_WEB_LOAD = { KeyEvent.VK_O, InputEvent.SHIFT_MASK };
    public static final int[] ACTION_REASSEMBLER = { KeyEvent.VK_R, InputEvent.SHIFT_MASK };
    public static final int[] ACTION_BASIC_SAVE = { KeyEvent.VK_S, InputEvent.SHIFT_MASK };
    public static final int[] ACTION_BASIC_OPEN = { KeyEvent.VK_T, InputEvent.SHIFT_MASK };
    public static final int[] ACTION_SCREENTEXT_QUICK = { KeyEvent.VK_U, InputEvent.SHIFT_MASK };

    public static void setMnemonic(JMenu jmi, int code) {
        jmi.setMnemonic(code);
    }

    public static KeyStroke getKeyStroke(int[] id) {
        int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        return KeyStroke.getKeyStroke(id[0], mask | id[1]);
    }
}
