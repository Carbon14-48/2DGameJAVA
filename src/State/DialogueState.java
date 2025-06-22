package State;

import main.GamePanel;
import java.awt.event.KeyEvent;

public class DialogueState implements GameState {
    GamePanel gp;

    public DialogueState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ENTER) {
            gp.setGameState(gp.playState);
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}
}