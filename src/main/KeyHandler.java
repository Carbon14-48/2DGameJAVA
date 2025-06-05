package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    public boolean upPressed , downPressed, leftPressed , rightPressed,enterPressed;
    boolean checkDrawTime;
    GamePanel gp;
    


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
        default:
            // Optionally handle unknown state
            break;
    }
}

// ----- State Handlers -----

private void handleTitleState(int code) {
    if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
        gp.ui.commandNum--;
        if(gp.ui.commandNum < 0) gp.ui.commandNum = 2;
    }
    if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
        gp.ui.commandNum++;
        if(gp.ui.commandNum > 2) gp.ui.commandNum = 0;
    }
    if(code == KeyEvent.VK_ENTER) {
        switch(gp.ui.commandNum) {
            case 0: // NEW GAME
                gp.stopMusic();
                gp.playMusic(0);
                gp.gameState = GamePanel.playState;
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
    }
}

private void handleDialogueState(int code) {
    if(code == KeyEvent.VK_ENTER) {
        gp.gameState = GamePanel.playState;
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
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }
    
}
