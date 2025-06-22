package State;

import main.GamePanel;
import java.awt.event.KeyEvent;

public class TitleState implements GameState {
    GamePanel gp;

    public TitleState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            gp.playSE(10);
            if(gp.ui.commandNum < 0) gp.ui.commandNum = 2;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            gp.playSE(10);
            if(gp.ui.commandNum > 2) gp.ui.commandNum = 0;
        }
        if(code == KeyEvent.VK_ENTER) {
            switch(gp.ui.commandNum) {
                case 0: // NEW GAME
                    gp.stopMusic();
                    gp.playMusic(0);
                    gp.setGameState(gp.playState);
                    gp.ui.commandNum=0;
                    break;
                case 1: // LOAD GAME
                    System.out.println("Load Game selected");
                    break;
                case 2: // QUIT
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}
}