package State;

import main.GamePanel;
import main.KeyHandler;
import java.awt.event.KeyEvent;

public class PlayState implements GameState {
    GamePanel gp;
    KeyHandler kh;

    public PlayState(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.kh = kh;
    }

    @Override
    public void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) kh.upPressed = true;
        if(code == KeyEvent.VK_S) kh.downPressed = true;
        if(code == KeyEvent.VK_A) kh.leftPressed = true;
        if(code == KeyEvent.VK_D) kh.rightPressed = true;

        if(code == KeyEvent.VK_P) {
            gp.setGameState(gp.pauseState);
            gp.stopMusic();
            gp.playMusic(4);
        }
        if(code == KeyEvent.VK_C) {
            gp.setGameState(gp.characterState);
        }
        if(code == KeyEvent.VK_ENTER)  {
            kh.enterPressed = true;
        }
        if(code==KeyEvent.VK_R){
            System.out.println("R key pressed! Attempting to reload map...");
            switch (gp.currentMap) {
                case 0:
                    gp.tileM.loadMap("/maps/worldV2.txt",0);
                    break;
                case 1:
                    gp.tileM.loadMap("/maps/worldV3.txt",1);
                    break;
                case 2:
                    gp.tileM.loadMap("/maps/interior01.txt",2);
                    break;
            }
            System.out.println("Map reload command sent!");
        }
        if(code==KeyEvent.VK_F){
            kh.shootKeyPressed=true;
        }
        if(code==KeyEvent.VK_ESCAPE){
            gp.setGameState(gp.optionsState);
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_W) kh.upPressed=false;
        if(code==KeyEvent.VK_S) kh.downPressed = false;
        if(code==KeyEvent.VK_A) kh.leftPressed=false;
        if(code==KeyEvent.VK_D) kh.rightPressed = false;
        if(code==KeyEvent.VK_F) kh.shootKeyPressed=false;
        if(code==KeyEvent.VK_T){
            if(!kh.checkDrawTime){
                kh.checkDrawTime=true;
            }else{
                kh.checkDrawTime=false;
            }
        }
    }
}