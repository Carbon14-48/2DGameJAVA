package State;

import main.GamePanel;
import java.awt.event.KeyEvent;

public class GameOverState implements GameState {
    GamePanel gp;

    public GameOverState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code==KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum<0){
                gp.ui.commandNum=1;
            }
            gp.playSE(10);
        }
        if(code==KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum>1){
                gp.ui.commandNum=0;
            }
            gp.playSE(10);
        }
        if(code==KeyEvent.VK_ENTER){
            if(gp.ui.commandNum==0){
                gp.setGameState(gp.playState);
                gp.retry();
            }else if (gp.ui.commandNum==1){
                gp.setGameState(gp.titleState);
                gp.stopMusic();
                gp.playMusic(5);
                gp.restart();
            }
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}
}