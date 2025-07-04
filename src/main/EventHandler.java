package main;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;
      
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0, row = 0, col = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 48;
            eventRect[map][col][row].height = 48;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
            if(row == gp.maxWorldRow){
                row = 0;
                map++;
            }
        }
    }
    
    public void checkEvent() {
                int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }
        
        if(canTouchEvent) {
            if(hit(0,27, 14, "any")) { damagePit(); }
            else if(hit(0,22, 6, "any")) { healingPool(); }
            else if(hit(0,7, 39, "any")) { teleport(1, 12, 13); }
            else if(hit(1,8, 8, "any")) { teleport(0, 8, 39); }
            else if(hit(1,10, 39, "up")) { teleport(2, 12, 13); }
            else if(hit(2,8, 11, "any")) { teleport(1, 10, 40); }
        }
    }
    
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].eventRectDefaultY;
            
            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if(gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    
    public void damagePit() {
        gp.setGameState(gp.dialogueState);
        gp.playSE(7);
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }
    
    public void healingPool() {
        if(gp.keyH.enterPressed) {
            gp.setGameState(gp.dialogueState);
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "You drink the water.\nYour life and mana have been recovered.";
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.playSE(2);
            gp.keyH.enterPressed = false;
        }
    }
    
    public void teleport(int map, int col, int row) {
        gp.setGameState(gp.dialogueState);
        gp.ui.currentDialogue = "Teleport!";
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSE(6);
    }
}