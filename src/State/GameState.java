package State;

import java.awt.event.KeyEvent;

public interface GameState {
    void handleKeyPressed(KeyEvent e);
    void handleKeyReleased(KeyEvent e);
    
}