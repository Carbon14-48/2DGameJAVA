package main;

import java.awt.Rectangle;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect ;
    int eventRectDefaultX, eventRectDefaultY;
EventHandler ( GamePanel gp){
    this.gp=gp;
    eventRect = new Rectangle();
    eventRect.x=23;
    eventRect.y=23;
    eventRect.width=42;
    eventRect.height=42;
    eventRectDefaultX=eventRect.x;
    eventRectDefaultY=eventRect.y;

}

public void checkEvent(){
    if (hit( 28, 14, "right")==true) damagePit(GamePanel.dialogueState); 
    if(hit(23,6,"down")==true) healingPool(GamePanel.dialogueState);
    if(hit(7,40,"down")==true) teleport(GamePanel.dialogueState);


    
}

public void teleport(int gameState){

    gp.gameState=gameState;
    gp.playSE(6);
    gp.ui.currentDialogue="Teleport";
   
    gp.player.worldX=gp.tileSize*37;
    gp.player.worldY=gp.tileSize*10;

}
public boolean hit(int eventCol, int eventRow, String reqDirection) {
    
    boolean hit = false;
    gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
    gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
    
    eventRect.x = eventCol * gp.tileSize + eventRect.x;
    eventRect.y = eventRow * gp.tileSize + eventRect.y;

    if (gp.player.solidArea.intersects(eventRect)) {
       
        if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
          
            hit = true;
        } else {
          
        }
    }

    gp.player.solidArea.x = gp.player.solidAreaDefaultX;
    gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    eventRect.x = eventRectDefaultX;
    eventRect.y = eventRectDefaultY;
    
    return hit;
}
//events

public void damagePit(int gameState){
    
    gp.gameState=gameState;
    gp.playSE(7);
    gp.ui.currentDialogue="You stepped into a mine";
    gp.player.life-=1;

}

public void healingPool(int gameState){
    System.out.println("healing");
    if(gp.keyH.enterPressed==true){
        
        gp.player.attackCanceled=true;
        gp.ui.currentDialogue="Drinking healing water \n Life has been recovered ";
        gp.gameState=gameState;
        gp.player.life=gp.player.maxLife;
        gp.playSE(8);
        gp.aSetter.setMonster();
    }
    
}
    
}