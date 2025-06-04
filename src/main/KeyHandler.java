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
            //play state
            if(gp.gameState == gp.titleState) {
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.ui.commandNum--; // Decrease commandNum
                    if(gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 2; // Wrap to last option (QUIT)
                    }
                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.ui.commandNum++; // Increase commandNum
                    if(gp.ui.commandNum > 2) {
                        gp.ui.commandNum = 0; // Wrap to first option (NEW GAME)
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.commandNum == 0) { // NEW GAME
                        gp.gameState = gp.playState;
                        gp.stopMusic();
                        gp.playMusic(0);
                        gp.gameState=gp.playState;
                    }
                    if(gp.ui.commandNum == 1) { // LOAD GAME
                        System.out.println("Load Game selected");
                    }
                    if(gp.ui.commandNum == 2) { // QUIT
                        System.exit(0);
                    }
                }
            }



        if(gp.gameState==gp.playState){

            if(code==KeyEvent.VK_W){

                upPressed=true;
    
            }
            if(code==KeyEvent.VK_S){
    
                downPressed = true;
    
            }
            if(code==KeyEvent.VK_A){
                leftPressed=true;
    
            }
            if(code==KeyEvent.VK_D){
    
                rightPressed = true;
    
            }
            if(code==KeyEvent.VK_P){
    
                if(gp.gameState==gp.playState){
                    gp.gameState=gp.pauseState;
                }
    
            }
            if(code==KeyEvent.VK_ENTER)  {
                enterPressed=true;
               } 
        } else if(gp.gameState==gp.pauseState){ //pause state
            if(code==KeyEvent.VK_P){
    
                
                    gp.gameState=gp.playState;
                }
        }
        if(gp.gameState==gp.dialogueState){
       if(code==KeyEvent.VK_ENTER)  {
        gp.gameState=gp.playState;
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
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }
    
}
