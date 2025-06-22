package State;

import main.GamePanel;
import main.KeyHandler;
import java.awt.event.KeyEvent;

public class OptionsState implements GameState {
    GamePanel gp;
    KeyHandler kh;

    public OptionsState(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.kh = kh;
    }

    @Override
    public void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_ESCAPE){
            gp.setGameState(gp.playState);
        }
        if(code ==KeyEvent.VK_ENTER){
            kh.enterPressed=true;
        }
        int maxCommandNum =0;
        switch (gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 1: maxCommandNum = 0; break; // Fullscreen notification
            case 2: maxCommandNum = 0; break; // Control screen
            case 3: maxCommandNum = 1; break;
        }
        if(code==KeyEvent.VK_W){
            gp.playSE(10);
            gp.ui.commandNum--;
            if(gp.ui.commandNum<0){
                gp.ui.commandNum=maxCommandNum;
            }
        }
        if(code==KeyEvent.VK_S){
            gp.playSE(10);
            gp.ui.commandNum++;
            if(gp.ui.commandNum>maxCommandNum){
                gp.ui.commandNum=0;
            }
        }
        if(code==KeyEvent.VK_A){
            if(gp.ui.subState==0){
                if(gp.ui.commandNum==1&& gp.soundManager.getMusicVolumeScale()>0){
                    System.out.println("Decreasing MUSIC volume");
                    gp.conf.setMusicVolume(gp.soundManager.getMusicVolumeScale() - 1); // CHANGED LINE
                    gp.playSE(10); // Play AFTER volume change
                    gp.conf.SaveConfig();
                }
                else if(gp.ui.commandNum==2 && gp.soundManager.getSEVolumeScale()>0){
                    gp.conf.setSEVolume(gp.soundManager.getSEVolumeScale() - 1); // CHANGED LINE
                    gp.playSE(10); // Play AFTER volume change
                    gp.conf.SaveConfig();
                }
            }
        }
        if(code==KeyEvent.VK_D){
            if(gp.ui.subState==0){
                if(gp.ui.commandNum==1 && gp.soundManager.getMusicVolumeScale()<5){
                    System.out.println("Increasing MUSIC volume");
                    gp.conf.setMusicVolume(gp.soundManager.getMusicVolumeScale() + 1); // CHANGED LINE
                    gp.playSE(10); // Play AFTER volume change
                    gp.conf.SaveConfig();
                }
                else  if(gp.ui.commandNum==2 && gp.soundManager.getSEVolumeScale()<5){
                    gp.conf.setSEVolume(gp.soundManager.getSEVolumeScale() + 1); // CHANGED LINE
                    gp.playSE(10); // Play AFTER volume change
                    gp.conf.SaveConfig();
                }
            }
        }
    }

    @Override
    public void handleKeyReleased(KeyEvent e) {}
}