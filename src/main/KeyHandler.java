package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shootKeyPressed;
    public boolean checkDrawTime;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (gp.currentState != null) {
            gp.currentState.handleKeyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (gp.currentState != null) {
            gp.currentState.handleKeyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }
    public void resetKeys() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        enterPressed = false;
        shootKeyPressed = false;
    }
}