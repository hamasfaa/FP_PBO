package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyboardInputs implements KeyListener {
    private static KeyboardInputs instance;
    private Set<Integer> pressedKeys;

    private KeyboardInputs() {
        pressedKeys = new HashSet<>();
    }

    public static KeyboardInputs getInstance() {
        if (instance == null) {
            instance = new KeyboardInputs();
        }
        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }
}
