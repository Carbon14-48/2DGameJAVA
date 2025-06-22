package State;

import main.GamePanel;
import java.awt.event.KeyEvent;

public class CharacterState implements GameState {
    GamePanel gp;

    public CharacterState(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_C) {
            gp.setGameState(gp.playState);
            gp.playSE(10);
        }
        if(code==KeyEvent.VK_W){
            if(gp.ui.slotRow!=0){
                gp.ui.slotRow--;
                gp.playSE(10);
            }
        }
        if(code==KeyEvent.VK_A){
            if(gp.ui.slotCol!=0){
                gp.ui.slotCol--;
                gp.playSE(10);
            }
        }
        if(code==KeyEvent.VK_S){
            if(gp.ui.slotRow!=3){
                gp.ui.slotRow++;
                gp.playSE(10);
            }
        }
        if(code==KeyEvent.VK_D){
            if(gp.ui.slotCol!=4){
                gp.ui.slotCol++;
                gp.playSE(10);
            }
        }
        if(code==KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}
}