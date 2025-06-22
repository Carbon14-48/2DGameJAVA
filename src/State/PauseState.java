package State;

import main.GamePanel;
import java.awt.event.KeyEvent;

public class PauseState implements GameState {
    GamePanel gp;

    public PauseState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_P) {
            gp.setGameState(gp.playState);
            gp.stopMusic();
            gp.playMusic(0);
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}
}