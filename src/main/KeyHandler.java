package main;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Sounds.SoundPool;

public class KeyHandler implements KeyListener{
    public boolean upPressed , downPressed, leftPressed , rightPressed,enterPressed, shootKeyPressed;
    boolean checkDrawTime;
    GamePanel gp;
    public SoundPool inventoryPool = new SoundPool(getClass().getResource("/sounds/cursor.wav"), 4);


    public KeyHandler(GamePanel gp){
        this.gp=gp;
    }
  
    @Override
public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    switch(gp.gameState) {
        case GamePanel.titleState:
            handleTitleState(code);
            break;
        case GamePanel.playState:
            handlePlayState(code);
            break;
        case GamePanel.pauseState:
            handlePauseState(code);
            break;
        case GamePanel.characterState:
            handleCharacterState(code);
            break;
        case GamePanel.dialogueState:
            handleDialogueState(code);
            break;
            case GamePanel.OptionsState:
            optionsState(code);
            break;
        case GamePanel.gameOverState:
        handleGameOverState(code);
        default:
            // Optionally handle unknown state
            break;
    }
}

// ----- State Handlers -----

private void handleTitleState(int code) {
    if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
        gp.ui.commandNum--;
        inventoryPool.play();
        if(gp.ui.commandNum < 0) gp.ui.commandNum = 2;
    }
    if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
        gp.ui.commandNum++;
        inventoryPool.play();
        if(gp.ui.commandNum > 2) gp.ui.commandNum = 0;
    }
    if(code == KeyEvent.VK_ENTER) {
        switch(gp.ui.commandNum) {
            case 0: // NEW GAME
                gp.stopMusic();
                gp.playMusic(0);
                gp.gameState = GamePanel.playState;
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


private void handleGameOverState(int code){
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
        gp.gameState=GamePanel.playState;
        gp.retry();
    }else if (gp.ui.commandNum==1){
        gp.gameState=GamePanel.titleState;
        gp.stopMusic();
        gp.playMusic(5);
        gp.restart();
    }
}


}
private void handlePlayState(int code) {
    if(code == KeyEvent.VK_W) upPressed = true;
    if(code == KeyEvent.VK_S) downPressed = true;
    if(code == KeyEvent.VK_A) leftPressed = true;
    if(code == KeyEvent.VK_D) rightPressed = true;

    if(code == KeyEvent.VK_P) {
        gp.gameState = GamePanel.pauseState;
        gp.stopMusic();
        gp.playMusic(4);
    }
    if(code == KeyEvent.VK_C) {
        gp.gameState = GamePanel.characterState;
    }
    if(code == KeyEvent.VK_ENTER)  {
        enterPressed = true;
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
        shootKeyPressed=true;
    }
    if(code==KeyEvent.VK_ESCAPE){
        gp.gameState=GamePanel.OptionsState;
    }
    
}

private void handlePauseState(int code) {
    if(code == KeyEvent.VK_P) {
        gp.gameState = GamePanel.playState;
        gp.stopMusic();
        gp.playMusic(0);
    }
}

private void handleCharacterState(int code) {
    if(code == KeyEvent.VK_C) {
        gp.gameState = GamePanel.playState;
        inventoryPool.play();
        
    }
    if(code==KeyEvent.VK_W){
        if(gp.ui.slotRow!=0){
            gp.ui.slotRow--;
            inventoryPool.play();
        }
        
        
    }
    if(code==KeyEvent.VK_A){
        if(gp.ui.slotCol!=0){
        gp.ui.slotCol--;
        inventoryPool.play();
    }
    }
    if(code==KeyEvent.VK_S){
        if(gp.ui.slotRow!=3){
        gp.ui.slotRow++;
        inventoryPool.play();
       
    }}
        if(code==KeyEvent.VK_D){
            if(gp.ui.slotCol!=4){
            gp.ui.slotCol++;
            inventoryPool.play();
        }}
        if(code==KeyEvent.VK_ENTER){
           gp.player.selectItem();
        }

}

private void handleDialogueState(int code) {
    if(code == KeyEvent.VK_ENTER) {
        gp.gameState = GamePanel.playState;
    }
}


public void optionsState(int code){
    if(code==KeyEvent.VK_ESCAPE){
        gp.gameState=GamePanel.playState;
    }
    if(code ==KeyEvent.VK_ENTER){
        enterPressed=true;
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
            if(gp.ui.commandNum==1&& gp.music.volumeScale>0){
                gp.music.volumeScale--;
                gp.music.checkVolme();
                gp.playSE(10);
                gp.conf.SaveConfig();
            }
            if(gp.ui.commandNum==2&& gp.se.volumeScale>0){
                gp.se.volumeScale--;
                
                gp.playSE(10);
                gp.conf.SaveConfig();
            }
        }
    }
    if(code==KeyEvent.VK_D){
        if(gp.ui.subState==0){
            if(gp.ui.commandNum==1&& gp.music.volumeScale<5){
                gp.music.volumeScale++;
                gp.music.checkVolme();
                gp.playSE(10);
                gp.conf.SaveConfig();
            }
            if(gp.ui.commandNum==2&& gp.se.volumeScale<5){
                gp.se.volumeScale++;
            
                gp.playSE(10);
                gp.conf.SaveConfig();
            }
        }
    }
}



    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_W){

            upPressed=false;

        }
        if(code==KeyEvent.VK_S){

            downPressed = false;

        }
        if(code==KeyEvent.VK_A){
            leftPressed=false;

        }
        if(code==KeyEvent.VK_D){

            rightPressed = false;

        }
        if(code==KeyEvent.VK_T){
            if(checkDrawTime==false){
                checkDrawTime=true;
            }else if(checkDrawTime==true){
                checkDrawTime=false;
            }
        }
        if(code==KeyEvent.VK_F){
            shootKeyPressed=false;
        }
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }
    
}
